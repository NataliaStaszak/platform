package com.example.platform.course;

public class UserAddRequest {
    private Long UserId;
    private Long CourseId;

    public UserAddRequest(Long userId, Long courseId) {
        UserId = userId;
        CourseId = courseId;
    }

    public UserAddRequest() {
    }

    public Long getUserId() {
        return UserId;
    }

    public void setUserId(Long userId) {
        UserId = userId;
    }

    public Long getCourseId() {
        return CourseId;
    }

    public void setCourseId(Long courseId) {
        CourseId = courseId;
    }
}
