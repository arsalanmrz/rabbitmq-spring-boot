package com.mirbozorgi.rabbitmq.plugin;


import com.mirbozorgi.rabbitmq.exception.CustomException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler {

  private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  @Value("${api.app.log.show-reason}")
  private boolean showReason;


  @ExceptionHandler(CustomException.class)
  public ResponseEntity handleCustomException(CustomException ex) {
    logger.warn(ex.toString());
    return ResponseHelper.response(ex.getData(), ex.getMsg(), ex.getStatus());
  }


  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity handelMethodArgumentNotValidException(MethodArgumentNotValidException ex) {

    List<String> errorMessages = new ArrayList<>();

    ex.getBindingResult().getFieldErrors()
        .stream()
        .collect(Collectors.groupingBy(i -> i.getField()))
        .forEach((s, fieldErrors) -> errorMessages.add(String.format("%s : %s", s,
            String.join(",", fieldErrors.stream().map(i -> i.getDefaultMessage()).collect(
                Collectors.toList())))));

    logger.warn(ex.getMessage());
    return ResponseHelper.response(errorMessages, "wrong_model", HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(MissingServletRequestParameterException.class)
  public ResponseEntity handelMethodMissingServletRequestParameterException(
      MissingServletRequestParameterException ex) {

    String errorMessage = String.format("%s : %s", ex.getParameterName(), ex.getMessage());

    logger.warn(errorMessage);

    return ResponseHelper.response(errorMessage, "invalid_model", HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity handelInvalidJsonException(Exception ex) {

    logger.warn(ex.getMessage());
    return ResponseHelper.response("Please check your request body structure!", "invalid_json",
        HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public ResponseEntity handelHttpRequestMethodNotSupportedException(
      HttpRequestMethodNotSupportedException ex) {

    logger.warn(ex.getMessage());
    return ResponseHelper.response("Please check your request structure!", "invalid_method",
        HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(BindException.class)
  public ResponseEntity handelBindException(
      BindException ex) {

    logger.warn(ex.getMessage());
    return ResponseHelper.response("Please check your request structure!", "invalid_model",
        HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity handleGeneralException(Exception ex) {
    logger.error(ex.getMessage());
    if (showReason) {
      return ResponseHelper
          .response(ex.getMessage(), "server_error", HttpStatus.INTERNAL_SERVER_ERROR);
    } else {
      return ResponseHelper
          .response("Please contact to Medrick Game Studio server department!", "server_error",
              HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
