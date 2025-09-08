package com.guidely.exhibitionservice.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class ExhibitionSummaryResponse {
    private UUID id;
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private int artworkCount;  // 소속된 작품 수
    private LocalDateTime createdAt;
} 