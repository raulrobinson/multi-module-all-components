package com.demo.infrastructure.driven.userclientapi;

import com.demo.domain.ports.out.ParameterStoreOutGateway;
import com.demo.domain.ports.out.SecretManagerOutGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserClientApiAdapter {

    private final SecretManagerOutGateway secretManagerOutGateway;
    private final ParameterStoreOutGateway parameterStoreOutGateway;

}
