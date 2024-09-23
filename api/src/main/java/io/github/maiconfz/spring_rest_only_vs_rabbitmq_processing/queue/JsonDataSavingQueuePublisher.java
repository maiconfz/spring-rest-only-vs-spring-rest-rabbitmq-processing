package io.github.maiconfz.spring_rest_only_vs_rabbitmq_processing.queue;

import java.util.stream.Stream;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import io.github.maiconfz.spring_rest_only_vs_rabbitmq_processing.config.QueueConfig;
import io.github.maiconfz.spring_rest_only_vs_rabbitmq_processing.data.model.JsonData;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@AllArgsConstructor
@Service
public class JsonDataSavingQueuePublisher {

    private final RabbitTemplate rabbitTemplate;

    public void publish(JsonData jsonData) {
        this.rabbitTemplate.convertAndSend(QueueConfig.TOPIC_EXCHANGE_NAME,
                JsonDataSavingQueueReceiver.QUEUE_NAME + ".save",
                jsonData);
    }

    public void publishAll(Stream<JsonData> jsonDataStream) {
        jsonDataStream.forEach(this::publish);
    }
}
