import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import gim.config.GIMConfig;
import gim.operations.GIMQuery;

import java.io.File;
import java.io.IOException;

public class FusekiQueryStation {

    public static void main(String[] args) throws IOException {

        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

        GIMConfig gimConfig = mapper.readValue(new File("src/main/resources/GIMconfig.yaml"), GIMConfig.class);

        GIMQuery gimQuery = new GIMQuery();

        // Accept formats can be: "turtle", "rdf/xml", "json-ld", "n-triples", "n3",
        // "n-quads", "rdf/json", "trig", "trix"
        gimQuery.setConstructFormat("turtle");

        // How many triple/statement exist in the database?

        String sparqlQuery = "PREFIX skos: <http://www.w3.org/2004/02/skos/core#>\n" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "PREFIX nalt: <http://lod.nal.usda.gov/nalt/>\n" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX xmpl: <http://www.co-ode.org/ontologies/ont.owl#>\n" +
                "\n" +
                "ASK {\n" +
                "  GRAPH <http://localhost:3030/ds/data/barley> {\n" +
                "    ?subject skos:prefLabel \"Lomerit\".\n" +
                "  }\n" +
                "} ";

        gimQuery.sendQuery(sparqlQuery, gimConfig);


    }

}
