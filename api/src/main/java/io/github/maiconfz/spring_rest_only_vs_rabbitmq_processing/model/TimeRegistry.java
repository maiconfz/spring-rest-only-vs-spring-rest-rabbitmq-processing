package io.github.maiconfz.spring_rest_only_vs_rabbitmq_processing.model;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "TIME_REGISTRY")
public class TimeRegistry {
    @Id
    @GeneratedValue()
    @Column(name = "ID")
    private UUID id;

    @Column(name = "TIME", nullable = false)
    private LocalDateTime time;

    @PrePersist
    void prePersist() {
        this.time = LocalDateTime.now();
    }
}
