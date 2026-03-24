package com.demo.infrastructure.driven.secretmanager;

import reactor.core.publisher.Mono;

@FunctionalInterface
public interface SecretValueProvider {
    Mono<String> getSecretString(String secretId);
}

