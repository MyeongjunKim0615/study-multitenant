package com.daou.common.postgresql.statics;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Order(1)
class TenantFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, jakarta.servlet.ServletException {

        // 서블렛에서 요청을 가져오고 그 내부에 있는 tenantId를 식별해온다.
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String tenantId = httpRequest.getHeader("X-Tenant-ID"); // 헤더에서 테넌트 Id를 식별한다.

        if (tenantId == null || tenantId.isEmpty()) {
            tenantId = "public"; // ✅ 기본 테넌트 설정
        }
        TenantContext.setCurrentTenant(tenantId);

        // TODO: 해당 영역은 이해안감
        try {
            // 해당 동작이 제어될 동작을 의미한다.
            chain.doFilter(request, response);
        } finally {
            // 현재 테넌트가 재설정 되도록 보장한다?
            TenantContext.clear();
        }
    }
}