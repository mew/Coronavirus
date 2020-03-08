/*
 * BSD 3-Clause License
 *
 * Copyright (c) 2020, Nora Cos <https://github.com/mew>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its
 *    contributors may be used to endorse or promote products derived from
 *    this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

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
