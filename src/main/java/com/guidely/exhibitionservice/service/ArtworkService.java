package com.guidely.exhibitionservice.service;

import com.guidely.exhibitionservice.dto.ArtworkCreateRequest;
import com.guidely.exhibitionservice.dto.ArtworkResponse;
import com.guidely.exhibitionservice.dto.ArtworkUpdateRequest;
import com.guidely.exhibitionservice.entity.Artwork;
import com.guidely.exhibitionservice.entity.Exhibition;
import com.guidely.exhibitionservice.exception.ArtworkNotFoundException;
import com.guidely.exhibitionservice.exception.ExhibitionNotFoundException;
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
        log.info("모든 작품 조회 요청");
        List<Artwork> artworks = artworkRepository.findAll();
        return artworks.stream()
                .map(this::mapToArtworkResponse)
                .collect(Collectors.toList());
    }
    
    public ArtworkResponse getArtworkById(Long id) {
        log.info("작품 조회 요청: ID={}", id);
        
        Artwork artwork = artworkRepository.findById(id)
                .orElseThrow(() -> new ArtworkNotFoundException("작품을 찾을 수 없습니다: " + id));
        
        return mapToArtworkResponse(artwork);
    }
    
    @Transactional
    public ArtworkResponse createArtwork(ArtworkCreateRequest request) {
        log.info("작품 생성 요청: {}", request.getTitle());
        
        Exhibition exhibition = exhibitionRepository.findById(request.getExhibitionId())
                .orElseThrow(() -> new ExhibitionNotFoundException("전시회를 찾을 수 없습니다: " + request.getExhibitionId()));
        
        Artwork artwork = Artwork.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .artist(request.getArtist())
                .creationYear(request.getCreationYear())
                .medium(request.getMedium())
                .style(request.getStyle())
                .imageUrl(request.getImageUrl())
                .keywords(request.getKeywords())
                .exhibition(exhibition)
                .build();
        
        Artwork savedArtwork = artworkRepository.save(artwork);
        log.info("작품 생성 완료: ID={}, 제목={}", savedArtwork.getId(), savedArtwork.getTitle());
        
        return mapToArtworkResponse(savedArtwork);
    }
    
    @Transactional
    public ArtworkResponse updateArtwork(Long id, ArtworkUpdateRequest request) {
        log.info("작품 수정 요청: ID={}", id);
        
        Artwork artwork = artworkRepository.findById(id)
                .orElseThrow(() -> new ArtworkNotFoundException("작품을 찾을 수 없습니다: " + id));
        
        if (request.getTitle() != null) {
            artwork.setTitle(request.getTitle());
        }
        if (request.getDescription() != null) {
            artwork.setDescription(request.getDescription());
        }
        if (request.getArtist() != null) {
            artwork.setArtist(request.getArtist());
        }
        if (request.getCreationYear() != null) {
            artwork.setCreationYear(request.getCreationYear());
        }
        if (request.getMedium() != null) {
            artwork.setMedium(request.getMedium());
        }
        if (request.getStyle() != null) {
            artwork.setStyle(request.getStyle());
        }
        if (request.getImageUrl() != null) {
            artwork.setImageUrl(request.getImageUrl());
        }
        if (request.getKeywords() != null) {
            artwork.setKeywords(request.getKeywords());
        }
        
        Artwork updatedArtwork = artworkRepository.save(artwork);
        log.info("작품 수정 완료: ID={}, 제목={}", updatedArtwork.getId(), updatedArtwork.getTitle());
        
        return mapToArtworkResponse(updatedArtwork);
    }
    
    public List<ArtworkResponse> getArtworksByExhibitionId(Long exhibitionId) {
        log.info("전시회별 작품 조회 요청: 전시회 ID={}", exhibitionId);
        List<Artwork> artworks = artworkRepository.findByExhibitionId(exhibitionId);
        return artworks.stream()
                .map(this::mapToArtworkResponse)
                .collect(Collectors.toList());
    }
    
    public List<ArtworkResponse> searchArtworksByTitle(String title) {
        log.info("제목으로 작품 검색 요청: {}", title);
        List<Artwork> artworks = artworkRepository.findByTitleContainingIgnoreCase(title);
        return artworks.stream()
                .map(this::mapToArtworkResponse)
                .collect(Collectors.toList());
    }
    
    public List<ArtworkResponse> searchArtworksByArtist(String artist) {
        log.info("작가로 작품 검색 요청: {}", artist);
        List<Artwork> artworks = artworkRepository.findByArtistContainingIgnoreCase(artist);
        return artworks.stream()
                .map(this::mapToArtworkResponse)
                .collect(Collectors.toList());
    }
    
    public List<ArtworkResponse> getTopArtworksByLikeCount() {
        log.info("인기 작품 조회 요청");
        List<Artwork> artworks = artworkRepository.findTopArtworksByLikeCount();
        return artworks.stream()
                .map(this::mapToArtworkResponse)
                .collect(Collectors.toList());
    }
    
    public List<ArtworkResponse> getArtworkResponsesByExhibitionId(Long exhibitionId) {
        return getArtworksByExhibitionId(exhibitionId);
    }
    
    private ArtworkResponse mapToArtworkResponse(Artwork artwork) {
        return ArtworkResponse.builder()
                .id(artwork.getId())
                .title(artwork.getTitle())
                .description(artwork.getDescription())
                .artist(artwork.getArtist())
                .creationYear(artwork.getCreationYear())
                .medium(artwork.getMedium())
                .style(artwork.getStyle())
                .imageUrl(artwork.getImageUrl())
                .keywords(artwork.getKeywords())
                .likeCount(artwork.getLikeCount())
                .dislikeCount(artwork.getDislikeCount())
                .exhibitionId(artwork.getExhibition() != null ? artwork.getExhibition().getId() : null)
                .createdAt(artwork.getCreatedAt())
                .updatedAt(artwork.getUpdatedAt())
                .build();
    }
} 