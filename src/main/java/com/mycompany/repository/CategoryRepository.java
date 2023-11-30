package com.mycompany.repository;

import java.util.List;

import com.mycompany.entity.NewEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.mycompany.entity.CategoryEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

	@Query("select c from CategoryEntity c where c.name like %?1%")
	List<CategoryEntity> search(String keyword);
	
	@Query("SELECT c.news FROM CategoryEntity c WHERE c.slug = :slug")
    List<NewEntity> findNewsByCategorySlug(@Param("slug") String slug);

	@Query("SELECT c FROM CategoryEntity c WHERE c.slug = :slug")
	CategoryEntity findBySlug(@Param("slug") String slug);

	@Query("SELECT c.name, c.slug, COUNT(p) AS postCount FROM CategoryEntity c JOIN c.news p GROUP BY c ORDER BY postCount DESC")
	List<Object[]> findTop7CategoriesWithPostCount(Pageable pageable);
}
