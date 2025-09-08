package com.guidely.exhibitionservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArtworkCreateRequest {
    
    @NotBlank(message = "작품 제목은 필수입니다")
    private String title;
    
    @NotBlank(message = "작가명은 필수입니다")
    private String artist;
    
    private String era;
    
    private String description;
    
    private String imageUrl;
    
    @NotNull(message = "전시회 ID는 필수입니다")
    private UUID exhibitionId;
} 