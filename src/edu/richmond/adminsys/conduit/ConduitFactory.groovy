package edu.richmond.adminsys.conduit

import groovy.util.slurpersupport.GPathResult
import edu.richmond.adminsys.conduit.sources.OracleSqlStatementSource
import edu.richmond.adminsys.conduit.monitors.ConsoleMonitor

import static java.lang.Class.forName
/**
 * 
 * The ConduitFactory class constructs instances of Conduit objects when supplied with GPathConfig objects containing their configuration settings.
 * 
 * @author dbroome
 *
 */
class ConduitFactory {

    public static Conduit createConduit(GPathResult config) {
        Conduit conduit = new Conduit()

        try {

            GPathResult source = config.source
            ICSource sourceInstance = (ICSource) createInstance(source)
            conduit.setDataSource(sourceInstance)

            ArrayList<ICSink> sinks = new ArrayList<ICSink>()
            config.sink.each { sink ->
                ICSink sinkInstance = (ICSink) createInstance(sink)
                sinks.add(sinkInstance)
            }
            conduit.setDataSinks(sinks)

            config.monitor.each { monitor ->
                ICMonitor monitorInstance = (ICMonitor) createInstance(monitor)
                DefaultMessagePump.getInstance().addMonitor(monitorInstance)
            }

        }
        catch (ConfigurationException ce) {
            if ((config.@name.isEmpty()) || (config.@name.text().equals(""))) {
                throw ce
            } else {
                throw new ConfigurationException(config.@name.text(), ce.message)
            }
        }

        return conduit
    }

    private static ICFitting createInstance(GPathResult config) {
        Class clazz = Class.forName(config.@type)
        ICFitting fitting = (ICFitting) clazz.newInstance()
        fitting.setUp(config)
        return fitting
    }
}
