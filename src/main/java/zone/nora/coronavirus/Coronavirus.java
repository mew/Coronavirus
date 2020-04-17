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

import com.google.gson.*;
import zone.nora.coronavirus.data.latest.Latest;
import zone.nora.coronavirus.data.locations.location.Location;
import zone.nora.coronavirus.data.locations.Locations;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class Coronavirus {
    private static final String BASE_URL = "https://coronavirus-tracker-api.herokuapp.com/v2/";
    private static final Gson gson = new Gson();

    /**
     * @return Object containing just the key real-time Coronavirus data.
     * @throws IOException error connecting to API.
     */
    public Latest getLatest() throws IOException {
        return gson.fromJson(getCoronaJson("latest"), Latest.class);
    }

    /**
     * Get latest Coronavirus data from a different source.
     * See: https://github.com/ExpDev07/coronavirus-tracker-api#available-sources
     *
     * @see zone.nora.coronavirus.sources.Sources
     * @param source Source you want to get data from.
     * @return Object containing just the key real-time Coronavirus data from the given source.
     * @throws IOException error connecting to the API.
     */
    public Latest getLatest(String source) throws IOException {
        return gson.fromJson(getCoronaJson("latest?source=" + source), Latest.class);
    }

    /**
     * @return ArrayList of real-time Coronavirus data for all locations.
     * @throws IOException error connecting to the API.
     */
    public ArrayList<Location> getLocations() throws IOException {
        return getLocationsList("locations");
    }

    /**
     * Get locations data from a different source.
     * See: https://github.com/ExpDev07/coronavirus-tracker-api#available-sources
     *
     * @see zone.nora.coronavirus.sources.Sources
     * @param source Source you want to get data from.
     * @return ArrayList of real-time Coronavirus data for all locations from the specified source.
     * @throws IOException error connecting to the API.
     */
    public ArrayList<Location> getLocations(String source) throws IOException {
        return getLocationsList("locations");
    }

    /**
     * Get location data for locations in a specified country.
     *
     * @param country Name of country (Case sensitive)
     * @return Location data of regions of the specified country.
     * @throws IOException error connecting to the API.
     */
    public Locations getLocationsByCountry(String country) throws IOException {
        return gson.fromJson(getCoronaJson("locations?country=" + country), Locations.class);
    }

    /**
     * Get location data for locations in a specified country via that country's alpha-2 country code.
     * See: https://en.wikipedia.org/wiki/ISO_3166-1_alpha-2
     *
     * @param countryCode The alpha-2 country code of a country (ie Canada is 'CA').
     * @return Location data of regions of the specified country.
     * @throws IOException error connecting to the API.
     */
    public Locations getLocationsByCountryCode(String countryCode) throws IOException {
        return gson.fromJson(getCoronaJson("locations?country_code=" + countryCode.toUpperCase()), Locations.class);
    }

    /**
     * Get location data for locations in a specified province, state or region.
     *
     * @param province Name of province, state or region (Case sensitive)
     * @return Location data of regions of the specified country.
     * @throws IOException error connecting to the API.
     */
    public Locations getLocationsByProvince(String province) throws IOException {
        return gson.fromJson(getCoronaJson("locations?province=" + province), Locations.class);
    }

    /**
     * Get data for a location by specifying it's ID.
     * I suggest you read the README for the actual API: https://git.io/JvDbv
     *
     * @param id ID of the location
     * @return Real-time Coronavirus data for the specified location.
     * @throws IOException error connecting to the API.
     */
    public Location getLocationById(int id) throws IOException {
        return gson.fromJson(getCoronaJson("locations/" + id), Location.class);
    }

    private static ArrayList<Location> getLocationsList(String endpoint) throws IOException {
        JsonArray jsonArray = getCoronaJson(endpoint).getAsJsonArray("locations");
        ArrayList<Location> locations = new ArrayList<>();
        jsonArray.forEach(jsonElement -> locations.add(gson.fromJson(jsonElement, Location.class)));
        return locations;
    }

    private static JsonObject getCoronaJson(String endpoint) throws IOException {
        String url = BASE_URL + endpoint;
        return readJsonUrl(url).getAsJsonObject();
    }

    private static JsonElement readJsonUrl(String url) throws IOException {
        return new JsonParser().parse(getPage(url));
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
