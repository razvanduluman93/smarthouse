package edu.nocturne.java.smarthouse.service.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import edu.nocturne.java.smarthouse.common.dto.EventNotification;

import javax.jms.JMSException;

public interface ActiveMqMessageProducer {

    void sendNotification(EventNotification eventNotification) throws JMSException, JsonProcessingException;

}
