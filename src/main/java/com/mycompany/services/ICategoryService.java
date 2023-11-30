package com.mycompany.services;

import com.mycompany.entity.CategoryEntity;
import com.mycompany.entity.NewEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ICategoryService{

    List<CategoryEntity> findAll();
    CategoryEntity create(CategoryEntity category);
    CategoryEntity findById(Long id);
    CategoryEntity update(Long id, CategoryEntity category);
    Boolean delete(Long id);
    List<CategoryEntity> search(String keyword);
    Page<CategoryEntity> findAll(Integer page);
    Page<CategoryEntity> search(String keyword, Integer page);
    List<NewEntity> findNewsByCategorySlug(String slug);
    CategoryEntity findBySlug(String slug);
    List<Object[]> findTop7CategoriesWithPostCount();
}
