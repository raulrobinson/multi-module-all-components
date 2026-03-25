package com.example.usecase.response;

import lombok.Builder;

@Builder
public record AlphaResponse(
        String id, String name, String status, String data
) {}
