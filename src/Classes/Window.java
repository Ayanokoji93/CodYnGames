package Classes;

import Compiler.*;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.util.Pair;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import javafx.scene.Node;

public class Window extends VBox {

    private Label exerciseDescriptionLabel;
    private Map<String, String> languageTextMap;
    private Label resultLabel;

    public Window(double spacing) {
        super(spacing);
        this.setAlignment(Pos.CENTER);

        HBox buttonsBox = new HBox();
        buttonsBox.getStyleClass().add("buttons-box");
        buttonsBox.setSpacing(10);


        VBox labelBox = new VBox();
        labelBox.getStyleClass().add("label-box");
        labelBox.setSpacing(10);

        exerciseDescriptionLabel = new Label();
        exerciseDescriptionLabel.setWrapText(true);

        TextArea codeInput = new TextArea();
        codeInput.setWrapText(true);
        codeInput.getStyleClass().add("text-area");

        resultLabel = new Label();
        resultLabel.setWrapText(true);

        MenuButton exerciseMenuButton = new MenuButton("Choisir un exercice");
        MenuButton languageMenuButton = new MenuButton("Choisir un langage");

        languageTextMap = new HashMap<>();
        languageTextMap.put("Java", "public class Main {\n\tpublic static void main(String[] args) {\n\t\tSystem.out.println(\"Hello, you chose Java!\");\n\t}\n}");
        languageTextMap.put("Python", "print('Hello, you chose Python!')");
        languageTextMap.put("JavaScript", "console.log('Hello, you chose JavaScript!');");
        languageTextMap.put("C", "#include <stdio.h>\n\nint main() {\n\tprintf(\"Hello, you chose C!\");\n\treturn 0;\n}");
        languageTextMap.put("PHP", "<?php\n\techo 'Hello, you chose PHP!';\n?>");

        ExerciseModel exerciseModel = null;
        try {
            exerciseModel = new ExerciseModel();
            List<Pair<String, String>> exercisesAndLanguages = exerciseModel.getLanguagesForExercise();


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
            labelBox.getChildren().add(exerciseDescriptionLabel);

            VBox textAreaContainer = new VBox(codeInput);
            textAreaContainer.getStyleClass().add("text-area-container");
            textAreaContainer.setAlignment(Pos.CENTER);
            VBox.setVgrow(textAreaContainer, Priority.ALWAYS);

            this.getChildren().addAll(buttonsBox, labelBox, textAreaContainer, resultLabel);

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
            String langageChoisi = languageMenuButton.getText();

            try {
                switch (langageChoisi) {
                    case "C":
                        CFile cFile = new CFile();
                        resultLabel.setText(cFile.executeC(code));
                        break;
                    case "Python":
                        PythonFile pythonFile = new PythonFile();
                        resultLabel.setText(pythonFile.executePython(code));
                        break;
                    case "PHP":
                        PhpFile phpFile = new PhpFile();
                        resultLabel.setText(phpFile.executePhp(code));
                        break;
                    case "JavaScript":
                        JavaScriptFile javascriptFile = new JavaScriptFile();
                        resultLabel.setText(javascriptFile.executeJavaScript(code));
                        break;
                    case "Java":
                        JavaFile javaFile = new JavaFile();
                        resultLabel.setText(javaFile.executeJava(code));
                        break;
                    default:
                        resultLabel.setText("Langage non pris en charge");
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        });

        this.getChildren().add(submitButton);
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
