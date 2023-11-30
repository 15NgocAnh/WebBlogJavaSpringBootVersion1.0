package com.mycompany.entity;

import jakarta.persistence.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "new")
@EntityListeners(AuditingEntityListener.class)
public class NewEntity extends BaseEntity { // đại diện cho table new

    @Column(name = "title")
    private String title;
    @Column(name = "thumbnail")
    private String thumbnail;
    @Column(name = "meta")
    private String meta;
    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

	@Column(name = "slug")
	private String slug;

	@ManyToOne
	@JoinColumn(name = "category_id", referencedColumnName = "id")
	private CategoryEntity category;

	public NewEntity() {
		super();
	}

	public NewEntity(String title, String thumbnail, String meta, String content, String slug, CategoryEntity category) {
		this.title = title;
		this.thumbnail = thumbnail;
		this.meta = meta;
		this.content = content;
		this.slug = slug;
		this.category = category;
	}

	public NewEntity(Long id, String createdBy, LocalDateTime createdDate, String modifiedBy, LocalDateTime modifiedDate, String title, String thumbnail, String meta, String content, String slug, CategoryEntity category) {
		super(id, createdBy, createdDate, modifiedBy, modifiedDate);
		this.title = title;
		this.thumbnail = thumbnail;
		this.meta = meta;
		this.content = content;
		this.slug = slug;
		this.category = category;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public String getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	
	public String getMeta() {
		return meta;
	}
	public void setMeta(String meta) {
		this.meta = meta;
	}
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public CategoryEntity getCategory() {
		return category;
	}
	public void setCategory(CategoryEntity category) {
		this.category = category;
	}

	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}
}
