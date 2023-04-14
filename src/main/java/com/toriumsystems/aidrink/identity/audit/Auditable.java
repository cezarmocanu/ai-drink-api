package com.toriumsystems.aidrink.identity.audit;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@NoArgsConstructor
public abstract class Auditable {
    @CreatedBy
    @Column(columnDefinition = "bigint default 1", updatable = false)
    protected Long createdBy;

    @CreatedDate
    @Column(columnDefinition = "timestamp default '2023-01-01 00:00:00.0'", updatable = false)
    protected LocalDateTime createdDate;

    @LastModifiedBy
    @Column(columnDefinition = "bigint default 1")
    protected Long lastModifiedBy;

    @LastModifiedDate
    @Column(columnDefinition = "timestamp default '2023-01-01 00:00:00.0'")
    protected LocalDateTime lastModifiedDate;
}
