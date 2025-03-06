package com.daou.common.postgresql.statics.gpt;

import com.daou.exception.ControlledException;
import com.daou.exception.errorcode.BasicErrorCode;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Service
public class TenantService {
    private final DataSource dataSource;

    /// 객체 주입
    public TenantService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /// 새로운 테넌트를 추가하는 함수
    public void createNewTenant(String tenantId) {
        try ( // 여기에 있는 소괄호`( )`는 자동으로 close()를 수행해준다.
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement()
        ) {
            // 새로운 테넌트(사용자)의 스키마 생성
            statement.executeUpdate("CREATE SCHEMA IF NOT EXISTS $tenantId");

            // 해당 스키마에 기본 테이블 생성
            statement.executeUpdate(
            "CREATE TABLE IF NOT EXISTS ${tenantId}.users (" + // users는 테이블 이름
                "id SERIAL PRIMARY KEY, " +
                "name VARCHAR(255) NOT NULL, " +
                "email VARCHAR(255) UNIQUE NOT NULL)"
            );
        } catch (SQLException e) {
            throw new ControlledException(BasicErrorCode.DATA_BASE_ERROR);
        }
    }
}

