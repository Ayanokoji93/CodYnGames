package Classes;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javafx.util.Pair;

public class ExerciseModel {
    private final Connection connection;

    public ExerciseModel() throws SQLException, ClassNotFoundException {
        try {
            // Initialisation de la connexion à la base de données
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/exercises_db?useSSL=false", "root", "");
        } catch (SQLException e) {
            System.err.println("Erreur lors de la connexion à la base de données : " + e.getMessage());
            throw e;
        }
    }

    public List<String> getExercise() throws SQLException {
        List<String> exercises = new ArrayList<>();
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            // Création de l'instruction SQL
            statement = connection.createStatement();
            // Exécution de la requête SQL
            resultSet = statement.executeQuery("SELECT DISTINCT title FROM exercises");

            // Traitement des résultats
            while (resultSet.next()) {
                exercises.add(resultSet.getString("title"));
            }
        } finally {
            // Fermeture des ressources
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
        }
        return exercises;
    }

    public List<Pair<String,String>> getExerciseDescription() throws SQLException {
        List<Pair<String, String>> exercisesAndDescription = new ArrayList<>();
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            // Création de l'instruction SQL avec une jointure pour récupérer les exercices et leurs descriptions
            String sqlQuery = "SELECT e.title, e.description FROM exercises e INNER JOIN " +
                    "(SELECT DISTINCT title FROM exercises) ex " +
                    "ON e.title = ex.title";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sqlQuery);

            // Traitement des résultats
            while (resultSet.next()) {
                String exerciseTitle = resultSet.getString("title");
                String description = resultSet.getString("description");
                exercisesAndDescription.add(new Pair<>(exerciseTitle, description));

            }
        } finally {
            // Fermeture des ressources
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
        }
        return exercisesAndDescription;
    }


    public List<Pair<String, String>> getLanguagesForExercise() throws SQLException {
        List<Pair<String, String>> exercisesAndLanguages = new ArrayList<>();
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            // Création de l'instruction SQL avec une jointure pour récupérer les exercices et leurs langages
            String sqlQuery = "SELECT e.title, e.code_languages FROM exercises e INNER JOIN " +
                    "(SELECT DISTINCT title FROM exercises) ex " +
                    "ON e.title = ex.title";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sqlQuery);

            // Traitement des résultats
            while (resultSet.next()) {
                String exerciseTitle = resultSet.getString("title");
                String language = resultSet.getString("code_languages");
                exercisesAndLanguages.add(new Pair<>(exerciseTitle, language));
            }
        } finally {
            // Fermeture des ressources
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
        }
        return exercisesAndLanguages;
    }

    public void close() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }
}
