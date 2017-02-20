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
