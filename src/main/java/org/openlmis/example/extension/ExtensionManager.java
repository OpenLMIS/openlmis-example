package org.openlmis.example.extension;

import lombok.Getter;
import lombok.Setter;
import org.openlmis.example.exception.ExtensionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Class responsible for returning the right implementation of extension point.
 */
@Component
public class ExtensionManager {

  private static final String EXTENSIONS_DIR = "/extensions";
  private static final String CONFIG_FILE = "extensions.properties";
  private static final Logger LOGGER = LoggerFactory.getLogger(ExtensionManager.class);

  @Autowired
  private ApplicationContext appContext;

  @Getter
  @Setter
  private HashMap<String, String> extensions = new HashMap<String, String>();

  /**
   * Returns implementation of an extension class that is defined in
   * configuration file for extension point with given Id.
   * @param pointId Id of extension point
   * @return implementation of chosen extension class (based on config file)
   */
  public Object getExtensionByPointId(String pointId) throws ExtensionException {
    Object object;
    String extensionId = extensions.get(pointId);
    try {
      object = appContext.getBean(extensionId);
    } catch (NoSuchBeanDefinitionException ex) {
      throw new ExtensionException("It was not possible to find an extension with id "
          + extensionId, ex);
    }
    return object;
  }

  @PostConstruct
  private void loadConfigurationFile() {
    Properties prop = new Properties();
    try {
      prop.load(new FileInputStream(new File(EXTENSIONS_DIR, CONFIG_FILE)));
      for (final Map.Entry<Object, Object> entry : prop.entrySet()) {
        extensions.put((String) entry.getKey(), (String) entry.getValue());
      }
    } catch (IOException ex) {
      LOGGER.debug("It was not possible to load extensions"
          + " configuration from extension.properties file", ex);
    }
  }
}
