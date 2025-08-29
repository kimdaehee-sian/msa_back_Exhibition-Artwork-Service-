package com.guidely.exhibitionservice.controller;

import com.guidely.exhibitionservice.dto.ExhibitionCreateRequest;
import com.guidely.exhibitionservice.dto.ExhibitionResponse;
import com.guidely.exhibitionservice.dto.ExhibitionUpdateRequest;
import com.guidely.exhibitionservice.service.ExhibitionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/exhibitions")
@RequiredArgsConstructor
@Slf4j
public class ExhibitionController {
    
    private final ExhibitionService exhibitionService;
    
    @GetMapping
    public ResponseEntity<List<ExhibitionResponse>> getAllExhibitions() {
        log.info("모든 전시회 조회 API 호출");
        List<ExhibitionResponse> responses = exhibitionService.getAllExhibitions();
        return ResponseEntity.ok(responses);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ExhibitionResponse> getExhibitionById(@PathVariable Long id) {
        log.info("전시회 조회 API 호출: ID={}", id);
        ExhibitionResponse response = exhibitionService.getExhibitionById(id);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/status/{status}")
    public ResponseEntity<List<ExhibitionResponse>> getExhibitionsByStatus(@PathVariable String status) {
        log.info("상태별 전시회 조회 API 호출: {}", status);
        List<ExhibitionResponse> responses = exhibitionService.getExhibitionsByStatus(status);
        return ResponseEntity.ok(responses);
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<ExhibitionResponse>> searchExhibitionsByTitle(@RequestParam String title) {
        log.info("제목으로 전시회 검색 API 호출: {}", title);
        List<ExhibitionResponse> responses = exhibitionService.searchExhibitionsByTitle(title);
        return ResponseEntity.ok(responses);
    }
    
    @PostMapping
    public ResponseEntity<ExhibitionResponse> createExhibition(@Valid @RequestBody ExhibitionCreateRequest request) {
        log.info("전시회 생성 API 호출: {}", request.getTitle());
        ExhibitionResponse response = exhibitionService.createExhibition(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @PatchMapping("/{id}")
    public ResponseEntity<ExhibitionResponse> updateExhibition(
            @PathVariable Long id, 
            @Valid @RequestBody ExhibitionUpdateRequest request) {
        log.info("전시회 수정 API 호출: ID={}", id);
        ExhibitionResponse response = exhibitionService.updateExhibition(id, request);
        return ResponseEntity.ok(response);
    }
} 