package com.mirbozorgi.rabbitmq.service.impl;

import com.mirbozorgi.rabbitmq.service.CustomLoggerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMqMessageSender {

  @Autowired
  private CustomLoggerService customLoggerService;

  private static final Logger log = LoggerFactory.getLogger(RabbitMqMessageSender.class);

  @Value("${mirbozorgi.queue.name}")
  private String mirbozorgiQueue;


  /**
   *
   */
  public void sendMessage(RabbitTemplate rabbitTemplate,
      String exchange,
      String routingKey,
      Object data) {
    MDC.put("queue", mirbozorgiQueue);
    customLoggerService
        .put("Sending message to the queue", data);
    rabbitTemplate.convertAndSend(exchange, routingKey, data);
    customLoggerService
        .put("The message has been sent to the queue.", data);

  }
}
