package com.demo.infrastructure.driven.parameterstore;

import com.demo.domain.ports.out.ParameterStoreOutGateway;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class ParameterStoreAdapter implements ParameterStoreOutGateway {

    private final ParameterValueProvider delegate;

    @Override
    public <T> Mono<T> getParameterValue(String parameterName, Class<T> valueType) {
        log.debug("Fetching parameter value for parameterName: {}", parameterName);
        return delegate.getParameterValue(parameterName, false)
                .doOnSuccess(value -> log.debug("Successfully fetched parameter value for parameterName: {}", parameterName))
                .doOnError(error -> log.error("Failed to fetch parameter value for parameterName: {}", parameterName, error))
                .handle((value, sink) -> {
                    try {
                        sink.next(JsonUtils.fromJson(value, valueType));
                    } catch (Exception e) {
                        log.error("Failed to parse parameter value for parameterName: {}", parameterName, e);
                        sink.error(new RuntimeException("Failed to parse parameter value", e));
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
