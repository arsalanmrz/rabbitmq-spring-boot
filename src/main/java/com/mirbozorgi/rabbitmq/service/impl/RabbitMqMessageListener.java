package com.mirbozorgi.rabbitmq.service.impl;



import com.mirbozorgi.rabbitmq.domain.ApiResponse;
import com.mirbozorgi.rabbitmq.domain.ExternalCallStructure;
import com.mirbozorgi.rabbitmq.domain.HttpRequestModel;
import com.mirbozorgi.rabbitmq.service.CustomLoggerService;
import com.mirbozorgi.rabbitmq.service.HttpClientService;
import com.mirbozorgi.rabbitmq.service.SerializerService;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import org.slf4j.MDC;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;

/**
 * Message Listener for RabbitMQ
 */
@Service
public class RabbitMqMessageListener {


  @Autowired
  private HttpClientService httpClientService;

  @Autowired
  private SerializerService serializerService;

  @Autowired
  private CustomLoggerService customLoggerService;


  @Value("${rabbitmq.max.requed.fail.response}")
  private int count;

  @Value("${rabbitmq.thread.sleep.after.api-exception}")
  private long threadSleep;


  @Value("${mirbozorgi.queue.name}")
  private String mirbozorgiQueue;


  private CountDownLatch countDownLatch = new CountDownLatch(10);

  private Message message = null;

  @RabbitListener(queues = "${mirbozorgi.queue.name}")
  public void receiveMessageForMirbozorgi(final Message message) throws InterruptedException {
    this.message = message;
    ApiResponse<ExternalCallStructure> response = null;
    HttpRequestModel request = mesesageToHttpRequest(message);

    try {

      MDC.put("queue", mirbozorgiQueue);
      customLoggerService
          .put("Received message", request);

      MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
      if (request.getHeaders() != null) {
        for (String s : request.getHeaders().keySet()) {
          headers.put(s, Collections.singletonList(request.getHeaders().get(s)));
        }
      }

      response = httpClientService.request(
          request.getMethod(),
          request.getUrl(),
          request.getBody(),
          request.getQueryData(),
          headers,
          ExternalCallStructure.class

      );

      customLoggerService
          .put("response of " + request.getUrl(), response);
    } catch (Exception e) {
      handleApiExceptions(e, response);
    }
  }


  private void handleApiExceptions(Exception e, ApiResponse response) throws InterruptedException {

    customLoggerService.put("api-call-exception", e);
    countDownLatch.countDown();
    Thread.sleep(threadSleep);

    customLoggerService.put("thread-sleep-milli-second", threadSleep);

    if (countDownLatch.getCount() == 0) {
      countDownLatch = new CountDownLatch(count);

      customLoggerService.put("message-reject-from-queue", message);

      throw new AmqpRejectAndDontRequeueException(e);
    }

    customLoggerService.put("message-requeue-to-queue", message);
    throw new HttpClientErrorException(response.getStatus());

  }


  private HttpRequestModel mesesageToHttpRequest(Message message) {
    HttpMethod method = null;
    byte[] bodyMessage = message.getBody();
    String result = new String(bodyMessage);

    Object object = serializerService
        .toObj(result, Object.class);

    String url = (String) ((LinkedHashMap) object).get("url");
    Object body = ((LinkedHashMap) object).get("body");
    Map<String, String> headers = (Map<String, String>) ((LinkedHashMap) object).get("headers");
    Map<String, ?> queryData = (Map<String, ?>) ((LinkedHashMap) object).get("queryData");
    String stringMethod = (String) ((LinkedHashMap) object).get("method");

    switch (stringMethod) {
      case "GET":
        method = HttpMethod.GET;
        break;
      case "PUT":
        method = HttpMethod.PUT;
        break;
      case "DELETE":
        method = HttpMethod.DELETE;
        break;
      default:
        method = HttpMethod.POST;
        break;
    }

    return new HttpRequestModel(method, url, body, queryData, headers);
  }

}
