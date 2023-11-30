package com.mycompany.entity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@Table(name = "category")
@EntityListeners(AuditingEntityListener.class)
public class CategoryEntity extends BaseEntity {

    @Column(name = "name")
    private String name;
	@Column(name = "slug")
	private String slug;
    @Column(name = "status")
    private Boolean status;

	@OneToMany(mappedBy = "category")
	private Set<NewEntity> news = new HashSet<>();
	
	
	public CategoryEntity() {
		super();
	}
	
	
	public CategoryEntity(String name, Boolean status, Set<NewEntity> news) {
		super();
		this.name = name;
		this.status = status;
		this.news = news;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean code) {
		this.status = code;
	}
	
	public Set<NewEntity> getNews() {
		return news;
	}
	public void setNews(Set<NewEntity> news) {
		this.news = news;
	}

	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}
}
