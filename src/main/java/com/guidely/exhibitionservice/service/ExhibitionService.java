package com.guidely.exhibitionservice.service;

import com.guidely.exhibitionservice.dto.ExhibitionCreateRequest;
import com.guidely.exhibitionservice.dto.ExhibitionResponse;
import com.guidely.exhibitionservice.dto.ExhibitionUpdateRequest;
import com.guidely.exhibitionservice.dto.ExhibitionSummaryResponse;
import com.guidely.exhibitionservice.dto.ExhibitionDropdownResponse;
import com.guidely.exhibitionservice.entity.Exhibition;
import com.guidely.exhibitionservice.exception.ExhibitionNotFoundException;
import com.guidely.exhibitionservice.exception.DuplicateTitleException;
import com.guidely.exhibitionservice.repository.ExhibitionRepository;
import com.guidely.exhibitionservice.repository.ArtworkRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ExhibitionService {
    
    private final ExhibitionRepository exhibitionRepository;
    private final ArtworkRepository artworkRepository;
    
    public List<ExhibitionResponse> getAllExhibitions() {
        log.info("전시회 목록 조회 요청");
        List<Exhibition> exhibitions = exhibitionRepository.findAll();
        return exhibitions.stream()
                .map(this::mapToExhibitionResponse)
                .collect(Collectors.toList());
    }
    
    public ExhibitionResponse getExhibitionById(UUID id) {
        log.info("전시회 상세 조회 요청: ID={}", id);
        
        Exhibition exhibition = exhibitionRepository.findById(id)
                .orElseThrow(() -> new ExhibitionNotFoundException("전시회를 찾을 수 없습니다: " + id));
        
        return mapToExhibitionResponse(exhibition);
    }
    
    @Transactional
    public ExhibitionResponse createExhibition(ExhibitionCreateRequest request) {
        log.info("전시회 생성 요청: {}", request.getTitle());
        
        // 제목 중복 검증
        if (exhibitionRepository.existsByTitle(request.getTitle())) {
            throw new DuplicateTitleException("이미 존재하는 전시회 제목입니다: " + request.getTitle());
        }
        
        Exhibition exhibition = Exhibition.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .build();
        
        Exhibition savedExhibition = exhibitionRepository.save(exhibition);
        log.info("전시회 생성 완료: ID={}, 제목={}", savedExhibition.getId(), savedExhibition.getTitle());
        
        return mapToExhibitionResponse(savedExhibition);
    }
    
    @Transactional
    public ExhibitionResponse updateExhibition(UUID id, ExhibitionUpdateRequest request) {
        log.info("전시회 수정 요청: ID={}", id);
        
        Exhibition exhibition = exhibitionRepository.findById(id)
                .orElseThrow(() -> new ExhibitionNotFoundException("전시회를 찾을 수 없습니다: " + id));
        
        if (request.getTitle() != null) {
            // 제목 중복 검증 (자신을 제외한 다른 전시회와 중복 체크)
            if (exhibitionRepository.existsByTitleAndIdNot(request.getTitle(), id)) {
                throw new DuplicateTitleException("이미 존재하는 전시회 제목입니다: " + request.getTitle());
            }
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
        
        Exhibition updatedExhibition = exhibitionRepository.save(exhibition);
        log.info("전시회 수정 완료: ID={}, 제목={}", updatedExhibition.getId(), updatedExhibition.getTitle());
        
        return mapToExhibitionResponse(updatedExhibition);
    }
    
    @Transactional
    public void deleteExhibition(UUID id) {
        log.info("전시회 삭제 요청: ID={}", id);
        
        Exhibition exhibition = exhibitionRepository.findById(id)
                .orElseThrow(() -> new ExhibitionNotFoundException("전시회를 찾을 수 없습니다: " + id));
        
        exhibitionRepository.delete(exhibition);
        log.info("전시회 삭제 완료: ID={}", id);
    }
    
    // 🔧 관리자용 API 메서드들
    
    public List<ExhibitionSummaryResponse> getExhibitionSummaries() {
        log.info("관리자 전시회 목록 조회 요청");
        List<Exhibition> exhibitions = exhibitionRepository.findAll();
        return exhibitions.stream()
                .map(this::mapToExhibitionSummaryResponse)
                .collect(Collectors.toList());
    }
    
    public List<ExhibitionDropdownResponse> getExhibitionDropdownOptions() {
        log.info("작품 생성용 전시회 드롭다운 목록 조회 요청");
        List<Exhibition> exhibitions = exhibitionRepository.findAll();
        return exhibitions.stream()
                .map(this::mapToExhibitionDropdownResponse)
                .collect(Collectors.toList());
    }
    
    private ExhibitionResponse mapToExhibitionResponse(Exhibition exhibition) {
        return ExhibitionResponse.builder()
                .id(exhibition.getId())
                .title(exhibition.getTitle())
                .description(exhibition.getDescription())
                .startDate(exhibition.getStartDate())
                .endDate(exhibition.getEndDate())
                .artworks(exhibition.getArtworks().stream()
                        .map(artwork -> com.guidely.exhibitionservice.dto.ArtworkResponse.builder()
                                .id(artwork.getId())
                                .title(artwork.getTitle())
                                .artist(artwork.getArtist())
                                .era(artwork.getEra())
                                .description(artwork.getDescription())
                                .imageUrl(artwork.getImageUrl())
                                .exhibitionId(artwork.getExhibition().getId())
                                .createdAt(artwork.getCreatedAt())
                                .build())
                        .collect(Collectors.toList()))
                .createdAt(exhibition.getCreatedAt())
                .build();
    }
    
    private ExhibitionSummaryResponse mapToExhibitionSummaryResponse(Exhibition exhibition) {
        return ExhibitionSummaryResponse.builder()
                .id(exhibition.getId())
                .title(exhibition.getTitle())
                .description(exhibition.getDescription())
                .startDate(exhibition.getStartDate())
                .endDate(exhibition.getEndDate())
                .artworkCount(exhibition.getArtworks().size())  // 소속된 작품 수
                .createdAt(exhibition.getCreatedAt())
                .build();
    }
    
    private ExhibitionDropdownResponse mapToExhibitionDropdownResponse(Exhibition exhibition) {
        String status = getExhibitionStatus(exhibition);
        return ExhibitionDropdownResponse.builder()
                .id(exhibition.getId())
                .title(exhibition.getTitle())
                .status(status)
                .build();
    }
    
    private String getExhibitionStatus(Exhibition exhibition) {
        LocalDate now = LocalDate.now();
        if (now.isBefore(exhibition.getStartDate())) {
            return "예정";
        } else if (now.isAfter(exhibition.getEndDate())) {
            return "종료";
        } else {
            return "진행중";
        }
    }
} 