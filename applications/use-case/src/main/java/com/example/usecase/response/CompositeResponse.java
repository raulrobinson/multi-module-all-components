package com.example.usecase.response;

import lombok.Builder;

@Builder
public record CompositeResponse(
        AlphaResponse alpha,
        BetaResponse  beta,
        GammaResponse gamma
) {}
