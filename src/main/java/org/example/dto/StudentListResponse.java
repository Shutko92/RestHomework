package org.example.dto;

import java.util.ArrayList;
import java.util.List;

public class StudentListResponse {
    List<StudentResponse> studentResponses = new ArrayList<>();

    public void add(StudentResponse studentResponse) {
        studentResponses.add(studentResponse);
    }
}
