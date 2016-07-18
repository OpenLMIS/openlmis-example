package org.openlmis.example.extensionPoint;

import org.openlmis.example.annotation.DefaultImplementation;

@DefaultImplementation
public class DefaultOrderQuantity implements OrderQuantity {
  public Integer calc(Integer x, Integer y) {
    return x*y+100;
  }
}
