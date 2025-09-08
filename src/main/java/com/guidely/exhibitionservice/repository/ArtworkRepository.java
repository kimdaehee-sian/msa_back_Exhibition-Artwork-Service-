package com.guidely.exhibitionservice.repository;

import com.guidely.exhibitionservice.entity.Artwork;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArtworkRepository extends JpaRepository<Artwork, Long> {
    
    List<Artwork> findByExhibitionId(Long exhibitionId);
    
    // 제목 중복 체크
    boolean existsByTitle(String title);
    
    // 수정 시 자신을 제외한 다른 작품의 제목 중복 체크
    boolean existsByTitleAndIdNot(String title, Long id);
} 