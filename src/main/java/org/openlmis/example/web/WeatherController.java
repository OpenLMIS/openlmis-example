package org.openlmis.example.web;

import org.openlmis.example.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeatherController extends BaseController {

  @Autowired
  private WeatherService weatherService;

  @PreAuthorize("hasAuthority('ADMIN')")
  @RequestMapping("/weather/{cityName}")
  public String weather(@PathVariable("cityName") String cityName) {
    logger.info("Returning weather for " + cityName);
    return weatherService.getWeather(cityName).toString();
  }
}
