package io.github.maiconfz.spring_rest_only_vs_rabbitmq_processing.api.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.hateoas.RepresentationModel;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class JsonDataModel extends RepresentationModel<JsonDataModel> {
    private UUID id;
    private LocalDateTime creationDate;
    private String data;
}
