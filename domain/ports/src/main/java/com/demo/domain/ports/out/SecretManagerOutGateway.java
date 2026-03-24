package com.demo.domain.ports.out;

import reactor.core.publisher.Mono;

public interface SecretManagerOutGateway {
    <T> Mono<T> getSecretValue(String secretId, Class<T> valueType);
}
