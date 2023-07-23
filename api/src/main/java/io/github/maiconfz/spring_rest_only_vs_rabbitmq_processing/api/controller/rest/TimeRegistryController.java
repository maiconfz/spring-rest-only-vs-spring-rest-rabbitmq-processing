package io.github.maiconfz.spring_rest_only_vs_rabbitmq_processing.api.controller.rest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/time-registry")
public class TimeRegistryController {

    @GetMapping(path = "/ping")
    public ResponseEntity<EntityModel<Map<String, String>>> ping() {
        var map = new HashMap<String, String>();
        map.put("response", "pong");
        return ResponseEntity.ok(EntityModel.of(map));
    }
}
