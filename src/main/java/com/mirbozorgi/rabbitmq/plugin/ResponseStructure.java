package com.mirbozorgi.rabbitmq.plugin;

public class ResponseStructure<T> {

  private String msg;
  private T data;

  public ResponseStructure() {
  }

  public ResponseStructure(T data) {
    this(data, "ok");
  }

  public ResponseStructure(T data, String msg) {
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
