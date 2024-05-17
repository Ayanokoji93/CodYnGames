package Classes;

import a.*;

import Compiler.*;

import javax.script.ScriptException;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;

public class Main extends Application {

    private final String WINDOW_TITLE = "CodYnGame";
    private final double WINDOW_WIDTH = 960;
    private final double WINDOW_HEIGHT = 540;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene mainViewScene = new Scene(new Window(10));
        primaryStage.setScene(mainViewScene);

        String cssPath = Main.class.getResource("/assets/style.css").toExternalForm();
        mainViewScene.getStylesheets().add(cssPath);

        primaryStage.setTitle(WINDOW_TITLE);
        primaryStage.setWidth(WINDOW_WIDTH);
        primaryStage.setHeight(WINDOW_HEIGHT);

        primaryStage.show();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        launch(args);
        /*try  {
            Exercise exo2 = new Exercise("exo 2", "python", "Write a python function that" +
                    "calculates the product of two numbers passed as parameters. \n");
            System.out.println(exo2);

            // Exécuter la méthode executePython pour générer le fichier utilisateur
            PythonFile pythonFile = new PythonFile();
            String userCodeOutput = pythonFile.executePython(exo2.getCode());
            System.out.println("User Code Output: " + userCodeOutput);

            String inputData = "3\n4\n";

            // Commandes pour exécuter les programmes de correction et de l'utilisateur
            String correctorProgramCommand = "python C:\\Users\\Fayçal\\Desktop\\JavaProject\\CodYnGames\\tcorrection\\correction.py";
            String userProgramCommand = "python C:\\Users\\Fayçal\\Desktop\\JavaProject\\CodYnGames\\tempFile\\temp.py";

            // Exécuter le programme de correction
            String correctorOutput = ExternalProgramExecutor.executeProgram(correctorProgramCommand, inputData);
            System.out.println("Corrector Output: " + correctorOutput);

            // Exécuter le programme utilisateur après avoir généré le fichier utilisateur et fourni les données d'entrée
            String userOutput = ExternalProgramExecutor.executeProgram(userProgramCommand, inputData);
            System.out.println("User Output: " + userOutput);

            // Comparer les sorties
            if (correctorOutput.equals(userOutput)) {
                System.out.println("Le code utilisateur est correct.");
            } else {
                System.out.println("Le code utilisateur est incorrect.");
                System.out.println("Sortie attendue: " + correctorOutput);
                System.out.println("Sortie reçue: " + userOutput);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            }*/



           Exercise exo1 = new Exercise("exo 1", "c", "Write a C function that calculates the product"
                    + " of two numbers passed as parameters.\n");
            System.out.println(exo1);
            if (exo1.getLanguage().equals("c")) {
                CFile cFile = new CFile();
                String result = cFile.executeC(exo1.getCode());
            }
            Exercise exo3 = new Exercise("exo 3", "java", "Write a javafunction that" +
                    "calculates the product of two numbers passed as parameters. \n");
            System.out.println(exo3);
            if(exo3.getLanguage().equals("java")) {
                JavaFile javaFile = new JavaFile();
                javaFile.executeJava(exo3.getCode());
            }
          /*Exercise exo4 = new Exercise("exo 4", "php", "Write a php function that calculates the product of two numbers passed as parameters.");
            System.out.println(exo4);

            if (exo4.getLanguage().equals("php")) {
                PhpFile phpFile = new PhpFile();
                String code = exo4.getCode();
                if (code == null || code.isEmpty()) {
                    throw new IllegalArgumentException("PHP code cannot be null or empty");
                }
                String result = phpFile.executePhp(code);
                System.out.println("Résultat de l'exécution du code PHP : " + result);
            }*/

          /* Exercise exo5 = new Exercise("exo 5", "js", "Write a js function that" +
                    "calculates the product of two numbers passed as parameters. \n");
            System.out.println(exo5);
            if(exo5.getLanguage().equals("js")) {
                JavaScriptFile javascriptFile = new JavaScriptFile();
                javascriptFile.executeJavaScript();
            }*//*
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }
}