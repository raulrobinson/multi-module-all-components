package com.demo.infrastructure.driven.parameterstore;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Setter
@Getter
@ConfigurationProperties(prefix = "aws.parameters")
public class ParameterStoreProperties {

    /**
     * AWS region, for example: us-east-1.
     */
    private String region;

    /**
     * Optional endpoint override (useful for local tests with LocalStack).
     */
    private String endpointOverride;

}

