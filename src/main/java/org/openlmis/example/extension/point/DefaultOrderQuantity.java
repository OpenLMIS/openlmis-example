package org.openlmis.example.extension.point;

import lombok.Getter;
import org.openlmis.example.extension.point.OrderQuantity;
import org.springframework.stereotype.Component;

@Component("DefaultOrderQuantity")
public class DefaultOrderQuantity implements OrderQuantity {

  public String getInfo() {
    return "I am default method";
  }
}
