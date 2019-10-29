package edu.nocturne.java.smarthouse.config;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cloud.aws.core.env.ResourceIdResolver;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;

@Configuration
public class SQSConfig {

    @Bean
    public QueueMessagingTemplate queueMessagingTemplate(AmazonSQSAsync amazonSqs, ResourceIdResolver resourceIdResolver, ObjectMapper objectMapper) {
        QueueMessagingTemplate queueMessagingTemplate = new QueueMessagingTemplate(amazonSqs, resourceIdResolver);

        MappingJackson2MessageConverter jacksonMessageConverter = new MappingJackson2MessageConverter();
        jacksonMessageConverter.setSerializedPayloadClass(String.class);
        jacksonMessageConverter.setObjectMapper(objectMapper);
        jacksonMessageConverter.setStrictContentTypeMatch(false);

        queueMessagingTemplate.setMessageConverter(jacksonMessageConverter);

        return queueMessagingTemplate;
    }

}
