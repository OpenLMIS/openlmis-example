package org.openlmis.example.web;

import org.openlmis.example.exception.ExtensionException;
import org.openlmis.example.extension.point.OrderQuantity;
import org.openlmis.example.i18n.ExposedMessageSource;
import org.openlmis.example.extension.ExtensionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class MessageController extends BaseController {

  private static final Logger LOGGER = LoggerFactory.getLogger(MessageController.class);

  @Autowired
  private ExtensionManager extensionManager;

  @Autowired
  private ExposedMessageSource messageSource;

  private OrderQuantity orderQuantity;

  /**
   * Returns information about extensionPoint called OrderQuantity.
   * It returns information about implementation defined for OrderQuantity
   * extension point in extensions.properties file.
   * @return information saying which class was returned as OrderQuantity implementation.
   */
  @RequestMapping("/extensionPoint")
  public String extensionPoint() throws ExtensionException {
    orderQuantity = (OrderQuantity)extensionManager.getExtensionByPointId(OrderQuantity.POINT_ID);
    String message = orderQuantity.getInfo();
    String[] msgArgs = {orderQuantity.getClass().getName(), message};
    LOGGER.debug("Returning extension point implementation.");
    return messageSource.getMessage("example.message.extensionPoint",
        msgArgs, LocaleContextHolder.getLocale());
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
