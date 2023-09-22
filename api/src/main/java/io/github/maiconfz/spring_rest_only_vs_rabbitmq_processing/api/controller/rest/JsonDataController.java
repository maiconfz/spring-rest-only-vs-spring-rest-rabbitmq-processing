package io.github.maiconfz.spring_rest_only_vs_rabbitmq_processing.api.controller.rest;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tinylog.Logger;

import com.google.common.collect.ImmutableSet;

import io.github.maiconfz.spring_rest_only_vs_rabbitmq_processing.api.dto.JsonDataDto;
import io.github.maiconfz.spring_rest_only_vs_rabbitmq_processing.api.dto.mapper.JsonDataToJsonDataDtoMapper;
import io.github.maiconfz.spring_rest_only_vs_rabbitmq_processing.data.model.JsonData;
import io.github.maiconfz.spring_rest_only_vs_rabbitmq_processing.data.repo.JsonDataRepositoy;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "/api/json-data")
@CrossOrigin(origins = "http://localhost:8081")
@AllArgsConstructor
public class JsonDataController {

    private final JsonDataRepositoy jsonDataRepositoy;
    private final JsonDataToJsonDataDtoMapper jsonDataToJsonDataDtoMapper;

    @GetMapping(path = "")
    public ResponseEntity<CollectionModel<UUID>> list() {
        // TODO
        return ResponseEntity
                .ok(CollectionModel.of(ImmutableSet.of(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID())));
    }

    @PostMapping(path = "")
    public ResponseEntity<EntityModel<JsonDataDto>> create(@RequestBody JsonData jsonData) {
        Logger.info(jsonData);
        if (jsonData != null && StringUtils.isNotBlank(jsonData.getData())) {
            final JsonDataDto jsonDataDto = jsonDataToJsonDataDtoMapper
                    .jsonDataToJsonDataDto(this.jsonDataRepositoy.save(jsonData));
            return ResponseEntity.ok(EntityModel.of(jsonDataDto));
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
