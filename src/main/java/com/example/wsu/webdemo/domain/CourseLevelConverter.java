package com.example.wsu.webdemo.domain;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.stream.Stream;

/**
 * Required converter that allows us to use domain objects
 * in JPA entity definition
 */
@Converter(autoApply = true) // auto-apply so we don't have to annotate entity member
public class CourseLevelConverter implements AttributeConverter<CourseLevel, String> {
    @Override
    public String convertToDatabaseColumn(CourseLevel courseLevel) {
        return courseLevel == null ? null : courseLevel.getName();
    }

    @Override
    public CourseLevel convertToEntityAttribute(String name) {
        if (name == null) {
            return null;
        }

        return Stream.of(CourseLevel.values())
            .filter(level -> level.getName().equals(name))
            .findFirst()
            .orElseThrow(IllegalAccessError::new);
    }
}
