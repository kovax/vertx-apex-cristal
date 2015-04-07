package org.cristalise.nbkernel.process

import java.util.function.Consumer;

import org.cristalise.nbkernel.lookup.RestRouter;

import groovy.transform.CompileStatic;
import groovy.util.logging.Slf4j;
import io.vertx.core.AbstractVerticle
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.ext.apex.Router;

@CompileStatic
@Slf4j
class ServerVerticle extends AbstractVerticle {

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        Router router = RestRouter.init()
        startFuture.complete()
        vertx.createHttpServer().requestHandler(router.&accept).listen(8080)
    }

    @Override
    public void stop() {
        log.info("Stopping ServerVerticle")
    }

    public static void main(String[] args) {
        boolean clustered = false;

        Consumer<Vertx> runner = {
            Vertx.vertx().deployVerticle(new ServerVerticle());
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
