package io.github.maiconfz.spring_rest_only_vs_rabbitmq_processing.api.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import io.github.maiconfz.spring_rest_only_vs_rabbitmq_processing.api.dto.JsonDataModel;
import io.github.maiconfz.spring_rest_only_vs_rabbitmq_processing.data.model.JsonData;

@Mapper(componentModel = "spring")
public interface JsonDataMapper {
    @Mapping(target = "add", ignore = true)
    JsonDataModel toJsonDataModel(JsonData jsonData);

    JsonData toJsonData(JsonDataModel jsonDataDto);
}
