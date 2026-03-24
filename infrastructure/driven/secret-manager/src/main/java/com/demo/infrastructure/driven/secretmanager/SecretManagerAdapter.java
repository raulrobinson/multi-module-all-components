package com.demo.infrastructure.driven.secretmanager;

import com.demo.domain.ports.out.SecretManagerOutGateway;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class SecretManagerAdapter implements SecretManagerOutGateway {

    private final SecretValueProvider delegate;

    @Override
    public <T> Mono<T> getSecretValue(String secretId, Class<T> valueType) {
        log.debug("Fetching secret value for secretId: {}", secretId);
        return delegate.getSecretString(secretId)
                .doOnSuccess(value -> log.debug("Successfully fetched secret value for secretId: {}", secretId))
                .doOnError(error -> log.error("Failed to fetch secret value for secretId: {}", secretId, error))
                .handle((value, sink) -> {
                    try {
                        sink.next(JsonUtils.fromJson(value, valueType));
                    } catch (Exception e) {
                        log.error("Failed to parse secret value for secretId: {}", secretId, e);
                        sink.error(new RuntimeException("Failed to parse secret value", e));
                    }
                });
    }

    private static class JsonUtils {
        private static final ObjectMapper objectMapper = new ObjectMapper();
        public static <T> T fromJson(String json, Class<T> valueType) throws Exception {
            return objectMapper.readValue(json, valueType);
        }
    }

}
