package com.example.wsu.webdemo.exception;

public class CourseNotFoundException extends RuntimeException {
    public CourseNotFoundException(Long id) {
        super("Invalid course id: " + id);
    }
}
