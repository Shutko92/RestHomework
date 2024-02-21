package org.example.dto;

import java.util.ArrayList;
import java.util.List;

public class SubjectListResponse {
    List<SubjectResponse> subjectResponses = new ArrayList<>();

    public void add(SubjectResponse subjectResponse) {
        subjectResponses.add(subjectResponse);
    }
}
