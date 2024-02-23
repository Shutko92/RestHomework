package org.example.repository;

import org.example.db.DBUtils;
import org.example.dto.StudentDto;
import org.example.dto.StudentListResponse;
import org.example.dto.SubjectListResponse;
import org.example.dto.StudentResponse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentRepository {
    private SubjectRepository subjectRepository = new SubjectRepository();
    private String SELECT_ALL = "SELECT * FROM students";
    private String SELECT = "SELECT * FROM students WHERE id =?";
    private String INSERT_STUDENT = "INSERT INTO students(name, surname) values(?, ?)";
    private String UPDATE_STUDENT = "UPDATE students SET name = ?, surname = ? WHERE id = ?";
    private String DELETE = "DELETE FROM students WHERE id =?";

    public void insert(StudentDto student) {
        try(Connection connection = DBUtils.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_STUDENT)) {

            preparedStatement.setString(1, student.getName());
            preparedStatement.setString(2, student.getSurName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public StudentListResponse findAll() {
        StudentListResponse list = new StudentListResponse();
        try(Connection connection = DBUtils.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL)) {
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                long id = rs.getLong("id");
                String name = rs.getString("name");
                String surname = rs.getString("surname");// тоже в маппер можно переместить
                SubjectListResponse allByStudent = subjectRepository.findAllByStudent(id);
                list.add(new StudentResponse(id, name, surname, allByStudent));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public StudentResponse findOne(Long id) {
        StudentResponse studentResponse = null;
        try(Connection connection = DBUtils.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT)) {

            preparedStatement.setLong(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                long unitId = rs.getLong("id");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                SubjectListResponse allByStudent = subjectRepository.findAllByStudent(id);
                studentResponse = new StudentResponse(unitId, name, surname, allByStudent);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return studentResponse;
    }

    public boolean update(StudentResponse studentResponse) {
        boolean rowUpdate = false;
        try(Connection connection = DBUtils.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_STUDENT)) {

            preparedStatement.setString(1, studentResponse.getName());
            preparedStatement.setString(2, studentResponse.getSurName());
            preparedStatement.setLong(4, studentResponse.getId());
            preparedStatement.executeUpdate();

            rowUpdate = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowUpdate;
    }

    public void delete(Long id) {
        try(Connection connection = DBUtils.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE)) {

            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
