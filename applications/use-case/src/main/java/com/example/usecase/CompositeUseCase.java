package com.example.usecase;

import com.example.domain.model.CompositeResult;
import com.example.domain.ports.out.AlphaGateway;
import com.example.domain.ports.out.BetaGateway;
import com.example.domain.ports.out.GammaGateway;
import com.example.usecase.mapper.CompositeResponseMapper;
import com.example.usecase.response.CompositeResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Orchestrates parallel calls to Alpha, Beta and Gamma external APIs.
 * All three calls run concurrently via Mono.zip — total latency equals
 * the slowest of the three, not the sum.
 *
 * Each gateway applies its own Circuit Breaker and Retry loaded from
 * AWS SSM Parameter Store (cached 12 h).
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CompositeUseCase {

    private final AlphaGateway             alphaGateway;
    private final BetaGateway              betaGateway;
    private final GammaGateway             gammaGateway;
    private final CompositeResponseMapper  mapper;

    public Mono<CompositeResponse> execute(String id) {
        log.info("[USE-CASE] Starting composite retrieval for id={}", id);

        return Mono.zip(
                alphaGateway.findById(id),
                betaGateway.findById(id),
                gammaGateway.findById(id)
        )
        .map(tuple -> CompositeResult.builder()
                .alpha(tuple.getT1())
                .beta(tuple.getT2())
                .gamma(tuple.getT3())
                .build())
        .map(mapper::toResponse)
        .doOnNext(r  -> log.info("[USE-CASE] Composite OK for id={}", id))
        .doOnError(e -> log.error("[USE-CASE] Composite FAILED for id={}: {}", id, e.getMessage()));
    }
}
