package org.example.convertion;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import jakarta.servlet.http.HttpServletRequest;
import org.example.dto.StudentDto;
import org.example.dto.StudentResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FromJsonStudentMapper {
    private Gson gson = new Gson();

    public StudentDto toStudent(HttpServletRequest req) throws IOException {
        JsonObject data = gson.fromJson(req.getReader(), JsonObject.class);
       return new StudentDto(
                data.get("name").getAsString(),
                data.get("surname").getAsString());
    }

    public Map<String, String> checkForUpdate(HttpServletRequest req) throws IOException {
        JsonObject data = gson.fromJson(req.getReader(), JsonObject.class);
        Map<String, String> map = new HashMap<>();
        JsonElement nameElement = data.get("name");
        JsonElement surnameElement = data.get("surname");
        map.put("name", nameElement != null ? nameElement.getAsString() : "empty");
        map.put("surname", surnameElement != null ? surnameElement.getAsString() : "empty");
        return map;
    }

    public StudentResponse mapValues(StudentResponse existing, Map<String, String> params) {
        existing.setName(!params.get("name").equals("empty") ? params.get("name") : existing.getName());
        existing.setSurName(!params.get("surname").equals("empty") ? params.get("surname") : existing.getSurName());
        return existing;
    }
}
