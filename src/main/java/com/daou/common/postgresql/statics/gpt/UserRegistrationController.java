package com.daou.common.postgresql.statics.gpt;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/register")
public class UserRegistrationController {
    private final TenantService tenantService;
    private final MultitenantConfiguration multitenantConfiguration;

    public UserRegistrationController(TenantService tenantService, MultitenantConfiguration multitenantConfiguration) {
        this.tenantService = tenantService;
        this.multitenantConfiguration = multitenantConfiguration;
    }

    @PostMapping("{tenantId}")
    public ResponseEntity<String> registerTenant(@PathVariable String tenantId) {
        // 1. 새로운 테넌트 스키마 생성
        tenantService.createNewTenant(tenantId);

        // 2. 새로운 테넌트를 데이터소스에 추가
        multitenantConfiguration.addNewTenant(tenantId);

        return ResponseEntity.ok("Tenant " + tenantId + " registered successfully.");
    }
}

