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
package org.cristalise.nbkernel;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;

@RunWith(VertxUnitRunner.class)
public class MongeServiceTestBase {

    private static MongodExecutable exe;

    protected static String getConnectionString() {
        return getProperty("connection_string");
    }

    protected static String getDatabaseName() {
        return getProperty("db_name");
    }

    protected static String getProperty(String name) {
        String s = System.getProperty(name);
        if (s != null) {
            s = s.trim();
            if (s.length() > 0) {
                return s;
            }
        }

        return null;
    }

    @BeforeClass
    public static void startMongo() throws Exception {
        if (getConnectionString() == null) {
            IMongodConfig config = new MongodConfigBuilder()
                    .version(Version.Main.PRODUCTION)
                    .net(new Net(27018, Network.localhostIsIPv6())).build();
            exe = MongodStarter.getDefaultInstance().prepare(config);
            exe.start();
        }
    }

    @AfterClass
    public static void stopMongo() {
        if (exe != null) {
            exe.stop();
        }
    }

    protected MongoClient mongoService;
    protected Vertx vertx;

    @Before
    public void setUp(TestContext context) throws Exception {
        vertx = Vertx.vertx();
        JsonObject config = getConfig();
        mongoService = MongoClient.createShared(vertx, config);
        dropCollections(context);
    }

    @After
    public void tearDown(TestContext context) throws Exception {
        mongoService.close();
        vertx.close(context.asyncAssertSuccess());
    }

    protected JsonObject getConfig() {
        JsonObject config = new JsonObject();
        String connectionString = getConnectionString();
        if (connectionString != null) {
            config.put("connection_string", connectionString);
        }
        else {
            config.put("connection_string", "mongodb://localhost:27018");
        }
        String databaseName = getDatabaseName();
        if (databaseName != null) {
            config.put("db_name", databaseName);
        }
        return config;
    }

    protected List<String> getOurCollections(List<String> colls) {
        List<String> ours = new ArrayList<>();
        for (String coll : colls) {
            if (coll.startsWith("ext-mongo")) {
                ours.add(coll);
            }
        }
        return ours;
    }
    
    protected void dropCollections(TestContext context) {
        Async async = context.async();
        // Drop all the collections in the db
        mongoService.getCollections(res -> {
            if (res.succeeded()) {
                List<String> toDrop = getOurCollections(res.result());
                int count = toDrop.size();
                if (!toDrop.isEmpty()) {
                    AtomicInteger collCount = new AtomicInteger();
                    for (String collection : toDrop) {
                        mongoService.dropCollection(collection, onSuccess(v -> {
                            if (collCount.incrementAndGet() == count) {
                                latch.countDown();
                            }
                        }));
                    }
                }
                async.complete();
            }
            else {
                context.fail(res.cause());
            }
        });
                
                
                
                onSuccess(list -> {
            
            List<String> toDrop = getOurCollections(list);
            int count = toDrop.size();
        }));
    }


    protected <T> Handler<AsyncResult<T>> onSuccess(Consumer<T> consumer) {
        return result -> {
            if (result.failed()) {
                result.cause().printStackTrace();
                // fail(result.cause().getMessage());
            }
            else {
                consumer.accept(result.result());
            }
        };
    }

    protected void dropCollectionsOLD(CountDownLatch latch) {
        // Drop all the collections in the db
        mongoService.getCollections(onSuccess(list -> {
            AtomicInteger collCount = new AtomicInteger();
            List<String> toDrop = getOurCollections(list);
            int count = toDrop.size();
            if (!toDrop.isEmpty()) {
                for (String collection : toDrop) {
                    mongoService.dropCollection(collection, onSuccess(v -> {
                        if (collCount.incrementAndGet() == count) {
                            latch.countDown();
                        }
                    }));
                }
            }
            else {
                latch.countDown();
            }
        }));
    }
}
