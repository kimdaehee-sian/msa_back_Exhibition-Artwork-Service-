package com.guidely.exhibitionservice.repository;

import com.guidely.exhibitionservice.entity.Exhibition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExhibitionRepository extends JpaRepository<Exhibition, Long> {
    
    List<Exhibition> findByStatus(String status);
    
    List<Exhibition> findByTitleContainingIgnoreCase(String title);
} 