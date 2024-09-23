package io.github.maiconfz.spring_rest_only_vs_rabbitmq_processing.api.controller.rest;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.maiconfz.spring_rest_only_vs_rabbitmq_processing.api.dto.JsonDataModel;
import io.github.maiconfz.spring_rest_only_vs_rabbitmq_processing.api.dto.mapper.JsonDataMapper;
import io.github.maiconfz.spring_rest_only_vs_rabbitmq_processing.queue.JsonDataSavingQueueListenerRepository;
import io.github.maiconfz.spring_rest_only_vs_rabbitmq_processing.queue.JsonDataSavingQueuePublisher;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping(path = "/api/json-data-queue")
@CrossOrigin(origins = { "http://localhost:8081", "http://127.0.0.1:8081", })
@AllArgsConstructor
public class JsonDataQueueController {
    private static final Logger logger = LoggerFactory.getLogger(JsonDataQueueController.class);

    private final JsonDataSavingQueuePublisher jsonDataQueuePublisher;
    private final JsonDataSavingQueueListenerRepository jsonDataSavingQueueListenerRepository;
    private final JsonDataMapper mapper;

    @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> create(@RequestBody JsonDataModel jsonDataModel) {
        logger.info("{}", jsonDataModel);

        if (jsonDataModel != null && StringUtils.isNotBlank(jsonDataModel.getData())) {
            final var jsonData = mapper.toJsonData(jsonDataModel);

            logger.info("{}", jsonData);

            this.jsonDataQueuePublisher.publish(jsonData);

            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping(path = "/add-all", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> create(@RequestBody List<JsonDataModel> jsonDataModelList) {
        logger.info("{}", jsonDataModelList);

        if (jsonDataModelList != null && !jsonDataModelList.isEmpty()) {
            this.jsonDataQueuePublisher.publishAll(jsonDataModelList.parallelStream().map(mapper::toJsonData));

            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/saved-stream")
    public Flux<ServerSentEvent<JsonDataModel>> savedStream() {
        return Flux.create(sink -> {
            JsonDataSavingQueueListenerRepository.JsonDataSavedListener listener = jsonData -> {
                JsonDataModel jsonDataModel = mapper.toJsonDataModel(jsonData);
                sink.next(ServerSentEvent.builder(jsonDataModel).build());
            };

            jsonDataSavingQueueListenerRepository.addListener(listener);

            sink.onCancel(() -> jsonDataSavingQueueListenerRepository.removeListener(listener));
        });
    }
}
