package com.guidely.exhibitionservice.repository;

import com.guidely.exhibitionservice.entity.Artwork;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArtworkRepository extends JpaRepository<Artwork, Long> {
    
    List<Artwork> findByExhibitionId(Long exhibitionId);
    
    List<Artwork> findByTitleContainingIgnoreCase(String title);
    
    List<Artwork> findByArtistContainingIgnoreCase(String artist);
    
    List<Artwork> findByStyle(String style);
    
    List<Artwork> findByMedium(String medium);
    
    @Query("SELECT a FROM Artwork a ORDER BY a.likeCount DESC")
    List<Artwork> findTopArtworksByLikeCount();
    
    @Query("SELECT a FROM Artwork a WHERE a.keywords LIKE %:keyword%")
    List<Artwork> findByKeywordsContaining(String keyword);
} 