package org.openlmis.example.exception;

/**
 * Exception that indicates that there was an error during
 * retrieving extension point implementation.
 */
public class ExtensionException extends RuntimeException {

  public ExtensionException(String message, Exception ex) {
    super(message, ex);
  }
}
