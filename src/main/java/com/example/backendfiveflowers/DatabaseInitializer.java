package com.example.backendfiveflowers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitializer implements CommandLineRunner {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... args) throws Exception {
        String checkColumnSql = "SHOW COLUMNS FROM media LIKE 'media_id'";
        Boolean columnExists = jdbcTemplate.query(checkColumnSql, rs -> {
            if (rs.next()) {
                return true;
            } else {
                return false;
            }
        });

        if (!columnExists) {
            String sql = "ALTER TABLE media CHANGE COLUMN id media_id BIGINT NOT NULL AUTO_INCREMENT";
            jdbcTemplate.execute(sql);
        }
    }
}
