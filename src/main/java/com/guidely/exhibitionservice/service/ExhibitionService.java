package com.guidely.exhibitionservice.service;

import com.guidely.exhibitionservice.dto.ExhibitionCreateRequest;
import com.guidely.exhibitionservice.dto.ExhibitionResponse;
import com.guidely.exhibitionservice.dto.ExhibitionUpdateRequest;
import com.guidely.exhibitionservice.entity.Exhibition;
import com.guidely.exhibitionservice.exception.ExhibitionNotFoundException;
import com.guidely.exhibitionservice.repository.ExhibitionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ExhibitionService {
    
    private final ExhibitionRepository exhibitionRepository;
    private final ArtworkService artworkService;
    
    public List<ExhibitionResponse> getAllExhibitions() {
        log.info("모든 전시회 조회 요청");
        List<Exhibition> exhibitions = exhibitionRepository.findAll();
        return exhibitions.stream()
                .map(this::mapToExhibitionResponse)
                .collect(Collectors.toList());
    }
    
    public ExhibitionResponse getExhibitionById(Long id) {
        log.info("전시회 조회 요청: ID={}", id);
        
        Exhibition exhibition = exhibitionRepository.findById(id)
                .orElseThrow(() -> new ExhibitionNotFoundException("전시회를 찾을 수 없습니다: " + id));
        
        return mapToExhibitionResponse(exhibition);
    }
    
    public List<ExhibitionResponse> getExhibitionsByStatus(String status) {
        log.info("상태별 전시회 조회 요청: {}", status);
        List<Exhibition> exhibitions = exhibitionRepository.findByStatus(status);
        return exhibitions.stream()
                .map(this::mapToExhibitionResponse)
                .collect(Collectors.toList());
    }
    
    public List<ExhibitionResponse> searchExhibitionsByTitle(String title) {
        log.info("제목으로 전시회 검색 요청: {}", title);
        List<Exhibition> exhibitions = exhibitionRepository.findByTitleContainingIgnoreCase(title);
        return exhibitions.stream()
                .map(this::mapToExhibitionResponse)
                .collect(Collectors.toList());
    }
    
    @Transactional
    public ExhibitionResponse createExhibition(ExhibitionCreateRequest request) {
        log.info("전시회 생성 요청: {}", request.getTitle());
        
        Exhibition exhibition = Exhibition.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .location(request.getLocation())
                .status(request.getStatus())
                .imageUrl(request.getImageUrl())
                .build();
        
        Exhibition savedExhibition = exhibitionRepository.save(exhibition);
        log.info("전시회 생성 완료: ID={}, 제목={}", savedExhibition.getId(), savedExhibition.getTitle());
        
        return mapToExhibitionResponse(savedExhibition);
    }
    
    @Transactional
    public ExhibitionResponse updateExhibition(Long id, ExhibitionUpdateRequest request) {
        log.info("전시회 수정 요청: ID={}", id);
        
        Exhibition exhibition = exhibitionRepository.findById(id)
                .orElseThrow(() -> new ExhibitionNotFoundException("전시회를 찾을 수 없습니다: " + id));
        
        if (request.getTitle() != null) {
            exhibition.setTitle(request.getTitle());
        }
        if (request.getDescription() != null) {
            exhibition.setDescription(request.getDescription());
        }
        if (request.getStartDate() != null) {
            exhibition.setStartDate(request.getStartDate());
        }
        if (request.getEndDate() != null) {
            exhibition.setEndDate(request.getEndDate());
        }
        if (request.getLocation() != null) {
            exhibition.setLocation(request.getLocation());
        }
        if (request.getStatus() != null) {
            exhibition.setStatus(request.getStatus());
        }
        if (request.getImageUrl() != null) {
            exhibition.setImageUrl(request.getImageUrl());
        }
        
        Exhibition updatedExhibition = exhibitionRepository.save(exhibition);
        log.info("전시회 수정 완료: ID={}, 제목={}", updatedExhibition.getId(), updatedExhibition.getTitle());
        
        return mapToExhibitionResponse(updatedExhibition);
    }
    
    private ExhibitionResponse mapToExhibitionResponse(Exhibition exhibition) {
        return ExhibitionResponse.builder()
                .id(exhibition.getId())
                .title(exhibition.getTitle())
                .description(exhibition.getDescription())
                .startDate(exhibition.getStartDate())
                .endDate(exhibition.getEndDate())
                .location(exhibition.getLocation())
                .status(exhibition.getStatus())
                .imageUrl(exhibition.getImageUrl())
                .artworks(artworkService.getArtworkResponsesByExhibitionId(exhibition.getId()))
                .createdAt(exhibition.getCreatedAt())
                .updatedAt(exhibition.getUpdatedAt())
                .build();
    }
} 