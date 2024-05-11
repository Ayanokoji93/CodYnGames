package Classes;

import Compiler.CFile;
import Compiler.JavaFile;
import Compiler.PythonFile;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class HelloWorldApp extends Application {

    private final String WINDOW_TITLE = "CodYnGame";
    private final double WINDOW_WIDTH = 960;
    private final double WINDOW_HEIGHT = 540;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene mainViewScene = new Scene(new MainView2(10));
        primaryStage.setScene(mainViewScene);
        primaryStage.setTitle(WINDOW_TITLE);
        primaryStage.setWidth(WINDOW_WIDTH);
        primaryStage.setHeight(WINDOW_HEIGHT);

        primaryStage.show();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        launch(args);
        try  {
       /* Exercise exo1 = new Exercise("exo 1","c","Write a C function that calculates the product"
                + " of two numbers passed as parameters.\n");
        System.out.println(exo1);
        if(exo1.getLanguage().equals("c")){
            CFile cFile = new CFile();
            cFile.executeC();
        }*/
            /*Exercise exo2 = new Exercise("exo 2", "python", "Write a python function that" +
                    "calculates the product of two numbers passed as parameters. \n");
            System.out.println(exo2);
            if(exo2.getLanguage().equals("python")){
                PythonFile pythonFile = new PythonFile();
                pythonFile.executePython();
            }*/
            Exercise exo3 = new Exercise("exo 3", "java", "Write a javafunction that" +
                    "calculates the product of two numbers passed as parameters. \n");
            System.out.println(exo3);
            if(exo3.getLanguage().equals("java")) {
                JavaFile javaFile = new JavaFile();
                javaFile.executeJava();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }


    }
}

