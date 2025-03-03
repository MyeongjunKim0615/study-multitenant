package com.daou.common.postgresql.statics;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/// 스키마 기반 멀티테넌트를 구현하기 위한 클래스이다.
///
/// 스키마를 구분하기 위해 현재 Tenant를 식별하기 위한 클래스이다.
/// AbstractRoutingDataSource를 상속한다.
public class MultitenantDataSource extends AbstractRoutingDataSource {
    /// 키를 가져오는 함수
    @Override
    protected String determineCurrentLookupKey() {
        String tenantId = TenantContext.getCurrentTenant();
        return (tenantId != null) ? tenantId : "public"; // null일 경우 기본 테넌트 사용
    }
}
