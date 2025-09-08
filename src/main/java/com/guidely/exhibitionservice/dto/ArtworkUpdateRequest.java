package com.guidely.exhibitionservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArtworkUpdateRequest {
    
    private String title;
    private String artist;
    private String era;
    private String description;
    private String imageUrl;
} 