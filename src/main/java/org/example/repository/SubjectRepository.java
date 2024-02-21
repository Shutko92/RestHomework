package org.example.repository;

import org.example.db.DBUtils;
import org.example.dto.SubjectDto;
import org.example.dto.SubjectListResponse;
import org.example.dto.SubjectResponse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SubjectRepository {
    private String INSERT_SUBJECT = "INSERT INTO subjects(student_id, name) values(?, ?)";
    private String SELECT_ALL = "SELECT * FROM subjects";
    private String SELECT = "SELECT * FROM subjects WHERE id =?";
    private String SELECT_BY_STUDENT = "SELECT * FROM subjects WHERE student_id =?";
    private String UPDATE_SUBJECT = "UPDATE subjects SET student_id = ?, name = ? WHERE id = ?";
    private String DELETE = "DELETE FROM subjects WHERE id =?";
    private String DELETE_BY_STUDENT = "DELETE FROM subjects WHERE student_id =?";

    public void insert(SubjectDto subject) {
        try(Connection connection = DBUtils.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SUBJECT)) {

            preparedStatement.setLong(1, subject.getStudentId());
            preparedStatement.setString(2, subject.getName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public SubjectListResponse findAll() {
        SubjectListResponse list = new SubjectListResponse();
        try(Connection connection = DBUtils.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL)) {
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Long id = rs.getLong("id");
                Long student_id = rs.getLong("student_id");
                String name = rs.getString("name");
                list.add(new SubjectResponse(id, student_id, name));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public SubjectResponse findOne(Long id) {
        SubjectResponse subjectResponse = null;
        try(Connection connection = DBUtils.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT)) {

            preparedStatement.setLong(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Long unitId = rs.getLong("id");
                Long student_id = rs.getLong("student_id");
                String name = rs.getString("name");
                subjectResponse = new SubjectResponse(unitId, student_id, name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return subjectResponse;
    }

    public SubjectListResponse findAllByStudent(Long studentId) {
        SubjectListResponse list = new SubjectListResponse();
        try(Connection connection = DBUtils.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_STUDENT)) {

            preparedStatement.setLong(1, studentId);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Long id = rs.getLong("id");
                Long student_id = rs.getLong("student_id");
                String name = rs.getString("name");
                list.add(new SubjectResponse(id, student_id, name));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean update(SubjectResponse subjectResponse) {
        boolean rowUpdate = false;
        try(Connection connection = DBUtils.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SUBJECT)) {

            preparedStatement.setLong(1, subjectResponse.getStudentId());
            preparedStatement.setString(2, subjectResponse.getName());
            preparedStatement.setLong(3, subjectResponse.getId());
            System.out.println(preparedStatement);
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

    public void deleteAllByStudent(Long studentId) {
        try(Connection connection = DBUtils.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_STUDENT)) {

            preparedStatement.setLong(1, studentId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
