package edu.richmond.adminsys.conduit
/**
 * The ICMessagePump interface is implemented by a class that distributes events from the source and sink objects to the monitor objects. The framework currently uses a class named DefaultMessagePump that implements this interface to perform this class.
 */
public interface ICMessagePump {

    /**
     * Used by the framework to add a monitor object to the list of monitors that will receive events during a transformation.
     * 
     * @param monitor A class implementing ICMonitor
     */
	void addMonitor(ICMonitor monitor)
	/**
	 * Method called by source and sink objects during a transformation. The class implementing ICMessagePump will call a method with the same parameters on each monitor object that is participating in a transformation.
	 * 
	 * @param generator The object that generated the message.
     * @param event A Map containing key-value pairs about what a source or sink component is doing during a transformation.
	 */
    void logEvent(ICFitting generator, Map event)
    void tearDown()
}

