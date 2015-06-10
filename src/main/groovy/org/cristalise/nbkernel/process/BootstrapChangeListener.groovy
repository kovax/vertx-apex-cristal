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
package org.cristalise.nbkernel.process;

import groovy.util.logging.Slf4j

import org.apache.commons.io.monitor.FileAlterationListener
import org.apache.commons.io.monitor.FileAlterationObserver

//@CompileStatic
@Slf4j
public class BootstrapChangeListener implements FileAlterationListener {

    private static String getStructure() {
        def json = new groovy.json.JsonBuilder()
        json {
            entitiy : {
                item  : []
                agent : []
                role  : []
            }
            property     : []
            lifecycle    : []
            auditTrail   : []
            outcome      : []
            stateMachine : []
            viewPoint    : []
        }

        return json.toString()
    }

    @Override
    public void onStart(FileAlterationObserver observer) { /*log.trace("Observer Start - do nothing");*/ }

    @Override
    public void onStop(FileAlterationObserver observer) { /*log.trace("Observer Stop - do nothing");*/ }

    @Override
    public void onDirectoryCreate(File directory) {
        log.debug("Directory Create : " + directory.getName());
    }

    @Override
    public void onDirectoryChange(File directory) {
        log.debug("Directory Change : " + directory.getName());

    }

    @Override
    public void onDirectoryDelete(File directory) {
        log.debug("Directory Delete : " + directory.getName());
    }

    @Override
    public void onFileCreate(File file) {
        log.debug("File Create : " + file.getName());
        log.debug(getStructure())
    }

    @Override
    public void onFileChange(File file) {
        log.debug("File Change : " + file.getName());

    }

    @Override
    public void onFileDelete(File file) {
        log.debug("File Delete : " + file.getName());

    }


}
