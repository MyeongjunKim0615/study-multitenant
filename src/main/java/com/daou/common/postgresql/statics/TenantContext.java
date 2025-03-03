package com.daou.common.postgresql.statics;

///  현재 테넌트의 정보를 저장하는 클래스이다.
public class TenantContext {
    ///  현제 테넌트의 ID(식별자)를 저장하는 객체
    private static final ThreadLocal<String> CURRENT_TENANT = new ThreadLocal<>();

    ///  현재 테넌트의 ID를 반환
    public static String getCurrentTenant() {
        return CURRENT_TENANT.get();
    }

    ///  현재 테넌트의 ID를 저장
    public static void setCurrentTenant(String tenant) {
        CURRENT_TENANT.set(tenant);
    }

    public static void clear() {
        CURRENT_TENANT.remove();
    }

}
