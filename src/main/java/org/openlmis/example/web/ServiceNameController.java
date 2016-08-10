package org.openlmis.example.web;

import org.openlmis.example.util.ServiceSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServiceNameController extends BaseController {

  private static final Logger LOGGER = LoggerFactory.getLogger(ServiceNameController.class);

  @RequestMapping("/")
  public ServiceSignature index() {
    LOGGER.debug("Returning service name and version");
    return new ServiceSignature(ServiceSignature.SERVICE_NAME, ServiceSignature.SERVICE_VERSION);
  }
}