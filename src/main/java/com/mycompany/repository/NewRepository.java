package com.mycompany.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.mycompany.entity.NewEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NewRepository extends JpaRepository<NewEntity, Long>{ //Bảng muốn thao tác, kiểu dữ liệu primary key

    @Query("select n from NewEntity n where n.title like %?1%")
    List<NewEntity> search(String keyword);

    @Query("SELECT n FROM NewEntity n WHERE n.slug = :slug")
    NewEntity findBySlug(@Param("slug") String slug);

    @Query("SELECT n FROM NewEntity n ORDER BY n.createdDate DESC")
    List<NewEntity> findTop5ByOrderByCreateDateDesc(Pageable pageable);
    @Query("SELECT n FROM NewEntity n ORDER BY n.createdDate DESC")
    List<NewEntity> findAllByOrderByCreateDateDesc();

    @Query("SELECT n FROM NewEntity n ORDER BY n.createdDate DESC")
    List<NewEntity> findTop3ByOrderByCreateDateDesc(Pageable pageable);

    @Query("SELECT n FROM NewEntity n WHERE n.category.id = :id")
    List<NewEntity> findNewsByCategoryId(@Param("id") Long id);
}
