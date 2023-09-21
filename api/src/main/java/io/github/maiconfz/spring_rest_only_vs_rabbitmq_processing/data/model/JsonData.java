package io.github.maiconfz.spring_rest_only_vs_rabbitmq_processing.data.model;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "JSON_DATA")
public class JsonData {
    @Id
    @GeneratedValue()
    @Column(name = "ID")
    private UUID id;

    @Column(name = "CREATION_DATE", nullable = false)
    private LocalDateTime creationDate;

    @Column(name = "DATA", nullable = false)
    @Lob
    private String data;

    @PrePersist
    void prePersist() {
        this.creationDate = LocalDateTime.now();
    }
}
