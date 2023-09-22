plugins {
    id("io.maiconfz.spring_rest_only_vs_rabbitmq_processing.java-application-conventions")
    java
	id("org.springframework.boot") version "3.1.3"
	id("io.spring.dependency-management") version "1.1.2"
}

group = "io.maiconfz.spring_rest_only_vs_rabbitmq_processing"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
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
	implementation("org.springframework.data:spring-data-rest-hal-explorer")
	
	implementation("com.google.guava:guava:32.1.1-jre")
	
	implementation("org.apache.commons:commons-lang3:3.13.0")
	
	implementation("org.tinylog:tinylog-api:2.6.2")
	implementation("org.tinylog:tinylog-impl:2.6.2")
	
	implementation("org.mapstruct:mapstruct:1.5.5.Final")
	annotationProcessor("org.mapstruct:mapstruct-processor:1.5.5.Final")

	implementation("org.postgresql:postgresql:42.6.0")

	compileOnly("org.projectlombok:lombok")

	developmentOnly("org.springframework.boot:spring-boot-devtools")
	developmentOnly("org.springframework.boot:spring-boot-docker-compose")

	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
	annotationProcessor("org.projectlombok:lombok")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.amqp:spring-rabbit-test")	
}

tasks.withType<Test> {
	useJUnitPlatform()
}
