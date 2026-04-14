package com.cmanager.app.application.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "EPISODE")
@Data
public class Episode {

    @Id
    private String id;

    @Column(name = "ID_INTEGRATION")
    private Integer idIntegration;

    @Column(name = "FK_SHOW", nullable = false)
    private String fkShow;

    @Column(name = "NAME")
    private String name;

    @Column(name = "SEASON")
    private Integer season;

    @Column(name = "NUMBER")
    private Integer number;

    @Column(name = "TYPE")
    private String type;

    @Column(name = "AIRDATE")
    private String airdate;

    @Column(name = "AIRTIME")
    private String airtime;

    @Column(name = "AIRSTAMP")
    private String airstamp;

    @Column(name = "RUNTIME")
    private Integer runtime;

    @Column(name = "RATING", precision = 5, scale = 2)
    private BigDecimal rating;

    @Column(name = "SUMMARY")
    private String summary;

    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMPTZ")
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = false, columnDefinition = "TIMESTAMPTZ")
    private OffsetDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        final OffsetDateTime now = OffsetDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
        this.id = UUID.randomUUID().toString();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = OffsetDateTime.now();
    }
}
