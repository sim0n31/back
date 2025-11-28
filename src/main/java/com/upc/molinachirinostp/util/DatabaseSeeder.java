package com.upc.molinachirinostp.util;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

@Component
@Profile("!test")
public class DatabaseSeeder implements CommandLineRunner {

    private final JdbcTemplate jdbcTemplate;

    public DatabaseSeeder(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            System.out.println("🌱 Loading test data...");

            // Read SQL file from project root
            String sqlFilePath = "test-data.sql";
            String sql = new String(Files.readAllBytes(Paths.get(sqlFilePath)), StandardCharsets.UTF_8);

            // Split by semicolon and execute each statement
            String[] statements = sql.split(";");
            int executed = 0;

            for (String statement : statements) {
                statement = statement.trim();
                if (!statement.isEmpty() && !statement.startsWith("--")) {
                    try {
                        jdbcTemplate.execute(statement);
                        executed++;
                    } catch (Exception e) {
                        // Ignore verification queries errors
                        if (!statement.toLowerCase().contains("select")) {
                            System.err.println("Error executing: " + statement.substring(0, Math.min(50, statement.length())));
                            System.err.println("Error: " + e.getMessage());
                        }
                    }
                }
            }

            System.out.println("✅ Test data loaded successfully! Executed " + executed + " statements.");

            // Print summary
            Long usuarios = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM usuario", Long.class);
            Long conexiones = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM conexion", Long.class);
            Long mensajes = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM mensaje", Long.class);
            Long publicaciones = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM publicacion", Long.class);
            Long oportunidades = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM oportunidad", Long.class);

            System.out.println("\n📊 Database Summary:");
            System.out.println("  - Usuarios: " + usuarios);
            System.out.println("  - Conexiones: " + conexiones);
            System.out.println("  - Mensajes: " + mensajes);
            System.out.println("  - Publicaciones: " + publicaciones);
            System.out.println("  - Oportunidades: " + oportunidades);

        } catch (Exception e) {
            System.err.println("❌ Error loading test data: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
