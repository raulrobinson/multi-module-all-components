package com.demo.infrastructure.driven.secretmanager;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.util.StringUtils;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClientBuilder;

import java.net.URI;

@AutoConfiguration
@ConditionalOnClass(SecretsManagerClient.class)
@EnableConfigurationProperties(SecretsManagerProperties.class)
public class SecretsManagerConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public SecretsManagerClient secretsManagerClient(SecretsManagerProperties properties) {

        if (!StringUtils.hasText(properties.getRegion())) {
            throw new IllegalArgumentException("Property aws.secrets.region is required");
        }

        SecretsManagerClientBuilder builder = SecretsManagerClient.builder()
                .region(Region.of(properties.getRegion()));

        if (StringUtils.hasText(properties.getEndpointOverride())) {
            builder.endpointOverride(URI.create(properties.getEndpointOverride()));
        }

        return builder.build();
    }

    @Bean
    @ConditionalOnMissingBean(SecretValueProvider.class)
    public SecretValueProvider secretsManagerReactiveService(SecretsManagerClient client) {
        return new SecretsManagerReactiveService(client);
    }

}
