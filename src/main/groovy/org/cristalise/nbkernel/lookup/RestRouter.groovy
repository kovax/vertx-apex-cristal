package org.cristalise.nbkernel.lookup;

import groovy.transform.CompileStatic;
import groovy.util.logging.Slf4j;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.apex.Router;
import io.vertx.ext.apex.RoutingContext;
import io.vertx.ext.apex.handler.BodyHandler;
import io.vertx.ext.mongo.MongoService;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import org.cristalise.nbkernel.process.Gateway;

@CompileStatic
@Slf4j
class RestRouter {
    
    private MongoService mongo = null

    RestRouter() {
    }

    public Router init() {
        mongo = Gateway.getMongoServiceProxy(null)
 
		Router router = Router.router(Vertx.vertx());

        router.route().handler(BodyHandler.create())

        //apex uses handlers in the order they are added to the router
        router.get("/entity").handler(this.&getEntityList)
        router.get("/entity/:uuid").handler(this.&getEntityProps)
        router.get("/entity/:uuid/:path").handler(this.&getEntityData)
        router.getWithRegex("/agent/.*").handler(this.&getAgentPath)
        router.getWithRegex("/.*").handler(this.&getDomainPath)

        return router
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
        }
        else {
//            def proxy = Gateway.getProxyManager().getProxy(new ItemPath(uuid))

            if (true /*proxy*/) {
                try {
                    response.putHeader("content-type", "application/xml").end("Here comes the data")
                }
                catch (Exception e) {
                    log.error "Could not create response", e
                    response.end(e.message)
                    sendError(404, response)
                }
            }
            else {
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
    
    
    private void sendError(int statusCode, HttpServerResponse response) {
        response.setStatusCode(statusCode).end()
    }
}
