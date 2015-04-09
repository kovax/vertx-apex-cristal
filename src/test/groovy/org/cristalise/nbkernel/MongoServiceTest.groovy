package org.cristalise.nbkernel

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import io.vertx.core.AsyncResult
import io.vertx.core.Vertx

import java.util.function.Consumer

@CompileStatic
@Slf4j
class MongoServiceTest {

    public static void main(String[] args) {
        Consumer<Vertx> runner = {
            try {
                Vertx.vertx().deployVerticle("service:io.vertx.mongo-service") { AsyncResult<String> result ->
                    if (result.succeeded()) {
                        log.info("Mongo service was started");
                    }
                    else {
                        log.error("FAILED to start Mongo service", result.cause());
                        Vertx.vertx().close()
                    }
                }
            }
            catch (Exception e) {
                log.error("FAILED to start Mongo service", e);
                Vertx.vertx().close()
            }
        }

        runner.accept(Vertx.vertx())
    }
}
