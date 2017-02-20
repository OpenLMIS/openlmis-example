package org.openlmis.example.i18n;

import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

/**
 * An exposed messages source which allows retrieval of all current messages
 * despite caching.
 */
@Component
public class ExposedMessageSourceImpl extends ReloadableResourceBundleMessageSource implements 
    ExposedMessageSource {

  @Override
  public Map<String, String> getAllMessages(Locale locale) {
    Properties props = getAllProperties(locale);
    Enumeration<String> keys = (Enumeration<String>) props.propertyNames();
    Map<String, String> asMap = new HashMap<>();
    while (keys.hasMoreElements()) {
      String key = keys.nextElement();
      asMap.put(key, props.getProperty(key));
    }
    return asMap;
  }

  private Properties getAllProperties(Locale locale) {
    clearCacheIncludingAncestors();
    PropertiesHolder propertiesHolder = getMergedProperties(locale);
    return propertiesHolder.getProperties();
  }
}