package org.example.convertion;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import jakarta.servlet.http.HttpServletRequest;
import org.example.dto.SubjectDto;
import org.example.dto.SubjectResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FromJsonSubjectMapper {
    private Gson gson = new Gson();

    public SubjectDto toSubject(HttpServletRequest req) throws IOException {
        JsonObject data = gson.fromJson(req.getReader(), JsonObject.class);
        return new SubjectDto(
                data.get("student_id").getAsLong(),
                data.get("name").getAsString());
    }

    public Map<String, String> checkForUpdate(HttpServletRequest req) throws IOException {
        JsonObject data = gson.fromJson(req.getReader(), JsonObject.class);
        Map<String, String> map = new HashMap<>();
        JsonElement nameElement = data.get("name");
        JsonElement surnameElement = data.get("student_id");
        map.put("name", nameElement != null ? nameElement.getAsString() : "empty");
        map.put("student_id", surnameElement != null ? surnameElement.getAsString() : "empty");
        return map;
    }

    public SubjectResponse mapValues(SubjectResponse existing, Map<String, String> params) {
        existing.setName(!params.get("name").equals("empty") ? params.get("name") : existing.getName());
        existing.setStudentId(!params.get("student_id").equals("empty") ? Long.valueOf(params.get("student_id")) : existing.getStudentId());
        return existing;
    }
}
