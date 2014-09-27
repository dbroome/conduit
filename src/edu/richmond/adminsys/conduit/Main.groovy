package edu.richmond.adminsys.conduit
/**
 * The Main class contains the entry point invoked by java -jar Conduit.jar conduits.xml. The main method looks for a config file passed on the command line parameter, runs any transformations for which it has a configuration, and exits. 
 * 
 */
import groovy.util.slurpersupport.GPathResult

//TODO: Make Data Set Empty Not a -1 return code
class Main {

    private static final String VERSION = "0.5"

    private static final String DEFAULT_CONFIG_FILE = "conduits.xml";

    public static void main (String[] args) {

        System.out.println(new Date().toString() + " ---------- Conduit Start" )

        int returnCode = 0

        //Create message pump
		ICMessagePump defaultMessagePump = new DefaultMessagePump();

		//Look for config file(s)		
        File configFile
        if (args.length == 0) {
            configFile = new File(DEFAULT_CONFIG_FILE)
            if (!configFile.exists()) {
                throw new Exception("Running without arguments and can't find ${DEFAULT_CONFIG_FILE}")
            }
        } else {
            configFile = new File(args[0])
            if (!configFile.exists()) {
                throw new Exception ("Can't find config file: ${args[0]}")
            }
        }

		//Gather XML configurations into an list of GPathResult objects		
        ArrayList<GPathResult>  conduitConfigs = new ArrayList<GPathResult>()
        if (configFile.name.toLowerCase().equals(DEFAULT_CONFIG_FILE.toLowerCase())) {
            GPathResult masterConfig = new XmlSlurper().parse(configFile)
            masterConfig.conduit.each { conduitConfigFilename->
                try {
                    File cf = new File(conduitConfigFilename.text())
                    if (!cf.exists()) {
                        throw new Exception("Can't find config file from conduit list: ${cf.name}")
                    }
                    GPathResult conduitConfig = new XmlSlurper().parse(cf)
                    conduitConfigs.add(conduitConfig)
                }
                catch (Exception ex) {
                    System.out.println(ex.getMessage())
                    returnCode = -1
                }
            }
        } else {
            GPathResult conduitConfig = new XmlSlurper().parse(configFile)
            conduitConfigs.add(conduitConfig)
        }

		//For each configuration, instantiate the components, perform the transformation, and de-allocate the resources
        conduitConfigs.each { conduitConfig ->
            try {
                Conduit conduitInstance = ConduitFactory.createConduit(conduitConfig)
                conduitInstance.apply()
                conduitInstance.tearDown()
            }
            catch (Exception ex) {
                if (!(conduitConfig.@name.text().equals(""))) {
                    System.out.println("Error in conduit ${conduitConfig.@name.text()}:")
                }
                System.out.println(ex.getMessage())
                returnCode = -1
            }
        }

        System.console().println(new Date().toString() + " ---------- Conduit End, return code is ${returnCode}")
        [1..5].each { System.console().println() }
        System.exit(returnCode)
    }
}
