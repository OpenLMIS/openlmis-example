package org.openlmis.example.manager;

import org.openlmis.example.annotation.DefaultImplementation;
import org.openlmis.example.util.Extension;
import org.openlmis.example.util.Extensions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import javax.annotation.PostConstruct;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Class responsible for returning the right implementation of extension point.
 */
public class ExtensionManager {

  private static final String CONFIG_FILE = "extensions.xml";
  private static final Logger LOGGER = LoggerFactory.getLogger(ExtensionManager.class);

  @Autowired
  private ApplicationContext appContext;

  private List<Extension> extensions;

  /**
   * Returns implementation for given extension point (interface). If there is an extension defined in configuration file,
   * this method returns implementation from extension. If not, method returns default implementation.
   * @param extensionPoint interface that is an extension point
   * @return implementation of given interface (default or from extension)
   */
  public Object getImplementation(Class extensionPoint) {
    Optional<Extension> extension = extensions.stream()
        .filter(a -> (a.getPoint().equals(extensionPoint.getName())))
        .findFirst();

    if (!extension.isPresent()) {
      return getDefaultImplementation(extensionPoint);
    }

    return getExtendedImplementation(extension.get());
  }

  @PostConstruct
  private List<Extension> getExtensions() {
    extensions = new ArrayList<>();
    File config = new File(CONFIG_FILE);
    if (config.exists() && config.length() > 0) {
      try {
        JAXBContext jaxbContext = JAXBContext.newInstance(Extensions.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        Extensions extensionsList = (Extensions) jaxbUnmarshaller.unmarshal(new File(CONFIG_FILE));
        extensions.addAll(extensionsList.getExtensions());
      } catch (JAXBException e) {
        LOGGER.debug(e.getMessage(), e);
      }
    }
    return extensions;
  }

  private Object getExtendedImplementation(Extension extension) {
    Object object = null;
    try {
      Class<?> clazz = Class.forName(extension.getClassName());
      object = appContext.getBeansOfType(clazz).values().iterator().next();
    } catch (ClassNotFoundException e) {
      LOGGER.debug(e.getMessage(), e);
    }
    return object;
  }

  private Object getDefaultImplementation(Class extensionPoint) {
    Collection<Object> defaultImpl = appContext.getBeansWithAnnotation(DefaultImplementation.class).values();
    for (Object o : defaultImpl) {
      if (extensionPoint.isAssignableFrom(o.getClass())) {
        return o;
      }
    }
    return null;
  }
}
