package gim.operations;

import gim.config.GIMConfig;
import org.apache.jena.query.Dataset;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.rdfconnection.RDFConnectionFactory;

public class GIMUpdate {

    /**
     * This method deletes data from the default graph in the Jena tdb.
     * @param gimConfig contains the configuration of the virtual machine.
     */
    public void removeDG(GIMConfig gimConfig) {

        // connect to Fuseki
        try (RDFConnection conn = RDFConnectionFactory.connect(
                "http://" + gimConfig.getRemoteHost() + ":" + gimConfig.getLocalRDFPort() + "/ds")) {

            conn.delete();

            System.out.println("The default graph was deleted.");

        }

    }

    /**
     * This method deletes data from a specific named graph in the Jena tdb.
     * @param ngName contains the name of the specific graph.
     * @param gimConfig contains the configuration of the virtual machine.
     */
    public void removeNG(String ngName, GIMConfig gimConfig) {

        // connect to Fuseki
        try (RDFConnection conn = RDFConnectionFactory.connect(
                "http://" + gimConfig.getRemoteHost() + ":" + gimConfig.getLocalRDFPort() + "/ds")) {

            conn.delete("data/"+ngName);

            System.out.println("The specific named graph '" + ngName + "' was deleted.");

        }

    }

    /**
     * This method adds a whole dataset to the Jena TDB via Fuseki.
     * @param dataset contains models
     * @param gimConfig contains the configuration of the virtual machine.
     */
    public void uploadDataset(Dataset dataset, GIMConfig gimConfig) {

        // connect to Fuseki
        try (RDFConnection conn = RDFConnectionFactory.connect(
                "http://" + gimConfig.getRemoteHost() + ":" + gimConfig.getLocalRDFPort() + "/ds")) {

            conn.loadDataset(dataset);

            System.out.println("A dataset was added to Jena via Fuseki.");

        }

    }

    /**
     * This method uploads data to a the default graph in the Jena tdb.
     * @param pathToFile contains the String path to you file(e.g. .rdf, .owl, .ttl).
     * @param gimConfig contains the configuration of the virtual machine.
     */
    public void uploadDG(String pathToFile, GIMConfig gimConfig) {

        // connect to Fuseki
        try (RDFConnection conn = RDFConnectionFactory.connect(
                "http://" + gimConfig.getRemoteHost() + ":" + gimConfig.getLocalRDFPort() + "/ds")) {

            conn.put(pathToFile);

            System.out.println("Data was added to the default graph.");

        }

    }

    /**
     * This method uploads data to a specific named graph in the Jena tdb.
     * @param ngName contains the name of the specific graph.
     * @param pathToFile contains the String path to you file(e.g. .rdf, .owl, .ttl).
     * @param gimConfig contains the configuration of the virtual machine.
     */
    public void uploadNG(String ngName, String pathToFile, GIMConfig gimConfig) {

        // connect to Fuseki
        try (RDFConnection conn = RDFConnectionFactory.connect(
                "http://" + gimConfig.getRemoteHost() + ":" + gimConfig.getLocalRDFPort() + "/ds")) {

            conn.put("data/"+ngName, pathToFile);

            System.out.println("Data was added to the specific named graph '" + ngName + "'.");

        }

    }

}
