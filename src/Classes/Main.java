package Classes;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The Main class represents the main entry point of the application.
 */
public class Main extends Application {

    // Parameters of the window
    private final String WINDOW_TITLE = "CodYnGame";
    private final double WINDOW_WIDTH = 1920;
    private final double WINDOW_HEIGHT = 1080;

    /**
     * Starts the JavaFX application.
     *
     * @param primaryStage the primary stage of the application.
     * @throws Exception if an error occurs during the run of the application.
     */
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

    /**
     * Main method, the entry point of the application.
     *
     * @param args the command-line arguments.
     * @throws IOException if an I/O error occurs.
     * @throws InterruptedException if the process is interrupted.
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        launch(args);
    }
}
