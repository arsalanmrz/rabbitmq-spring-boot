package com.mirbozorgi.rabbitmq.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


@Configuration
@PropertySource("classpath:application.properties")
public class ApplicationConfigReader {


  @Value("${mirbozorgi.exchange.name}")
  private String mirbozorgiExcahange;
  @Value("${mirbozorgi.queue.name}")
  private String mirbozorgiQueu;
  @Value("${mirbozorgi.routing.key}")
  private String mirbozorgiRoutingKey;

  public String getMirbozorgiExcahange() {
    return mirbozorgiExcahange;
  }

  public void setMirbozorgiExcahange(String mirbozorgiExcahange) {
    this.mirbozorgiExcahange = mirbozorgiExcahange;
  }

  public String getMirbozorgiQueu() {
    return mirbozorgiQueu;
  }

  public void setMirbozorgiQueu(String mirbozorgiQueu) {
    this.mirbozorgiQueu = mirbozorgiQueu;
  }

  public String getMirbozorgiRoutingKey() {
    return mirbozorgiRoutingKey;
  }

  public void setMirbozorgiRoutingKey(String mirbozorgiRoutingKey) {
    this.mirbozorgiRoutingKey = mirbozorgiRoutingKey;
  }
}
