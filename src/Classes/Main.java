package Classes;

import Compiler.*;

import Compiler.factor.GeneralCompiler;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
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
        if (args.length < 3) {
            System.err.println("Usage: java Main <language> <seed> <mode>");
            System.err.println("language: python, c, java, javascript, php");
            System.err.println("mode: generate or verify");
            return;
        }

        String language = args[0];
        long seed = Long.parseLong(args[1]);
        String mode = args[2];

        GeneralCompiler compiler = null;
        switch (language.toLowerCase()) {
            case "python":
                compiler = new PythonFile();
                break;
            case "c":
                compiler = new CFile();
                break;
            case "java":
                compiler = new JavaFile();
                break;
            case "javascript":
                compiler = new JavaScriptFile();
                break;
            case "php":
                compiler = new PhpFile();
                break;
            default:
                System.err.println("Unknown language. Use python, c, java, javascript, or php.");
                return;
        }

        if (mode.equals("generate")) {
            String numbers = StdinStdout.generateNumbers(seed);
            System.out.println(numbers);
        } else if (mode.equals("verify")) {
            try {
                String numbers = StdinStdout.generateNumbers(seed);
                System.out.println("Les nombres à multiplier sont: " + numbers);

                String[] splitNumbers = numbers.split(" ");
                String number1 = splitNumbers[0];
                String number2 = splitNumbers[1];

                compiler.askResponse();
                String result = compiler.exercisesWNumber(number1, number2);
                System.out.println("Résultat: " + result);

                int userResult = Integer.parseInt(result.trim());
                String verificationMessage = StdinStdout.verifyNumbers(seed, userResult);

                if (verificationMessage.equals("Résultat correct")) {
                    System.out.println(verificationMessage);
                } else {
                    System.err.println(verificationMessage);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (compiler instanceof GeneralCompiler) {
                    ((GeneralCompiler) compiler).deleteTempFile();
                }
            }
        } else {
            System.err.println("Mode inconnu. Utilisez 'generate' ou 'verify'.");
        }
    }
}
