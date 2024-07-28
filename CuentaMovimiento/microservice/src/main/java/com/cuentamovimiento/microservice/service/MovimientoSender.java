package com.cuentamovimiento.microservice.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovimientoSender {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public MovimientoSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMovimiento(String mensaje) {
        rabbitTemplate.convertAndSend("movimientoQueue", mensaje);
    }
}
