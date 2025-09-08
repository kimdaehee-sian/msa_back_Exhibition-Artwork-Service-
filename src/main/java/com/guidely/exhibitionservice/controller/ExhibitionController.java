package com.guidely.exhibitionservice.controller;

import com.guidely.exhibitionservice.dto.ExhibitionResponse;
import com.guidely.exhibitionservice.service.ExhibitionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/exhibitions")
@RequiredArgsConstructor
@Slf4j
public class ExhibitionController {
    
    private final ExhibitionService exhibitionService;
    
    @GetMapping
    public ResponseEntity<List<ExhibitionResponse>> getAllExhibitions() {
        log.info("전시회 목록 조회 API 호출");
        List<ExhibitionResponse> responses = exhibitionService.getAllExhibitions();
        return ResponseEntity.ok(responses);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ExhibitionResponse> getExhibitionById(@PathVariable UUID id) {
        log.info("전시회 상세 조회 API 호출: ID={}", id);
        ExhibitionResponse response = exhibitionService.getExhibitionById(id);
        return ResponseEntity.ok(response);
    }
} 