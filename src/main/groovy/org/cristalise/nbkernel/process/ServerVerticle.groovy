/**
 * This file is part of the CRISTAL-iSE kernel.
 * Copyright (c) 2001-2014 The CRISTAL Consortium. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation; either version 3 of the License, or (at
 * your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; with out even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation,
 * Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.
 *
 * http://www.fsf.org/licensing/licenses/lgpl.html
 */
package org.cristalise.nbkernel.process

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import io.vertx.core.AbstractVerticle
import io.vertx.core.AsyncResult
import io.vertx.core.Future
import io.vertx.core.Vertx
import io.vertx.core.VertxOptions
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.Router

import org.cristalise.nbkernel.lookup.RestRouter

@CompileStatic
@Slf4j
public class ServerVerticle extends AbstractVerticle {

    private static Closure vertxRunner = { Vertx vertx ->
        try {
            vertx.deployVerticle(new ServerVerticle()) { AsyncResult<String> result ->
                if (result.succeeded()) {
                    log.info("CRISTAL-iSE Server was started");
                }
                else {
                    log.error("FAILED to start CRISTAL-iSE Server", result.cause());
                    vertx.close()
                }
            }
        }
        catch (Exception e) {
            log.error("FAILED to start CRISTAL-iSE Server", e);
            vertx.close()
        }
    }


    @Override
    public void start(Future<Void> startResult) throws Exception {
        new Bootstrap(vertx)
        
        try {
            Gateway.createMongo(vertx, mongoConfig())
            Router router = new RestRouter().init(vertx);

            vertx.createHttpServer().requestHandler(router.&accept).listen(8888);

            log.info("HTTP server was initialised and started");

            startResult.complete();
        }
        catch(Exception ex) {
            startResult.fail(ex);
        }
    }
    
    //FIXME: do proper mongo config
    protected JsonObject mongoConfig() {
        JsonObject config = new JsonObject();
        config.put("connection_string", "mongodb://localhost:27017");
        config.put("db_name", "cise-app-test");
        return config;
    }


    @Override
    public void stop() {
        log.info("Stopping CRISTAL-iSE Server")
        Gateway.destroyMongo()
        vertx.close()
    }


    public static void main(String[] args) {
        boolean clustered = false;

        if (clustered) {
            Vertx.clusteredVertx(new VertxOptions().setClustered(true)) { AsyncResult<Vertx> result ->
                if (result.succeeded()) {
                    log.info("Starting CRISTAL-iSE Cluster");
                    vertxRunner(result.result());
                }
                else {
                    log.error("FAILED to start CRISTAL-iSE Cluster", result.cause());
                    vertx.close()
                }
            }
        }
        else {
            vertxRunner(Vertx.vertx())
        }
    }
}
