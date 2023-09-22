package io.github.maiconfz.spring_rest_only_vs_rabbitmq_processing.api.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Data;

@Data
public class JsonDataDto {
    private final UUID id;
    private final LocalDateTime creationDate;
    private final String data;
}
