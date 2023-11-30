package com.mycompany.services.impl;

import com.mycompany.entity.CategoryEntity;
import com.mycompany.entity.NewEntity;
import com.mycompany.repository.NewRepository;
import com.mycompany.services.INewService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

@Service
public class NewService implements INewService {

    @Autowired
    private NewRepository newRepository;

    @Override
    public List<NewEntity> findAll() {
        return newRepository.findAll();
    }

    @Override
    public NewEntity findById(Long id) {
        return this.newRepository.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public NewEntity create(NewEntity newEntity) {
        // Thực hiện lưu thông tin ngày tạo và người tạo
        newEntity.setCreatedDate(LocalDateTime.now());
        newEntity.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());

        return this.newRepository.save(newEntity);
    }

    @Transactional
    @Override
    public NewEntity update(Long id, NewEntity updatedNew) {
        NewEntity existingNew = this.newRepository.findById(id).orElse(null);
        if (existingNew != null) {
            // Cập nhật thông tin ngày sửa đổi và người sửa đổi
            existingNew.setModifiedDate(LocalDateTime.now());
            existingNew.setModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
            // Cập nhật thông tin
            existingNew.setTitle(updatedNew.getTitle());
            existingNew.setSlug(updatedNew.getSlug());
            existingNew.setThumbnail(updatedNew.getThumbnail());
            existingNew.setCategory(updatedNew.getCategory());
            existingNew.setMeta(updatedNew.getMeta());
            existingNew.setContent(updatedNew.getContent());
            // Lưu đối tượng cập nhật
            return this.newRepository.save(existingNew);
        } else {
            return null;
        }
    }

    @Override
    public Boolean delete(Long id) {
        try {
            this.newRepository.deleteById(id);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<NewEntity> search(String keyword) {
        return this.newRepository.search(keyword);
    }

    @Override
    public Page<NewEntity> findAll(Integer page) {
        Pageable pageable = PageRequest.of(page-1, 5);
        return this.newRepository.findAll(pageable);
    }

    @Override
    public Page<NewEntity> search(String keyword, Integer page) {
        List news = this.search(keyword);
        Pageable pageable = PageRequest.of(page-1, 5);
        Integer start = (int) pageable.getOffset();
        Integer end = (int) ((pageable.getOffset() + pageable.getPageSize()) >
        news.size() ? news.size() : pageable.getOffset() + pageable.getPageSize());
        news = news.subList(start, end);
        return new PageImpl<NewEntity>(news, pageable, this.search(keyword).size());
    }

    @Override
    public NewEntity findBySlug(String slug) {
        return this.newRepository.findBySlug(slug);
    }

	@Override
	public List<NewEntity> findTop5ByOrderByCreateDateDesc() {
		Pageable page = PageRequest.of(0, 5);
		return this.newRepository.findTop5ByOrderByCreateDateDesc(page);
	}

    @Override
    public List<NewEntity> findAllByOrderByCreateDateDesc() {
        return this.newRepository.findAllByOrderByCreateDateDesc();
    }

    @Override
    public List<NewEntity> findTop3ByOrderByCreateDateDesc() {
        Pageable pg = PageRequest.of(0, 3);
        return this.newRepository.findTop3ByOrderByCreateDateDesc(pg);
    }
}
