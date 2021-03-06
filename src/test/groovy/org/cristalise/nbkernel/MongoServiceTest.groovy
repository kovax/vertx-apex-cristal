package org.cristalise.nbkernel

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import io.vertx.core.AsyncResult
import io.vertx.core.Vertx

@CompileStatic
@Slf4j
class MongoServiceTest {

    public static void main(String[] args) {
        Closure runner = { Vertx vertx ->
            try {
                vertx.deployVerticle("service:io.vertx.mongo-service") { AsyncResult<String> result ->
//                vertx.deployVerticle(new MongoServiceVerticle()) { AsyncResult<String> result ->
                    if (result.succeeded()) {
                        log.info("Mongo service was started");
                    }
                    else {
                        log.error("FAILED to start Mongo service", result.cause());
                        Vertx.vertx().close()
                        log.info("Vertx closed");
                    }
                }
            }
            catch (Exception e) {
                log.error("Exception to start Mongo service", e);
                Vertx.vertx().close()
                log.info("Vertx closed");
            }
        }

        runner(Vertx.vertx())
    }
}
