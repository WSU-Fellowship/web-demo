package com.example.wsu.webdemo.dto;

import com.example.wsu.webdemo.domain.CourseLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CourseDTO {
    private Long id;
    private String type;
    private String code;
    private String name;
    private CourseLevel level;
    private int creditHours;
}

