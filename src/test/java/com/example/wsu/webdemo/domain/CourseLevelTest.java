package com.example.wsu.webdemo.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CourseLevelTest {

    @Test
    public void testGraduateDefinition() {
        assertEquals("Graduate", CourseLevel.GRADUATE.getName());
    }

    @Test
    public void testUndergraduateDefinition() {
        assertEquals("Undergraduate", CourseLevel.UNDERGRADUATE.getName());
    }
}
