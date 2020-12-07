package com.mirbozorgi.rabbitmq;

import com.mirbozorgi.rabbitmq.config.ApplicationConfigReader;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;

@EnableRabbit
@SpringBootApplication
public class RabbitmqApplication extends SpringBootServletInitializer implements
    RabbitListenerConfigurer {

  @Autowired
  private ApplicationConfigReader applicationConfig;

  public ApplicationConfigReader getApplicationConfig() {
    return applicationConfig;
  }

  public void setApplicationConfig(ApplicationConfigReader applicationConfig) {
    this.applicationConfig = applicationConfig;
  }

  public static void main(String[] args) {
    Thread thread = new Thread();
    thread.setName("rabbitmq-retry");
    thread.start();
    SpringApplication.run(RabbitmqApplication.class, args);
  }

  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(RabbitmqApplication.class);
  }


  // * This bean is to read the properties file configs */
  @Bean
  public ApplicationConfigReader applicationConfig() {
    return new ApplicationConfigReader();
  }

  /* Creating a bean for the Message queue Exchange */
  @Bean
  public TopicExchange getApp1Exchange() {
    return new TopicExchange(getApplicationConfig().getMirbozorgiExcahange());
  }

  /* Creating a bean for the Message queue */
  @Bean
  public Queue getApp1Queue() {
    return new Queue(getApplicationConfig().getMirbozorgiQueu());
  }

  /* Binding between Exchange and Queue using routing key */
  @Bean
  public Binding declareBindingApp1() {
    return BindingBuilder.bind(getApp1Queue()).to(getApp1Exchange())
        .with(getApplicationConfig().getMirbozorgiRoutingKey());
  }

  /* Bean for rabbitTemplate */
  @Bean
  public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
    final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
    rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
    return rabbitTemplate;
  }

  @Bean
  public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
    return new Jackson2JsonMessageConverter();
  }

  @Bean
  public MappingJackson2MessageConverter consumerJackson2MessageConverter() {
    return new MappingJackson2MessageConverter();
  }

  @Bean
  public DefaultMessageHandlerMethodFactory messageHandlerMethodFactory() {
    DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
    factory.setMessageConverter(consumerJackson2MessageConverter());
    return factory;
  }

  @Override
  public void configureRabbitListeners(final RabbitListenerEndpointRegistrar registrar) {
    registrar.setMessageHandlerMethodFactory(messageHandlerMethodFactory());
  }

}
