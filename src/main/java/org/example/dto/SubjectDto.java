package org.example.dto;

public class SubjectDto {
    private Long StudentId;
    private String name;

    public SubjectDto(Long studentId, String name) {
        StudentId = studentId;
        this.name = name;
    }

    public Long getStudentId() {
        return StudentId;
    }

    public void setStudentId(Long studentId) {
        StudentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "SubjectDto{" +
                "StudentId=" + StudentId +
                ", name='" + name + '\'' +
                '}';
    }
}
