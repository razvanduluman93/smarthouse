package edu.nocturne.java.smarthouse.service.business.command.impl;

import edu.nocturne.java.smarthouse.common.type.Command;
import edu.nocturne.java.smarthouse.common.validation.ValidationNotification;
import edu.nocturne.java.smarthouse.dao.DeviceEventsDao;
import edu.nocturne.java.smarthouse.domain.DeviceEvent;
import edu.nocturne.java.smarthouse.service.business.command.DeviceEventProcessor;
import edu.nocturne.java.smarthouse.service.business.query.DeviceProcessor;
import edu.nocturne.java.smarthouse.service.mapper.DeviceMapper;
import edu.nocturne.java.smarthouse.service.validator.DeviceEventValidatorChain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UpdateDeviceEventProcessor implements DeviceEventProcessor {

    private final DeviceEventsDao deviceEventsDao;
    private final DeviceProcessor deviceProcessor;
    private final DeviceMapper deviceMapper;
    private final DeviceEventValidatorChain deviceEventValidatorChain;

    private Logger logger = LoggerFactory.getLogger(UpdateDeviceEventProcessor.class);

    @Autowired
    public UpdateDeviceEventProcessor(DeviceEventsDao deviceEventsDao,
                                      DeviceProcessor deviceProcessor,
                                      DeviceMapper deviceMapper,
                                      DeviceEventValidatorChain deviceEventValidatorChain) {
        this.deviceEventsDao = deviceEventsDao;
        this.deviceProcessor = deviceProcessor;
        this.deviceMapper = deviceMapper;
        this.deviceEventValidatorChain = deviceEventValidatorChain;
    }

    @Override
    public void process(DeviceEvent deviceEvent) {
        ValidationNotification validationNotification = new ValidationNotification();
        deviceEventValidatorChain.validate(deviceEvent, validationNotification);
        if (validationNotification.hasNoErrors()) {
            deviceEventsDao.createDeviceEvent(deviceEvent);
            deviceProcessor.update(deviceMapper.map(deviceEvent));
        } else {
            logger.error(validationNotification.toString());
        }
    }

    @Override
    public Command getCommand() {
        return Command.UPDATE;
    }

}
