import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import gim.config.GIMConfig;
import gim.config.GIMConnector;


import java.io.*;


public class FusekiConnector {

    public static void main(String[] args) {

        try {

            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

            GIMConfig gimConfig = mapper.readValue(new File("src/main/resources/GIMconfig.yaml"), GIMConfig.class);

            GIMConnector gimConnector = new GIMConnector(gimConfig);

            gimConnector.connect();

        } catch (Exception e) {

            e.printStackTrace();

        }

    }
}