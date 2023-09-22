package io.github.maiconfz.spring_rest_only_vs_rabbitmq_processing.api.dto.mapper;

import org.mapstruct.Mapper;

import io.github.maiconfz.spring_rest_only_vs_rabbitmq_processing.api.dto.JsonDataDto;
import io.github.maiconfz.spring_rest_only_vs_rabbitmq_processing.data.model.JsonData;

@Mapper(componentModel = "spring")
public interface JsonDataToJsonDataDtoMapper {
    JsonDataDto jsonDataToJsonDataDto(JsonData jsonData);
}
