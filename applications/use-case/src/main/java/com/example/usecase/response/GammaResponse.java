package com.example.usecase.response;

import lombok.Builder;

@Builder
public record GammaResponse(
        String id, String description, boolean active
) {}
