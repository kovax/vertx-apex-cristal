/**
 * This file is part of the CRISTAL-iSE kernel.
 * Copyright (c) 2001-2015 The CRISTAL Consortium. All rights reserved.
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
package org.cristalise.nbkernel

import groovy.transform.CompileStatic
import io.vertx.core.AsyncResult
import io.vertx.core.json.JsonObject
import io.vertx.rxjava.core.AbstractVerticle
import io.vertx.rxjava.ext.mongo.MongoClient
import rx.Observable

/**
 * @author kovax
 *
 */
@CompileStatic
public class RxJavaMongoGroovyTest extends AbstractVerticle {

    static final String collection = "users"

    @Override
    public void start() throws Exception {
        JsonObject config = new JsonObject().put("connection_string", "mongodb://localhost:27018").put("db_name", "my_DB")

        // Deploy an embedded mongo database so we can test against that
        vertx.deployVerticle("service:io.vertx.vertx-mongo-embedded-db") { AsyncResult<String> db -> 
            if (db.succeeded()) {
                // Create the client
                MongoClient mongo = MongoClient.createShared(vertx, config)

                // Documents to insert
                Observable<JsonObject> documents = Observable.just(
                        new JsonObject().put("username", "temporalfox").put("firstname", "Julien").put("password", "bilto"),
                        new JsonObject().put("username", "purplefox").  put("firstname", "Tim").   put("password", "wibble"));

                mongo.createCollectionObservable(collection).with {
                    // After collection is created we insert each document
                        flatMap() { Void v -> documents.flatMap() { JsonObject doc -> mongo.insertObservable(collection, doc) } }
                        subscribe (
                            //onNext
                            { Object id -> println("Inserted document id:$id") },
                            //onError
                            { Throwable error -> error.printStackTrace() },
                            //onComplete: Everything has been inserted now we can query mongo
                            { mongo.findObservable(collection, new JsonObject()).subscribe() { results -> println("Results: $results") } }
                        )
                }
            }
            else {
                System.out.println("Could not start mongo embedded")
                db.cause().printStackTrace()
            }
        };
    }
}