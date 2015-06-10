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

import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.NonFinal;

/**
 * Very simple extension to Path. Only copies constructors and defines root
 * 
 */
@Value  @NonFinal
@EqualsAndHashCode(callSuper = true)
public class DomainPath extends Path {

    /**
     * 
     */
    ItemPath entity;

    /**
     * 
     * @param p
     * @throws InvalidPathException
     */
    public DomainPath(String[] p) throws InvalidPathException {
        super(p, Type.CONTEXT);
        entity = null;
    }

    /**
     * 
     * @param p
     * @throws InvalidPathException
     */
    public DomainPath(String p) throws InvalidPathException {
        super(p, Type.CONTEXT);
        entity = null;
    }

    /**
     * 
     * @param p
     * @param e
     * @throws InvalidPathException
     */
    public DomainPath(String p, ItemPath e) throws InvalidPathException {
        super(p, Type.ENTITY);
        entity = e;
    }

    /**
     * the root of domain paths is 'domain' clearly
     */
    @Override
    public String getRoot() {
        return "domain";
    }

    /**
     * Retrieves the domkey of the path
     * 
     * @return the last path component;
     */
    public String getName() {
        return path[path.length - 1];
    }
}
