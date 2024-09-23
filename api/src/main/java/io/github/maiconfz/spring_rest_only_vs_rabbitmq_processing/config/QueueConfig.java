package io.github.maiconfz.spring_rest_only_vs_rabbitmq_processing.config;

import org.springframework.amqp.support.converter.DefaultJackson2JavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueConfig {
    public static final String TOPIC_EXCHANGE_NAME = "app-queue-exchange";

    @Bean
    public MessageConverter jsonMessageConverter() {
        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
        converter.setClassMapper(trustedClassMapper());
        return converter;
    }

    @Bean
    public DefaultJackson2JavaTypeMapper trustedClassMapper() {
        DefaultJackson2JavaTypeMapper typeMapper = new DefaultJackson2JavaTypeMapper();
        typeMapper.setTrustedPackages("io.github.maiconfz.spring_rest_only_vs_rabbitmq_processing.data.model");
        return typeMapper;
    }

}
