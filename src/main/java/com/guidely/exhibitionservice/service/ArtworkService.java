package com.guidely.exhibitionservice.service;

import com.guidely.exhibitionservice.dto.ArtworkCreateRequest;
import com.guidely.exhibitionservice.dto.ArtworkResponse;
import com.guidely.exhibitionservice.dto.ArtworkUpdateRequest;
import com.guidely.exhibitionservice.dto.ArtworkSummaryResponse;
import com.guidely.exhibitionservice.entity.Artwork;
import com.guidely.exhibitionservice.entity.Exhibition;
import com.guidely.exhibitionservice.exception.ArtworkNotFoundException;
import com.guidely.exhibitionservice.exception.ExhibitionNotFoundException;
import com.guidely.exhibitionservice.exception.DuplicateTitleException;
import com.guidely.exhibitionservice.repository.ArtworkRepository;
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
public class ArtworkService {
    
    private final ArtworkRepository artworkRepository;
    private final ExhibitionRepository exhibitionRepository;
    
    public List<ArtworkResponse> getAllArtworks() {
        log.info("작품 목록 조회 요청");
        List<Artwork> artworks = artworkRepository.findAll();
        return artworks.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    public ArtworkResponse getArtworkById(Long id) {
        log.info("작품 상세 조회 요청: ID={}", id);
        Artwork artwork = artworkRepository.findById(id)
                .orElseThrow(() -> new ArtworkNotFoundException("작품을 찾을 수 없습니다: " + id));
        return convertToResponse(artwork);
    }
    
    @Transactional
    public ArtworkResponse createArtwork(ArtworkCreateRequest request) {
        log.info("작품 생성 요청: {}", request.getTitle());
        
        // 제목 중복 체크
        if (artworkRepository.existsByTitle(request.getTitle())) {
            throw new DuplicateTitleException("이미 존재하는 작품 제목입니다: " + request.getTitle());
        }
        
        // 전시회 존재 확인
        Exhibition exhibition = exhibitionRepository.findById(request.getExhibitionId())
                .orElseThrow(() -> new ExhibitionNotFoundException("전시회를 찾을 수 없습니다: " + request.getExhibitionId()));
        
        Artwork artwork = Artwork.builder()
                .title(request.getTitle())
                .artist(request.getArtist())
                .era(request.getEra())
                .description(request.getDescription())
                .imageUrl(request.getImageUrl())
                .exhibition(exhibition)
                .build();
        
        Artwork savedArtwork = artworkRepository.save(artwork);
        log.info("작품 생성 완료: ID={}, 제목={}", savedArtwork.getId(), savedArtwork.getTitle());
        
        return convertToResponse(savedArtwork);
    }
    
    @Transactional
    public ArtworkResponse updateArtwork(Long id, ArtworkUpdateRequest request) {
        log.info("작품 수정 요청: ID={}", id);
        
        Artwork artwork = artworkRepository.findById(id)
                .orElseThrow(() -> new ArtworkNotFoundException("작품을 찾을 수 없습니다: " + id));
        
        // 제목 중복 체크 (자신 제외)
        if (request.getTitle() != null && 
            artworkRepository.existsByTitleAndIdNot(request.getTitle(), id)) {
            throw new DuplicateTitleException("이미 존재하는 작품 제목입니다: " + request.getTitle());
        }
        
        // 수정할 필드들 업데이트
        if (request.getTitle() != null) {
            artwork.setTitle(request.getTitle());
        }
        if (request.getArtist() != null) {
            artwork.setArtist(request.getArtist());
        }
        if (request.getEra() != null) {
            artwork.setEra(request.getEra());
        }
        if (request.getDescription() != null) {
            artwork.setDescription(request.getDescription());
        }
        if (request.getImageUrl() != null) {
            artwork.setImageUrl(request.getImageUrl());
        }
        
        Artwork updatedArtwork = artworkRepository.save(artwork);
        log.info("작품 수정 완료: ID={}, 제목={}", updatedArtwork.getId(), updatedArtwork.getTitle());
        
        return convertToResponse(updatedArtwork);
    }
    
    @Transactional
    public void deleteArtwork(Long id) {
        log.info("작품 삭제 요청: ID={}", id);
        
        Artwork artwork = artworkRepository.findById(id)
                .orElseThrow(() -> new ArtworkNotFoundException("작품을 찾을 수 없습니다: " + id));
        
        artworkRepository.delete(artwork);
        log.info("작품 삭제 완료: ID={}", id);
    }
    
    public List<ArtworkResponse> getArtworksByExhibitionId(Long exhibitionId) {
        log.info("전시회별 작품 조회 요청: 전시회 ID={}", exhibitionId);
        List<Artwork> artworks = artworkRepository.findByExhibitionId(exhibitionId);
        return artworks.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    // 🔧 관리자 페이지용 메서드들
    
    public List<ArtworkSummaryResponse> getArtworkSummaries() {
        log.info("관리자 작품 목록 조회 요청");
        List<Artwork> artworks = artworkRepository.findAll();
        return artworks.stream()
                .map(this::convertToSummaryResponse)
                .collect(Collectors.toList());
    }
    
    // 🔄 변환 메서드들
    
    private ArtworkResponse convertToResponse(Artwork artwork) {
        return ArtworkResponse.builder()
                .id(artwork.getId())
                .title(artwork.getTitle())
                .artist(artwork.getArtist())
                .era(artwork.getEra())
                .description(artwork.getDescription())
                .imageUrl(artwork.getImageUrl())
                .exhibitionId(artwork.getExhibition().getId())
                .createdAt(artwork.getCreatedAt())
                .build();
    }
    
    private ArtworkSummaryResponse convertToSummaryResponse(Artwork artwork) {
        return ArtworkSummaryResponse.builder()
                .id(artwork.getId())
                .title(artwork.getTitle())
                .artist(artwork.getArtist())
                .era(artwork.getEra())
                .exhibitionId(artwork.getExhibition().getId())
                .exhibitionTitle(artwork.getExhibition().getTitle())
                .createdAt(artwork.getCreatedAt())
                .build();
    }
} 