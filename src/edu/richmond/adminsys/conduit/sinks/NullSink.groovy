package edu.richmond.adminsys.conduit.sinks

import edu.richmond.adminsys.conduit.ICSink
import groovy.util.slurpersupport.GPathResult
import edu.richmond.adminsys.conduit.ICMessagePump

/**
 * The NullSink is a sink class intended to do nothing during a transformation. It is useful when you want to examine the behavior of a source component without actually writing the data anywhere.
 * 
 * @author dbroome
 *
 */

class NullSink implements ICSink {

    @Override
    void set(List data, ICMessagePump messagePump) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    void setUp(GPathResult config) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    void tearDown() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
