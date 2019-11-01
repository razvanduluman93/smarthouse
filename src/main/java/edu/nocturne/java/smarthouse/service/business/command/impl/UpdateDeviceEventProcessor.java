package edu.nocturne.java.smarthouse.service.business.command.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import edu.nocturne.java.smarthouse.common.dto.EventNotification;
import edu.nocturne.java.smarthouse.common.type.Command;
import edu.nocturne.java.smarthouse.common.type.MessageStatus;
import edu.nocturne.java.smarthouse.common.validation.ValidationNotification;
import edu.nocturne.java.smarthouse.dao.DeviceEventsDao;
import edu.nocturne.java.smarthouse.domain.DeviceEvent;
import edu.nocturne.java.smarthouse.service.business.command.DeviceEventProcessor;
import edu.nocturne.java.smarthouse.service.business.query.DeviceProcessor;
import edu.nocturne.java.smarthouse.service.mapper.DeviceMapper;
import edu.nocturne.java.smarthouse.service.producer.ActiveMqMessageProducer;
import edu.nocturne.java.smarthouse.service.validator.DeviceEventValidatorChain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;

@Component
public class UpdateDeviceEventProcessor implements DeviceEventProcessor {

    private final DeviceEventsDao deviceEventsDao;
    private final DeviceProcessor deviceProcessor;
    private final DeviceMapper deviceMapper;
    private final DeviceEventValidatorChain deviceEventValidatorChain;
    private final ActiveMqMessageProducer activeMqMessageProducer;

    private Logger logger = LoggerFactory.getLogger(UpdateDeviceEventProcessor.class);

    @Autowired
    public UpdateDeviceEventProcessor(DeviceEventsDao deviceEventsDao,
                                      DeviceProcessor deviceProcessor,
                                      DeviceMapper deviceMapper,
                                      DeviceEventValidatorChain deviceEventValidatorChain,
                                      ActiveMqMessageProducer activeMqMessageProducer) {
        this.deviceEventsDao = deviceEventsDao;
        this.deviceProcessor = deviceProcessor;
        this.deviceMapper = deviceMapper;
        this.deviceEventValidatorChain = deviceEventValidatorChain;
        this.activeMqMessageProducer = activeMqMessageProducer;
    }

    @Override
    public void process(DeviceEvent deviceEvent) {
        ValidationNotification validationNotification = new ValidationNotification();
        deviceEventValidatorChain.validate(deviceEvent, validationNotification);
        if (validationNotification.hasNoErrors()) {
            deviceEventsDao.createDeviceEvent(deviceEvent);
            deviceProcessor.update(deviceMapper.map(deviceEvent));
            sendMessage(deviceEvent, validationNotification, MessageStatus.OK);
        } else {
            logger.error(validationNotification.toString());
            sendMessage(deviceEvent, validationNotification, MessageStatus.FAILURE);
        }
    }

    @Override
    public Command getCommand() {
        return Command.UPDATE;
    }

    private void sendMessage(DeviceEvent deviceEvent, ValidationNotification validationNotification, MessageStatus messageStatus) {
        try {
            activeMqMessageProducer.sendNotification(EventNotification.builder()
                                                                      .validationNotification(validationNotification)
                                                                      .deviceReference(deviceEvent.getDeviceReference())
                                                                      .houseReference(deviceEvent.getHouseReference())
                                                                      .messageStatus(messageStatus)
                                                                      .build());
        } catch (JMSException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
