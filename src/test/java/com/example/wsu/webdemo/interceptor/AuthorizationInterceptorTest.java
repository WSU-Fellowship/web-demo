package com.example.wsu.webdemo.interceptor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@AutoConfigureMockMvc
public class AuthorizationInterceptorTest {
    @MockBean
    AuthorizationInterceptor authorizationInterceptor = new AuthorizationInterceptor();

    @Mock
    MockHttpServletRequest mockHttpServletRequest;

    @Test
    void requestIncludesAdminRoleHeader_returnsTrue() throws Exception {
        mockHttpServletRequest = new MockHttpServletRequest();
        mockHttpServletRequest.addHeader("role", "ADMIN");
        boolean results = authorizationInterceptor.preHandle(mockHttpServletRequest, new MockHttpServletResponse(), new Object());
        Assertions.assertTrue(results);
    }

    @Test
    void whenGetRequest_requestIncludesUserRoleHeader_returnsTrue() throws Exception {
        mockHttpServletRequest = new MockHttpServletRequest();
        mockHttpServletRequest.addHeader("role", "USER");
        mockHttpServletRequest.setRequestURI("/courses");
        mockHttpServletRequest.setMethod("GET");
        boolean results = authorizationInterceptor.preHandle(mockHttpServletRequest, new MockHttpServletResponse(), new Object());
        Assertions.assertTrue(results);
    }

    @Test
    void whenRequestMissingHeaders_returnsFalseWithBadRequestStatus() throws Exception {
        MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
        mockHttpServletRequest = new MockHttpServletRequest();
        mockHttpServletRequest.setRequestURI("/courses");
        mockHttpServletRequest.setMethod("GET");
        boolean results = authorizationInterceptor.preHandle(mockHttpServletRequest,mockHttpServletResponse , new Object());
        Assertions.assertFalse(results);
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED.value(), mockHttpServletResponse.getStatus());
    }
}
