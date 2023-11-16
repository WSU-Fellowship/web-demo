package com.example.wsu.webdemo.service;

import com.example.wsu.webdemo.dto.CourseDTO;
import com.example.wsu.webdemo.entity.Course;
import com.example.wsu.webdemo.exception.CourseNotFoundException;
import com.example.wsu.webdemo.repository.CourseRepository;
import java.util.Collections;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static com.example.wsu.webdemo.utils.TestEntityFactory.validCourse;
import static com.example.wsu.webdemo.utils.TestEntityFactory.validCourseDTO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CourseServiceTest {

    @InjectMocks
    CourseService courseService;

    @Mock
    CourseRepository courseRepository;

    @Spy
    ModelMapper mapper;

    @Test
    public void createCourseReturnsNewCourse() {
        Course newCourse = validCourse();
        when(courseRepository.save(any(Course.class))).thenReturn(newCourse);

        CourseDTO result = courseService.createCourse(validCourseDTO());

        ArgumentCaptor<Course> courseCaptor = ArgumentCaptor.forClass(Course.class);
        verify(courseRepository, times(1)).save(courseCaptor.capture());

        assertEquals(mapper.map(newCourse, CourseDTO.class), result);
    }

    @Test
    public void getCourseReturnsCourseIfFound() {
        when(courseRepository.findById(1L)).thenReturn(Optional.of(validCourse()));

        assertEquals(mapper.map(validCourse(), CourseDTO.class), courseService.getCourse(1L));
    }

    @Test
    public void getCourseThrowsExceptionWhenCourseNotFound() {
        when(courseRepository.findById(1L)).thenReturn(Optional.empty());

        Exception e = assertThrows(CourseNotFoundException.class, () -> courseService.getCourse(1L));
        assertEquals("Invalid course id: 1", e.getMessage());
    }

    @Test
    public void getAllCoursesReturnsAllCourses() {
        when(courseRepository.findAll()).thenReturn(Collections.singletonList(validCourse()));

        assertEquals(1, courseService.getAllCourses().size());
    }

    @Test
    public void updateCourseReturnsUpdatedCourse() {
        when(courseRepository.findById(1L)).thenReturn(Optional.of(validCourse()));
        when(courseRepository.save(any(Course.class))).thenReturn(validCourse());

        assertEquals(mapper.map(validCourse(), CourseDTO.class), courseService.updateCourse(1L, validCourseDTO()));
    }

    @Test
    public void updateCourseThrowsExceptionWhenCourseNotFound() {
        when(courseRepository.findById(1L)).thenReturn(Optional.empty());

        Exception e = assertThrows(CourseNotFoundException.class, () -> courseService.updateCourse(1L, validCourseDTO()));
        assertEquals("Invalid course id: 1", e.getMessage());
    }

    @Test
    public void deleteCourseDeletesCourse() {
        courseService.deleteCourse(1L);

        ArgumentCaptor<Long> idCaptor = ArgumentCaptor.forClass(Long.class);
        verify(courseRepository, times(1)).deleteById(idCaptor.capture());

        assertEquals(1L, idCaptor.getValue());
    }
}
