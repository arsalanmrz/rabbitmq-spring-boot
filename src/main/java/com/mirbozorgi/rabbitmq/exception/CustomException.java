package com.mirbozorgi.rabbitmq.exception;

import org.springframework.http.HttpStatus;

public class CustomException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  private HttpStatus status;

  private String msg;

  private Object data;

  public CustomException() {
    this("error");
  }

  public CustomException(String msg) {
    this(msg, HttpStatus.BAD_REQUEST);
  }

  public CustomException(String msg, HttpStatus status) {
    this(msg, status, null);
  }

  public CustomException(String msg, HttpStatus status, Object data) {
    super();
    this.msg = msg;
    this.status = status;
    this.data = data;
  }

  public HttpStatus getStatus() {
    return status;
  }

  public void setStatus(HttpStatus status) {
    this.status = status;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  public Object getData() {
    return data;
  }

  public void setData(Object data) {
    this.data = data;
  }

  @Override
  public String toString() {
    return String.format("%s => %s", this.getMsg(), this.getData());
  }

}
