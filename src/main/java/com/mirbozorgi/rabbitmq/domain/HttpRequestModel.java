package com.mirbozorgi.rabbitmq.domain;//package games.medrick.medusa.api.rabbitmqSync;

import java.io.Serializable;
import java.util.Map;
import org.springframework.http.HttpMethod;

public class HttpRequestModel implements Serializable {

  private HttpMethod method;
  private String url;
  private Object body;
  private Map<String, ?> queryData;
  private Map<String, String> headers;


  public HttpRequestModel(HttpMethod method, String url, Object body,
      Map<String, ?> queryData, Map<String, String> headers) {
    this.method = method;
    this.url = url;
    this.body = body;
    this.queryData = queryData;
    this.headers = headers;
  }

  public HttpMethod getMethod() {
    return method;
  }

  public void setMethod(HttpMethod method) {
    this.method = method;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public Object getBody() {
    return body;
  }

  public void setBody(Object body) {
    this.body = body;
  }

  public Map<String, ?> getQueryData() {
    return queryData;
  }

  public void setQueryData(Map<String, ?> queryData) {
    this.queryData = queryData;
  }

  public Map<String, String> getHeaders() {
    return headers;
  }

  public void setHeaders(Map<String, String> headers) {
    this.headers = headers;
  }
}
