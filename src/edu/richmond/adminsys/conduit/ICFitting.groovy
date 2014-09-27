package edu.richmond.adminsys.conduit

import groovy.util.slurpersupport.GPathResult

/**
 * The ICFitting interface is the superinterface for all source, sink, and monitor components in the Conduit framework
 * 
 * @author dbroome
 *
 */

interface ICFitting {
	/**
	 * Called by the framework prior to a transformation to allow the class to set configuration properties and allocate any needed resources.
	 * 
	 * @param config A GPathResult derived from a parsed XML node containing configuration information for the component.
	 */
    void setUp(GPathResult config)
	
	/**
	 * Called by the framework after a transformation to allow the class to finalize any resources allocated during setup.
	 */
    void tearDown()
}
