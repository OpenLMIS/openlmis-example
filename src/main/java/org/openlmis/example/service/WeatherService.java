package org.openlmis.example.service;


import com.google.gson.JsonObject;
import org.openlmis.example.util.WeatherAPIClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class WeatherService {

    private WeatherAPIClient apiClient = null;

    @Autowired
    public WeatherService(@Value("${openWeatherMap.host}") String host,
                          @Value("${openWeatherMap.port}") Integer port,
                          @Value("${openWeatherMap.apiKey}") String apiKey) {
        this.apiClient = new WeatherAPIClient(host, port, apiKey);
    }

    public JsonObject getWeather(String cityName) {
        return this.apiClient.getWeather(cityName);
    }

}
