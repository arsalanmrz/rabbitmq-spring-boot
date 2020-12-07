package com.mirbozorgi.rabbitmq.service;

import com.mirbozorgi.rabbitmq.domain.ApiResponse;
import java.util.Map;
import org.springframework.http.HttpMethod;
import org.springframework.util.MultiValueMap;

public interface HttpClientService {

  <T> ApiResponse<T> request(
      HttpMethod method,
      String url,
      Object body,
      Map<String, ?> queryData,
      MultiValueMap<String, String> headers,
      Class<T> classType);
}