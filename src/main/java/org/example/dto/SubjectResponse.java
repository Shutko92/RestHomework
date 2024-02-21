package org.example.dto;

public class SubjectResponse {
    private Long id;
    private Long StudentId;
    private String name;

    public SubjectResponse(Long id, Long studentId, String name) {
        this.id = id;
        StudentId = studentId;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        return "SubjectResponse{" +
                "id=" + id +
                ", StudentId=" + StudentId +
                ", name='" + name + '\'' +
                '}';
    }
}
