package edu.nocturne.java.smarthouse;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import static edu.nocturne.java.smarthouse.common.constant.TableNameConstants.DEVICES_TABLE;
import static edu.nocturne.java.smarthouse.common.constant.TableNameConstants.DEVICE_EVENTS_TABLE;

@SpringBootApplication
public class SmarthouseApplication {

    @Value("${cloud.aws.credentials.accessKey}")
    private String accessKey;
    @Value("${cloud.aws.credentials.secretKey}")
    private String secretKey;
    @Value("${cloud.aws.region.static}")
    private String region;

    public static void main(String[] args) {
        SpringApplication.run(SmarthouseApplication.class, args);
    }

    @Bean
    public DynamoDB dynamoDB() {
        AWSStaticCredentialsProvider credentialsProvider = new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey,
                                                                                                                    secretKey));
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
                                                           .withRegion(region)
                                                           .withCredentials(credentialsProvider)
                                                           .build();
        return new DynamoDB(client);
    }

    @Bean
    public Table deviceEvents(DynamoDB dynamoDB) {
        return dynamoDB.getTable(DEVICE_EVENTS_TABLE);
    }

    @Bean
    public Table devices(DynamoDB dynamoDB) {
        return dynamoDB.getTable(DEVICES_TABLE);
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper;
    }

}
