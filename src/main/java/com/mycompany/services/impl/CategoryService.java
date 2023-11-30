package com.mycompany.services.impl;

import com.mycompany.entity.CategoryEntity;
import com.mycompany.entity.NewEntity;
import com.mycompany.repository.CategoryRepository;
import com.mycompany.repository.NewRepository;
import com.mycompany.services.ICategoryService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CategoryService implements ICategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private NewRepository newRepository;

    @Override
    public List<CategoryEntity> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public CategoryEntity create(CategoryEntity category) {
        // Thực hiện lưu thông tin ngày tạo và người tạo
        category.setCreatedDate(LocalDateTime.now());
        category.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());

        return this.categoryRepository.save(category);
            
    }

    @Override
    public CategoryEntity findById(Long id) {
        return this.categoryRepository.findById(id).orElse(null);
    }

    @Override
    public CategoryEntity update(Long categoryId, CategoryEntity updatedCategory) {
        CategoryEntity existingCategory = categoryRepository.findById(categoryId).orElse(null);

        if (existingCategory != null) {
            // Cập nhật thông tin ngày sửa đổi và người sửa đổi
            existingCategory.setModifiedDate(LocalDateTime.now());
            existingCategory.setModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());

            existingCategory.setName(updatedCategory.getName());
            existingCategory.setSlug(updatedCategory.getSlug());
            existingCategory.setStatus(updatedCategory.getStatus());

            // Lưu đối tượng cập nhật
            return categoryRepository.save(existingCategory);
        } else {
            return null;
        }
    }

	@Override
	public Boolean delete(Long id) {
        try {
            // Trước khi xóa bản ghi từ bảng cha, xóa các bản ghi liên quan từ bảng con
            List<NewEntity> relatedNews = newRepository.findNewsByCategoryId(id);
            for (NewEntity news : relatedNews) {
                newRepository.deleteById(news.getId());
            }

            // Sau đó, xóa bản ghi từ bảng cha
            this.categoryRepository.deleteById(id);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

	@Override
	public List<CategoryEntity> search(String keyword) {
        return this.categoryRepository.search(keyword);
	}

    @Override
    public Page<CategoryEntity> findAll(Integer page) {
        Pageable pageable = PageRequest.of(page-1, 5);
        return this.categoryRepository.findAll(pageable);
    }

    @Override
    public Page<CategoryEntity> search(String keyword, Integer page) {
        List categories = this.search(keyword);
        Pageable pageable = PageRequest.of(page-1, 5);
        Integer start = (int) pageable.getOffset();
        Integer end = (int) ((pageable.getOffset() + pageable.getPageSize()) >
                categories.size() ? categories.size() : pageable.getOffset() + pageable.getPageSize());
        categories = categories.subList(start, end);
        return new PageImpl<CategoryEntity>(categories, pageable, this.search(keyword).size());
    }

    public List<NewEntity> findNewsByCategorySlug(String slug) {
        List<NewEntity> news =  this.categoryRepository.findNewsByCategorySlug(slug);
        if (news != null) {
            return news;
        } else {
            return null;
        }
    }

    @Override
    public CategoryEntity findBySlug(String slug) {
        return this.categoryRepository.findBySlug(slug);
    }

    @Override
    public List<Object[]> findTop7CategoriesWithPostCount() {
        Pageable page = PageRequest.of(0, 7);
        return this.categoryRepository.findTop7CategoriesWithPostCount(page);
    }
}
