package com.fran.spring_boot_neo4j.queryresults;

import com.fran.spring_boot_neo4j.models.User;

/**
 * A query result that encapsulates the relationship between a {@link User} and a {@link Course}.
 */
public class CourseEnrollmentQueryResult {

    private User user;
    private Course course;

    public CourseEnrollmentQueryResult() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
