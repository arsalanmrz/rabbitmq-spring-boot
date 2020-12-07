package com.mirbozorgi.rabbitmq.service.impl;


import com.mirbozorgi.rabbitmq.domain.ApiResponse;
import com.mirbozorgi.rabbitmq.exception.common.WrongDataException;
import com.mirbozorgi.rabbitmq.service.HttpClientService;
import com.mirbozorgi.rabbitmq.service.SerializerService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class HttpClientServiceImpl implements HttpClientService {


  @Autowired
  SerializerService serializerService;

  @Override
  public <T> ApiResponse<T> request(
      HttpMethod method,
      String url,
      Object body,
      Map<String, ?> queryData,
      MultiValueMap<String, String> headers,
      Class<T> classType) {
    ApiResponse<T> result;

    RestTemplate restTemplate = new RestTemplate();

    UriComponentsBuilder uri = UriComponentsBuilder.fromHttpUrl(url);

    if (queryData != null) {
      for (Map.Entry<String, ?> item : queryData.entrySet()) {
        uri.queryParam(item.getKey(), item.getValue());
      }
    }

    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
    if (headers != null) {
      httpHeaders.addAll(headers);
    }

    HttpEntity<String> httpEntity = new HttpEntity<String>(
        serializerService.toJson(body),
        httpHeaders);
    try {
      ResponseEntity<T> responseEntity =
          restTemplate.exchange(
              uri.toUriString()
              , method
              , httpEntity
              , classType
          );

      result = new ApiResponse<>(responseEntity.getStatusCode(), responseEntity.getBody());

    } catch (HttpClientErrorException exp) {
      throw new WrongDataException("",
          serializerService.toObj(exp.getResponseBodyAsString(), Object.class));
    } catch (Exception exp) {
      throw new WrongDataException("unkown exception", null);
    }

    return result;
  }


}
