package com.daou.common.postgresql.statics.gpt;

import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Service
public class TenantService {
    private final DataSource dataSource;

    public TenantService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void createNewTenant(String tenantId) {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {

            // 새로운 테넌트(사용자)의 스키마 생성
            String createSchemaSQL = "CREATE SCHEMA IF NOT EXISTS " + tenantId;
            statement.executeUpdate(createSchemaSQL);

            // 해당 스키마에 기본 테이블 생성
            String createTableSQL = "CREATE TABLE IF NOT EXISTS " + tenantId + ".users (" +
                    "id SERIAL PRIMARY KEY, " +
                    "name VARCHAR(255) NOT NULL, " +
                    "email VARCHAR(255) UNIQUE NOT NULL)";
            statement.executeUpdate(createTableSQL);

            System.out.println("New schema created for tenant: " + tenantId);

        } catch (SQLException e) {
            throw new RuntimeException("Error creating schema for tenant: " + tenantId, e);
        }
    }
}

