package com.example.wsu.webdemo.service;

import com.example.wsu.webdemo.dto.CourseDTO;
import com.example.wsu.webdemo.exception.CourseNotFoundException;
import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CourseServiceTest {
    private CourseService courseService;

    @BeforeEach
    void init() {
        courseService = new CourseService();
    }

    @Test
    void createCourseCreatesAndReturnsNewCourse() {
        CourseDTO newCourse = courseService.createCourse(
            CourseDTO.builder().id(23L).build()
        );

        assertEquals(23L, newCourse.getId());
        assertEquals(23L, courseService.getCourse(23L).getId());
    }

    @Test
    void getCourseReturnsCourseWhenCourseExists() {
        courseService.createCourse(CourseDTO.builder().id(13L).build());

        CourseDTO fetchedCourse = courseService.getCourse(13L);

        assertEquals(13L, fetchedCourse.getId());
    }

    @Test
    void getCourseThrowsExceptionWhenCourseDoesNotExist() {
        CourseNotFoundException e = assertThrows(CourseNotFoundException.class, () -> {
            courseService.getCourse(123L);
        });

        // optionally assert message, though it can be brittle
        assertEquals("Invalid course id: 123", e.getMessage());
    }

    @Test
    void getAllCoursesReturnsEmptyListWhenNoCourses() {
        assertEquals(Collections.emptyList(), courseService.getAllCourses());
    }

    @Test
    void getAllCoursesReturnsListOfAllCourses() {
        courseService.createCourse(CourseDTO.builder().id(1L).build());
        courseService.createCourse(CourseDTO.builder().id(2L).build());

        assertEquals(2, courseService.getAllCourses().size());
    }

    @Test
    void updateCourseThrowsExceptionWhenCourseDoesNotExist() {
        CourseNotFoundException e = assertThrows(CourseNotFoundException.class, () -> {
            courseService.updateCourse(1L, CourseDTO.builder().id(1L).code("101").build());
        });
    }

    @Test
    void updateCourseUpdatesAndReturnsCourseWhenCourseExists() {
        CourseDTO course = courseService.createCourse(
            CourseDTO.builder().id(1L).code("101").build());
        course.setCode("201");

        CourseDTO updatedCourse = courseService.updateCourse(1L, course);

        assertEquals(course, updatedCourse);
        assertEquals(course, courseService.getCourse(1L));
    }

    @Test
    void deleteCourseDeletesCourse() {
        courseService.createCourse(CourseDTO.builder().id(1L).build());

        courseService.deleteCourse(1L);

        assertEquals(Collections.emptyList(), courseService.getAllCourses());
    }
}
