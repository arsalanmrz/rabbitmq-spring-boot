package com.mirbozorgi.rabbitmq.plugin;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseHelper {

  private ResponseHelper() {
  }

  public static <T> ResponseEntity response(T t) {
    return response(t, "OK", HttpStatus.OK);
  }

  public static <T> ResponseEntity response(T t, HttpStatus status) {
    return response(t, "OK", status);
  }

  public static <T> ResponseEntity response(T t, String msg, HttpStatus status) {
    return new ResponseEntity(new ResponseStructure(t, msg), status);
  }
}
