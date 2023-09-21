package io.github.maiconfz.spring_rest_only_vs_rabbitmq_processing.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan("io.github.maiconfz.spring_rest_only_vs_rabbitmq_processing")
@EnableJpaRepositories("io.github.maiconfz.spring_rest_only_vs_rabbitmq_processing.data.repo")
@EntityScan("io.github.maiconfz.spring_rest_only_vs_rabbitmq_processing.data.model")
public class App {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

}
