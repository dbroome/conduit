package edu.richmond.adminsys.conduit

import edu.richmond.adminsys.conduit.ICMonitor
import edu.richmond.adminsys.conduit.ICFitting
import groovy.util.slurpersupport.GPathResult
import edu.richmond.adminsys.conduit.ICMessagePump
/**
 * 
 * The DefaultMessagePump class is a simple implementation of the ICMessagePump interface.  It iterates through each monitor specified in a conduit configuration and calls its logEvent method for each event sent by a source or sink object.
 * 
 * @author dbroome
 *
 */
class DefaultMessagePump implements ICMessagePump {

    static DefaultMessagePump instance

    List<ICMonitor> monitors = []

	
    @Override
	void addMonitor(ICMonitor monitor) {
         monitors.add(monitor)
    }

    @Override
    void logEvent(ICFitting generator, Map event) {
        monitors.each { monitor -> monitor.logEvent(generator, event) }
    }

    @Override
    void tearDown() {
        monitors.each { monitor -> monitor.tearDown() }
    }

    static ICMessagePump getInstance() {
        if (instance == null) {
            instance = new DefaultMessagePump()
        }
        return instance
    }
}
