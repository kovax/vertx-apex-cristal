package org.cristalise.nbkernel.process;

import lombok.extern.slf4j.Slf4j;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoService;

@Slf4j
class Gateway {

    public static MongoService getMongoServiceProxy(JsonObject config) {
        deployService("service:io.vertx.mongo-service", config);

        return MongoService.createEventBusProxy(Vertx.vertx(), "vertx.mongo");
    }

    private static void deployService(String name, JsonObject config) {
        DeploymentOptions options = new DeploymentOptions().setConfig(config);

        Vertx.vertx().deployVerticle(name, options, result -> {
            if (result.succeeded()) {
                log.debug("Deployed - " + name);
            }
            else {
                log.error("Failed to deploy - " + name);
                throw new RuntimeException(result.cause());
            }
        });
    }

}
