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
package org.cristalise.nbkernel.lookup.impl;

import java.util.Iterator;

import org.cristalise.nbkernel.lookup.AgentPath;
import org.cristalise.nbkernel.lookup.DomainPath;
import org.cristalise.nbkernel.lookup.InvalidItemPathException;
import org.cristalise.nbkernel.lookup.ItemPath;
import org.cristalise.nbkernel.lookup.Lookup;
import org.cristalise.nbkernel.lookup.ObjectNotFoundException;
import org.cristalise.nbkernel.lookup.Path;
import org.cristalise.nbkernel.lookup.RolePath;

/**
 * @author kovax
 *
 */
public class MongoLookup implements Lookup {

    /**
     * 
     */
    public MongoLookup() {
        // TODO Auto-generated constructor stub
    }

    /* (non-Javadoc)
     * @see org.cristalise.nbkernel.lookup.Lookup#open()
     */
    @Override
    public void open() {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see org.cristalise.nbkernel.lookup.Lookup#close()
     */
    @Override
    public void close() {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see org.cristalise.nbkernel.lookup.Lookup#getItemPath(java.lang.String)
     */
    @Override
    public ItemPath getItemPath(String sysKey) throws InvalidItemPathException,
            ObjectNotFoundException {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see org.cristalise.nbkernel.lookup.Lookup#resolvePath(org.cristalise.nbkernel.lookup.DomainPath)
     */
    @Override
    public ItemPath resolvePath(DomainPath domainPath)
            throws InvalidItemPathException, ObjectNotFoundException {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see org.cristalise.nbkernel.lookup.Lookup#exists(org.cristalise.nbkernel.lookup.Path)
     */
    @Override
    public boolean exists(Path path) {
        // TODO Auto-generated method stub
        return false;
    }

    /* (non-Javadoc)
     * @see org.cristalise.nbkernel.lookup.Lookup#getChildren(org.cristalise.nbkernel.lookup.Path)
     */
    @Override
    public Iterator<Path> getChildren(Path path) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see org.cristalise.nbkernel.lookup.Lookup#search(org.cristalise.nbkernel.lookup.Path, java.lang.String)
     */
    @Override
    public Iterator<Path> search(Path start, String name) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see org.cristalise.nbkernel.lookup.Lookup#searchAliases(org.cristalise.nbkernel.lookup.ItemPath)
     */
    @Override
    public Iterator<Path> searchAliases(ItemPath itemPath) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see org.cristalise.nbkernel.lookup.Lookup#getAgentPath(java.lang.String)
     */
    @Override
    public AgentPath getAgentPath(String agentName)
            throws ObjectNotFoundException {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see org.cristalise.nbkernel.lookup.Lookup#getRolePath(java.lang.String)
     */
    @Override
    public RolePath getRolePath(String roleName) throws ObjectNotFoundException {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see org.cristalise.nbkernel.lookup.Lookup#getAgents(org.cristalise.nbkernel.lookup.RolePath)
     */
    @Override
    public AgentPath[] getAgents(RolePath rolePath)
            throws ObjectNotFoundException {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see org.cristalise.nbkernel.lookup.Lookup#getRoles(org.cristalise.nbkernel.lookup.AgentPath)
     */
    @Override
    public RolePath[] getRoles(AgentPath agentPath) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see org.cristalise.nbkernel.lookup.Lookup#hasRole(org.cristalise.nbkernel.lookup.AgentPath, org.cristalise.nbkernel.lookup.RolePath)
     */
    @Override
    public boolean hasRole(AgentPath agentPath, RolePath role) {
        // TODO Auto-generated method stub
        return false;
    }

    /* (non-Javadoc)
     * @see org.cristalise.nbkernel.lookup.Lookup#getAgentName(org.cristalise.nbkernel.lookup.AgentPath)
     */
    @Override
    public String getAgentName(AgentPath agentPath)
            throws ObjectNotFoundException {
        // TODO Auto-generated method stub
        return null;
    }

}
