package edu.nocturne.java.smarthouse.service.producer.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.nocturne.java.smarthouse.common.dto.EventNotification;
import edu.nocturne.java.smarthouse.service.producer.ActiveMqMessageProducer;
import org.apache.activemq.jms.pool.PooledConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.*;

@Component
public class ActiveMqMessageProducerImpl implements ActiveMqMessageProducer {

    private static final String NOCTURNE_QUEUE = "nocturne";

    private final PooledConnectionFactory pooledConnectionFactory;
    private final ObjectMapper objectMapper;

    @Autowired
    public ActiveMqMessageProducerImpl(PooledConnectionFactory pooledConnectionFactory,
                                       ObjectMapper objectMapper) {
        this.pooledConnectionFactory = pooledConnectionFactory;
        this.objectMapper = objectMapper;
    }

    @Override
    public void sendNotification(EventNotification eventNotification) throws JMSException, JsonProcessingException {
        Connection producerConnection = pooledConnectionFactory.createConnection();
        producerConnection.start();

        Session producerSession = producerConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination producerDestination = producerSession.createQueue(NOCTURNE_QUEUE);

        MessageProducer producer = producerSession.createProducer(producerDestination);
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

        producer.send(producerSession.createTextMessage(objectMapper.writeValueAsString(eventNotification)));

        producer.close();
        producerSession.close();
        producerConnection.close();
    }
}
