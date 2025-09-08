package com.guidely.exhibitionservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExhibitionUpdateRequest {
    
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
} 