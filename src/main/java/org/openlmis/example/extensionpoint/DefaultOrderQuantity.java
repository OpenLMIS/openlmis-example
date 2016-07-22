package org.openlmis.example.extensionpoint;

import org.openlmis.example.annotation.DefaultImplementation;

@DefaultImplementation
public class DefaultOrderQuantity implements OrderQuantity {

  public String getInfo() {
    return "I am default method";
  }
}
