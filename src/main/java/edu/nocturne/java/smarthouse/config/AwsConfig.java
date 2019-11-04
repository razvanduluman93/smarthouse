package edu.nocturne.java.smarthouse.config;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import com.amazonaws.services.secretsmanager.model.*;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@Configuration
public class AwsConfig {

    @Value("${cloud.aws.region.static}")
    private String region;
    @Value("${cloud.aws.secrets.accessKeys.name}")
    private String accessKeysSecret;
    @Value("${cloud.aws.secrets.accessKeys.keys.accessKey}")
    private String accessKey;
    @Value("${cloud.aws.secrets.accessKeys.keys.secretKey}")
    private String secretKey;
    @Value("${cloud.aws.secrets.activeMqLogin.name}")
    private String activeMqLoginSecret;
    @Value("${cloud.aws.secrets.activeMqLogin.keys.activeMqUsername}")
    private String activeMqUsername;
    @Value("${cloud.aws.secrets.activeMqLogin.keys.activeMqPassword}")
    private String activeMqPassword;
    @Value("${cloud.aws.activeMq.endpoint}")
    private String activeMqEndpoint;

    @Bean
    public BasicAWSCredentials basicAWSCredentials() {

        JSONObject jsonObject = getSecret(accessKeysSecret, region);

        return new BasicAWSCredentials(jsonObject.getString(accessKey),
                                       jsonObject.getString(secretKey));
    }

    @Bean
    ActiveMQConnectionFactory activeMQConnectionFactory() {

        JSONObject jsonObject = getSecret(activeMqLoginSecret, region);

        return new ActiveMQConnectionFactory(jsonObject.getString(activeMqUsername),
                                             jsonObject.getString(activeMqPassword),
                                             activeMqEndpoint);
    }

    @PostConstruct
    void started() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    private JSONObject getSecret(String secretName, String region) {
        AWSSecretsManager client = AWSSecretsManagerClientBuilder.standard()
                                                                 .withRegion(region)
                                                                 .build();

        String secret = null;
        GetSecretValueRequest getSecretValueRequest = new GetSecretValueRequest().withSecretId(secretName);
        GetSecretValueResult getSecretValueResult;

        try {
            getSecretValueResult = client.getSecretValue(getSecretValueRequest);
        } catch (DecryptionFailureException | InternalServiceErrorException | InvalidParameterException | InvalidRequestException | ResourceNotFoundException e) {
            throw e;
        }

        if (getSecretValueResult.getSecretString() != null) {
            secret = getSecretValueResult.getSecretString();
        }
        return new JSONObject(secret);
    }

}
