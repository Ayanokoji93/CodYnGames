package Classes;

import Compiler.*;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.util.Pair;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Window extends VBox {

    private Label exerciseDescriptionLabel;
    private Map<String, String> languageTextMap;
    private Label resultLabel;
    private List<Integer> numbers;

    private static final String[] KEYWORDS = new String[]{
            "abstract", "boolean", "byte", "catch", "char", "class", "const", "debugger", "declare", "delete", "double", "echo", "elif",
            "export", "extends", "False", "final", "finally", "printf", "console.log", "float", "foreach", "function", "global", "if", "implements", "import", "include",
            "insteadof", "interface", "is", "isset", "lambda", "let", "list", "long", "native", "new", "None", "nonlocal", "not", "or", "package",
            "pass", "print", "private", "protected", "public", "raise", "register", "require", "require_once", "restrict", "return", "short",
            "signed", "sizeof", "static", "strictfp", "struct", "super", "switch",
            "this", "throw", "throws", "True", "try", "typedef", "typeof", "union", "unset",
            "unsigned", "use", "var", "void", "while", "with", "System.out.println", "int" , "def", "php"
    };

    private static final Pattern PATTERN = Pattern.compile(
            "(?<KEYWORD>" + String.join("|", KEYWORDS) + ")"
                    + "|(?<PAREN>\\(|\\))"
                    + "|(?<BRACE>\\{|\\})"
                    + "|(?<BRACKET>\\[|\\])"
                    + "|(?<SEMICOLON>;)"
                    + "|(?<STRING>\"([^\"]|\"\")*\")"
                    + "|(?<COMMENT>//[^\n]*|/\\*(.|\\R)*?\\*/)"
    );

    private void executeExercise(String code, String generateScriptPath, String correctionScriptPath, String selectedLanguage, Label resultLabel) throws IOException, InterruptedException {
        StdinStdout generateExecutor = new StdinStdout(generateScriptPath);
        StdinStdout correctionExecutor = new StdinStdout(correctionScriptPath);
        List<Integer> numbers = generateExecutor.executeScript();

        String result = "";
        String correctionResult = correctionExecutor.executeScriptWithArgs(numbers);
        switch (selectedLanguage) {
            case "C":
                CFile cFile = new CFile();
                result = cFile.execute(code, numbers);
                break;
            case "Python":
                PythonFile pythonFile = new PythonFile();
                result = pythonFile.execute(code, numbers);
                break;
            case "PHP":
                PhpFile phpFile = new PhpFile();
                result = phpFile.execute(code, numbers);
                break;
            case "JavaScript":
                JavaScriptFile javascriptFile = new JavaScriptFile();
                result = javascriptFile.execute(code, numbers);
                break;
            case "Java":
                JavaFile javaFile = new JavaFile();
                result = javaFile.execute(code, numbers);
                break;
            default:
                resultLabel.setText("Langage non pris en charge");
                return;
        }

        String finalResult = "Résultat de l'utilisateur: " + result + "\n"
                + "Résultat de la correction : " + correctionResult + "\n";
        if (result.trim().equalsIgnoreCase(correctionResult.trim())) {
            finalResult += ("Résultat correct.");
        } else {
            finalResult += ("Pas correct.");
        }
        resultLabel.setText(finalResult);
    }

    public void executeExerciseInclude(String code, String generateScriptPath, String targetFilePath, String selectedLanguage, Label resultLabel) throws IOException {
        try {

            System.out.println("Exécution de executeExerciseInclude avec le langage: " + selectedLanguage);

            StdinStdout generateExecutor = new StdinStdout(generateScriptPath);
            List<Integer> numbers = generateExecutor.executeScript();

            System.out.println("Nombres générés: " + numbers);

            String result = "";
            switch (selectedLanguage) {
                case "C":
                    CFile cFile = new CFile();
                    cFile.writeResponseInFile(code);
                    cFile.addContentToFile(targetFilePath);

                    StdinStdout cExecutor = new StdinStdout(cFile.getPathFile());
                    result = cExecutor.executeScriptWithArgs(numbers);
                    break;
                case "Python":
                    PythonFile pythonFile = new PythonFile();
                    pythonFile.writeResponseInFile(code);
                    pythonFile.addContentToFile(targetFilePath);

                    StdinStdout pythonExecutor = new StdinStdout(pythonFile.getPathFile());
                    result = pythonExecutor.executeScriptWithArgs(numbers);
                    break;
                case "PHP":
                    PhpFile phpFile = new PhpFile();
                    phpFile.writeResponseInFile(code);
                    phpFile.addContentToFile(targetFilePath);

                    StdinStdout phpExecutor = new StdinStdout(phpFile.getPathFile());
                    result = phpExecutor.executeScriptWithArgs(numbers);
                    break;
                case "JavaScript":
                    JavaScriptFile jsFile = new JavaScriptFile();
                    jsFile.writeResponseInFile(code);
                    jsFile.addContentToFile(targetFilePath);

                    StdinStdout jsExecutor = new StdinStdout(jsFile.getPathFile());
                    result = jsExecutor.executeScriptWithArgs(numbers);
                    break;
                case "Java":
                    JavaFile javaFile = new JavaFile();
                    javaFile.writeResponseInFile(code);
                    javaFile.addContentToFile(targetFilePath);

                    StdinStdout javaExecutor = new StdinStdout(javaFile.getPathFile());
                    result = javaExecutor.executeScriptWithArgs(numbers);
                    break;
                default:
                    resultLabel.setText("Langage non pris en charge");
                    System.out.println("Langage non pris en charge");
                    return;
            }
            resultLabel.setText(result);
            System.out.println("Résultat: " + result);

        } catch (IOException e) {
            resultLabel.setText("Erreur lors de l'exécution du code: " + e.getMessage());
            e.printStackTrace();
        }
    }

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

        CodeArea codeInput = new CodeArea();
        codeInput.setParagraphGraphicFactory(LineNumberFactory.get(codeInput));
        codeInput.textProperty().addListener((obs, oldText, newText) -> {
            codeInput.setStyleSpans(0, computeHighlighting(newText));
        });
        codeInput.getStyleClass().add("text-area");

        resultLabel = new Label();
        resultLabel.setWrapText(true);

        MenuButton languageMenuButton = new MenuButton("Choisir un langage");
        MenuButton exerciseMenuButton = new MenuButton("Choisir un exercice");

        languageTextMap = new HashMap<>();
        languageTextMap.put("Java", "public class Main {\n\tpublic static void main(String[] args) {\n\t\tSystem.out.println(\"Hello, you chose Java!\");\n\t}\n}");
        languageTextMap.put("Python", "def calculate_product(a, b):\n\treturn a * b\n\ndef main():\n\ta = int(input())\n\tb = int(input())\n\tresult = calculate_product(a, b)\n\tprint(result)\n\nif __name__ == \"__main__\":\n\tmain()");
        languageTextMap.put("JavaScript", "console.log(\"Hello, you chose JavaScript!\");");
        languageTextMap.put("C", "#include <stdio.h>\n\nint main() {\n\tprintf(\"Hello, you chose C!\");\n\treturn 0;\n}");
        languageTextMap.put("PHP", "<?php\n\techo \"Hello, you chose PHP!\";\n?>");

        ExerciseModel exerciseModel = null;
        try {
            exerciseModel = new ExerciseModel();
            List<Pair<String, String>> exercisesAndLanguages = exerciseModel.getLanguagesForExercise();

            for (String lang : languageTextMap.keySet()) {
                MenuItem languageMenuItem = new MenuItem(lang);
                languageMenuItem.setOnAction(event -> {
                    languageMenuButton.setText(lang);
                    codeInput.replaceText(languageTextMap.get(lang));
                    resultLabel.setText("");
                    updateExerciseMenu(exerciseMenuButton, lang, exercisesAndLanguages, codeInput, resultLabel);
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
            submitButton.getStyleClass().add("button");
            submitButton.setOnAction(event -> {
                String code = codeInput.getText();
                String selectedLanguage = languageMenuButton.getText();
                String selectedExercise = exerciseMenuButton.getText();

                try {
                    switch (selectedLanguage) {
                        case "C", "Python", "Java", "PHP", "JavaScript":
                            switch (selectedExercise) {
                                case "Produit de nombres entiers":
                                    executeExercise(code, "C:\\Users\\FiercePC\\Desktop\\JavaProject\\CodYnGames\\correctionFile\\generateNumberExo1.py",
                                            "C:\\Users\\FiercePC\\Desktop\\JavaProject\\CodYnGames\\correctionFile\\correctionExo1.py",
                                            selectedLanguage, resultLabel);
                                    break;
                                case "Calcul d'une factorielle":
                                    executeExercise(code, "C:\\Users\\FiercePC\\Desktop\\JavaProject\\CodYnGames\\correctionFile\\generateNumberExo2.py",
                                            "C:\\Users\\FiercePC\\Desktop\\JavaProject\\CodYnGames\\correctionFile\\correctionExo2.py",
                                            selectedLanguage, resultLabel);
                                    break;
                                case "Calcul d'une moyenne":
                                    executeExercise(code, "C:\\Users\\FiercePC\\Desktop\\JavaProject\\CodYnGames\\correctionFile\\generateNumberExo3.py",
                                            "C:\\Users\\FiercePC\\Desktop\\JavaProject\\CodYnGames\\correctionFile\\correctionExo3.py",
                                            selectedLanguage, resultLabel);
                                    break;
                                case "Inverse d'un nombre":
                                    executeExercise(code, "C:\\Users\\FiercePC\\Desktop\\JavaProject\\CodYnGames\\correctionFile\\generateNumberExo4.py",
                                            "C:\\Users\\FiercePC\\Desktop\\JavaProject\\CodYnGames\\correctionFile\\correctionExo4.py",
                                            selectedLanguage, resultLabel);
                                    break;
                                case "Calculer nombre de chiffre d'un entier":
                                    executeExerciseInclude(code, "C:\\Users\\FiercePC\\Desktop\\JavaProject\\CodYnGames\\correctionFile\\generateNumberExo4.py",
                                            "C:\\Users\\FiercePC\\Desktop\\JavaProject\\CodYnGames\\mainInclude\\mainExo5.py",
                                            selectedLanguage, resultLabel);
                                    break;
                                default:
                                    resultLabel.setText("Veuillez choisir un exercice.");
                                    break;
                            }
                            break;
                        default:
                            resultLabel.setText("Veuillez choisir un langage.");
                    }
                } catch (IOException | InterruptedException e) {
                    resultLabel.setText("Erreur lors de l'exécution du code : " + e.getMessage());
                    e.printStackTrace();
                }
            });

            VBox submissionBox = new VBox(submitButton, resultLabel);
            submissionBox.setAlignment(Pos.CENTER);
            submissionBox.setSpacing(10);

            this.getChildren().addAll(buttonsBox, labelBox, textAreaContainer, submissionBox);

        } catch (SQLException | IOException | ClassNotFoundException | InterruptedException e) {
            e.printStackTrace();
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


    private void updateExerciseMenu(MenuButton exerciseMenuButton, String selectedLanguage, List<Pair<String, String>> exercisesAndLanguages, CodeArea codeInput, Label resultLabel) {
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
                    resultLabel.setText("");
                });
                exerciseMenuButton.getItems().add(exerciseMenuItem);
            }
        }
    }

    private void updateExerciseDescription(String exerciseTitle) {
        ExerciseModel exerciseModel = null;
        try {
            exerciseModel = new ExerciseModel();
            List<Pair<String, String>> exercisesAndDescriptions = exerciseModel.getExerciseDescription();

            for (Pair<String, String> pair : exercisesAndDescriptions) {
                if (pair.getKey().equals(exerciseTitle)) {
                    exerciseDescriptionLabel.setText(pair.getValue());
                    break;
                }
            }
        } catch (SQLException | ClassNotFoundException | IOException | InterruptedException e) {
            e.printStackTrace();
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

    private static StyleSpans<Collection<String>> computeHighlighting(String text) {
        Matcher matcher = PATTERN.matcher(text);
        int lastKwEnd = 0;
        StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<>();
        while (matcher.find()) {
            String styleClass =
                    matcher.group("KEYWORD") != null ? "keyword" :
                            matcher.group("PAREN") != null ? "paren" :
                                    matcher.group("BRACE") != null ? "brace" :
                                            matcher.group("BRACKET") != null ? "bracket" :
                                                    matcher.group("SEMICOLON") != null ? "semicolon" :
                                                            matcher.group("STRING") != null ? "string" :
                                                                    matcher.group("COMMENT") != null ? "comment" :
                                                                            null;
            assert styleClass != null;
            spansBuilder.add(Collections.emptyList(), matcher.start() - lastKwEnd);
            spansBuilder.add(Collections.singleton(styleClass), matcher.end() - matcher.start());
            lastKwEnd = matcher.end();
        }
        spansBuilder.add(Collections.emptyList(), text.length() - lastKwEnd);
        return spansBuilder.create();
    }
}
