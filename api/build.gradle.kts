plugins {
    id("io.maiconfz.spring_rest_only_vs_rabbitmq_processing.java-application-conventions")
    java
	id("org.springframework.boot") version "3.4.0"
	id("io.spring.dependency-management") version "1.1.6"
}

group = "io.maiconfz.spring_rest_only_vs_rabbitmq_processing"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_21
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-amqp")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-hateoas")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	
	implementation("com.google.guava:guava:33.3.1-jre")
	
	implementation("org.apache.commons:commons-lang3:3.17.0")
	
	implementation("org.mapstruct:mapstruct:1.6.3")

	implementation("org.postgresql:postgresql:42.7.4")

	compileOnly("org.projectlombok:lombok:1.18.36")

	developmentOnly("org.springframework.boot:spring-boot-devtools")
	developmentOnly("org.springframework.boot:spring-boot-docker-compose")
	developmentOnly("org.springframework.data:spring-data-rest-hal-explorer")

	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
	annotationProcessor("org.projectlombok:lombok:1.18.36")
	annotationProcessor("org.mapstruct:mapstruct-processor:1.6.3")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.amqp:spring-rabbit-test")	
}

tasks.withType<Test> {
	useJUnitPlatform()
}
