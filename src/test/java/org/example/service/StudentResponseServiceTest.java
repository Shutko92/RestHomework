package org.example.service;

import com.google.gson.Gson;
import org.example.convertion.FromJsonStudentMapper;
import org.example.dto.StudentDto;
import org.example.dto.StudentListResponse;
import org.example.dto.SubjectListResponse;
import org.example.dto.StudentResponse;
import org.example.repository.StudentRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class StudentResponseServiceTest {
    @Mock
    private static StudentRepository studentRepository;
    @InjectMocks
    private static StudentService studentService;
    private static StudentDto testDto = new StudentDto("Testname", "TestSurname");
    private static StudentResponse testStudentResponse = new StudentResponse(1L, "Testname", "TestSurname", new SubjectListResponse());
    private static StudentListResponse testListStudents = new StudentListResponse();

    @BeforeAll
    static void setUp() {
        studentService = new StudentService(studentRepository, new Gson(), new FromJsonStudentMapper());
        testListStudents.add(testStudentResponse);
    }

    @Test
    @Order(2)
    void getInvokesFindAllAndReturnIsEqual() {
        String[] ids = {""};
        when(studentRepository.findAll()).thenReturn(testListStudents);
        String actual = studentService.get(ids);
        String expected = "{\"students\":[{\"id\":1,\"name\":\"Testname\",\"surName\":\"TestSurname\",\"courses\":{\"subjects\":[]}}]}";
        verify(studentRepository, times(1)).findAll();
        assertEquals(expected, actual);
    }

    @Test
    @Order(3)
    void getInvokesFindOneAndReturnIsEqual() {
        String[] ids = {"1"};
        when(studentRepository.findOne(1L)).thenReturn(testStudentResponse);
        String actual = studentService.get(ids);
        String expected = "{\"id\":1,\"name\":\"Testname\",\"surName\":\"TestSurname\",\"courses\":{\"subjects\":[]}}";
        verify(studentRepository, times(1)).findOne(1L);
        assertEquals(expected, actual);
    }

    @Test
    @Order(1)
    void addInvokesInsertAndReturnIsEqual() {
        String actual = studentService.add(testDto);
        verify(studentRepository, times(1)).insert(testDto);
        assertEquals("success", actual);
    }

    @Test
    @Order(5)
    void deleteInvokesDeleteAndReturnIsEqual() {
        String actual = studentService.delete(1L);
        verify(studentRepository, times(1)).delete(1L);
        assertEquals("success", actual);
    }

    @Test
    @Order(4)
    void updateInvokesRepositoryAndReturnIsTrue() {
        Map<String, String> map = new HashMap<>();
        map.put("name", "Update");
        map.put("surname", "empty");
        when(studentRepository.findOne(1L)).thenReturn(testStudentResponse);
        when(studentRepository.update(any(StudentResponse.class))).thenReturn(true);
        boolean actual = studentService.update(1L, map);
        verify(studentRepository, times(1)).findOne(1L);
        assertTrue(actual);
    }
}