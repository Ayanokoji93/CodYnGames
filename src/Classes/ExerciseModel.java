package Classes;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExerciseModel {
    private Connection connection;

    public ExerciseModel() throws SQLException, ClassNotFoundException {
        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/exercises_db?useSSL=false", "root", "");
        } catch (SQLException e){
            System.err.println("Erreur lors du chargement du pilote JDBC MySQL : " + e.getMessage());
        }


    }

    public List<String> getLanguages() throws SQLException {
        List<String> languages = new ArrayList<>();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT DISTINCT code_languages FROM exercises");

        while (resultSet.next()) {
            languages.add(resultSet.getString("code_languages"));
        }

        statement.close();
        return languages;
    }

    public List<String> getExercisesForLanguage(String language) throws SQLException {
        List<String> exercises = new ArrayList<>();
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareStatement("SELECT description FROM exercises WHERE code_languages = ?");
        preparedStatement.setString(1, language);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            exercises.add(resultSet.getString("description"));
        }

        preparedStatement.close();
        return exercises;
    }

    public void close() throws SQLException {
        connection.close();
    }
}

