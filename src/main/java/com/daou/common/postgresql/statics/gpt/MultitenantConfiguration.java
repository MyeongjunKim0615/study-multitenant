package com.daou.common.postgresql.statics.gpt;

import com.daou.common.postgresql.statics.MultitenantDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
class MultitenantConfiguration {
    private final Map<Object, Object> dataSourceMap = new ConcurrentHashMap<>();

    @Value("${defaultTenant:public}") // 환경 변수에서 읽되, 없으면 "public" 사용
    private String defaultTenant;

    @Bean
    public DataSource dataSource() {
        MultitenantDataSource dataSource = new MultitenantDataSource();

        // ✅ 기본 테넌트(public) 데이터 소스 추가
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.url("jdbc:postgresql://localhost:5432/test?currentSchema=public");
        dataSourceBuilder.username("user1");
        dataSourceBuilder.password("user1234");
        dataSourceBuilder.driverClassName("org.postgresql.Driver");

        DataSource defaultDataSource = dataSourceBuilder.build();
        dataSourceMap.put("public", defaultDataSource); // 기본 테넌트 추가

        // ✅ 초기화
        dataSource.setTargetDataSources(dataSourceMap);
        dataSource.setDefaultTargetDataSource(defaultDataSource);
        dataSource.afterPropertiesSet();

        return dataSource;
    }


    public void addNewTenant(String tenantId) {
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.url("jdbc:postgresql://localhost:5432/test?currentSchema=" + tenantId);
        dataSourceBuilder.username("postgres");
        dataSourceBuilder.password("password");
        dataSourceBuilder.driverClassName("org.postgresql.Driver");

        DataSource newDataSource = dataSourceBuilder.build();
        dataSourceMap.put(tenantId, newDataSource);

        // 💡 새롭게 설정한 테넌트를 런타임에서 반영
        ((MultitenantDataSource) dataSource()).setTargetDataSources(dataSourceMap);
        ((MultitenantDataSource) dataSource()).afterPropertiesSet();
    }

}
