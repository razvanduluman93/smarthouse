package edu.nocturne.java.smarthouse;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import com.amazonaws.services.secretsmanager.model.*;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.aws.core.env.ResourceIdResolver;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;


@SpringBootApplication
public class SmarthouseApplication {

    @Value("${cloud.aws.region.static}")
    private String region;
    @Value("${cloud.aws.dynamodb.tables.DeviceEvents.name}")
    private String deviceEventsTable;
    @Value("${cloud.aws.dynamodb.tables.Devices.name}")
    private String devicesTable;

    public static void main(String[] args) {
        SpringApplication.run(SmarthouseApplication.class, args);
    }

    @Bean
    public BasicAWSCredentials basicAWSCredentials() {
        String accessKey;
        String secretKey;
        String secretName = "AWS";
        String region = "eu-west-1";

        AWSSecretsManager client = AWSSecretsManagerClientBuilder.standard()
                                                                 .withRegion(region)
                                                                 .build();

        String secret = null;
        GetSecretValueRequest getSecretValueRequest = new GetSecretValueRequest()
                .withSecretId(secretName);
        GetSecretValueResult getSecretValueResult;

        try {
            getSecretValueResult = client.getSecretValue(getSecretValueRequest);
        } catch (DecryptionFailureException | InternalServiceErrorException | InvalidParameterException | InvalidRequestException | ResourceNotFoundException e) {
            throw e;
        }

        if (getSecretValueResult.getSecretString() != null) {
            secret = getSecretValueResult.getSecretString();
        }

        // Your code goes here.
        JSONObject jsonObject = new JSONObject(secret);
        accessKey = jsonObject.getString("accessKey");
        secretKey = jsonObject.getString("secretKey");
        return new BasicAWSCredentials(accessKey, secretKey);
    }

    @Bean
    public DynamoDB dynamoDB() {
        AWSStaticCredentialsProvider credentialsProvider = new AWSStaticCredentialsProvider(basicAWSCredentials());
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
                                                           .withRegion(region)
                                                           .withCredentials(credentialsProvider)
                                                           .build();
        return new DynamoDB(client);
    }

    @Bean
    public Table deviceEvents(DynamoDB dynamoDB) {
        return dynamoDB.getTable(deviceEventsTable);
    }

    @Bean
    public Table devices(DynamoDB dynamoDB) {
        return dynamoDB.getTable(devicesTable);
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper;
    }

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
