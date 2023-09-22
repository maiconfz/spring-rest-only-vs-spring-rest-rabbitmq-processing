package io.github.maiconfz.spring_rest_only_vs_rabbitmq_processing.api.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import io.github.maiconfz.spring_rest_only_vs_rabbitmq_processing.data.model.JsonData;
import lombok.Data;

@Data
public class JsonDataDto {
    private final UUID id;
    private final LocalDateTime creationDate;
    private final String data;

    public JsonDataDto(JsonData jsonData) {
        this.id = jsonData.getId();
        this.creationDate = jsonData.getCreationDate();
        this.data = jsonData.getData();
    }

}
