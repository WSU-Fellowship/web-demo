package com.example.wsu.webdemo.service;

import com.example.wsu.webdemo.dto.CourseDTO;
import com.example.wsu.webdemo.security.Role;
import java.util.HashMap;
import java.util.List;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

@Service
public class CourseService {
    private HashMap<Long, CourseDTO> db = new HashMap<>();

    @Secured(Role.ADMIN)
    public CourseDTO createCourse(CourseDTO course) {
        db.put(course.getId(), course);
        return course;
    }

    public CourseDTO getCourse(Long id) {
        return db.get(id);
    }

    public List<CourseDTO> getAllCourses() {
        return this.db.values().stream().toList();
    }

    @Secured(Role.ADMIN)
    public CourseDTO updateCourse(Long id, CourseDTO dto) {
        db.remove(id);
        dto.setId(id);
        db.put(id, dto);
        return dto;
    }

    @Secured(Role.ADMIN)
    public void deleteCourse(Long id) {
        db.remove(id);
    }
}
