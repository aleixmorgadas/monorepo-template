package dev.aleixmorgadas.example.run;

import dev.aleixmorgadas.example.Application;
import dev.aleixmorgadas.example.config.TestEnvironmentConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.Map;

@Slf4j
public class TestApplication {
    public static void main(String[] args) {
        SpringApplication.from(Application::main)
            .with(TestEnvironmentConfiguration.class)
            .run(args);
    }
}
