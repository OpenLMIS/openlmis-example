package org.openlmis.example.extension.point;

/**
 * Example extension point that defines a unique ID.
 * Extension modules can contain custom implementation of methods defined in this interface.
 * @see org.openlmis.example.extension.point.DefaultOrderQuantity
 */
public interface OrderQuantity {

  String POINT_ID = "OrderQuantity";

  /**
   * Method that can be implemented by an extension module.
   * @return String with information about invoked method.
   */
  String getInfo();
}
