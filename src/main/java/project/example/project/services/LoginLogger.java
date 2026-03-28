package project.example.project.services;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.nio.file.Path;


import jakarta.validation.constraints.Email;

public class LoginLogger {
    private String loadPath = "C:/Users/Eric/Desktop/project2/project2/project/data/logs";
    public void logLoginAction(String email, String role) {
        try {
            Path directoryPath = Path.of(loadPath);
            Files.createDirectories(directoryPath);

            String fileName = LocalDate.now() + ".jsonl";
            Path filePath = directoryPath.resolve(fileName);

            String jsonLine = String.format(
                "{\"timestamp\":\"%s\",\"email\":\"%s\",\"role\":\"%s\"}%n",
                LocalDateTime.now(),
                email,
                role
);

            Files.writeString(
                    filePath,
                    jsonLine,
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND
            );
        } catch (IOException e) {
            throw new RuntimeException("Failed to log user login with email " + email, e);
        }
    }
}
