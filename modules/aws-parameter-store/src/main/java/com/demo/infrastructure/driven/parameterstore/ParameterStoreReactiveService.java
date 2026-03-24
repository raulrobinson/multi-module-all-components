package com.demo.infrastructure.driven.parameterstore;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import software.amazon.awssdk.services.ssm.SsmClient;
import software.amazon.awssdk.services.ssm.model.GetParameterRequest;
import software.amazon.awssdk.services.ssm.model.GetParameterResponse;

import java.util.Objects;

public class ParameterStoreReactiveService implements ParameterValueProvider {

    private final SsmClient client;

    public ParameterStoreReactiveService(SsmClient client) {
        this.client = Objects.requireNonNull(client, "client must not be null");
    }

    @Override
    public Mono<String> getParameterValue(String parameterName, boolean decrypt) {
        return Mono.fromCallable(() -> {
                    GetParameterResponse response = client.getParameter(
                            GetParameterRequest.builder()
                                    .name(parameterName)
                                    .withDecryption(decrypt)
                                    .build()
                    );
                    return response.parameter().value();
                })
                .subscribeOn(Schedulers.boundedElastic());
    }

}
