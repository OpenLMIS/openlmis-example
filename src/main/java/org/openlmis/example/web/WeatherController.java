package org.openlmis.example.web;

import org.openlmis.example.service.WeatherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeatherController {

  Logger logger = LoggerFactory.getLogger(WeatherController.class);

  @Autowired
  private WeatherService weatherService;

  @RequestMapping("/weather/{cityName}")
  public String weather(@PathVariable("cityName") String cityName) {
    logger.info("Returning weather for " + cityName);
    return weatherService.getWeather(cityName).toString();
  }
}
