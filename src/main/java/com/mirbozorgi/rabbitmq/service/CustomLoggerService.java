package com.mirbozorgi.rabbitmq.service;

public interface CustomLoggerService {

  void put(String key, Object value);

  void putWithOutLog(String key, Object value);

}
