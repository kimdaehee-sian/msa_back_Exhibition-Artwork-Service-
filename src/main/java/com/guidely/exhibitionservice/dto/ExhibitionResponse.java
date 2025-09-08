package com.guidely.exhibitionservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExhibitionResponse {
    
    private UUID id;
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<ArtworkResponse> artworks;
    private LocalDateTime createdAt;
} 