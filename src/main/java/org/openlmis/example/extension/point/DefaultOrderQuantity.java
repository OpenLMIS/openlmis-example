package org.openlmis.example.extension.point;

import lombok.Getter;
import org.openlmis.example.extension.point.OrderQuantity;
import org.springframework.stereotype.Component;

/**
 * Example extension module - a default implementation of example extension point.
 * It is a Spring Bean with a unique ID "DefaultOrderQuantity".
 * @see org.openlmis.example.extension.point.OrderQuantity
 */
@Component("DefaultOrderQuantity")
public class DefaultOrderQuantity implements OrderQuantity {

  /**
   *
   * @return
   */
  public String getInfo() {
    return "I am default method";
  }
}
