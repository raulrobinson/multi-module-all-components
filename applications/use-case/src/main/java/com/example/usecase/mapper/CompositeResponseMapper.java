package com.example.usecase.mapper;

import com.example.domain.model.AlphaResult;
import com.example.domain.model.BetaResult;
import com.example.domain.model.CompositeResult;
import com.example.domain.model.GammaResult;
import com.example.usecase.response.AlphaResponse;
import com.example.usecase.response.BetaResponse;
import com.example.usecase.response.CompositeResponse;
import com.example.usecase.response.GammaResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CompositeResponseMapper {

    CompositeResponse toResponse(CompositeResult result);

    AlphaResponse toResponse(AlphaResult result);
    BetaResponse  toResponse(BetaResult  result);
    GammaResponse toResponse(GammaResult result);
}
