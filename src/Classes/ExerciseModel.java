package Classes;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javafx.util.Pair;

/**
 * The ExerciseModel class handles interactions with the exercises database.
 */
public class ExerciseModel {
    private final Connection connection;

    /**
     * Constructs an ExerciseModel object and establishes a connection to the MySQL database.
     *
     * @throws SQLException if a database access error occurs.
     * @throws ClassNotFoundException if the MySQL JDBC driver is not found.
     * @throws IOException if an I/O error occurs.
     * @throws InterruptedException if the process is interrupted.
     */
    public ExerciseModel() throws SQLException, ClassNotFoundException, IOException, InterruptedException {
        if (!isMySQLServerRunning()) {
            startMySQLServer();
        }
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/exercises_db?useSSL=false", "root", "");
    }

    /**
     * Checks if the MySQL server is running by attempting to connect to the database.
     *
     * @return true if the MySQL server is running, false otherwise.
     */
    private boolean isMySQLServerRunning() {
        try {
            DriverManager.getConnection("jdbc:mysql://localhost:3306/exercises_db?useSSL=false", "root", "");
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Starts the MySQL server process.
     *
     * @throws IOException if an I/O error occurs.
     * @throws InterruptedException if the process is interrupted.
     */
    private void startMySQLServer() throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder("C:\\xampp\\mysql\\bin\\mysqld.exe");
        processBuilder.inheritIO();
        Process process = processBuilder.start();
        process.waitFor();
    }

    /**
     * Retrieves a list of exercise titles from the database.
     *
     * @return a list of exercise titles.
     * @throws SQLException if a database access error occurs.
     */
    public List<String> getExercise() throws SQLException {
        List<String> exercises = new ArrayList<>();
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT DISTINCT title FROM exercises");
            while (resultSet.next()) {
                exercises.add(resultSet.getString("title"));
            }
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
        }
        return exercises;
    }

    /**
     * Retrieves a list of exercise titles along with their descriptions from the database.
     *
     * @return a list of pairs containing exercise titles and descriptions.
     * @throws SQLException if a database access error occurs.
     */
    public List<Pair<String,String>> getExerciseDescription() throws SQLException {
        List<Pair<String, String>> exercisesAndDescription = new ArrayList<>();
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            String sqlQuery = "SELECT e.title, e.description FROM exercises e INNER JOIN " +
                    "(SELECT DISTINCT title FROM exercises) ex " +
                    "ON e.title = ex.title";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sqlQuery);
            while (resultSet.next()) {
                String exerciseTitle = resultSet.getString("title");
                String description = resultSet.getString("description");
                exercisesAndDescription.add(new Pair<>(exerciseTitle, description));
            }
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
        }
        return exercisesAndDescription;
    }

    /**
     * Retrieves a list of exercise titles along with their supported programming languages from the database.
     *
     * @return a list of pairs containing exercise titles and supported programming languages.
     * @throws SQLException if a database access error occurs.
     */
    public List<Pair<String, String>> getLanguagesForExercise() throws SQLException {
        List<Pair<String, String>> exercisesAndLanguages = new ArrayList<>();
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            String sqlQuery = "SELECT e.title, e.code_languages FROM exercises e INNER JOIN " +
                    "(SELECT DISTINCT title FROM exercises) ex " +
                    "ON e.title = ex.title";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sqlQuery);
            while (resultSet.next()) {
                String exerciseTitle = resultSet.getString("title");
                String language = resultSet.getString("code_languages");
                exercisesAndLanguages.add(new Pair<>(exerciseTitle, language));
            }
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
        }
        return exercisesAndLanguages;
    }

    /**
     * Closes the database connection.
     *
     * @throws SQLException if a database access error occurs.
     */
    public void close() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }
}
