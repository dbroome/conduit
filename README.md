Conduit
=======

#Description

Conduit is a Groovy application framework that performs data transformation jobs by reading data from data sources and writing them to data sinks. Data sources and data sinks send messages during this process that are collected by monitors. Data sources are classes that implement the ICSource interface. Data Sinks are classes that implement the ICSink interface. A class that implements the ICMessagePump interface collects events from the sources and sinks and distributes them to one or more monitors, which are classes that implement the ICMonitor interface.

Each transformation job is a single conduit and is described in an XML configuration file. A configuration file consists of a conduit root element with one source child node, one sink child node, and one or more monitor child nodes. When the Conduit application starts it looks for one or more XML configuration files and executes the jobs that they describe.

The Main class is the entry point for the application and is responsible for initializing the source, sink, and monitor components from the information in the XML configuration file and executing the data transformation job. It parses the XML configuration file for the conduit using the XMLSlurper parser and passes the configuration element for each component as a GPathResult object into its setUp method. It then instantiates the message pump and adds each monitor to the list of components that will receive events sent by the sources and sinks. After initializing the source, sink, and montiors it calls the get method from the source component to retrieve a list of rows representing the data from the source. It passes this list into the set method of the sink component which performs a transformation on the data and writes them to the data sink. When the transformation is complete, it calss the tearDown method for each source, sink, and monitor component.

#Operation

Conduit expects the path of its XML configuration file to be passed as the first parameter on the command line:

    java -jar Conduit.jar configuration.xml

Conduit will run through the transformation described in the XML configuration file and exit. If it does not detect an error during the transformation it will exit with a code of 0, otherwise it will exit with a code of -1.

If Conduit does not find any command line parameters it expects to run in batch mode. In batch mode, conduit looks for a file named conduits.xml on the path in which it was started. It expects this file to contain a root element named conduits with one or more child elements each named conduit. The text of each of these elements contains a path to a single XML configuration file. Conduit will load each configuration and process them in the order in which they appear in the file. Conduit will exit with code of 0 if it does not detect an error in any of the transformations, otherwise it will exit with a code of -1.

#Attribution

This project contains 

SQLite JDBC, developed by [Taro L. Saito](http://www.xerial.org/leo)
