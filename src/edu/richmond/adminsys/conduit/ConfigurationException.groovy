package edu.richmond.adminsys.conduit

/**
 * A ConfigurationException is thrown by the framework if there is a problem reading the XML configuration file or setting properties on source, sink, or monitor objects.
 * 
 * @author dbroome
 *
 */

class ConfigurationException extends ConduitException {

    public ConfigurationException(String message) {
        super(message)
    }

    public ConfigurationException(String configName, String message) {
        super("Error in configuration ${configName}: ${message}")
    }

}
