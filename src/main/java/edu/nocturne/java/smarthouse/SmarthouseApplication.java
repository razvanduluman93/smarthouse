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
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SmarthouseApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmarthouseApplication.class, args);
    }

    @Bean
    public DynamoDB dynamoDB() {
        AWSStaticCredentialsProvider credentialsProvider = new AWSStaticCredentialsProvider(new BasicAWSCredentials("AKIAWKXWUP6I2EFF6MOJ",
                                                                                                                    "VWwW/oEg/3lRP5O5Kv2mGvnofc32pc9grHitEBmO"));
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
                                                           .withRegion("eu-west-1")
                                                           .withCredentials(credentialsProvider)
                                                           .build();
        return new DynamoDB(client);
    }

    @Bean
    public Table deviceEvents(DynamoDB dynamoDB) {
        return dynamoDB.getTable("device_events");
    }

    @Bean
    public Table devices(DynamoDB dynamoDB) {
        return dynamoDB.getTable("devices");
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
