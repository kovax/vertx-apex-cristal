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

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import lombok.Value;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

/**
 * Extends ItemPath with name and pwd
 */
@Value
//@EqualsAndHashCode(callSuper = true)
public class AgentPath extends ItemPath {

    String agentName;
    //FIXME: replace pwd field with auth package
    String password;

    /**
     * 
     * @throws InvalidPathException
     */
    public AgentPath() throws InvalidPathException {
        this(UUID.randomUUID());
    }

    /**
     * 
     * @param uuid
     * @throws InvalidPathException
     */
    public AgentPath(UUID uuid) throws InvalidPathException  {
        super(uuid);
        agentName = null;
        password = null;
    }

    public AgentPath(String name, String pwd) throws InvalidPathException {
        super();
        agentName = name;
        password = pwd;
    }

    public AgentPath(UUID sysKey, String name, String pwd) throws InvalidPathException {
        super(sysKey);
        agentName = name;
        password = pwd;
    }

    public AgentPath(String p, String name, String pwd) throws InvalidPathException {
        super(p);
        agentName = name;
        password = pwd;
    }

    public RolePath[] getRoles() {
//        return Gateway.getLookup().getRoles(this);
        return null;
    }

    public boolean hasRole(RolePath role) {
//        return Gateway.getLookup().hasRole(this, role);
        return false;
    }

    public boolean hasRole(String role) {
        //return hasRole(Gateway.getLookup().getRolePath(role));
        return false;
    }

    public static String generateUserPassword(String pass, String algo) throws NoSuchAlgorithmException {
        MessageDigest sha = MessageDigest.getInstance(algo);
        sha.reset();
        sha.update(pass.getBytes());
        StringBuffer digest = new StringBuffer("{").append(algo).append("}");
        return digest.append(Base64.encode(sha.digest())).toString();
    }
}
