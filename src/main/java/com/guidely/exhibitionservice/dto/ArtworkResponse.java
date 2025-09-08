package com.guidely.exhibitionservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArtworkResponse {
    
    private UUID id;
    private String title;
    private String artist;
    private String era;
    private String description;
    private String imageUrl;
    private UUID exhibitionId;
    private LocalDateTime createdAt;
} 