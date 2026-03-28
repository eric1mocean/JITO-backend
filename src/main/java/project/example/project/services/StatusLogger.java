package project.example.project.services;

import project.example.project.commonDomain.ETaskStatus;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import project.example.project.commonDomain.ETaskStatus;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class StatusLogger {

    public void logTaskProgress(Long taskId, String status) {
        RestClient restClient = null;

        try {
            // client hardcodat pentru POC
            restClient = RestClient.builder(
                    new HttpHost("localhost", 9200, "http")
            ).build();

            var transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
            var esClient = new ElasticsearchClient(transport);

            // document simplu
            Map<String, Object> logDocument = new HashMap<>();
            logDocument.put("timestamp", LocalDateTime.now().toString());
            logDocument.put("taskId", taskId);
            logDocument.put("status", status);

            IndexResponse response = esClient.index(i -> i
                    .index("task-logs")
                    .document(logDocument)
            );

            System.out.println("Inserted log with id: " + response.id());

        } catch (IOException e) {
            throw new RuntimeException("Failed to insert log into Elasticsearch", e);
        } finally {
            if (restClient != null) {
                try {
                    restClient.close();
                } catch (IOException ignored) {
                }
            }
        }
    }

}
