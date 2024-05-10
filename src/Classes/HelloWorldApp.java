package Classes;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

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

    public static void main(String[] args)
    {
        launch(args);
    }
}
