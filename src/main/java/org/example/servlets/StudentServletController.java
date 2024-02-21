package org.example.servlets;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.dto.StudentDto;
import org.example.repository.StudentRepository;
import org.example.convertion.FromJsonStudentMapper;
import org.example.convertion.IdParser;
import org.example.convertion.ResponseBuilder;
import org.example.service.StudentService;

import java.io.IOException;
import java.util.Map;

@WebServlet("/students/*")
public class StudentServletController extends HttpServlet {
    private StudentService studentService;
    private IdParser parser;
    private ResponseBuilder responseBuilder;
    private FromJsonStudentMapper mapper;

    @Override
    public void init() throws ServletException {
        parser = new IdParser();
        responseBuilder = new ResponseBuilder();
        mapper = new FromJsonStudentMapper();
        studentService = new StudentService(new StudentRepository(), new Gson(), mapper);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] ids = parser.parse(req);
        String jsonText = studentService.get(ids);
        responseBuilder.build(resp, jsonText, HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StudentDto dto = mapper.toStudent(req);
        String jsonText = studentService.add(dto);
        responseBuilder.build(resp, jsonText, HttpServletResponse.SC_CREATED);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] ids = parser.parse(req);
        Map<String, String> params = mapper.checkForUpdate(req);
        if (studentService.update(Long.valueOf(ids[0]), params)){
            responseBuilder.build(resp, "success", HttpServletResponse.SC_OK);
        } else {
            responseBuilder.build(resp, "failed", HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] ids = parser.parse(req);
        String jsonText = studentService.delete(Long.valueOf(ids[0]));
        responseBuilder.build(resp, jsonText, HttpServletResponse.SC_NO_CONTENT);
    }
}
