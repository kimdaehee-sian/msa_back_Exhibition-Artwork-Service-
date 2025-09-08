package com.guidely.exhibitionservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArtworkResponse {
    
    private Long id;
    private String title;
    private String artist;
    private String era;
    private String description;
    private String imageUrl;
    private Long exhibitionId;
    private LocalDateTime createdAt;
} 