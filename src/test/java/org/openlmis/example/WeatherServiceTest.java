package org.openlmis.example;


import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.google.gson.JsonObject;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.openlmis.example.service.WeatherService;

public class WeatherServiceTest {
    private static String TEST_HOST = "localhost";
    private static Integer TEST_PORT = 8080;
    private static String TEST_API_KEY = "506bc2e0c27da38e628dd249599ea779";

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(TEST_PORT);

    private WeatherService weatherService = new WeatherService(TEST_HOST, TEST_PORT, TEST_API_KEY);

    @Test
    public void testWeather() {
        JsonObject result = weatherService.getWeather("London");
        Assert.assertEquals(result.get("name").getAsString(), "London");
        Assert.assertEquals(result.get("main").getAsJsonObject().get("temp").getAsDouble(), 285.89, 0);

        result = weatherService.getWeather("Paris");
        Assert.assertEquals(result.get("name").getAsString(), "Paris");
        Assert.assertEquals(result.get("main").getAsJsonObject().get("temp").getAsDouble(), 286.16, 0);
    }

}
