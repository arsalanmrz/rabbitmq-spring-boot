package com.mirbozorgi.rabbitmq.domain;

public class ExternalCallStructure<T> {

  private String msg;
  private T data;

  public ExternalCallStructure() {
  }

  public ExternalCallStructure(T data) {
    this(data, "ok");
  }

  public ExternalCallStructure(T data, String msg) {
    this.msg = msg;
    this.data = data;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }
}
