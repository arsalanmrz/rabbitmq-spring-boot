package com.mirbozorgi.rabbitmq.service.impl;

import com.mirbozorgi.rabbitmq.service.CustomLoggerService;
import com.mirbozorgi.rabbitmq.service.SerializerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomLoggerServiceImp implements CustomLoggerService {

  private static final Logger logger = LoggerFactory.getLogger(CustomLoggerServiceImp.class);

  @Autowired
  private SerializerService serializerService;


  @Override
  public void put(String key, Object value) {
    MDC.put(key, serializerService.toJson(value));
    logger.info(key);


  }

  @Override
  public void putWithOutLog(String key, Object value) {
    MDC.put(key, serializerService.toJson(value));
  }


}
