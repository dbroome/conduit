Installation
============

This repository contains an Eclipse project that has been tested on PC and Mac versions of Eclipse Kepler configured with the Groovy IDE plugin described at http://groovy.codehaus.org/Eclipse+Plugin. The Java version is 1.7 and the Groovy version is 2.3.

You can clone straight from github by choosing File | Import | Git | Projects from Git and selecting the "Clone URI" option. Give the URI of this repository and then choose "Import existing projects" at the prompt. After importing, create a new Run Configuration for a Java Application and specify edu.richmond.adminsys.conduit.Main as the Main class. 

If goes well, running the application will execute a pre-configured data transformation that extracts data from a SQLite database located in the testinputs subfolder and exports the data to a CSV file in the testoutputs subfolder.
