package edu.richmond.adminsys.conduit

/**
 * This class is the embodiment of a single data transformation. It is used by the framework to perform the transformation after the XML configuration has been parsed and all the configuration components have been created.
 *  
 * @author dbroome
 * 
 */

class Conduit {
    ICSource dataSource
    List<ICSink> dataSinks

    void setDataSource(ICSource source) {
        dataSource = source
    }

    void setDataSinks(List<ICSink> sinks) {
        dataSinks = sinks
    }

    void apply() {
        List data = dataSource.get(DefaultMessagePump.getInstance())
        for (ICSink sink in dataSinks) {
            sink.set(data, DefaultMessagePump.getInstance())
        }
    }

    void tearDown() {
        dataSource.tearDown()
        dataSinks.each { dataSink ->
            dataSink.tearDown()
        }
        DefaultMessagePump.getInstance().tearDown()
    }
}
