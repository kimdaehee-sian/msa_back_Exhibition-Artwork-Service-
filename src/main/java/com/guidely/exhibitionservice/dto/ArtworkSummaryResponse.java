package com.guidely.exhibitionservice.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class ArtworkSummaryResponse {
    private UUID id;
    private String title;
    private String artist;
    private String era;
    private UUID exhibitionId;
    private String exhibitionTitle;  // 소속 전시회 제목
    private LocalDateTime createdAt;
} 