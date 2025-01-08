package fmi.enroll.config;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Optional;
import java.util.Set;

@Configuration
@EnableJpaAuditing
@EnableTransactionManagement
public class DatabaseConfig {
    private static final Logger log = LoggerFactory.getLogger(DatabaseConfig.class);

    @Bean
    public AuditorAware<String> auditorProvider() {
        return () -> Optional.of("SYSTEM");
    }

    @PostConstruct
    public void configureDatabase() {
        String userHome = System.getProperty("user.home");
        File dbDir = new File(userHome);

        if (!dbDir.exists()) {
            log.info("Creating database directory at: {}", dbDir.getAbsolutePath());
            boolean created = dbDir.mkdirs();
            if (!created) {
                log.error("Failed to create database directory at: {}", dbDir.getAbsolutePath());
                return;
            }
        }

        try {
            Set<PosixFilePermission> permissions = PosixFilePermissions.fromString("rwxr-x---");
            Files.setPosixFilePermissions(dbDir.toPath(), permissions);
            log.info("Successfully set database directory permissions");
        } catch (IOException e) {
            log.error("Failed to set database directory permissions", e);
        }
    }
}