package org.example.dto;

public class StudentResponse {
    private Long id;
    private String name;
    private String surName;
    private SubjectListResponse courses;

    public StudentResponse() {
    }

    public StudentResponse(Long id, String name, String surName, SubjectListResponse courses) {
        this.id = id;
        this.name = name;
        this.surName = surName;
        this.courses = courses;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public SubjectListResponse getCourses() {
        return courses;
    }

    public void setCourses(SubjectListResponse courses) {
        this.courses = courses;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surName='" + surName + '\'' +
                ", courses=" + courses +
                '}';
    }
}
