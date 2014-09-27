package edu.richmond.adminsys.conduit.sinks

import groovy.util.slurpersupport.GPathResult
import java.text.SimpleDateFormat
import java.sql.Timestamp
import edu.richmond.adminsys.conduit.Utilities
import edu.richmond.adminsys.conduit.ICSink
import edu.richmond.adminsys.conduit.ICMessagePump

class CSVFileSink implements ICSink {

    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy")
    String csvFilePath
    List<String> csvFields
    String conduitPrefix = ""
    //TODO: Redesign so conduitName is not needed

    @Override
    void set(List data, ICMessagePump messagePump) {


        File csv = new File(csvFilePath).withWriter { writer ->


            Utilities.iterateWithRowCount(data, { row, current, end ->

                def vals = []

                csvFields.each { column ->
                    vals.add(getValue(row[column]))
                }


                if (!(vals[0].equals(""))) {

                    def quotedVals = []
                    vals.each { val -> quotedVals.add("\"${val}\"")}
                    writer.append(quotedVals.join(","))
                    writer.append("\r\n")
                    writer.flush()

                    messagePump.logEvent(this, [message: "${conduitPrefix}Wrote CSV line ${current} of ${end}"])

                } else {

                    messagePump.logEvent(this, [message: "${conduitPrefix}Omitting line ${current} of ${end}"])
                }

            })

        }

    }

    @Override
    void setUp(GPathResult config) {
        Utilities.checkRequiredConfigItems(config, ['csvFile', 'csvFields'])
        csvFilePath = config.csvFile.text()
        csvFields = new ArrayList<String>()
        config.csvFields.csvField.each { field ->
            csvFields.add(field.text().toLowerCase())
        }
        if (!(config.parent().@name.text().equals(""))) {
            conduitPrefix = config.parent().@name.text() + ": "
        }
    }

    @Override
    void tearDown() {
        //nothing to do
    }

    def getValue(obj) {
        if (obj == null) {
            return '';
        } else {
            if (obj instanceof Timestamp)     {
                Timestamp t = obj as Timestamp
                return dateFormat.format(t).toUpperCase()
            }   else {
                return obj.toString()
            }
        }
    }
}



