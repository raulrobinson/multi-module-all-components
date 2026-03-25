package com.demo.infrastructure.driven.parameterstore;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Setter
@Getter
@ConfigurationProperties(prefix = "aws.parameters")
public class ParameterStoreProperties {
    private String region;
    private String endpointOverride;
}

