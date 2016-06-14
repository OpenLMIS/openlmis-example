package org.openlmis.example.exception;

/**
 * Example exception.
 */
public class ExampleException extends RuntimeException {

  public ExampleException(String message, Exception ex) {
    super(message, ex);
  }
}
