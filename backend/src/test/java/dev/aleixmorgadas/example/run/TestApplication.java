package dev.aleixmorgadas.example.run;

import dev.aleixmorgadas.example.Application;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.Map;

@SpringBootApplication()
@Import({Application.class})
@Slf4j
public class TestApplication {

    static PostgreSQLContainer<?> postgres;

    public static void main(String[] args) {
        postgres = new PostgreSQLContainer<>("postgres:15-alpine").withReuse(true);

        postgres.start();

        System
            .getProperties()
            .putAll(
                Map.ofEntries(
                    Map.entry(
                        "spring.datasource.url",
                        "jdbc:postgresql://%s:%d/%s".formatted(postgres.getHost(), postgres.getMappedPort(5432), postgres.getDatabaseName())),
                    Map.entry(
                        "spring.flyway.url",
                        "jdbc:postgresql://%s:%d/%s".formatted(postgres.getHost(), postgres.getMappedPort(5432), postgres.getDatabaseName())),
                    Map.entry(
                        "spring.datasource.username",
                        postgres.getUsername()),
                    Map.entry(
                        "spring.datasource.password",
                        postgres.getPassword()),
                    Map.entry(
                        "spring.flyway.user",
                        postgres.getUsername()),
                    Map.entry(
                        "spring.flyway.password",
                        postgres.getPassword())));

        var app = new SpringApplication(TestApplication.class);
        app.setAdditionalProfiles("webapp");
        app.setRegisterShutdownHook(true);
        app.run(args);
    }
}
