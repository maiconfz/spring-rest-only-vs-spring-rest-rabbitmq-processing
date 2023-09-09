package io.github.maiconfz.spring_rest_only_vs_rabbitmq_processing.api.controller.rest;

import java.util.Map;
import java.util.UUID;

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

import io.github.maiconfz.spring_rest_only_vs_rabbitmq_processing.model.JsonData;

@RestController
@RequestMapping(path = "/api/json-data")
@CrossOrigin(origins = "http://localhost:8081")
public class JsonDataController {

    @GetMapping(path = "")
    public ResponseEntity<CollectionModel<UUID>> list() {
        // TODO
        return ResponseEntity
                .ok(CollectionModel.of(ImmutableSet.of(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID())));
    }

    @PostMapping(path = "")
    public ResponseEntity<EntityModel<Map<String, UUID>>> create(@RequestBody JsonData jsonData)
            throws InterruptedException {
        // TODO
        Logger.info(jsonData);
        return ResponseEntity.ok(EntityModel.of(Map.of("id", UUID.randomUUID())));
    }
}
