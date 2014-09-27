package edu.richmond.adminsys.conduit

/**
 * The ConduitException is the base class for any exceptions thrown by the framework during a transformation. 
 * 
 * @author dbroome
 *
 */

class ConduitException extends Exception {
    
	public ConduitException(String message) {
        super(message)
    }
}
