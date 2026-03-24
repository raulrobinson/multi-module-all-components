package com.demo.domain.ports.out;

import reactor.core.publisher.Mono;

public interface ParameterStoreOutGateway {
    <T> Mono<T> getParameterValue(String secretId, Class<T> valueType);
}
