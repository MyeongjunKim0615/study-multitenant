package com.daou.common.postgresql.statics.gpt;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/register")
public class UserRegistrationController {
    private final TenantService tenantService;
    private final MultitenantConfiguration multitenantConfiguration;

    /// 생성자: 파라미터로 받는게, 의존성 주입을 위한 것인가?
    /// TODO: 분석할 것
    public UserRegistrationController(TenantService tenantService, MultitenantConfiguration multitenantConfiguration) {
        this.tenantService = tenantService;
        this.multitenantConfiguration = multitenantConfiguration;
    }

    /// 새로운 테넌트를 등록하는 함수
    @PostMapping("/api/v1/tenant")
    public ResponseEntity<String> registerTenant(@RequestBody String tenantId) {
        // 1. 새로운 테넌트 스키마 생성
        tenantService.createNewTenant(tenantId);

        // 2. 새로운 테넌트를 데이터소스에 추가
        multitenantConfiguration.addNewTenant(tenantId);

        // 응답 반환
        return ResponseEntity.ok("Tenant " + tenantId + " registered successfully.");
    }
}

