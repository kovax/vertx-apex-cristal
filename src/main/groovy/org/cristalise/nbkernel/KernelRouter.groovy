package org.cristalise.nbkernel;

import groovy.transform.CompileStatic;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.apex.Router;
import io.vertx.ext.apex.RoutingContext;
import io.vertx.ext.apex.handler.BodyHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import org.cristalise.kernel.lookup.ItemPath;
import org.cristalise.kernel.lookup.Path;
import org.cristalise.kernel.process.AbstractMain;
import org.cristalise.kernel.process.Gateway;
import org.cristalise.kernel.process.auth.Authenticator;
import org.cristalise.kernel.utils.Logger;

@CompileStatic
class KernelRouter extends AbstractVerticle {
    
    KernelRouter() {
    }

    @Override
    public void start() {
		Router router = Router.router(vertx);
        
        // read args and init Gateway
		String[] args = ['-logLevel', '8', '-config', 'src/test/conf/devClient.conf', '-connect', 'src/test/conf/devServer.clc']
		Gateway.init( AbstractMain.readC2KArgs(args) )

        // connect to LDAP as root
		Authenticator auth = Gateway.connect()

        //start console
        Logger.initConsole("ItemServer")

        //initialize the server objects
        Gateway.startServer(auth)

        router.route().handler(BodyHandler.create())

        //apex uses handlers in the order they are added to the router
        router.get("/entity").handler(this.&getEntityList)
        router.get("/entity/:uuid").handler(this.&getEntityProps)
        router.get("/entity/:uuid/:path").handler(this.&getEntityData)
        router.getWithRegex("/agent/.*").handler(this.&getAgentPath)
        router.getWithRegex("/.*").handler(this.&getDomainPath)

        Logger.msg(5, "KernelRouter::start() - complete.")

        vertx.createHttpServer().requestHandler(router.&accept).listen(8888)
    }


    private void getEntityList(RoutingContext routingContext) {
        HttpServerResponse response = routingContext.response()
        
    }

    
    private void getEntitiyProps(RoutingContext routingContext) {
		String uuid = routingContext.request().getParam("uuid")

        HttpServerResponse response = routingContext.response()

        queryData(uuid, "properties", response)
    }


    /**
     * 
     * @param routingContext
     */
    private void getEntityData(RoutingContext routingContext) {
        HttpServerResponse response = routingContext.response()

        String uuid = routingContext.request().getParam("uuid")
        String path = routingContext.request().getParam("path")
        
        queryData(uuid, path, response)
    }
    

    /**
     * 
     * @param uuid
     * @param path
     * @param response
     */
    private void queryData(String uuid, String path,  HttpServerResponse response) {
        if (!uuid) {
            sendError(400, response);
        } else {
            def proxy = Gateway.getProxyManager().getProxy(new ItemPath(uuid))

            if (proxy) {
                try {
                    response.putHeader("content-type", "application/xml").end(proxy.queryData(path))
                } catch (Exception e) {
                    Logger.error(e)
                    response.end(e.message)
                    sendError(404, response)
                }
            } else {
                sendError(404, response);
            }
        }
    }


    /**
     * 
     * @param routingContext
     */
    private void getDomainPath(RoutingContext routingContext) {
        
    }
    
    
    
    @Override
    public void stop() throws Exception {
        Gateway.close()
    }


    private void sendError(int statusCode, HttpServerResponse response) {
        response.setStatusCode(statusCode).end()
    }
    
    
    public static void main(String[] args) {
        Logger.addLogStream(System.out, 8)
        boolean clustered = false

        Consumer<Vertx> runner = { Vertx vertx ->
            vertx.deployVerticle(new KernelRouter());
        }

        if (clustered) {
            Vertx.clusteredVertx(new VertxOptions().setClustered(true))
            {
                if (it.succeeded()) { runner.accept(it.result()); }
                else                { it.cause().printStackTrace(); }
            }
        }
        else {
            runner.accept(Vertx.vertx());
        }
    }
}
