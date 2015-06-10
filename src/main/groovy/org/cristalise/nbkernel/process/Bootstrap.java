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

import io.vertx.core.Vertx;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.io.monitor.FileAlterationObserver;

@Slf4j
public class Bootstrap {
    Vertx vertx = null;
    
    private static final long defaultInterval = 5000;
    private static final String defaultModuleDir = "src/main/cise-app";

    FileAlterationObserver directoryObserver = null;
    long timerInterval = -1;
    long timerID = -1;

    Bootstrap(Vertx v) throws Exception {
        vertx = v;
        timerInterval = defaultInterval;
        init(defaultModuleDir);
    }

    Bootstrap(long interval, String moduleDir) throws Exception {
        timerInterval = interval;
        init(moduleDir);
    }

    public void init(String directory) throws Exception {
       log.debug("init directory:"+directory);
        
        directoryObserver = new FileAlterationObserver(directory);

        directoryObserver.addListener(new BootstrapChangeListener());

        directoryObserver.initialize();

        timerID = vertx.setPeriodic(timerInterval, id -> {
            directoryObserver.checkAndNotify();
        });

        log.debug("Bootstrap timer was started ID:" + timerID);
    }
    
    public void destroy() throws Exception {
        directoryObserver.destroy();
        if(timerID != -1) vertx.cancelTimer(timerID);
    }
}
