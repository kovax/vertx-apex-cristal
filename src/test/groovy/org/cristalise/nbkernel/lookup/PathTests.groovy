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
package org.cristalise.nbkernel.lookup

import groovy.json.JsonBuilder
import groovy.json.JsonSlurper

import org.cristalise.nbkernel.lookup.Path.Type

import spock.lang.Specification

/**
 * 
 */
class PathTests extends Specification {
    
    def "ItemPath represents an ENTITY"() {
        when: "an ItemPath created"
        def uuid = new UUID(0,1);
        def ip = new ItemPath(uuid)

        then: "the path is an ENTITY"
        ip.type == Type.ENTITY
        
        and: "the sysKey is set"
        ip.getSysKey() != null
        
        and: "the string form of the path starts with /entity"
        ip.getString().startsWith("/entity")

        and: "ends with the uuid"
        ip.getString().endsWith(uuid.toString())
    }

    def "AgentPath represents an ENTITY"() {
        when: "an AgentPath created"
        def uuid = new UUID(0,1);
        def ap = new AgentPath(uuid)

        then: "the path is a ENTITY"
        ap.type == Type.ENTITY
        
        and: "the sysKey is set"
        ap.getSysKey() != null

        and: "the string form of the path starts with /entity"
        ap.getString().startsWith("/entity")

        and: "ends with the uuid"
        ap.getString().endsWith(uuid.toString())
    }

    def "DomainPath can represent a CONTEXT"() {
        when: "an DomainPath created with no inputs"
        def dp = new DomainPath("/cristal/barel/6L")

        then: "the path is a CONTEXT"
        dp.getType() == Type.CONTEXT
        
        and: "the entity is null"
        dp.getEntity() == null
        
        and: "the string form of the path starts with /domain"
        dp.getString() == "/domain/cristal/barel/6L" 
    }

    def "DomainPath can represent an ENTITY"() {
        when: "an DomainPath created with no inputs"
        def dp = new DomainPath("/cristal/barel/6L", new ItemPath())

        then: "the path is a ENTITY"
        dp.getType() == Type.ENTITY
        
        and: "the entity is set"
        dp.getEntity() != null

        and: "the string form of the path starts with /domain"
        dp.getString() == "/domain/cristal/barel/6L" 
    }

    def "Path can be JSON marshalled"() {
        when: "an ItemPath created"
        def uuid = new UUID(0,1);
        def ip = new ItemPath(uuid)

        then: "it can be mconverted to json"
        def json = new JsonBuilder(ip).toPrettyString()
        println json
        def ip1 = new ItemPath( new JsonSlurper().parseText(json) )

    }
}
