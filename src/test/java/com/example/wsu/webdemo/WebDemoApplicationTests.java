package com.example.wsu.webdemo;

import com.example.wsu.webdemo.controller.CourseController;
import com.example.wsu.webdemo.repository.CourseRepository;
import com.example.wsu.webdemo.service.CourseService;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class WebDemoApplicationTests {

	@Test
	void contextLoads(ApplicationContext context) {
		assertThat(context).isNotNull();
	}

	@Test
	void hasCourseRelatedBeansRegistered(ApplicationContext context) {
		assertThat(context.getBean(CourseController.class)).isNotNull();
		assertThat(context.getBean(CourseService.class)).isNotNull();
		assertThat(context.getBean(CourseRepository.class)).isNotNull();
	}

	@Test
	void hasModelMapperRegistered(ApplicationContext context) {
		assertThat(context.getBean(ModelMapper.class)).isNotNull();
	}

}
