package com.example.usecase.response;

import lombok.Builder;

@Builder
public record BetaResponse(
        String id, String category, Double value
) {}
