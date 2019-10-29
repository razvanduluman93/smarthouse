package edu.nocturne.java.smarthouse.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DynamoDBConfig {

    @Value("${cloud.aws.region.static}")
    private String region;
    @Value("${cloud.aws.dynamodb.tables.DeviceEvents.name}")
    private String deviceEventsTable;
    @Value("${cloud.aws.dynamodb.tables.Devices.name}")
    private String devicesTable;

    @Bean
    public DynamoDB dynamoDB(BasicAWSCredentials basicAWSCredentials) {
        AWSStaticCredentialsProvider credentialsProvider = new AWSStaticCredentialsProvider(basicAWSCredentials);
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

}
