package com.example.wsu.webdemo.service;

import com.example.wsu.webdemo.dto.CourseDTO;
import java.util.HashMap;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CourseService {

    private HashMap<Long, CourseDTO> db = new HashMap<>();

    public CourseDTO createCourse(CourseDTO course) {
        db.put(course.getId(), course);
        return course;
    }

    public List<CourseDTO> getAllCourses() {
        return db.values().stream().toList();
    }

    public CourseDTO getCourse(Long id) {
        return db.get(id);
    }

    // placeholder for PUT impl

    public void deleteCourse(Long id) {
        db.remove(id);
    }
}
