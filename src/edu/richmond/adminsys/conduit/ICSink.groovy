package edu.richmond.adminsys.conduit

/**
 *
 * The ICSink interface is implemented by any classes that write data to a destination.
 *
 * @author dbroome
 *
 */
interface ICSink extends ICFitting {
	/**
	 * Called by the framework after retrieving a List of data from the source class.
	 * 
	 * @param data A List containing data from the data source.
	 * @param messagePump The object that will distribute messages to the monitor classes.
	 */
    void set(List data, ICMessagePump messagePump)
}
