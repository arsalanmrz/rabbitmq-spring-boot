package com.mirbozorgi.rabbitmq.domain;

import org.springframework.http.HttpStatus;

public class ApiResponse<T> {

  private HttpStatus status;

  private T data;

  public ApiResponse() {
  }

  public ApiResponse(HttpStatus status, T data) {
    this.status = status;
    this.data = data;
  }

  public HttpStatus getStatus() {
    return status;
  }

  public void setStatus(HttpStatus status) {
    this.status = status;
  }

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }
}