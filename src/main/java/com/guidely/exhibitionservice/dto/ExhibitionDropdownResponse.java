package com.guidely.exhibitionservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExhibitionDropdownResponse {
    
    private Long id;
    private String title;
    private String status;  // 예정, 진행중, 종료
} 