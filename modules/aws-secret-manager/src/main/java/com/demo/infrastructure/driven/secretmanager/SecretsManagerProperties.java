package com.demo.infrastructure.driven.secretmanager;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Setter
@Getter
@ConfigurationProperties(prefix = "aws.secrets")
public class SecretsManagerProperties {

    /**
     * AWS region, for example: us-east-1.
     */
    private String region;

    /**
     * Optional endpoint override (useful for local tests with LocalStack).
     */
    private String endpointOverride;

}

