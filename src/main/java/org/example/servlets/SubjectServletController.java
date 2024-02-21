package org.example.servlets;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.convertion.FromJsonSubjectMapper;
import org.example.convertion.ResponseBuilder;
import org.example.dto.SubjectDto;
import org.example.repository.StudentRepository;
import org.example.repository.SubjectRepository;
import org.example.convertion.IdParser;
import org.example.service.SubjectService;

import java.io.IOException;
import java.util.Map;

@WebServlet("/subjects/*")
public class SubjectServletController extends HttpServlet {
    private SubjectService subjectService;
    private IdParser parser;
    private ResponseBuilder responseBuilder;
    private FromJsonSubjectMapper mapper;
    @Override
    public void init() throws ServletException {
        parser = new IdParser();
        responseBuilder = new ResponseBuilder();
        mapper = new FromJsonSubjectMapper();
        subjectService = new SubjectService(new SubjectRepository(), new StudentRepository(),
                new Gson(), mapper);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] ids = parser.parse(req);
        String jsonText = subjectService.get(ids);
        responseBuilder.build(resp, jsonText, HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SubjectDto dto = mapper.toSubject(req);
        String jsonText = subjectService.add(dto);
        responseBuilder.build(resp, jsonText, HttpServletResponse.SC_CREATED);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] ids = parser.parse(req);
        Map<String, String> params = mapper.checkForUpdate(req);
        if (subjectService.update(ids, params)) {
            responseBuilder.build(resp, "success", HttpServletResponse.SC_OK);
        }else {
            responseBuilder.build(resp, "failed", HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] ids = parser.parse(req);
        String jsonText = subjectService.delete(ids);
        responseBuilder.build(resp, jsonText, HttpServletResponse.SC_NO_CONTENT);
    }
}
