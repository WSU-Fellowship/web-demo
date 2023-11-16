package com.example.wsu.webdemo.service;

import com.example.wsu.webdemo.dto.CourseDTO;
import com.example.wsu.webdemo.entity.Course;
import com.example.wsu.webdemo.exception.CourseNotFoundException;
import com.example.wsu.webdemo.repository.CourseRepository;
import com.example.wsu.webdemo.security.Role;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

/**
 * Performs CRUD operations for courses
 */
@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository repository;
    private final ModelMapper mapper;

    /**
     * Creates a new course
     * @param dto transport object containing course details
     * @return newly created course
     */
    @Secured(Role.ADMIN)
    public CourseDTO createCourse(CourseDTO dto) {
        Course newCourse = mapper.map(dto, Course.class);
        return mapper.map(repository.save(newCourse), CourseDTO.class);
    }

    /**
     * Retrieves a specific course by its id
     * @param id id of course to retrieve
     * @return specified course
     */
    public CourseDTO getCourse(Long id) {
        return repository.findById(id)
            .map(course -> mapper.map(course, CourseDTO.class))
            .orElseThrow(() -> new CourseNotFoundException(id));
    }

    /**
     * Retrieves all courses
     * @return list of available courses
     */
    public List<CourseDTO> getAllCourses() {
        return repository.findAll().stream()
            .map(course -> mapper.map(course, CourseDTO.class))
            .collect(Collectors.toList());
    }

    /**
     * Updates a specific course
     * @param id id of course to update
     * @param dto course details to save
     * @return updated course or 400 response with error message if course cannot be found
     */
    @Secured(Role.ADMIN)
    public CourseDTO updateCourse(Long id, CourseDTO dto) {
        return repository.findById(id)
            .map(course -> {
                course.setType(dto.getType());
                course.setCode(dto.getCode());
                course.setName(dto.getName());
                course.setLevel(dto.getLevel());
                course.setCreditHours(dto.getCreditHours());
                return mapper.map(repository.save(course), CourseDTO.class);
            })
            .orElseThrow(() -> new CourseNotFoundException(id));
    }

    /**
     * Deletes a specific course
     * @param id id of course to delete
     */
    @Secured(Role.ADMIN)
    public void deleteCourse(Long id) {
        repository.deleteById(id);
    }
}
