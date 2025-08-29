package com.guidely.exhibitionservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExhibitionCreateRequest {
    
    @NotBlank(message = "전시회 제목은 필수입니다")
    @Size(max = 200, message = "전시회 제목은 200자 이하여야 합니다")
    private String title;
    
    @Size(max = 1000, message = "전시회 설명은 1000자 이하여야 합니다")
    private String description;
    
    @NotNull(message = "시작일은 필수입니다")
    private LocalDateTime startDate;
    
    @NotNull(message = "종료일은 필수입니다")
    private LocalDateTime endDate;
    
    @Size(max = 100, message = "위치는 100자 이하여야 합니다")
    private String location;
    
    @Size(max = 50, message = "상태는 50자 이하여야 합니다")
    private String status; // ACTIVE, INACTIVE, UPCOMING
    
    @Size(max = 500, message = "이미지 URL은 500자 이하여야 합니다")
    private String imageUrl;
} 