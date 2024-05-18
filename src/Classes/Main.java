package Classes;

import a.*;

import Compiler.*;

import javax.script.ScriptException;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
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
        if (args.length < 2) {
            System.err.println("Usage: java Main <seed> <mode>");
            System.err.println("mode: generate or verify");
            return;
        }

        long seed = Long.parseLong(args[0]);
        String mode = args[1];

        if (mode.equals("generate")) {
            String numbers = StdinStdout.generateNumbers(seed);
            System.out.println(numbers);
        } else if (mode.equals("verify")) {
            try {
                String numbers = StdinStdout.getNumbersForVerification(seed);
                System.out.println(numbers);

                PythonFile pythonFile = new PythonFile();
                pythonFile.askResponse();
                String pythonCode = pythonFile.getResponse();
                String result = pythonFile.executePython(pythonCode);
                System.out.println("Résultat du Python: " + result);

                int userResult = Integer.parseInt(result.trim());

                String verificationMessage = StdinStdout.verifyNumbers(seed, userResult);
                if (verificationMessage.equals("Résultat correct")) {
                    System.out.println(verificationMessage);
                } else {
                    System.err.println(verificationMessage);
                }

                pythonFile.deleteTempFile();

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("Mode inconnu. Utilisez 'generate' ou 'verify'.");
        }
    }
}
