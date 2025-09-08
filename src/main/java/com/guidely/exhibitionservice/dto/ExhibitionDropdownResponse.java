package com.guidely.exhibitionservice.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class ExhibitionDropdownResponse {
    private UUID id;
    private String title;
    private String status;  // "진행중", "예정", "종료" 등
} 