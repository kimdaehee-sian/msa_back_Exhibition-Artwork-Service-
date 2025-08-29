package com.guidely.exhibitionservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArtworkCreateRequest {
    
    @NotBlank(message = "작품 제목은 필수입니다")
    @Size(max = 200, message = "작품 제목은 200자 이하여야 합니다")
    private String title;
    
    @Size(max = 1000, message = "작품 설명은 1000자 이하여야 합니다")
    private String description;
    
    @Size(max = 100, message = "작가명은 100자 이하여야 합니다")
    private String artist;
    
    private Integer creationYear;
    
    @Size(max = 50, message = "재료는 50자 이하여야 합니다")
    private String medium;
    
    @Size(max = 50, message = "스타일은 50자 이하여야 합니다")
    private String style;
    
    @Size(max = 500, message = "이미지 URL은 500자 이하여야 합니다")
    private String imageUrl;
    
    @Size(max = 1000, message = "키워드는 1000자 이하여야 합니다")
    private String keywords;
    
    @NotNull(message = "전시회 ID는 필수입니다")
    private Long exhibitionId;
} 