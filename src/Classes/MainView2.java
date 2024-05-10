package Classes;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.sql.SQLException;
import java.util.List;

public class MainView2 extends VBox {
    public MainView2(double spacing) {
        super(spacing);

        ObservableList components = this.getChildren();

        MenuButton menuButton = new MenuButton("Choisir un langage");

        ExerciseModel exerciseModel = null;
        try {
            exerciseModel = new ExerciseModel();
            List<String> languages = exerciseModel.getLanguages();
            for (String language : languages) {
                MenuItem languageItem = new MenuItem(language);
                menuButton.getItems().add(languageItem);

                ExerciseModel finalExerciseModel = exerciseModel;
                languageItem.setOnAction(event -> {
                    try {
                        List<String> exercises = finalExerciseModel.getExercisesForLanguage(language);
                        // Afficher les exercices dans la TextArea
                        // codeInput.setText(exercises.toString());
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            if (exerciseModel != null) {
                try {
                    exerciseModel.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        TextArea codeInput = new TextArea();
        codeInput.setWrapText(true);

        Button submitButton = new Button("Soumettre");
        submitButton.setOnAction(event -> {
            String code = codeInput.getText();
            System.out.println("Code soumis : " + code);
        });

        components.addAll(menuButton, codeInput, submitButton);
    }
}

