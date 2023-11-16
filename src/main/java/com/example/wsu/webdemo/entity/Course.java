package com.example.wsu.webdemo.entity;

import com.example.wsu.webdemo.domain.CourseLevel;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.example.wsu.webdemo.domain.CourseComplexity.*;

/**
 * Internal (private) representation of a course
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Course {
    @Id
    @GeneratedValue
    private Long id;

    private String type;
    private String code;
    private String name;
    private CourseLevel level;
    private int creditHours;

    /**
     * Calculates course complexity taking course level and
     * credit hours into account
     *
     * @return course complexity
     */
    public String getComplexity() {
        return switch (level) {
            case GRADUATE -> calcGradComplexity();
            case UNDERGRADUATE -> calcUndergradComplexity();
        };
    }

    private String calcGradComplexity() {
        if (creditHours <= 1) {
            return EASY;
        } else if (creditHours <= 3) {
            return MEDIUM;
        } else {
            return HARD;
        }
    }

    private String calcUndergradComplexity() {
        if (creditHours <= 2) {
            return EASY;
        } else if (creditHours <= 4) {
            return MEDIUM;
        } else {
            return HARD;
        }
    }
}
