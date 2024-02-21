package org.example.service;

import com.google.gson.Gson;
import org.example.convertion.FromJsonStudentMapper;
import org.example.dto.StudentDto;
import org.example.dto.StudentListResponse;
import org.example.dto.StudentResponse;
import org.example.repository.StudentRepository;

import java.util.Map;

public class StudentService {
    private StudentRepository studentRepository;
    private Gson gson;
    private FromJsonStudentMapper mapper;

    public StudentService(StudentRepository studentRepository, Gson gson, FromJsonStudentMapper mapper) {
        this.studentRepository = studentRepository;
        this.gson = gson;
        this.mapper = mapper;
    }

    public String get(String[] ids) {
        if (!ids[0].isEmpty()) {
            StudentResponse one = studentRepository.findOne(Long.valueOf(ids[0]));
            return gson.toJson(one);
        } else {
            StudentListResponse all = studentRepository.findAll();
            return gson.toJson(all);
        }
    }

    public String add(StudentDto dto) {
        studentRepository.insert(dto);
        return "success";
    }

    public String delete(Long id) {
        studentRepository.delete(id);
        return "success";
    }

    public boolean update(Long id, Map<String, String> params) {
        StudentResponse existing = studentRepository.findOne(id);
        StudentResponse toUpdate = mapper.mapValues(existing, params);
        return studentRepository.update(toUpdate);
    }
}
