package com.guidely.exhibitionservice.controller;

import com.guidely.exhibitionservice.dto.ArtworkCreateRequest;
import com.guidely.exhibitionservice.dto.ArtworkResponse;
import com.guidely.exhibitionservice.dto.ArtworkUpdateRequest;
import com.guidely.exhibitionservice.service.ArtworkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/artworks")
@RequiredArgsConstructor
@Slf4j
public class ArtworkController {
    
    private final ArtworkService artworkService;
    
    @GetMapping
    public ResponseEntity<List<ArtworkResponse>> getAllArtworks() {
        log.info("모든 작품 조회 API 호출");
        List<ArtworkResponse> responses = artworkService.getAllArtworks();
        return ResponseEntity.ok(responses);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ArtworkResponse> getArtworkById(@PathVariable Long id) {
        log.info("작품 조회 API 호출: ID={}", id);
        ArtworkResponse response = artworkService.getArtworkById(id);
        return ResponseEntity.ok(response);
    }
    
    @PostMapping
    public ResponseEntity<ArtworkResponse> createArtwork(@Valid @RequestBody ArtworkCreateRequest request) {
        log.info("작품 생성 API 호출: {}", request.getTitle());
        ArtworkResponse response = artworkService.createArtwork(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @PatchMapping("/{id}")
    public ResponseEntity<ArtworkResponse> updateArtwork(
            @PathVariable Long id, 
            @Valid @RequestBody ArtworkUpdateRequest request) {
        log.info("작품 수정 API 호출: ID={}", id);
        ArtworkResponse response = artworkService.updateArtwork(id, request);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/exhibition/{exhibitionId}")
    public ResponseEntity<List<ArtworkResponse>> getArtworksByExhibitionId(@PathVariable Long exhibitionId) {
        log.info("전시회별 작품 조회 API 호출: 전시회 ID={}", exhibitionId);
        List<ArtworkResponse> responses = artworkService.getArtworksByExhibitionId(exhibitionId);
        return ResponseEntity.ok(responses);
    }
    
    @GetMapping("/search/title")
    public ResponseEntity<List<ArtworkResponse>> searchArtworksByTitle(@RequestParam String title) {
        log.info("제목으로 작품 검색 API 호출: {}", title);
        List<ArtworkResponse> responses = artworkService.searchArtworksByTitle(title);
        return ResponseEntity.ok(responses);
    }
    
    @GetMapping("/search/artist")
    public ResponseEntity<List<ArtworkResponse>> searchArtworksByArtist(@RequestParam String artist) {
        log.info("작가로 작품 검색 API 호출: {}", artist);
        List<ArtworkResponse> responses = artworkService.searchArtworksByArtist(artist);
        return ResponseEntity.ok(responses);
    }
    
    @GetMapping("/top/likes")
    public ResponseEntity<List<ArtworkResponse>> getTopArtworksByLikeCount() {
        log.info("인기 작품 조회 API 호출");
        List<ArtworkResponse> responses = artworkService.getTopArtworksByLikeCount();
        return ResponseEntity.ok(responses);
    }
} 