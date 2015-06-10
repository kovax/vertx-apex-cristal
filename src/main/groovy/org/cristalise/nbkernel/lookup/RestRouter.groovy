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

 package org.cristalise.nbkernel.lookup;

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import io.vertx.core.Vertx
import io.vertx.core.http.HttpServerResponse
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import io.vertx.ext.web.handler.BodyHandler

@CompileStatic
@Slf4j
public class RestRouter {

    RestRouter() {}

    public Router init(Vertx vertx) {
		Router router = Router.router(vertx);

        router.route().handler(BodyHandler.create())

        //apex uses handlers in the order they are added to the router
        router.get("/entity").handler(this.&getEntityList)
        router.get("/entity/:uuid").handler(this.&getEntityProps)
        router.get("/entity/:uuid/:path").handler(this.&getEntityData)
        router.getWithRegex("/agent/.*").handler(this.&getAgentPath)
        router.getWithRegex("/.*").handler(this.&getDomainPath)

        return router
    }


    private void getEntityList(RoutingContext routingContext) {
        HttpServerResponse response = routingContext.response()
        
    }

    
    private void getEntitiyProps(RoutingContext routingContext) {
		String uuid = routingContext.request().getParam("uuid")

        HttpServerResponse response = routingContext.response()

        queryData(uuid, "properties", response)
    }


    /**
     * 
     * @param routingContext
     */
    private void getEntityData(RoutingContext routingContext) {
        HttpServerResponse response = routingContext.response()

        String uuid = routingContext.request().getParam("uuid")
        String path = routingContext.request().getParam("path")
        
        queryData(uuid, path, response)
    }
    

    /**
     * 
     * @param uuid
     * @param path
     * @param response
     */
    private void queryData(String uuid, String path,  HttpServerResponse response) {
        if (!uuid) {
            sendError(400, response);
        }
        else {
//            def proxy = Gateway.getProxyManager().getProxy(new ItemPath(uuid))

            if (true /*proxy*/) {
                try {
                    response.putHeader("content-type", "application/xml").end("Here comes the data")
                }
                catch (Exception e) {
                    log.error "Could not create response", e
                    response.end(e.message)
                    sendError(404, response)
                }
            }
            else {
                sendError(404, response);
            }
        }
    }


    /**
     * 
     * @param routingContext
     */
    private void getDomainPath(RoutingContext routingContext) {
    }
    
    
    private void sendError(int statusCode, HttpServerResponse response) {
        response.setStatusCode(statusCode).end()
    }
}
