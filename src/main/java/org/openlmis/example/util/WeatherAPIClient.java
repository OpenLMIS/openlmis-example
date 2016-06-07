package org.openlmis.example.util;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

public class WeatherAPIClient  {
    Logger logger = LoggerFactory.getLogger(WeatherAPIClient.class);

    private String host;

    private Integer port;

    private String apiKey;

    public WeatherAPIClient(String host, Integer port, String apiKey) {
        this.host = host;
        this.port = port;
        this.apiKey = apiKey;
    }

    private JsonObject fetchJson(String path, Map<String, String> params) {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance()
                .scheme("http").host(this.host).port(this.port).path("data/2.5/" + path);

        params.put("appid", this.apiKey);
        params.forEach(uriComponentsBuilder::queryParam);

        String uriString = uriComponentsBuilder.build().toUriString();
        logger.debug("Fetching json from OpenWeatherMap API, uri: " + uriString);

        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(uriString, String.class);
        return new JsonParser().parse(response).getAsJsonObject();
    }

    public JsonObject getWeather(String cityName) {
        final Map<String, String> params = new HashMap<>();
        params.put("q", cityName);

        return fetchJson("weather", params);
    }
}
