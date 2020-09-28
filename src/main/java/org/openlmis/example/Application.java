/*
 * This program is part of the OpenLMIS logistics management information system platform software.
 * Copyright © 2017 VillageReach
 *
 * This program is free software: you can redistribute it and/or modify it under the terms
 * of the GNU Affero General Public License as published by the Free Software Foundation, either
 * version 3 of the License, or (at your option) any later version.
 *  
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 * See the GNU Affero General Public License for more details. You should have received a copy of
 * the GNU Affero General Public License along with this program. If not, see
 * http://www.gnu.org/licenses.  For additional information contact info@OpenLMIS.org. 
 */

package org.openlmis.example;


import java.util.Locale;
import org.openlmis.example.i18n.ExposedMessageSourceImpl;
import org.openlmis.example.web.NotificationValidator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

/**
 * Configuration for the Sprint Boot application (this service).
 */
@SpringBootApplication(
    scanBasePackages = {"org.openlmis.example", "extensions.org.openlmis.example.extension"}
    )
//@SpringBootApplication
//@ComponentScan("org.openlmis.example*")
/*@ComponentScan(
    basePackages = {"org.openlmis.example*", "extensions.org.openlmis.example*"}
    )*/
@EntityScan(basePackages = "org.openlmis.example*")
@ImportResource("applicationContext.xml")
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  /**
   * Configures the {@link LocaleResolver} bean for Spring.
   * @return the locale resolver
   */
  @Bean
  public LocaleResolver localeResolver() {
    SessionLocaleResolver slr = new SessionLocaleResolver();
    slr.setDefaultLocale(Locale.ENGLISH);
    return slr;
  }

  /**
   * Configures the message source bean for Spring - source of translatable
   * messages.
   * @return the message source
   */
  @Bean
  public ExposedMessageSourceImpl messageSource() {
    ExposedMessageSourceImpl messageSource = new ExposedMessageSourceImpl();
    messageSource.setBasename("classpath:messages");
    messageSource.setDefaultEncoding("UTF-8");
    messageSource.setUseCodeAsDefaultMessage(true);
    return messageSource;
  }


  /**
   * Associate a NotificationValidator with the beforeCreateNotification event
   * (NOTE that this should work out of the box. In reality, though,
   * it requires the configuration offered by ValidatorRegistrar.java).
   * @return the validator for notifications
   */
  @Bean
  public NotificationValidator beforeCreateNotificationValidator() {
    return new NotificationValidator();
  }

  /**
   * Associate a NotificationValidator with the beforeSaveNotification event
   * (NOTE that this should work out of the box. In reality, though,
   * it requires the configuration offered by ValidatorRegistrar.java)
   * @return the validator for notifications
   */
  @Bean
  public NotificationValidator beforeSaveNotificationValidator() {
    return new NotificationValidator();
  }
}