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

import java.util.ArrayList;
import java.util.StringTokenizer;

import lombok.Value;
import lombok.experimental.NonFinal;

/**
 * 
 *
 */
@Value @NonFinal
public abstract class Path {
    private static final String delim = "/";
    private static final String rootSign = "/";

    public enum Type { UNKNOWN, CONTEXT, ENTITY }

    protected Type type;
    protected String[] path;

    /**
     * Creates a path with an arraylist of the path (big endian)
     * 
     * @param path
     * @param t
     * @throws InvalidPathException 
     */
    public Path(String[] p, Type t) throws InvalidPathException {
        if(p != null && p.length > 0 && p[0] != null && !"".equals(p[0])) {
            path = p;
            type = t;
        }
        else {
            throw new InvalidPathException("Path must not be empty or null");
        }
    }

    /**
     * Creates a path from a slash separated string (big endian)
     * 
     * @param path
     * @param t
     * @throws InvalidPathException 
     */
    public Path(String p, Type t) throws InvalidPathException {
        if (p != null && !"".equals(p)) {
            ArrayList<String> newPath = new ArrayList<String>();

            StringTokenizer tok = new StringTokenizer(p, delim);
            
            if (tok.hasMoreTokens()) {
                String first = tok.nextToken();
                
                if (!first.equals(getRoot())) newPath.add(first);

                while (tok.hasMoreTokens()) newPath.add(tok.nextToken());
            }

            path = newPath.toArray(new String[0]);
            type = t;
        }
        else {
            throw new InvalidPathException("Path string '"+p+"' must not be empty or null");
        }
    }

    /**
     * Create a path by appending a child string to an existing path
     * 
     * @param parent
     * @param child
     * @param t
     * @throws InvalidPathException 
     */
    public Path(Path parent, String child, Type t) throws InvalidPathException {
        if(parent != null && child != null && t != null) {
            int      newLength = parent.getPath().length +1;
            String[] newPath   = new String[newLength];
            newPath = parent.getPath().clone();
            newPath[newLength] = child;

            path = newPath;
            type = t;
        }
        else {
            throw new InvalidPathException("Parent or cild or type must not be empty or null");
        }
    }

    /**
     * Create a path by appending a child and inheriting the type
     * 
     * @param parent
     * @param child
     * @throws InvalidPathException 
     */
    public Path(Path parent, String child) throws InvalidPathException {
        this(parent, child, parent.getType());
    }

    /**
     * root is defined as 'domain' or 'entity' in subclasses
     * 
     * @return the root string
     */
    public abstract String getRoot();
    
    public String getString() {
        StringBuffer stringPathBuffer = new StringBuffer(rootSign).append(getRoot());

        for (String element : path) stringPathBuffer.append(delim).append(element);

        return stringPathBuffer.toString();
    }
}
