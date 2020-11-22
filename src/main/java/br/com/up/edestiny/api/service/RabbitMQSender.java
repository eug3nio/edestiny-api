package br.com.up.edestiny.api.service;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQSender {
	
	@Autowired
	private AmqpTemplate rabbitTemplate;
	
	@Value("${rabbitmq.exchange}")
	private String exchange;
	
	@Value("${rabbitmq.routingkey}")
	private String routingkey;	
	
	public void send(String mensagem) {
		System.out.println(routingkey + " - " + mensagem);
		rabbitTemplate.convertAndSend(routingkey, mensagem);
		System.out.println("Send msg = " + mensagem);
	}
}