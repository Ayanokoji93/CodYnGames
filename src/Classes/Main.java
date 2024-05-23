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
        String generateScriptPath = "C:\\Users\\Fayçal\\Desktop\\JavaProject\\CodYnGames\\correctionFile\\generateNumber.py";
        String correctionScriptPath = "C:\\Users\\Fayçal\\Desktop\\JavaProject\\CodYnGames\\correctionFile\\correctionMultiply.py";

        StdinStdout generateExecutor = new StdinStdout(generateScriptPath);
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

        // Comparaison en ignorant les espaces blancs et la casse
        if (userResult.trim().equalsIgnoreCase(correctionResult.trim())) {
            System.out.println("Résultat correct.");
        } else {
            System.out.println("Pas attendu ahah t'es trop bête.");
        }

    }
}
