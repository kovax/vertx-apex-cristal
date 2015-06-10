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

/**
 * 
 */
@Value
@EqualsAndHashCode(callSuper = true)
public class RolePath extends DomainPath {
    boolean hasJobList;

    /**
     * 
     * @throws InvalidPathException
     */
    public RolePath() throws InvalidPathException {
        super("agent");
        hasJobList = false;
    }

    /**
     * 
     * @param path
     * @param jobList
     * @throws InvalidPathException
     */
    public RolePath(String[] path, boolean jobList) throws InvalidPathException {
        super(path);
        hasJobList = jobList;
    }

    /**
     * @return Returns the hasJobList.
     */
    public boolean hasJobList() {
        return hasJobList;
    }
}