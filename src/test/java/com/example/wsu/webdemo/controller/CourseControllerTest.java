package com.example.wsu.webdemo.controller;

import com.example.wsu.webdemo.config.TestSecurityConfig;
import com.example.wsu.webdemo.domain.CourseLevel;
import com.example.wsu.webdemo.dto.CourseDTO;
import com.example.wsu.webdemo.exception.CourseNotFoundException;
import com.example.wsu.webdemo.security.Role;
import com.example.wsu.webdemo.service.CourseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static com.example.wsu.webdemo.utils.BodyMatchers.responseBody;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CourseController.class)
@Import(TestSecurityConfig.class)
public class CourseControllerTest {
    private final String BASE_URL = "/courses";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseService courseService;

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void createCourse() throws Exception {
        mockMvc.perform(post(BASE_URL)
                .contentType("application/json")
                .header("role", Role.ADMIN)
                .content(mapper.writeValueAsString(validDTO())))
            .andExpect(status().isCreated());
    }

    @Test
    public void getCourse() throws Exception {
        CourseDTO course = validDTO();
        when(courseService.getCourse(1L)).thenReturn(course);

        mockMvc.perform(get(BASE_URL + "/{id}", 1L)
                .header("role", "USER"))
            .andExpect(status().isOk())
            .andExpect(responseBody().toContain(course, CourseDTO.class));
    }

    @Test
    public void getCourseThatDoesNotExist() throws Exception {
        when(courseService.getCourse(1L)).thenThrow(new CourseNotFoundException(1L));
        mockMvc.perform(get(BASE_URL + "/{id}", 1L).header("role", "USER"))
            .andExpect(status().isNotFound());
    }

    @Test
    public void getAllCourses() throws Exception {
        List<CourseDTO> courseList = Collections.singletonList(validDTO());
        when(courseService.getAllCourses()).thenReturn(courseList);

        mockMvc.perform(get(BASE_URL).header("role", "USER"))
            .andExpect(status().isOk())
            .andExpect(responseBody().toContain(courseList, CourseDTO.class));
    }

    @Test
    public void updateCourse() throws Exception {
        CourseDTO updatedCourse = validDTO();
        when(courseService.updateCourse(1L, updatedCourse)).thenReturn(updatedCourse);

        mockMvc.perform(put(BASE_URL + "/{id}", 1L).header("role", "USER")
                .contentType("application/json")
                .content(mapper.writeValueAsString(validDTO())))
            .andExpect(status().isOk())
            .andExpect(responseBody().toContain(updatedCourse, CourseDTO.class));
    }

    @Test
    public void updateCourseThatDoesNotExist() throws Exception {
        when(courseService.updateCourse(1L, validDTO())).thenThrow(new CourseNotFoundException(1L));

        mockMvc.perform(put(BASE_URL + "/{id}", 1L).header("role", "user")
                .contentType("application/json")
                .content(mapper.writeValueAsString(validDTO())))
            .andExpect(status().isNotFound());
    }

    @Test
    public void deleteCourse() throws Exception {
        mockMvc.perform(delete(BASE_URL + "/{id}", 1L).header("role", "USER"))
            .andExpect(status().isNoContent());
    }

    private CourseDTO validDTO() {
        return CourseDTO.builder()
            .type("CS")
            .code("1000")
            .name("Technology and Society")
            .level(CourseLevel.UNDERGRADUATE)
            .build();
    }
}
