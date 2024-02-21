package org.example.service;

import com.google.gson.Gson;
import org.example.convertion.FromJsonSubjectMapper;
import org.example.dto.StudentDto;
import org.example.dto.SubjectDto;
import org.example.dto.SubjectListResponse;
import org.example.dto.StudentResponse;
import org.example.dto.SubjectResponse;
import org.example.repository.StudentRepository;
import org.example.repository.SubjectRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SubjectResponseServiceTest {
    @Mock
    private static StudentRepository studentRepository;
    @Mock
    private static SubjectRepository subjectRepository;
    @InjectMocks
    private static SubjectService subjectService;
    private static SubjectDto testDto = new SubjectDto(1L, "Testname");
    private static SubjectResponse testSubjectResponse = new SubjectResponse(1L, 1L, "Testname");
    private static SubjectListResponse testListSubject = new SubjectListResponse();
    private static StudentResponse testStudentResponse = new StudentResponse(1L, "Testname", "TestSurname", new SubjectListResponse());

    @BeforeAll
    static void setUp() {
        subjectService = new SubjectService(subjectRepository, studentRepository, new Gson(), new FromJsonSubjectMapper());
        testListSubject.add(testSubjectResponse);
    }

    @Test
    @Order(2)
    void getInvokesFindAllAndReturnIsEqual() {
        String[] ids = {""};
        when(subjectRepository.findAll()).thenReturn(testListSubject);
        String actual = subjectService.get(ids);
        String expected = "{\"subjects\":[{\"id\":1,\"StudentId\":1,\"name\":\"Testname\"}]}";
        verify(subjectRepository, times(1)).findAll();
        assertEquals(expected, actual);
    }

    @Test
    @Order(3)
    void getInvokesFindOneAndReturnIsEqual() {
        String[] ids = {"1", "1"};
        when(subjectRepository.findAllByStudent(1L)).thenReturn(testListSubject);
        when(studentRepository.findOne(1L)).thenReturn(testStudentResponse);
        String actual = subjectService.get(ids);
        System.out.println(actual);
        String expected = "{\"subjects\":[{\"id\":1,\"StudentId\":1,\"name\":\"Testname\"}]}";
        verify(subjectRepository, times(1)).findAllByStudent(1L);
        assertEquals(expected, actual);
    }

    @Test
    @Order(1)
    void addInvokesInsertAndReturnIsEqual() {
        studentRepository.insert(new StudentDto("Testname", "TestSurname"));
        String actual = subjectService.add(testDto);
        verify(subjectRepository, times(1)).insert(testDto);
        assertEquals("success", actual);
    }

    @Test
    @Order(4)
    void updateInvokesRepositoryAndReturnIsEqual() {
        String[] ids = {"1", "1"};
        Map<String, String> map = new HashMap<>();
        map.put("name", "Update");
        map.put("student_id", "1");
        when(subjectRepository.findOne(1L)).thenReturn(testSubjectResponse);
        when(subjectRepository.update(any(SubjectResponse.class))).thenReturn(true);
        boolean actual = subjectService.update(ids, map);
        verify(subjectRepository, times(1)).findOne(1L);
        assertTrue(actual);
    }

    @Test
   @Order(5)
    void deleteInvokesDeleteAndReturnIsEqual() {
        String[] ids = {"1", "1"};
        String actual = subjectService.delete(ids);
        verify(subjectRepository, times(1)).delete(1L);
        assertEquals("success", actual);
    }
}