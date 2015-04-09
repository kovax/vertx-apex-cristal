package org.cristalise.nbkernel.process;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Gateway {

    public static void deployMongo(final Future<Void> startResult, JsonObject config) {
        deployService("service:io.vertx.mongo-service", config, startResult);
    }

    public static MongoService getMongoProxy() {
        return MongoService.createEventBusProxy(Vertx.vertx(), "vertx.mongo");
    }


    private static void deployService(String name, JsonObject config, final Future<Void> startResult) {
        DeploymentOptions options = new DeploymentOptions().setConfig(config);

        Vertx.vertx().deployVerticle(name, options, result -> {
            if (result.succeeded()) {
                log.debug("Deployed - " + name);
                startResult.complete();
            }
            else {
                log.error("Failed to deploy - " + name);
                startResult.fail(result.cause());
            }
        });
    }

}
