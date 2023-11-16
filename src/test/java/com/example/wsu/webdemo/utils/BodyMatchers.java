package com.example.wsu.webdemo.utils;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.assertj.core.api.Assertions.assertThat;

public class BodyMatchers {
    private final ObjectMapper mapper = new ObjectMapper();

    /**
     * Asserts a given object is returned as JSON in the response.
     *
     * @param expectedObject object that should be present in response body.
     * @param targetClass class object of expected type. This is provided to an ObjectMapper
     *                    and used to map the response body to an object.
     * @return ResultMatcher
     */
    public <T> ResultMatcher toContain(T expectedObject, Class<T> targetClass) {
        return mvcResult -> {
            JSONObject res = new JSONObject(mvcResult.getResponse().getContentAsString());
            T actualObject = mapper.readValue(res.toString(), targetClass);
            assertThat(actualObject).usingRecursiveComparison().isEqualTo(expectedObject);
        };
    }

    /**
     * Asserts a given list of objects are returned as JSON in the response.
     *
     * @param expectedList object list that should be present in response body.
     * @param targetClass class object of expected type. This is provided to an ObjectMapper
     *                    and used to map the response body to a list of objects.
     * @return ResultMatcher
     */
    public <T> ResultMatcher toContain(List<T> expectedList, Class<T> targetClass) {
        JavaType type = mapper.getTypeFactory().constructCollectionType(List.class, targetClass);
        return mvcResult -> {
            JSONArray res = new JSONArray(mvcResult.getResponse().getContentAsString());
            List<T> actualList = mapper.readValue(res.toString(), type);
            assertThat(actualList).usingRecursiveComparison().isEqualTo(expectedList);
        };
    }

    public static BodyMatchers responseBody() {
        return new BodyMatchers();
    }
}
