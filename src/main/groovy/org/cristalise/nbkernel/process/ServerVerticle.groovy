package org.cristalise.nbkernel.process

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import io.vertx.core.AbstractVerticle
import io.vertx.core.AsyncResult
import io.vertx.core.Future
import io.vertx.core.Vertx
import io.vertx.core.VertxOptions
import io.vertx.core.json.JsonObject
import io.vertx.ext.apex.Router

import java.util.function.Consumer

import org.cristalise.nbkernel.lookup.RestRouter

@CompileStatic
@Slf4j
public class ServerVerticle extends AbstractVerticle {

    @Override
    public void start(final Future<Void> startResult) throws Exception {
        //new Bootstrap()
        Gateway.deployMongo(startResult, mongoConfig())

        if(startResult.succeeded()) {
            Router router = new RestRouter().init();
            vertx.createHttpServer().requestHandler(router.&accept).listen(8080);

            log.info("HTTP server was initialised and started");
        }
    }
    
    //FIXME: do proper mongo config
    protected JsonObject mongoConfig() {
        JsonObject config = new JsonObject();
        config.put("connection_string", "mongodb://localhost:27018");
        config.put("db_name", "cise-app-test");
        return config;
      }

    @Override
    public void stop() {
        log.info("Stopping ServerVerticle")
    }

    public static void main(String[] args) {
        boolean clustered = false;

        Consumer<Vertx> runner = {
            try {
                Vertx.vertx().deployVerticle(new ServerVerticle()) {  AsyncResult<String> result ->
                    if (result.succeeded()) {
                        log.info("CRISTAL-iSE Server was started");
                    }
                    else {
                        log.error("FAILED to start CRISTAL-iSE Server", result.cause());
                        Vertx.vertx().close()
                    }
                }
            } 
            catch (Exception e) {
                log.error("FAILED to start CRISTAL-iSE Server", e);
                Vertx.vertx().close()
            }
        }

        if (clustered) {
            Vertx.clusteredVertx(new VertxOptions().setClustered(true)) {
                if (it.succeeded()) {
                    runner.accept(it.result());
                }
                else {
                    it.cause().printStackTrace();
                }
            }
        }
        else {
            runner.accept(Vertx.vertx())
        }
    }
}
