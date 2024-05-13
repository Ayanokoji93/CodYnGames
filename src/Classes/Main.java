package Classes;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class Main {
    public static void main(String[] args){

        try{
            Class.forName("com.mysql.jdbc.Driver");

            Connection co = DriverManager.getConnection("jdbc:mysql://localhost:3306/exercises_db?useSSL=false", "root", "");
            Statement stmt = co.createStatement();

            ResultSet res = stmt.executeQuery("SELECT * FROM Exercises");

            while(res.next()){
                String title = res.getString("title");
                String code_languages = res.getString("code_languages");
                String description = res.getString("description");

                Exercise exercise = new Exercise(title,code_languages,description);

                System.out.println(exercise.getName() + ", " + exercise.getLanguage() + ", " + exercise.getStatement());
            }

            co.close();

        }
        catch (SQLException e){
            System.err.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("Erreur lors du chargement du pilote JDBC MySQL : " + e.getMessage());
        }
    }
}
