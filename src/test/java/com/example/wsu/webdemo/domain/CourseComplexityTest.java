package com.example.wsu.webdemo.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CourseComplexityTest {

    @Test
    public void testExposesCourseComplexities() {
        assertEquals("Easy", CourseComplexity.EASY);
        assertEquals("Medium", CourseComplexity.MEDIUM);
        assertEquals("Hard", CourseComplexity.HARD);
        assertEquals("Unknown", CourseComplexity.UNKNOWN);
    }
}
