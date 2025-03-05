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

    @Value("${spring.datasource.url}")
    private String _url;

    @Value("${spring.datasource.username}")
    private String _username;

    @Value("${spring.datasource.password}")
    private String _password;

    @Value("${spring.datasource.driver-class-name}")
    private String _driverClassName;

    /// 멀티테넌트 데이터 소스를 선언하는 함수
    ///
    /// 이후 사용할 때 우리는 (MultitenantDataSource)로 다운 캐스팅을 해야한다.
    /// TODO: 멀티테넌트 데이터 소스 의미 조사
    @Bean
    public DataSource dataSource() {
        // 멀티테넌트 데이터 소스 클래스 생성
        MultitenantDataSource dataSource = new MultitenantDataSource();

        // 기본 테넌트(public) 데이터 소스 추가
        DataSource defaultDataSource = DataSourceBuilder
                .create()
                .url(_url)
                .username(_username)
                .password(_password)
                .driverClassName(_driverClassName)
                .build();

        // 기본 테넌트 추가 // TODO: 조사 필요
        // public으로 데이터 소스 매핑
        dataSourceMap.put("public", defaultDataSource);

        dataSource.setTargetDataSources(dataSourceMap); // 멀티테넌트 데이터 소스로 추가
        dataSource.setDefaultTargetDataSource(defaultDataSource); // 기본 데이터 소스로 지정
        dataSource.afterPropertiesSet(); // TODO: 조사 필요

        return dataSource; // 멀티 테넌트 데이터 소스 반환
    }

    /// 파라미터로 전달받은 tenantId로 스키마를 생성하는 함수
    public void addNewTenant(String tenantId) {
        DataSource newDataSource = DataSourceBuilder.create()
                // 부여받은 테넌트 아이디로 스키마 생성
                .url(_url + "?currentSchema=" + tenantId)
                .username(_username)
                .password(_password)
                .driverClassName(_driverClassName)
                .build();

        // 담당 테넌트 아이디와 데이터 소스 매핑
        dataSourceMap.put(tenantId, newDataSource);

        // 새롭게 설정한 테넌트를 런타임에서 반영
        // 새로운 데이터 소스를 넣은 Mapper 객체 갱신
        ((MultitenantDataSource) dataSource()).setTargetDataSources(dataSourceMap);
        ((MultitenantDataSource) dataSource()).afterPropertiesSet();
    }
}
