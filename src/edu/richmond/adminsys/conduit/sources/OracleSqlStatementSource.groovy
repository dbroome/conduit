package edu.richmond.adminsys.conduit.sources

import groovy.util.slurpersupport.GPathResult
import groovy.sql.Sql
import groovy.sql.GroovyRowResult
import edu.richmond.adminsys.conduit.ICSource
import edu.richmond.adminsys.conduit.Utilities
import edu.richmond.adminsys.conduit.ICMessagePump

/**
 * The OracleSqlStatementSource component executes a query contained in the config file 
 * 
 * @author dbroome
 *
 */

class OracleSqlStatementSource implements ICSource {

    private Sql sqlObj;
    private String query;
    int rowLimit
    String conduitPrefix = ""
    //TODO: Redesign so conduitName is not needed

	List get(ICMessagePump messagePump) {
        def results = []

        iterateOverRows(query, { row ->
            def resultRow = [:]
            row.keySet().each { key ->
                resultRow[key.toString().toLowerCase()] = (row[key] == null)? '' : row[key]
            }
            results.add(resultRow)
        }, rowLimit, messagePump)

        return results
    }

    void setUp(GPathResult config) {
        Utilities.checkRequiredConfigItems(config, ['connectString', 'userName', 'password', 'query'])
        String connectString = config.connectString.text()
        String userName = config.userName.text()
        String password = config.password.text()
        sqlObj = Sql.newInstance(connectString, userName, password)
        query = config.query.text()
        rowLimit = (config.rowLimit.isEmpty())? 0: Integer.parseInt(config.rowLimit.text())
        if (!(config.parent().@name.text().equals(""))) {
            conduitPrefix = config.parent().@name.text() + ": "
        }
    }

    void tearDown() {
        sqlObj.close()
    }

    private int getRowCountForQuery(String sql) {
        int result = 0;
        sqlObj.eachRow(sql, { row ->
            result = row[0]
        })
        return result
    }

    public void iterateOverRows(String sql, Closure rowClosure, int rowLimit, ICMessagePump messagePump) {
        //turn query into select count(*) query to get total rows
        int rowCount = getRowCountForQuery("SELECT COUNT(*) FROM (${sql})")
        //pass each row from query into rowClosure printing "Row x of y" before call
        List<GroovyRowResult> rows = sqlObj.rows(sql)
        for (int i = 0; i < (((rowCount > rowLimit)&&(rowLimit > 0))? rowLimit : rowCount); i += 1) {
            messagePump.logEvent(this, [message: "${conduitPrefix}Retrieving row ${(i+1)} of $rowCount"])
            rowClosure(rows[i])
        }
    }
}
