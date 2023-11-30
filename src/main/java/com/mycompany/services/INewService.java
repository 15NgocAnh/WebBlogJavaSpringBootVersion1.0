package com.mycompany.services;

import com.mycompany.entity.NewEntity;
import org.springframework.data.domain.Page;

import java.util.List;

public interface INewService {

    List<NewEntity> findAll();
    NewEntity findById(Long id);
    NewEntity create(NewEntity newEntity);
    NewEntity update(Long id, NewEntity updatedNew);
    Boolean delete(Long id);
    List<NewEntity> search(String keyword);
    Page<NewEntity> findAll(Integer page);
    Page<NewEntity> search(String keyword, Integer page);
    NewEntity findBySlug(String slug);
    List<NewEntity> findTop5ByOrderByCreateDateDesc();
    List<NewEntity> findAllByOrderByCreateDateDesc();

    List<NewEntity> findTop3ByOrderByCreateDateDesc();

}
