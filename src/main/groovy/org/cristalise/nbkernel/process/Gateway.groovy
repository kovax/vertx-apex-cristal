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
package org.cristalise.nbkernel.process;

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import io.vertx.core.AsyncResult
import io.vertx.core.DeploymentOptions
import io.vertx.core.Vertx
import io.vertx.core.json.JsonObject
import io.vertx.ext.mongo.MongoClient

@Slf4j
@CompileStatic
public class Gateway {

    MongoClient mongo = null;

    /**
     * 
     * @param vertx
     * @param config
     */
    public void createMongo(Vertx vertx, JsonObject config) {
        mongo = MongoClient.createShared(vertx, config, "cise-mongo");
    }

    /**
     * 
     */
    public void destroyMongo() {
        mongo.close()
    }

    /**
     * Generic method to deploy a service using its name
     * 
     * @param name name of the service
     * @param config 
     */
    private static void deployService(String name, JsonObject config) {
        DeploymentOptions options = new DeploymentOptions().setConfig(config);

        Vertx.vertx().deployVerticle(name, options) { AsyncResult<String> result ->
            if (result.succeeded()) {
                log.debug("Deployed - " + name);
            }
            else {
                log.error("Failed to deploy - " + name);
            }
        };
    }
}
