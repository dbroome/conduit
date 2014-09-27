package edu.richmond.adminsys.conduit
/**
 * 
 * The ICSource interface is implemented by any classes that read data from a source.
 * 
 * @author dbroome
 *   
 */
interface ICSource extends  ICFitting {
	/**
	 * Gets a collection of rows from the data source. Called by the framework at the beginning of a transformation.
	 * 
	 * @param messagePump The object that will distribute messages to objects implementing the ICMonitor interface
	 * @return A List containing the data from the source
	 */
    List get(ICMessagePump messagePump)
}