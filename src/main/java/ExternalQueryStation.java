import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class ExternalQueryStation {

    private String service;
    private String apikey;


    private ExternalQueryStation(String service) {
        this.service = service;
        this.apikey = null;
    }

    private ExternalQueryStation(String service, String apikey) {
        this.service = service;
        this.apikey = apikey;
    }

    private String executeQuery(String httpQueryString) throws IOException {

        // create URL
        URL url = new URL(this.service + "?" + httpQueryString);
        // create http connection and set parameters
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:70.0) Gecko/20100101 Firefox/70.0 Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        // connect http connection
        conn.connect();

        InputStream in = conn.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder buff = new StringBuilder();
        String line;
        while ((line = reader.readLine())!=null) {
            buff.append(line);
            buff.append("\n");
        }
        conn.disconnect();
        return buff.toString();
    }

    private String executeQueryToBioPortal(String queryText) throws Exception {
        String httpQueryString = String.format("query=%s&apikey=%s",
                URLEncoder.encode(queryText, "UTF-8"),
                URLEncoder.encode(this.apikey, "UTF-8"));

        return executeQuery(httpQueryString);
    }

    private String executeQueryToWikiData(String queryText) throws Exception {
        String httpQueryString = String.format("query=%s",
                URLEncoder.encode(queryText, "UTF-8"));

        return executeQuery(httpQueryString);
    }

    public static void main(String[] args) throws Exception {



        String bioPortalService = "http://sparql.bioontology.org/sparql";
        String bioPortalAPIKey = "geo-info-management";

        /*
         * More query examples here:
         * http://sparql.bioontology.org/examples
         */
        String query = "";

        ExternalQueryStation externalQueryStation = new ExternalQueryStation(bioPortalService, bioPortalAPIKey);

        String response = externalQueryStation.executeQueryToBioPortal(query);
        System.out.println(response);


        /*String wikidataService = "https://query.wikidata.org/sparql";

        query = "";

        externalQueryStation = new ExternalQueryStation(wikidataService);

        response = externalQueryStation.executeQueryToWikiData(query);
        System.out.println(response);*/

    }

}
