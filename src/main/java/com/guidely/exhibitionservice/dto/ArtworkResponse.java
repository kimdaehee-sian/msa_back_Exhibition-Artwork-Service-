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
    private String description;
    private String artist;
    private Integer creationYear;
    private String medium;
    private String style;
    private String imageUrl;
    private String keywords;
    private Integer likeCount;
    private Integer dislikeCount;
    private Long exhibitionId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
} 