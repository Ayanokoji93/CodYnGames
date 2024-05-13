package Classes;

import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Pair;

import java.sql.SQLException;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import javafx.scene.Node; 


public class MainView extends VBox {

    private Label exerciseDescriptionLabel;
    private Map<String, String> languageTextMap;

    public MainView(double spacing) {
        super(spacing);

        ObservableList<Node> components = this.getChildren();

        HBox buttonsBox = new HBox();
        buttonsBox.setSpacing(10);

        VBox labelBox = new VBox();
        labelBox.setSpacing(10);

        exerciseDescriptionLabel = new Label();
        exerciseDescriptionLabel.setWrapText(true);

        TextArea codeInput = new TextArea();
        codeInput.setWrapText(true);

        MenuButton exerciseMenuButton = new MenuButton("Choisir un exercice");
        MenuButton languageMenuButton = new MenuButton("Choisir un langage");

        languageTextMap = new HashMap<>();
        languageTextMap.put("Java", "public class Main {\n\tpublic static void main(String[] args) {\n\t\tSystem.out.println(\"Hello, you chose Java!\");\n\t}\n}");
        languageTextMap.put("Python", "print('Hello, you chose Python!')");
        languageTextMap.put("JavaScript", "console.log('Hello, you chose JavaScript!')");
        languageTextMap.put("C", "#include <stdio.h>\n\nint main() {\n\tprintf(\"Hello, you chose C!\");\n\treturn 0;\n}");
        languageTextMap.put("PHP", "<?php\n\techo 'Hello, you chose PHP!';\n?>");

        ExerciseModel exerciseModel = null;
        try {
            exerciseModel = new ExerciseModel();
            List<Pair<String, String>> exercisesAndLanguages = exerciseModel.getLanguagesForExercise();

            // Création du menu d'exercices
            for (Pair<String, String> pair : exercisesAndLanguages) {
                String exerciseTitle = pair.getKey();
                String language = pair.getValue();

                MenuItem exerciseMenuItem = new MenuItem(exerciseTitle);
                ExerciseModel finalExerciseModel = exerciseModel;
                exerciseMenuItem.setOnAction(event -> {
                    exerciseMenuButton.setText(exerciseTitle);
                    updateLanguageMenu(languageMenuButton, exerciseTitle, exercisesAndLanguages,codeInput);
                    updateExerciseDescription(exerciseTitle);

                });
                exerciseMenuButton.getItems().add(exerciseMenuItem);
            }
            buttonsBox.getChildren().addAll(exerciseMenuButton,languageMenuButton);
            labelBox.getChildren().addAll(exerciseDescriptionLabel,codeInput);
            components.addAll(buttonsBox,labelBox);

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

        Button submitButton = new Button("Soumettre");
        submitButton.setOnAction(event -> {
            String code = codeInput.getText();
            System.out.println("Code soumis : " + code);
        });

        components.add(submitButton);
    }

    private void updateLanguageMenu(MenuButton languageMenuButton, String selectedExercise, List<Pair<String, String>> exercisesAndLanguages, TextArea codeInput) {
        languageMenuButton.getItems().clear();

        languageMenuButton.setText("Choisir un langage");
        codeInput.clear();

        for (Pair<String, String> pair : exercisesAndLanguages) {
            String exerciseTitle = pair.getKey();
            String language = pair.getValue();
            if (exerciseTitle.equals(selectedExercise)) {
                String[] languages = language.split(",");

                for (String lang : languages) {
                    MenuItem languageMenuItem = new MenuItem(lang);
                    languageMenuItem.setOnAction(event -> {
                        languageMenuButton.setText(lang);
                        if (languageTextMap.containsKey(lang)) {
                            codeInput.setText(languageTextMap.get(lang));
                        }
                    });
                    languageMenuButton.getItems().add(languageMenuItem);
                }
                break;
            }
        }
    }

    private void updateExerciseDescription(String exerciseTitle) {
        ExerciseModel exerciseModel;
        try {
            exerciseModel = new ExerciseModel();
            List<Pair<String, String>> exercisesAndDescriptions = exerciseModel.getExerciseDescription();

            for (Pair<String, String> pair : exercisesAndDescriptions) {
                if (pair.getKey().equals(exerciseTitle)) {
                    exerciseDescriptionLabel.setText(pair.getValue());
                    break;
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
