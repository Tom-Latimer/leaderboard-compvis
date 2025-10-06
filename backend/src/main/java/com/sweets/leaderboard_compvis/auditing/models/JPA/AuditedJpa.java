package com.sweets.leaderboard_compvis.auditing.models.JPA;

import jakarta.persistence.Embedded;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.OffsetDateTime;

@MappedSuperclass
public abstract class AuditedJpa {

    @Embedded
    private AuditMetadata auditMetadata;

    public AuditedJpa() {
        this.auditMetadata = new AuditMetadata();
    }

    @PrePersist
    public void prePersist() {
        OffsetDateTime now = OffsetDateTime.now();
        auditMetadata.setCreatedAt(now);       // set only on create
        auditMetadata.setUpdatedAt(now);       // set on create
    }

    @PreUpdate
    public void preUpdate() {
        auditMetadata.setUpdatedAt(OffsetDateTime.now());  // set on every update
    }

    public AuditMetadata getAuditMetadata() {
        return auditMetadata;
    }

    public void setAuditMetadata(AuditMetadata auditMetadata) {
        this.auditMetadata = auditMetadata;
    }
}
