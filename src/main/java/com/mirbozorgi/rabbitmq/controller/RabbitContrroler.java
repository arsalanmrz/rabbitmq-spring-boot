package com.mirbozorgi.rabbitmq.controller;


import com.mirbozorgi.rabbitmq.config.ApplicationConfigReader;
import com.mirbozorgi.rabbitmq.service.RabbitMqMessageSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

//This is test contoller//


@RestController
@RequestMapping(path = "/rabbit")
public class RabbitContrroler {


  private static final Logger log = LoggerFactory.getLogger(RabbitContrroler.class);

  @Autowired
  private RabbitTemplate rabbitTemplate;

  @Autowired
  private ApplicationConfigReader applicationConfig;

  @Autowired
  private RabbitMqMessageSender messageSender;


  @RequestMapping(path = "/add", method = RequestMethod.POST)
  public ResponseEntity<?> sendMessage(@RequestBody Object user) {

    String exchange = applicationConfig.getMirbozorgiExcahange();
    String routingKey = applicationConfig.getMirbozorgiRoutingKey();

    /* Sending to Message Queue */
    try {
      messageSender.sendMessage(rabbitTemplate, exchange, routingKey, user);
      return new ResponseEntity<String>("IN QUEUE", HttpStatus.OK);
    } catch (Exception ex) {
      log.error("Exception occurred while sending message to the queue. Exception= {}", ex);
      return new ResponseEntity("IN QUEUE SEND ERROR",
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

}
