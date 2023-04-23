package dev.aleixmorgadas.example;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest(
    classes = {
        Application.class
    },
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@Slf4j
public abstract class AbstractIntegrationTest {

    private static final DockerImageName POSTGRESQL_IMAGE = DockerImageName.parse("postgres:15-alpine");

    static PostgreSQLContainer<?> postgres;


    @BeforeEach
    public void setup() {
        log.info(
            "PostgreSQL connection string: jdbc:postgresql://{}:{}@{}:{}/{}",
            postgres.getUsername(),
            postgres.getPassword(),
            postgres.getHost(),
            postgres.getMappedPort(5432),
            postgres.getDatabaseName()
        );
    }

    @DynamicPropertySource
    static void keys(DynamicPropertyRegistry registry) {
        postgres = new PostgreSQLContainer<>(POSTGRESQL_IMAGE)
            .withUsername("root")
            .withPassword("password")
            .withDatabaseName("tcl");
        postgres.start();

        var datasource = "jdbc:postgresql://%s:%d/%s".formatted(
            postgres.getHost(),
            postgres.getMappedPort(5432),
            postgres.getDatabaseName());

        registry.add("spring.datasource.url", () -> datasource);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }
}
