package org.cristalise.nbkernel.process;

import io.vertx.core.Vertx;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.io.monitor.FileAlterationObserver;

@Slf4j
public class Bootstrap {
    private static final long defaultInterval = 5000;
    private static final String defaultModuleDir = "src/main/cise-app";

    FileAlterationObserver directoryObserver = null;
    long timerInterval = -1;
    long timerID = -1;

    Bootstrap() throws Exception {
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

        timerID = Vertx.vertx().setPeriodic(timerInterval, id -> {
            directoryObserver.checkAndNotify();
        });

        log.debug("Bootstrap timer was started ID:" + timerID);
    }
    
    public void destroy() throws Exception {
        directoryObserver.destroy();
        if(timerID != -1) Vertx.vertx().cancelTimer(timerID);
    }
}
