package io.github.maiconfz.spring_rest_only_vs_rabbitmq_processing.data.repo;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import io.github.maiconfz.spring_rest_only_vs_rabbitmq_processing.data.model.JsonData;

public interface JsonDataRepositoy extends CrudRepository<JsonData, UUID> {

}
