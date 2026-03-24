package com.demo.infrastructure.driven.parameterstore;

import reactor.core.publisher.Mono;

@FunctionalInterface
public interface ParameterValueProvider {
    Mono<String> getParameterValue(String parameterName, boolean decrypt);
}

