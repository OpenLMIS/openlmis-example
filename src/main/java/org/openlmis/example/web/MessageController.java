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

package org.openlmis.example.web;

import org.openlmis.example.extension.ExtensionManager;
import org.openlmis.example.extension.point.OrderQuantity;
import org.openlmis.example.i18n.ExposedMessageSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class MessageController extends BaseController {

  @Autowired
  private ExtensionManager extensionManager;

  @Autowired
  private ExposedMessageSource messageSource;

  /**
   * Returns information about extensionPoint called OrderQuantity.
   * It returns information about implementation defined for OrderQuantity
   * extension point in extensions.properties file.
   * @return information saying which class was returned as OrderQuantity implementation.
   */
  @RequestMapping("/extensionPoint")
  public String extensionPoint() {
    OrderQuantity orderQuantity = (OrderQuantity)
        extensionManager.getExtensionByPointId(OrderQuantity.POINT_ID);

    logger.debug("Returning extension point implementation.");

    String message = orderQuantity.getInfo();
    String[] msgArgs = {orderQuantity.getClass().getName(), message};

    return messageSource.getMessage("example.message.extensionPoint",
        msgArgs, LocaleContextHolder.getLocale());
  }

  /**
   * Returns a hello world message.
   * @return the classic hello world message
   */
  @RequestMapping("/hello")
  public String hello() {
    String[] msgArgs = {"world"};
    logger.debug("Returning hello world message");
    return messageSource.getMessage("example.message.hello", msgArgs,
        LocaleContextHolder.getLocale());
  }

  /**
   * Returns a map of all messages for this service.
   * @return a map of all messages
   */
  @RequestMapping("/messages")
  public Map<String, String> getAllMessages() {
    logger.info("Returning all messages for current locale");
    return messageSource.getAllMessages(LocaleContextHolder.getLocale());
  }
}
