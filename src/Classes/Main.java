package Classes;

import Compiler.*;
import Compiler.factor.GeneralCompiler;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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

        /*StdinStdout generateExecutor = new StdinStdout("C:\\Users\\FiercePC\\Desktop\\JavaProject\\CodYnGames\\correctionFile\\generateNumberExo4.py");
        PythonFile test = new PythonFile();
        test.askResponse();
        test.writeResponseInFile(test.getResponse());
        String sourceFilePath = test.getPathFile();
        String targetFilePath = "C:\\Users\\FiercePC\\Desktop\\JavaProject\\CodYnGames\\mainInclude\\mainExo5.py";
        test.addContentToFile(targetFilePath);
        List<Integer> list = generateExecutor.executeScript();

        StdinStdout test2 = new StdinStdout(test.getPathFile());
        System.out.println(test2.executeScriptWithArgs(list));*/
    }
}
        /*CFile test2 = new CFile();
        test2.askResponse();
        String sourceFilePath2 = "C:\\Users\\FiercePC\\Desktop\\JavaProject\\CodYnGames\\tempFile\\temp.c";
        String targetFilePath2 = "C:\\Users\\FiercePC\\Desktop\\JavaProject\\CodYnGames\\mainInclude\\mainExo5.c";
        Include.addContentToFile(sourceFilePath2, targetFilePath2);
        JavaFile test3 = new JavaFile();
        test3.askResponse();
        String sourceFilePath3 = "C:\\Users\\FiercePC\\Desktop\\JavaProject\\CodYnGames\\tempFile\\temp.java";
        String targetFilePath3 = "C:\\Users\\FiercePC\\Desktop\\JavaProject\\CodYnGames\\mainInclude\\mainExo5.java";
        Include.addContentToFile(sourceFilePath3, targetFilePath3);
        JavaScriptFile test4 = new JavaScriptFile();
        test4.askResponse();
        String sourceFilePath4 = "C:\\Users\\FiercePC\\Desktop\\JavaProject\\CodYnGames\\tempFile\\temp.py";
        String targetFilePath4 = "C:\\Users\\FiercePC\\Desktop\\JavaProject\\CodYnGames\\mainInclude\\mainExo5.js";
        Include.addContentToFile(sourceFilePath4, targetFilePath4);*/


        /*StdinStdout generateExecutor = new StdinStdout(generateScriptPath);
        List<Integer> numbers = generateExecutor.executeScript();
        for (Integer number : numbers) {
            System.out.println("Nombre: " + number);
        }

        PythonFile executorPythonFile = new PythonFile();
        executorPythonFile.askResponse();
        String userResult = executorPythonFile.execute(executorPythonFile.getResponse(), numbers);

        StdinStdout correctionExecutor = new StdinStdout(correctionScriptPath);
        String correctionResult = correctionExecutor.executeScriptWithArgs(numbers);

        System.out.println("Résultat de l'utilisateur: " + userResult);
        System.out.println("Résultat de la correction: " + correctionResult);

        if (userResult.trim().equalsIgnoreCase(correctionResult.trim())) {
            System.out.println("Résultat correct.");
        } else {
            System.out.println("Pas correct.");
        }*/
