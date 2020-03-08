package zone.nora.coronavirus;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import zone.nora.coronavirus.data.CoronavirusData;
import zone.nora.coronavirus.data.all.AllCoronavirusData;
import zone.nora.coronavirus.data.latest.LatestData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class Coronavirus {
    private static final String BASE_URL = "https://coronavirus-tracker-api.herokuapp.com/";
    private static final Gson gson = new Gson();

    /**
     * @return Object containing all real-time Coronavirus statistics.
     * @throws IOException error connecting to api.
     */
    public AllCoronavirusData getAllData() throws IOException {
        return gson.fromJson(getCoronaJson("all"), AllCoronavirusData.class);
    }

    /**
     * @return Object containing just the key real-time Coronavirus statistics.
     * @throws IOException error connecting to api.
     */
    public LatestData getLatestData() throws IOException {
        return getAllData().getLatest();
    }

    /**
     * @return Object containing real-time Coronavirus death statistics.
     * @throws IOException error connecting to api.
     */
    public CoronavirusData getDeaths() throws IOException {
        return gson.fromJson(getCoronaJson("deaths"), CoronavirusData.class);
    }

    /**
     * @return Object containing real-time statistics of confirmed cases of Coronavirus.
     * @throws IOException error connecting to api.
     */
    public CoronavirusData getConfirmed() throws IOException {
        return gson.fromJson(getCoronaJson("confirmed"), CoronavirusData.class);
    }

    /**
     * @return Object containing real-time Coronavirus recovery statistics.
     * @throws IOException error connecting to api.
     */
    public CoronavirusData getRecovered() throws IOException {
        return gson.fromJson(getCoronaJson("recovered"), CoronavirusData.class);
    }

    private static JsonObject getCoronaJson(String endpoint) throws IOException {
        String url = BASE_URL + endpoint;
        return readJsonUrl(url);
    }

    private static JsonObject readJsonUrl(String url) throws IOException {
        return new JsonParser().parse(getPage(url)).getAsJsonObject();
    }

    private static String getPage(String url) throws IOException {
        URL url1 = new URL(url);
        URLConnection connection = url1.openConnection();
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 5.1; rv:19.0) Gecko/20100101 Firefox/19.0");
        connection.connect();
        BufferedReader serverResponse = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String response = serverResponse.readLine();
        serverResponse.close();
        return response;
    }
}
