package com.mirbozorgi.rabbitmq.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mirbozorgi.rabbitmq.service.SerializerService;
import java.io.IOException;
import org.springframework.stereotype.Service;

@Service
public class SerializerServiceImpl implements SerializerService {

  private static ObjectMapper mapper = new ObjectMapper();

  /**
   * object to json.
   */
  @Override
  public <T> String toJson(T obj) {
    try {
      return mapper.writeValueAsString(obj);
    } catch (JsonProcessingException e) {
      return null;
    }
  }

  /**
   * json string to object.
   */
  @Override
  public <T> T toObj(String value, Class<T> classType) {
    try {
      return mapper.readValue(value, classType);
    } catch (IOException e) {
      return null;
    }
  }

}
