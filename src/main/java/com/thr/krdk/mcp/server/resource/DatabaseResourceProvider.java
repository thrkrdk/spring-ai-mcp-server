package com.thr.krdk.mcp.server.resource;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.logaritex.mcp.annotation.McpResource;
import io.modelcontextprotocol.server.McpSyncServerExchange;
import io.modelcontextprotocol.spec.McpSchema.ReadResourceRequest;
import io.modelcontextprotocol.spec.McpSchema.ReadResourceResult;
import io.modelcontextprotocol.spec.McpSchema.ResourceContents;
import io.modelcontextprotocol.spec.McpSchema.TextResourceContents;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DatabaseResourceProvider {

    private final JdbcTemplate jdbcTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public DatabaseResourceProvider(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    public void initializeSchema() {
        // Basit bir 'users' tablosu oluşturdum
        jdbcTemplate.execute(
                "CREATE TABLE IF NOT EXISTS users (" +
                        "id VARCHAR(50) PRIMARY KEY, " +
                        "name VARCHAR(100), " +
                        "email VARCHAR(100)" +
                        ")"
        );
        // Örnek veri ekle (varsa güncelleme)
        jdbcTemplate.update(
                "MERGE INTO users KEY(id) VALUES (?, ?, ?)",
                "1", "Ali Kaya", "ali.kaya@deneme.com"
        );
        jdbcTemplate.update(
                "MERGE INTO users KEY(id) VALUES (?, ?, ?)",
                "2", "Ayşe Döne", "ayse.done@deneme.com"
        );
    }

    /**
     * Tek bir tablo satırını JSON formatında döner
     */
    @McpResource(
            uri = "database-row://{table}/{id}",
            name = "Database Row",
            description = "Belirtilen tablo ve ID için veri satırı döner",
            mimeType = "application/json"
    )
    public ReadResourceResult getRow(ReadResourceRequest request, String table, String id) {
        Map<String, Object> row = jdbcTemplate.queryForMap(
                String.format("SELECT * FROM %s WHERE id = ?", table), id
        );
        String json;
        try {
            json = objectMapper.writeValueAsString(row);
        } catch (JsonProcessingException e) {
            json = "{\"error\": \"JSON parsing error\"}";
        }
        ResourceContents contents = new TextResourceContents(
                request.uri(),
                "application/json",
                json
        );
        return new ReadResourceResult(List.of(contents));
    }

    /**
     * Tablodaki tüm satırları liste halinde döner
     */
    @McpResource(
            uri = "database-table://{table}",
            name = "Database Table",
            description = "Belirtilen tablodaki tüm satırları JSON listesi olarak döner",
            mimeType = "application/json"
    )
    public ReadResourceResult listTable(ReadResourceRequest request, String table) {
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(
                String.format("SELECT * FROM %s", table)
        );
        String json;
        try {
            json = objectMapper.writeValueAsString(rows);
        } catch (JsonProcessingException e) {
            json = "[]";
        }
        ResourceContents contents = new TextResourceContents(
                request.uri(),
                "application/json",
                json
        );
        return new ReadResourceResult(List.of(contents));
    }

    /**
     * Sunucu alışverişi bilgisi ile satır okuma
     */
    @McpResource(
            uri = "database-row-exchange://{table}/{id}",
            name = "Database Row with Exchange",
            description = "Sunucu exchange bilgisi ile satır okur",
            mimeType = "application/json"
    )
    public ReadResourceResult getRowWithExchange(McpSyncServerExchange exchange, ReadResourceRequest request,
                                                 String table, String id) {
        // exchange üzerinden oturum veya log bilgisi kullanılabilir
        return getRow(request, table, id);
    }
}
