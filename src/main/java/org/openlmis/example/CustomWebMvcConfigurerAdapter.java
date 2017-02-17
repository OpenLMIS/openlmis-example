package org.openlmis.example;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class CustomWebMvcConfigurerAdapter extends WebMvcConfigurerAdapter {

  @Value("${service.url}")
  private String serviceUrl;

  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    // When running with the Reference Distribution...
    registry.addViewController("/example/docs")
        .setViewName("redirect:" + serviceUrl + "/example/docs/");
    registry.addViewController("/example/docs/")
        .setViewName("forward:/example/docs/index.html");
    // ...when running locally
    registry.addViewController("/docs")
        .setViewName("redirect:/docs/");
    registry.addViewController("/docs/")
        .setViewName("forward:/example/docs/index.html");

    super.addViewControllers(registry);
  }

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/example/webjars/**")
        .addResourceLocations("classpath:/META-INF/resources/webjars/");
    super.addResourceHandlers(registry);
  }
}
