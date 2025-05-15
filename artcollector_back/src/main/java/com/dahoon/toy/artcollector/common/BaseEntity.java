package com.dahoon.toy.artcollector.common;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdTime;

    @LastModifiedDate
    private LocalDateTime updatedTime;

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public LocalDateTime getUpdatedTime() {
        return updatedTime;
    }

    // Test 전용 Setter
    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }
    public void setUpdatedTime(LocalDateTime updatedTime) {
        this.updatedTime = updatedTime;
    }
}
