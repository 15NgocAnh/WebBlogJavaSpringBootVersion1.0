package com.mycompany.entity;


import java.time.LocalDateTime;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.context.SecurityContextHolder;

@MappedSuperclass //Truyền dữ liệu từ BaseEntity sang các lớp khác xuống CSDL
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    @Id //Set the primary key, not null
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Auto increment
    private Long id;

    @Column(name = "createdBy") //Column name
	@CreatedBy
    private String createdBy;

    @Column
	@CreatedDate
    private LocalDateTime createdDate;

    @Column
	@LastModifiedBy
    private String modifiedBy;

    @Column
	@LastModifiedDate
    private LocalDateTime  modifiedDate;
    
    

	public BaseEntity() {
		super();
	}


	public BaseEntity(Long id, String createdBy, LocalDateTime createdDate, String modifiedBy, LocalDateTime modifiedDate) {
		super();
		this.id = id;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}


	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public LocalDateTime getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(LocalDateTime modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@PrePersist
	public void prePersist() {
		this.createdBy = SecurityContextHolder.getContext().getAuthentication().getName();
	}

	@PreUpdate
	public void preUpdate() {
		this.modifiedBy = SecurityContextHolder.getContext().getAuthentication().getName();
	}
    
}
