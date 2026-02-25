package kz.zzhalelov.staffflow.server.common;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {
    @Version
    @Column(nullable = false)
    private long version;

    @Column(nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(nullable = false)
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @CreatedBy
    @Column(updatable = false)
    private String createdBy;

    @LastModifiedBy
    private String updatedBy;

    @Column(nullable = false)
    protected boolean deleted = false;
    @Column(name = "deleted_at")
    protected LocalDateTime deletedAt;
    @Column(name = "deleted_by")
    protected String deletedBy;

    public void markAsDeleted(String username) {
        if (this.deleted) {
            return;
        }
        this.deleted = true;
        this.deletedAt = LocalDateTime.now();
        this.deletedBy = username;
    }

    public void restore(String username) {
        this.deleted = false;
        this.deletedAt = null;
        this.deletedBy = null;
    }
}