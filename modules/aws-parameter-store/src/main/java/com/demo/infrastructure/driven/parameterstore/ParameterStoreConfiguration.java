package com.demo.infrastructure.driven.parameterstore;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.util.StringUtils;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ssm.SsmClient;
import software.amazon.awssdk.services.ssm.SsmClientBuilder;

import java.net.URI;

@AutoConfiguration
@ConditionalOnClass(SsmClient.class)
@EnableConfigurationProperties(ParameterStoreProperties.class)
public class ParameterStoreConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public SsmClient ssmClient(ParameterStoreProperties properties) {

        if (!StringUtils.hasText(properties.getRegion())) {
            throw new IllegalArgumentException("Property aws.parameters.region is required");
        }

        SsmClientBuilder builder = SsmClient.builder()
                .region(Region.of(properties.getRegion()));

        if (StringUtils.hasText(properties.getEndpointOverride())) {
            builder.endpointOverride(URI.create(properties.getEndpointOverride()));
        }

        return builder.build();
    }

    @Bean
    @ConditionalOnMissingBean(ParameterValueProvider.class)
    public ParameterValueProvider parameterStoreReactiveService(SsmClient client) {
        return new ParameterStoreReactiveService(client);
    }

}
