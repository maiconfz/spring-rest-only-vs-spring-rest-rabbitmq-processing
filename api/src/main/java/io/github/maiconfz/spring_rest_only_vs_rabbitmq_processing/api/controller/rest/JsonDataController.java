package io.github.maiconfz.spring_rest_only_vs_rabbitmq_processing.api.controller.rest;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
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
import org.tinylog.Logger;

import io.github.maiconfz.spring_rest_only_vs_rabbitmq_processing.api.dto.JsonDataModel;
import io.github.maiconfz.spring_rest_only_vs_rabbitmq_processing.api.dto.mapper.JsonDataMapper;
import io.github.maiconfz.spring_rest_only_vs_rabbitmq_processing.data.repo.JsonDataRepositoy;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "/api/json-data", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "http://localhost:8081")
@AllArgsConstructor
public class JsonDataController {

    private final JsonDataRepositoy jsonDataRepositoy;
    private final JsonDataMapper mapper;

    @PostMapping(path = "")
    public ResponseEntity<EntityModel<JsonDataModel>> create(@RequestBody JsonDataModel jsonDataDto) {
        Logger.info(jsonDataDto);
        if (jsonDataDto != null && StringUtils.isNotBlank(jsonDataDto.getData())) {
            final var jsonDataDtoRes = mapper
                    .toJsonDataDto(this.jsonDataRepositoy.save(mapper.toJsonData(jsonDataDto)));

            jsonDataDtoRes.add(WebMvcLinkBuilder.linkTo(getClass()).slash(jsonDataDtoRes.getId()).withSelfRel());

            return ResponseEntity.ok(EntityModel.of(jsonDataDtoRes));
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping(path = "/add-all")
    public ResponseEntity<CollectionModel<JsonDataModel>> create(@RequestBody List<JsonDataModel> jsonDataDtoList) {
        Logger.info(jsonDataDtoList);
        if (jsonDataDtoList != null && !jsonDataDtoList.isEmpty()) {
            final List<JsonDataModel> jsonDataDtoListRes = new ArrayList<>();

            for (var jsonDataDto : jsonDataDtoList) {
                if (StringUtils.isNotBlank(jsonDataDto.getData())) {
                    final var jsonDataDtoRes = mapper
                            .toJsonDataDto(
                                    this.jsonDataRepositoy.save(mapper.toJsonData(jsonDataDto)));
                    jsonDataDtoRes
                            .add(WebMvcLinkBuilder.linkTo(getClass()).slash(jsonDataDtoRes.getId()).withSelfRel());

                    jsonDataDtoListRes
                            .add(jsonDataDtoRes);

                }
            }

            return ResponseEntity.ok(CollectionModel.of(jsonDataDtoListRes));
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
