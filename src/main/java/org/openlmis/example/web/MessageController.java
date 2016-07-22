package org.openlmis.example.web;

import org.openlmis.example.extensionPoint.OrderQuantity;
import org.openlmis.example.i18n.ExposedMessageSource;
import org.openlmis.example.manager.ExtensionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class MessageController extends BaseController {

  private static final Class ORDER_QUANTITY = org.openlmis.example.extensionPoint.OrderQuantity.class;
  private static final Logger LOGGER = LoggerFactory.getLogger(ServiceNameController.class);

  @Autowired
  private ExtensionManager extensionManager;

  @Autowired
  private ExposedMessageSource messageSource;

  private OrderQuantity orderQuantity;

  /**
   * Returns information about extensionPoint called OrderQuantity. If there is an extension attached and defined in
   * configuration file ("extensions.xml") it will return information about extended implementation.
   * If not, it will return information about default implementation.
   * @return information saying which class was returned as OrderQuantity implementation and what was
   * the result of "calc" method defined by OrderQuantity interface.
   */
  @RequestMapping("/extensionPoint")
  public String extensionPoint() {
    orderQuantity = (OrderQuantity)extensionManager.getImplementation(ORDER_QUANTITY);
    String message = orderQuantity.getInfo();
    String[] msgArgs = {orderQuantity.getClass().getName(), message};
    LOGGER.debug("Returning extension point implementation.");
    return messageSource.getMessage("example.message.extensionPoint", msgArgs, LocaleContextHolder.getLocale());
  }

  @RequestMapping("/hello")
  public String hello() {
    String[] msgArgs = {"world"};
    LOGGER.debug("Returning hello world message");
    return messageSource.getMessage("example.message.hello", msgArgs,
        LocaleContextHolder.getLocale());
  }

  @RequestMapping("/messages")
  public Map<String, String> getAllMessages() {
    LOGGER.info("Returning all messages for current locale");
    return messageSource.getAllMessages(LocaleContextHolder.getLocale());
  }
}
