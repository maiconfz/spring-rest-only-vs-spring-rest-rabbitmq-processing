package io.github.maiconfz.spring_rest_only_vs_rabbitmq_processing.api.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import io.github.maiconfz.spring_rest_only_vs_rabbitmq_processing.api.dto.JsonDataModel;
import io.github.maiconfz.spring_rest_only_vs_rabbitmq_processing.data.model.JsonData;

@Mapper(componentModel = "spring")
public interface JsonDataMapper {

    @Mapping(target = "links", ignore = true)
    @Mapping(source = "id", target = "id")
    @Mapping(source = "creationDate", target = "creationDate")
    @Mapping(source = "data", target = "data")
    JsonDataModel toJsonDataModel(JsonData jsonData);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "creationDate", target = "creationDate")
    @Mapping(source = "data", target = "data")
    JsonData toJsonData(JsonDataModel jsonDataDto);
}
