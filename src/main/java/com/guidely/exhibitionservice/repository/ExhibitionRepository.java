package com.guidely.exhibitionservice.repository;

import com.guidely.exhibitionservice.entity.Exhibition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ExhibitionRepository extends JpaRepository<Exhibition, UUID> {
    
    // 제목 중복 체크
    boolean existsByTitle(String title);
    
    // 수정 시 자신을 제외한 다른 전시회의 제목 중복 체크
    boolean existsByTitleAndIdNot(String title, UUID id);
} 