package com.example.wsu.webdemo.controller;

import com.example.wsu.webdemo.dto.CourseDTO;
import com.example.wsu.webdemo.service.CourseService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Exposes CRUD operations for courses
 */
@RestController
@RequestMapping("courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    /**
     * Creates a new course
     * @param course details of course to create
     * @return newly created course
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CourseDTO createCourse(@RequestBody CourseDTO course) {
        return courseService.createCourse(course);
    }

    /**
     * Retrieves a specific course by id
     * @param courseId id of course to retrieve
     * @return specified course
     */
    @GetMapping(value = "/{id}")
    public CourseDTO getCourse(@PathVariable("id") Long courseId) {
        return courseService.getCourse(courseId);
    }

    /**
     * Retrieves all courses
     * @return list of all courses
     */
    @GetMapping
    public List<CourseDTO> getAllCourses() {
        return courseService.getAllCourses();
    }

    /**
     * Updates a specific course
     * @param courseId id of course to update
     * @param courseDTO details of course to update
     * @return updated course
     */
    @PutMapping(value = "/{id}")
    public CourseDTO updateCourse(@PathVariable("id") Long courseId, @RequestBody CourseDTO courseDTO) {
        return courseService.updateCourse(courseId, courseDTO);
    }

    /**
     * Deletes a specific course
     * @param courseId id of course to delete
     */
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCourse(@PathVariable("id") Long courseId) {
        courseService.deleteCourse(courseId);
    }
}
