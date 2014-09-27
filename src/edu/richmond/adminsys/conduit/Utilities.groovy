package edu.richmond.adminsys.conduit

import groovy.util.slurpersupport.GPathResult

/**
 * The Utilities class contains static convenience methods for other classes in the framework to use.
 * 
 * @author dbroome
 *
 */
class Utilities {

	/**
	 * 
	 * Accepts a List of data and a closure that implements some work to perform on the List. Iterates through the list one row at a time, calling the closure with the list item, the current row number of the list item, and the total number of rows. Used by classes to display messages like "Updating row 1 of 15..."
	 * 
	 * @param list A List of data for the closure to operate on.
	 * @param workClosure A closure with three arguments: 1. A variable to accept a row of data to work on, 2. A variable to accept the current row number, 3. A variable to accept the total number of rows in the List passed in as the first parameter to the method.
	 * 
	 */
    public static final iterateWithRowCount(List list, Closure workClosure) {

        int listSize = list.size()

        for (int i = 0; i < listSize; i++) {
            workClosure(list[i], (i+1), listSize)
        }
    }

	/**
	 * Accepts a GPathResult and a List of Strings. Iterates through the GPathResult and ensures that all the items in the list of Strings are represented by a node in the GPathResult with a value. If not, the method throws a ConfigurationException. Used by ICFitting classes to ensure that all required configuration parameters were included in the XML that serves as the source for the GPathResult.
	 * 
	 * @param config A parsed fragment of XML containing configuration information for a source or sink class.
	 * @param requiredItems A list of Strings that must be represented by a node in the GPathResult.
	 * 
	 */
    public static final checkRequiredConfigItems(GPathResult config, List<String> requiredItems) {
        requiredItems.each { requiredItem ->

            if ((config[requiredItem].isEmpty()) || (config[requiredItem].text().trim().equals(""))) {
                throw new ConfigurationException("Required configuration item ${requiredItem} missing from ${config.name()}")
            }

        }
    }

}
