package io.github.maiconfz.spring_rest_only_vs_rabbitmq_processing.api.controller.rest;

import java.util.Map;
import java.util.UUID;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.ImmutableSet;

@RestController
@RequestMapping(path = "/api/time-registries")
@CrossOrigin(origins = "http://localhost:8081")
public class TimeRegistryController {

    @GetMapping(path = "")
    public ResponseEntity<CollectionModel<UUID>> list() {
        // TODO: list time registry
        return ResponseEntity
                .ok(CollectionModel.of(ImmutableSet.of(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID())));
    }

    @PostMapping(path = "")
    public ResponseEntity<EntityModel<Map<String, UUID>>> create() throws InterruptedException {
        // TODO: create timeRegistry
        Thread.sleep(100);
        return ResponseEntity.ok(EntityModel.of(Map.of("id", UUID.randomUUID())));
    }
}
