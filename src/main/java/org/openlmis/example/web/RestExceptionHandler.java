package org.openlmis.example.web;

import org.openlmis.example.exception.ExceptionDetail;
import org.openlmis.example.exception.ExtensionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;
import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class RestExceptionHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(RestExceptionHandler.class);

  @ExceptionHandler(ExtensionException.class)
  public ResponseEntity<?> handleExtensionException(ExtensionException exception) {
    LOGGER.debug(exception.getMessage(), exception);
    HttpStatus status = HttpStatus.BAD_REQUEST;
    String title = "Extension manager failure";
    ExceptionDetail exceptionDetail = getExceptionDetail(exception, status, title);
    return new ResponseEntity<>(exceptionDetail, null, status);
  }

  @ExceptionHandler(TransactionSystemException.class)
  public ResponseEntity<?> handleConstraintViolationException(TransactionSystemException exception,
                                                              HttpServletRequest request) {
    if (exception.contains(javax.validation.ConstraintViolationException.class)
        || exception.contains(org.hibernate.exception.ConstraintViolationException.class)) {
      Throwable specificException = exception.getMostSpecificCause();
      HttpStatus status = HttpStatus.BAD_REQUEST;
      String title = "Resource Property Validation Failure";
      ExceptionDetail exceptionDetail = getExceptionDetail(specificException, status, title);

      return new ResponseEntity<>(exceptionDetail, null, status);
    } else {
      throw exception;
    }
  }

  @ExceptionHandler(javax.validation.ConstraintViolationException.class)
  public ResponseEntity<?> handleConstraintViolationException(
      javax.validation.ConstraintViolationException exception, HttpServletRequest request) {
    HttpStatus status = HttpStatus.BAD_REQUEST;
    String title = "Resource Property Validation Failure";
    ExceptionDetail exceptionDetail = getExceptionDetail(exception, status, title);

    return new ResponseEntity<>(exceptionDetail, null, status);
  }

  //TODO: Determine why this ExceptionHandler isn't being used
  @ExceptionHandler(org.hibernate.exception.ConstraintViolationException.class)
  public ResponseEntity<?> handleConstraintViolationException2(
      org.hibernate.exception.ConstraintViolationException exception, HttpServletRequest request) {
    HttpStatus status = HttpStatus.BAD_REQUEST;
    String title = "Resource Property Validation Failure";
    ExceptionDetail exceptionDetail = getExceptionDetail(exception, status, title);

    return new ResponseEntity<>(exceptionDetail, null, status);
  }

  private static ExceptionDetail getExceptionDetail(Throwable throwable, HttpStatus status,
                                                    String title) {
    ExceptionDetail exceptionDetail = new ExceptionDetail();
    exceptionDetail.setTimeStamp(new Date().getTime());
    exceptionDetail.setStatus(status.value());
    exceptionDetail.setTitle(title);
    exceptionDetail.setDetail(throwable.getMessage());
    exceptionDetail.setDeveloperMessage(throwable.getClass().getName());
    return exceptionDetail;
  }
}