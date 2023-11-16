package com.example.wsu.webdemo.exception;

/**
 * Domain exception to throw when a specified course cannot be found
 */
public class CourseNotFoundException extends RuntimeException {
    public CourseNotFoundException(Long id) {
        super("Invalid course id: " + id);
    }
}
