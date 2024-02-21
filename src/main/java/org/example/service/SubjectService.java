package org.example.service;

import com.google.gson.Gson;
import org.example.convertion.FromJsonSubjectMapper;
import org.example.dto.SubjectDto;
import org.example.dto.SubjectListResponse;
import org.example.dto.SubjectResponse;
import org.example.repository.StudentRepository;
import org.example.repository.SubjectRepository;

import java.util.Map;

public class SubjectService {
    private SubjectRepository subjectRepository;
    private StudentRepository studentRepository;
    private Gson gson;
    private FromJsonSubjectMapper mapper;

    public SubjectService(SubjectRepository subjectRepository, StudentRepository studentRepository, Gson gson, FromJsonSubjectMapper mapper) {
        this.subjectRepository = subjectRepository;
        this.studentRepository = studentRepository;
        this.gson = gson;
        this.mapper = mapper;
    }

    public String get(String[] ids) {
        if (ids[0].isEmpty()) {
            SubjectListResponse all = subjectRepository.findAll();
            return gson.toJson(all);
        } else if (studentRepository.findOne(Long.valueOf(ids[0])) != null) {
            SubjectListResponse allByStudent = subjectRepository.findAllByStudent(Long.valueOf(ids[0]));
            return gson.toJson(allByStudent);
        }
        return "failed";
    }

    public String add(SubjectDto dto) {
        subjectRepository.insert(dto);
        return "success";
    }

    public boolean update(String[] ids, Map<String, String> params) {
        if (ids.length == 2) {
            SubjectResponse existing = subjectRepository.findOne(Long.valueOf(ids[1]));

            SubjectResponse toUpdate = mapper.mapValues(existing, params);
            subjectRepository.update(toUpdate);
            return subjectRepository.update(toUpdate);
        }
        return false;
    }

    public String delete(String[] ids) {
        if (ids.length == 1) {
            subjectRepository.deleteAllByStudent(Long.valueOf(ids[0]));
        } else {
            subjectRepository.delete(Long.valueOf(ids[1]));
        }
        return "success";
    }
}
