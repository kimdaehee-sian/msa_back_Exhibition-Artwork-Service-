package com.guidely.exhibitionservice.controller;

import com.guidely.exhibitionservice.dto.ArtworkResponse;
import com.guidely.exhibitionservice.service.ArtworkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/artworks")
@RequiredArgsConstructor
@Slf4j
public class ArtworkController {
    
    private final ArtworkService artworkService;
    
    @GetMapping
    public ResponseEntity<List<ArtworkResponse>> getAllArtworks() {
        log.info("작품 목록 조회 API 호출");
        List<ArtworkResponse> responses = artworkService.getAllArtworks();
        return ResponseEntity.ok(responses);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ArtworkResponse> getArtworkById(@PathVariable UUID id) {
        log.info("작품 상세 조회 API 호출: ID={}", id);
        ArtworkResponse response = artworkService.getArtworkById(id);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/exhibition/{exhibitionId}")
    public ResponseEntity<List<ArtworkResponse>> getArtworksByExhibitionId(@PathVariable UUID exhibitionId) {
        log.info("전시회별 작품 목록 조회 API 호출: 전시회 ID={}", exhibitionId);
        List<ArtworkResponse> responses = artworkService.getArtworksByExhibitionId(exhibitionId);
        return ResponseEntity.ok(responses);
    }
} 