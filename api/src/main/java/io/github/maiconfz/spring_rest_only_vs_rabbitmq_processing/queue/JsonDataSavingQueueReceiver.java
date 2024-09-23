package io.github.maiconfz.spring_rest_only_vs_rabbitmq_processing.queue;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import io.github.maiconfz.spring_rest_only_vs_rabbitmq_processing.config.QueueConfig;
import io.github.maiconfz.spring_rest_only_vs_rabbitmq_processing.data.model.JsonData;
import io.github.maiconfz.spring_rest_only_vs_rabbitmq_processing.data.repo.JsonDataRepositoy;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@AllArgsConstructor
@Component
public class JsonDataSavingQueueReceiver {

    public static final String QUEUE_NAME = "json-data-saving-queue";

    private final JsonDataRepositoy jsonDataRepository;
    private final JsonDataSavingQueueListenerRepository jsonDataSavingQueueListenerRepository;

    @RabbitListener(bindings = @QueueBinding(value = @Queue(value = QUEUE_NAME, durable = "false"), exchange = @Exchange(value = QueueConfig.TOPIC_EXCHANGE_NAME, ignoreDeclarationExceptions = "true", type = "topic"), key = QUEUE_NAME
            + ".#"))
    public void receiveMessage(JsonData jsonData) {
        log.info("Saving json to database: {}", jsonData);
        final JsonData savedJsonData = this.jsonDataRepository.save(jsonData);
        log.info("Msg saved: {}", savedJsonData);
        notifyListeners(savedJsonData);
    }

    private void notifyListeners(JsonData savedJsonData) {
        this.jsonDataSavingQueueListenerRepository.getListeners()
                .forEach(listener -> listener.onJsonDataSaved(savedJsonData));
    }

}
