package com.demo.infrastructure.driven.secretmanager;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;

import java.util.Objects;

public class SecretsManagerReactiveService implements SecretValueProvider {

    private final SecretsManagerClient client;

    public SecretsManagerReactiveService(SecretsManagerClient client) {
        this.client = Objects.requireNonNull(client, "client must not be null");
    }

    @Override
    public Mono<String> getSecretString(String secretId) {
        return Mono.fromCallable(() -> {
                    GetSecretValueResponse response = client.getSecretValue(
                            GetSecretValueRequest.builder().secretId(secretId).build()
                    );
                    return response.secretString();
                })
                .subscribeOn(Schedulers.boundedElastic());
    }
}
