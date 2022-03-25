package gim.operations;

import gim.config.GIMConfig;

import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.rdfconnection.RDFConnectionFactory;

import java.io.*;

public class GIMQuery {

    private String format;

    /**
     * This method sends a query to Fuseki (The SPARQL-Endpoint of Apache Jena)
     * @param sparqlQueryString contains a SPARQL query in a String
     * @param gimConfig contains the configuration of the virtual machine.
     */
    public void sendQuery(String sparqlQueryString, GIMConfig gimConfig) {

        System.out.println("Query: \n" + sparqlQueryString + "\n\n");

        // connect to Fuseki
        RDFConnection conn = RDFConnectionFactory.connect(
                "http://" + gimConfig.getRemoteHost() + ":" + gimConfig.getLocalRDFPort() + "/ds/");

        // create a Query
        Query query = QueryFactory.create(sparqlQueryString);

        // send query and get result
        QueryExecution qExec = conn.query(query) ;

        // get the query type
        int queryType = query.getQueryType();

        switch (queryType) {

            // ASK-SPARQL-Query
            case Query.QueryTypeAsk:

                // fill a boolean with data from the query
                boolean result = qExec.execAsk();

                System.out.print("Result:\n" + result);

                break;

            // CONSTRUCT-SPARQL-Query
            case Query.QueryTypeConstruct:

                // save the response data to a model/graph
                Model results = qExec.execConstruct();

                // create a string writer to convert the model to a string
                StringWriter outConstruct = new StringWriter();

                // write model with outputFormat to string writer
                results.write(outConstruct, format);

                // convert string writer to string
                System.out.println("Result:\n" + outConstruct.toString());

                break;

            // DESCRIBE-SPARQL-Query
            case Query.QueryTypeDescribe:

                // save the response data to a model/graph
                results = qExec.execDescribe();

                StringWriter outDescribe = new StringWriter();

                results.write(outDescribe, format);

                System.out.println("Result:\n" + outDescribe.toString());

                break;

            // SELECT-SPARQL-Query
            case Query.QueryTypeSelect:

                // save the response data to a result set
                ResultSet rs = qExec.execSelect() ;

                System.out.println("Result:\n" + ResultSetFormatter.asText(rs));

                break;

        }

        System.out.println("\n--------------------------------------------\n");

        qExec.close();
        conn.close();

    }

    /**
     * This method sets the format for the CONSTRUCT output of a SPARQL query.
     * Accept formats can be: "turtle", "rdf/xml", "json-ld", "n-triples", "n3", "n-quads", "rdf/json", "trig", "trix"
     * @param format contains a format for the output
     */
    public void setConstructFormat(String format) {

        this.format = format;

    }

}
