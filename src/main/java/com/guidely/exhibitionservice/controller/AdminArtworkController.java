package com.guidely.exhibitionservice.controller;

import com.guidely.exhibitionservice.dto.ArtworkCreateRequest;
import com.guidely.exhibitionservice.dto.ArtworkResponse;
import com.guidely.exhibitionservice.dto.ArtworkUpdateRequest;
import com.guidely.exhibitionservice.dto.ArtworkSummaryResponse;
import com.guidely.exhibitionservice.service.ArtworkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/artworks")
@RequiredArgsConstructor
@Slf4j
public class AdminArtworkController {
    
    private final ArtworkService artworkService;
    
    // 🔧 관리자 페이지용 API
    
    @GetMapping("/summaries")
    public ResponseEntity<List<ArtworkSummaryResponse>> getArtworkSummaries() {
        log.info("관리자 작품 목록 조회 API 호출");
        List<ArtworkSummaryResponse> summaries = artworkService.getArtworkSummaries();
        return ResponseEntity.ok(summaries);
    }
    
    // 🔧 기본 CRUD API
    
    @PostMapping
    public ResponseEntity<ArtworkResponse> createArtwork(@Valid @RequestBody ArtworkCreateRequest request) {
        log.info("관리자 작품 생성 API 호출: {}", request.getTitle());
        ArtworkResponse response = artworkService.createArtwork(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @PatchMapping("/{id}")
    public ResponseEntity<ArtworkResponse> updateArtwork(
            @PathVariable UUID id, 
            @Valid @RequestBody ArtworkUpdateRequest request) {
        log.info("관리자 작품 수정 API 호출: ID={}", id);
        ArtworkResponse response = artworkService.updateArtwork(id, request);
        return ResponseEntity.ok(response);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArtwork(@PathVariable UUID id) {
        log.info("관리자 작품 삭제 API 호출: ID={}", id);
        artworkService.deleteArtwork(id);
        return ResponseEntity.noContent().build();
    }
} 