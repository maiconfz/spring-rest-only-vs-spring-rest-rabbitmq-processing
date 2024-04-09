package io.github.maiconfz.spring_rest_only_vs_rabbitmq_processing.api.controller.rest;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.ImmutableList;

import io.github.maiconfz.spring_rest_only_vs_rabbitmq_processing.api.dto.JsonDataModel;
import io.github.maiconfz.spring_rest_only_vs_rabbitmq_processing.api.dto.mapper.JsonDataMapper;
import io.github.maiconfz.spring_rest_only_vs_rabbitmq_processing.data.repo.JsonDataRepositoy;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "/api/json-data", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = { "http://localhost:8081", "http://127.0.0.1:8081", })
@AllArgsConstructor
public class JsonDataController {

    private static final Logger logger = LoggerFactory.getLogger(JsonDataController.class);

    private final JsonDataRepositoy jsonDataRepositoy;
    private final JsonDataMapper mapper;

    @PostMapping(path = "")
    public ResponseEntity<EntityModel<JsonDataModel>> create(@RequestBody JsonDataModel jsonDataModel) {
        logger.info("{}", jsonDataModel);

        if (jsonDataModel != null && StringUtils.isNotBlank(jsonDataModel.getData())) {
            final var jsonData = mapper.toJsonData(jsonDataModel);

            logger.info("{}", jsonData);

            final var jsonDataModelRes = mapper
                    .toJsonDataModel(this.jsonDataRepositoy.save(jsonData));

            jsonDataModelRes.add(WebMvcLinkBuilder.linkTo(getClass()).slash(jsonDataModelRes.getId()).withSelfRel());

            return ResponseEntity.ok(EntityModel.of(jsonDataModelRes));
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping(path = "/add-all")
    public ResponseEntity<CollectionModel<JsonDataModel>> create(@RequestBody List<JsonDataModel> jsonDataModelList) {
        logger.info("{}", jsonDataModelList);

        if (jsonDataModelList != null && !jsonDataModelList.isEmpty()) {
            final var jsonDataList = jsonDataModelList.parallelStream().map(mapper::toJsonData)
                    .collect(Collectors.toList());

            final var jsonDataModelListRes = ImmutableList.copyOf(this.jsonDataRepositoy.saveAll(jsonDataList))
                    .parallelStream().map(jsonData -> {
                        final var jsonDataModel = mapper.toJsonDataModel(jsonData);
                        jsonDataModel
                                .add(WebMvcLinkBuilder.linkTo(getClass()).slash(jsonDataModel.getId()).withSelfRel());
                        return jsonDataModel;
                    }).collect(Collectors.toList());

            return ResponseEntity.ok(CollectionModel.of(jsonDataModelListRes));
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
