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
