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

import java.util.UUID;

import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.NonFinal;

/**
 * Extends Path with sysKey and proper root
 **/
@Value @NonFinal
@EqualsAndHashCode(callSuper=true)
public class ItemPath extends Path {

    /**
     * 
     * @throws InvalidPathException
     */
    public ItemPath() throws InvalidPathException {
        this(UUID.randomUUID());
    }

    /**
     * 
     * @param uuid
     * @throws InvalidPathException
     */
    public ItemPath(UUID uuid) throws InvalidPathException {
        super(new String[] {uuid.toString()}, Type.ENTITY);
    }

    /**
     * 
     * @param p
     * @throws InvalidPathException
     */
    public ItemPath(String p) throws InvalidPathException {
        super(new String[] {p}, Type.ENTITY);
    }

    /**
     * @return the sysKey
     */
    public UUID getSysKey() {
        return UUID.fromString(path[0]);
    }

    /**
     * ItemPaths root in /entity
     */
    public String getRoot() {
        return "entity";
    }
}
