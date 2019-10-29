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

        String secret = getSecret(accessKeysSecret, region);
        JSONObject jsonObject = new JSONObject(secret);

        return new BasicAWSCredentials(jsonObject.getString(accessKey), jsonObject.getString(secretKey));
    }

    @Bean
    ActiveMQConnectionFactory activeMQConnectionFactory() {

        String secret = getSecret(activeMqLoginSecret, region);
        JSONObject jsonObject = new JSONObject(secret);

        return new ActiveMQConnectionFactory(jsonObject.getString(activeMqUsername),
                                             jsonObject.getString(activeMqPassword),
                                             activeMqEndpoint);
    }

    private String getSecret(String secretName, String region) {
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
        return secret;
    }

}
