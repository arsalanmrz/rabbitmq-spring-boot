### rabbitmq-spring-boot

#### follow these steps:
- first download and install docker from this link : `https://docs.docker.com/get-docker/`
- pull the rabbitmq image from docker hub : `sudo docker pull rabbitmq:3-management-alpine`
- init the node of docker swarm : `sudo docker swarm init`
- deploy the service of rabbitmq : `sudo docker stack deploy -c  path-to-reach-the-docker-compose/docker-compose-rabbitmq.yml rabbitmq`
- go to your browser and put this link : `http://yourIpMaChine:15672` , `user : guest`, `pass:guest`
- #### now the rabbitmq service is available .

- run the repository`(java application)` in your ide or docker or tomcat .
- now java is bound to rabbitmq. 
- the only api of this java application is : `yourIpMachine:6040/rabbit/add` with `POST` method
- the body of above api is like the sample.
- Use Case of this api :
  - for example, you have an API and every single request must be returned `200` status but your server or your machine`(for example http://localhost:5030/)` is down now, so you push your request to rabbitmq's quest to save this request from exception and whenever your machine will return live again, requests will listen from the queue. I set requeue number to` 8` times` rabbitmq.max.requed.fail.response=${rabbitmq_max_requed_fail_response:8}` in application. properties which mean that , every  request, if one request fail 8 times, It will be removed from the queue. (you can increase this number to your preferences)


##### Sample for body of `yourIpMachine:6040/rabbit/add`
``` js
{
    "method": "POST",
    "url": "http://localhost:5030/game/register1",
    "body": {
        "name": 1,
        "sid": 123
    }

}



```
