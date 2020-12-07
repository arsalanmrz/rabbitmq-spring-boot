package com.mirbozorgi.rabbitmq.service;

public interface SerializerService {

  <T> String toJson(T obj);

  <T> T toObj(String value, Class<T> classType);
}
