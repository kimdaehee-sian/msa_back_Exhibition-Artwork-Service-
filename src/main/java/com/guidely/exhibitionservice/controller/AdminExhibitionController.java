package com.guidely.exhibitionservice.controller;

import com.guidely.exhibitionservice.dto.ExhibitionCreateRequest;
import com.guidely.exhibitionservice.dto.ExhibitionResponse;
import com.guidely.exhibitionservice.dto.ExhibitionUpdateRequest;
import com.guidely.exhibitionservice.dto.ExhibitionSummaryResponse;
import com.guidely.exhibitionservice.dto.ExhibitionDropdownResponse;
import com.guidely.exhibitionservice.service.ExhibitionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/admin/exhibitions")
@RequiredArgsConstructor
@Slf4j
public class AdminExhibitionController {
    
    private final ExhibitionService exhibitionService;
    
    // 🔧 관리자 페이지용 API
    
    @GetMapping("/summaries")
    public ResponseEntity<List<ExhibitionSummaryResponse>> getExhibitionSummaries() {
        log.info("관리자 전시회 목록 조회 API 호출");
        List<ExhibitionSummaryResponse> summaries = exhibitionService.getExhibitionSummaries();
        return ResponseEntity.ok(summaries);
    }
    
    @GetMapping("/dropdown")
    public ResponseEntity<List<ExhibitionDropdownResponse>> getExhibitionDropdownOptions() {
        log.info("작품 생성용 전시회 드롭다운 목록 조회 API 호출");
        List<ExhibitionDropdownResponse> options = exhibitionService.getExhibitionDropdownOptions();
        return ResponseEntity.ok(options);
    }
    
    // 🔧 기본 CRUD API
    
    @PostMapping
    public ResponseEntity<ExhibitionResponse> createExhibition(@Valid @RequestBody ExhibitionCreateRequest request) {
        log.info("관리자 전시회 생성 API 호출: {}", request.getTitle());
        ExhibitionResponse response = exhibitionService.createExhibition(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @PatchMapping("/{id}")
    public ResponseEntity<ExhibitionResponse> updateExhibition(
            @PathVariable Long id, 
            @Valid @RequestBody ExhibitionUpdateRequest request) {
        log.info("관리자 전시회 수정 API 호출: ID={}", id);
        ExhibitionResponse response = exhibitionService.updateExhibition(id, request);
        return ResponseEntity.ok(response);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExhibition(@PathVariable Long id) {
        log.info("관리자 전시회 삭제 API 호출: ID={}", id);
        exhibitionService.deleteExhibition(id);
        return ResponseEntity.noContent().build();
    }
} 