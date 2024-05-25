package Classes;

import Compiler.*;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.util.Pair;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

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

        MenuButton languageMenuButton = new MenuButton("Choisir un langage");
        MenuButton exerciseMenuButton = new MenuButton("Choisir un exercice");

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


            for (String lang : languageTextMap.keySet()) {
                MenuItem languageMenuItem = new MenuItem(lang);
                languageMenuItem.setOnAction(event -> {
                    languageMenuButton.setText(lang);
                    codeInput.setText(languageTextMap.get(lang));
                    updateExerciseMenu(exerciseMenuButton, lang, exercisesAndLanguages, codeInput);
                });
                languageMenuButton.getItems().add(languageMenuItem);
            }

            buttonsBox.getChildren().addAll(languageMenuButton, exerciseMenuButton);
            labelBox.getChildren().add(exerciseDescriptionLabel);

            VBox textAreaContainer = new VBox(codeInput);
            textAreaContainer.getStyleClass().add("text-area-container");
            textAreaContainer.setAlignment(Pos.CENTER);
            VBox.setVgrow(textAreaContainer, Priority.ALWAYS);

            Button submitButton = new Button("Soumettre");
            submitButton.getStyleClass().add("submit-button");
            submitButton.setOnAction(event -> {
                String code = codeInput.getText();
                String selectedLanguage = languageMenuButton.getText();

                try {
                    switch (selectedLanguage) {
                        case "C":
                            CFile cFile = new CFile();
                            //resultLabel.setText(cFile.execute(code));
                            break;
                        case "Python":
                            PythonFile pythonFile = new PythonFile();
                            //resultLabel.setText(pythonFile.execute(code));
                            break;
                        case "PHP":
                            PhpFile phpFile = new PhpFile();
                            //resultLabel.setText(phpFile.execute(code));
                            break;
                        case "JavaScript":
                            JavaScriptFile javascriptFile = new JavaScriptFile();
                            //resultLabel.setText(javascriptFile.execute(code));
                            break;
                        case "Java":
                            JavaFile javaFile = new JavaFile();
                            //resultLabel.setText(javaFile.execute(code));
                            break;
                        default:
                            resultLabel.setText("Langage non pris en charge");
                    }
                } catch (IOException /*| InterruptedException*/ e) {
                    e.printStackTrace();
                }
            });

            VBox submissionBox = new VBox(submitButton, resultLabel);
            submissionBox.setAlignment(Pos.CENTER);
            submissionBox.setSpacing(10);

            this.getChildren().addAll(buttonsBox, labelBox, textAreaContainer, submissionBox);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
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
    }

    private void updateExerciseMenu(MenuButton exerciseMenuButton, String selectedLanguage, List<Pair<String, String>> exercisesAndLanguages, TextArea codeInput) {
        exerciseMenuButton.getItems().clear();
        exerciseMenuButton.setText("Choisir un exercice");

        for (Pair<String, String> pair : exercisesAndLanguages) {
            String exerciseTitle = pair.getKey();
            String languages = pair.getValue();

            if (languages.contains(selectedLanguage)) {
                MenuItem exerciseMenuItem = new MenuItem(exerciseTitle);
                exerciseMenuItem.setOnAction(event -> {
                    exerciseMenuButton.setText(exerciseTitle);
                    updateExerciseDescription(exerciseTitle);
                    codeInput.clear();
                });
                exerciseMenuButton.getItems().add(exerciseMenuItem);
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
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}