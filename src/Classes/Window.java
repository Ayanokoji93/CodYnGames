package Classes;

import Compiler.*;

import javafx.geometry.Pos;

import javafx.scene.control.*;
import javafx.scene.layout.*;

import javafx.util.Pair;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;

import java.io.IOException;
import java.sql.SQLException;

/**
 * The Window class is the main window of the application.
 */
public class Window extends VBox {

    // Labels for exercise description, result and message
    private final Label exerciseDescriptionLabel;
    private final Map<String, String> languageTextMap;
    private final Label resultLabel;
    private final Label completionMessageLabel;

    private boolean exercise5Completed = false;

    // Array of keywords for syntax highlighting
    private static final String[] KEYWORDS = new String[]{
            "abstract", "boolean", "byte", "catch", "char", "class", "const", "debugger", "declare", "delete", "double", "echo", "elif",
            "export", "extends", "False", "final", "finally", "printf", "console.log", "float", "foreach", "function", "global", "if", "implements", "import", "include",
            "insteadof", "interface", "is", "isset", "lambda", "let", "list", "long", "native", "new", "None", "nonlocal", "not", "or", "package",
            "pass", "print", "private", "protected", "public", "raise", "register", "require", "require_once", "restrict", "return", "short",
            "signed", "sizeof", "static", "strictfp", "struct", "super", "switch",
            "this", "throw", "throws", "True", "try", "typedef", "typeof", "union", "unset",
            "unsigned", "use", "var", "void", "while", "with", "System.out.println", "int" , "def", "php"
    };

    // Regular expression pattern for syntax highlighting
    private static final Pattern PATTERN = Pattern.compile(
            "(?<KEYWORD>" + String.join("|", KEYWORDS) + ")"
                    + "|(?<PAREN>\\(|\\))"
                    + "|(?<BRACE>\\{|\\})"
                    + "|(?<BRACKET>\\[|\\])"
                    + "|(?<SEMICOLON>;)"
                    + "|(?<STRING>\"([^\"]|\"\")*\")"
                    + "|(?<COMMENT>//[^\n]*|/\\*(.|\\R)*?\\*/)"
    );

    /**
     * This method is used for the Stdin/Stdout mode.
     *
     * @param code the code that is type by the user.
     * @param generateScriptPath the script path to the python file who generates random numbers.
     * @param correctionScriptPath the script path to the python file who makes the exercise.
     * @param selectedLanguage the selected language.
     * @param resultLabel the label for writing text in the window.
     * @throws IOException if an I/O error occurs.
     * @throws InterruptedException if the process is interrupted.
     */
    private void executeExerciseStdinStdout(String code, String generateScriptPath, String correctionScriptPath, String selectedLanguage, Label resultLabel) throws IOException, InterruptedException {
        exerciseDescriptionLabel.setText("You can code freely by not choosing any exercise.");
        // Create executors for generating random numbers and for correction
        StdinStdout generateExecutor = new StdinStdout(generateScriptPath);
        StdinStdout correctionExecutor = new StdinStdout(correctionScriptPath);
        List<Integer> numbers = generateExecutor.executeScript();

        String result = "";
        String correctionResult = correctionExecutor.executeScriptWithArgs(numbers);
        switch (selectedLanguage) {
            // Switch based on selected language to execute the code
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
                resultLabel.setText("Language unknown.");
                return;
        }

        // Display the final result message
        String finalResult = "Result of the user : " + result + "\n"
                + "Result of the correction : " + correctionResult + "\n";
        if (result.trim().equalsIgnoreCase(correctionResult.trim())) {
            finalResult += ("Correct result.");
        } else {
            finalResult += ("Wrong result.");
        }
        resultLabel.setText(finalResult);
    }

    /**
     * This method execute a file in a selected language if an exercise isn't choose.
     *
     * @param code the code that is typed by the user.
     * @param selectedLanguage the selected language.
     * @throws IOException if an I/O errors occurs.
     * @throws InterruptedException if the process is interrupted.
     */
    private void defaultExecute(String code, String selectedLanguage) throws IOException, InterruptedException {
        String result = "";
        List<Integer> numbers = new ArrayList<>();
        // Switch based on selected language to execute the code
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
                JavaScriptFile javaScriptFile = new JavaScriptFile();
                result =  javaScriptFile.execute(code, numbers);
                break;
            case "Java":
                JavaFile javaFile = new JavaFile();
                result = javaFile.execute(code, numbers);
                break;
            default:
                System.out.println("Please, select a language.");
                break;
        }
        // Display the result with the result label
        resultLabel.setText(result);
    }

    // HashMap used for the extension of the "include" exercise (here, this is exercise 5)
    private static final Map<String, String> LANGUAGE_EXTENSIONS = new HashMap<>();

    static {
        LANGUAGE_EXTENSIONS.put("C", "c");
        LANGUAGE_EXTENSIONS.put("Python", "py");
        LANGUAGE_EXTENSIONS.put("Java", "java");
        LANGUAGE_EXTENSIONS.put("PHP", "php");
        LANGUAGE_EXTENSIONS.put("JavaScript", "js");
    }

    private final Map<String, Integer> exerciseCompletionCounts = new HashMap<>();

    {
        exerciseCompletionCounts.put("Calculer nombre de chiffre d'un entier", 0);
    }

    /**
     * This method is used for the include mode.
     *
     * @param code the code that is type by the user.
     * @param generateScriptPath the script path to the python file who generates random numbers.
     * @param targetFilePath the main script, which contains the other part of the code.
     * @param correctionPathFile the script path to the python file who makes the exercise.
     * @param selectedLanguage the selected language.
     * @param resultLabel the label for writing text in the window.
     * @throws IOException if an I/O error occurs.
     */
    private void executeExerciseInclude(String code, String generateScriptPath, String targetFilePath, String correctionPathFile, String selectedLanguage, String selectedExercise, Label resultLabel) throws IOException {
        try {
            // Create executors for generating random numbers and for correction
            StdinStdout generateExecutor = new StdinStdout(generateScriptPath);
            StdinStdout correctionExecutor = new StdinStdout(correctionPathFile);
            // Generate random numbers
            List<Integer> numbers = generateExecutor.executeScript();
            // Get the expected result from the correction script
            String correctionResult = correctionExecutor.executeScriptWithArgs(numbers);
            String result = "";

            // Determine file extension based on selected language
            String fileExtension = LANGUAGE_EXTENSIONS.get(selectedLanguage);
            if (fileExtension == null) {
                resultLabel.setText("Unsupported language");
                return;
            }

            // Adjust target file path with proper extension
            targetFilePath = targetFilePath.replace("mainExo5", "mainExo5." + fileExtension);

            // Switch case based on selected language
            switch (selectedLanguage) {
                case "C":
                    CFile cFile = new CFile();
                    cFile.writeResponseInFile(code.trim() + "\n");
                    cFile.addContentToFile(targetFilePath);

                    Include cExecutor = new Include(cFile.getPathFile());
                    result = cExecutor.executeScriptWithArgs("gcc", numbers);

                    cFile.deleteTempFile();
                    break;
                case "Python":
                    PythonFile pythonFile = new PythonFile();
                    pythonFile.writeResponseInFile(code.trim() + "\n");
                    pythonFile.addContentToFile(targetFilePath);

                    Include pythonExecutor = new Include(pythonFile.getPathFile());
                    result = pythonExecutor.executeScriptWithArgs("python", numbers);

                    pythonFile.deleteTempFile();
                    break;
                case "PHP":
                    PhpFile phpFile = new PhpFile();
                    phpFile.writeResponseInFile(code.trim() + "\n");
                    phpFile.addContentToFile(targetFilePath);

                    Include phpExecutor = new Include(phpFile.getPathFile());
                    result = phpExecutor.executeScriptWithArgs("php", numbers);
                    phpFile.deleteTempFile();
                    break;
                case "JavaScript":
                    JavaScriptFile javascriptFile = new JavaScriptFile();
                    javascriptFile.writeResponseInFile(code.trim() + "\n");
                    javascriptFile.addContentToFile(targetFilePath);

                    Include jsExecutor = new Include(javascriptFile.getPathFile());
                    result = jsExecutor.executeScriptWithArgs("node", numbers);

                    javascriptFile.deleteTempFile();
                    break;
                case "Java":
                    JavaFile javaFile = new JavaFile();
                    javaFile.writeResponseInFile(code.trim() + "\n");
                    javaFile.addContentToFile(targetFilePath);

                    Include javaExecutor = new Include(javaFile.getPathFile());
                    result = javaExecutor.executeScriptWithArgs("java", numbers);

                    javaFile.deleteTempFile();
                    break;
                default:
                    resultLabel.setText("Unknown language");
                    return;
            }

            // If the user's result matches the correction result, update completion count and display a success message
            if (result.trim().equalsIgnoreCase(correctionResult.trim())) {
                int count = exerciseCompletionCounts.getOrDefault(selectedExercise, 0);
                count++;
                exerciseCompletionCounts.put(selectedExercise, count);
                if (count > 1) {
                    completionMessageLabel.setText("You have successfully completed this exercise " + count + " times !");
                } else {
                    completionMessageLabel.setText("You have successfully completed this exercise !");
                }
            } else {
                exercise5Completed = false;
                completionMessageLabel.setText("");
            }

            // Display the final result message
            String finalResult = "Result of the user : " + result + "\n"
                    + "Result of the correction : " + correctionResult + "\n";
            if (result.trim().equalsIgnoreCase(correctionResult.trim())) {
                finalResult += ("Correct result.");
            } else {
                finalResult += ("Wrong result.");
            }
            resultLabel.setText(finalResult);
        } catch (IOException e) {
            resultLabel.setText("Error executing code : " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Constructs a Window object with the specified spacing.
     *
     * @param spacing the spacing between elements in the window
     */
    public Window(double spacing) {
        super(spacing);
        this.setAlignment(Pos.CENTER);

        HBox buttonsBox = new HBox();
        buttonsBox.getStyleClass().add("buttons-box");
        buttonsBox.setSpacing(10);

        VBox labelBox = new VBox();
        labelBox.getStyleClass().add("label-box");
        labelBox.setSpacing(10);

        // Initialize labels and code input area
        completionMessageLabel = new Label();
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

        // Initialize language and exercise selection menus
        MenuButton languageMenuButton = new MenuButton("Choisir un langage");
        MenuButton exerciseMenuButton = new MenuButton("Choisir un exercice");

        // Initialize language options
        languageTextMap = new HashMap<>();
        languageTextMap.put("Java", "public class Main {\n\tpublic static void main(String[] args) {\n\t\tSystem.out.println(\"Hello, you chose Java!\");\n\t}\n}");
        languageTextMap.put("Python", "print(\"Hello, you choose Python\")");
        languageTextMap.put("JavaScript", "console.log(\"Hello, you chose JavaScript!\");");
        languageTextMap.put("C", "#include <stdio.h>\n\nint main() {\n\tprintf(\"Hello, you chose C!\");\n\treturn 0;\n}");
        languageTextMap.put("PHP", "<?php\n\techo \"Hello, you chose PHP!\";\n?>");

        ExerciseModel exerciseModel = null;
        try {
            exerciseModel = new ExerciseModel();
            List<Pair<String, String>> exercisesAndLanguages = exerciseModel.getLanguagesForExercise();

            // Add language options to the language selection menu
            for (String lang : languageTextMap.keySet()) {
                MenuItem languageMenuItem = new MenuItem(lang);
                languageMenuItem.setOnAction(event -> {
                    languageMenuButton.setText(lang);
                    codeInput.replaceText(languageTextMap.get(lang));
                    resultLabel.setText("");
                    // Update exercise menu based on selected language
                    updateExerciseMenu(exerciseMenuButton, lang, exercisesAndLanguages, codeInput, resultLabel, completionMessageLabel);
                    exercise5Completed = false;
                    completionMessageLabel.setText("");

                });
                languageMenuButton.getItems().add(languageMenuItem);
            }

            // Arrange the elements for display
            buttonsBox.getChildren().addAll(languageMenuButton, exerciseMenuButton);
            labelBox.getChildren().add(exerciseDescriptionLabel);
            labelBox.getChildren().add(completionMessageLabel);

            VBox textAreaContainer = new VBox(codeInput);
            textAreaContainer.getStyleClass().add("text-area-container");
            textAreaContainer.setAlignment(Pos.CENTER);
            VBox.setVgrow(textAreaContainer, Priority.ALWAYS);

            // Create submission box
            Button submitButton = new Button("Soumettre");
            submitButton.getStyleClass().add("button");
            submitButton.setOnAction(event -> {
                String code = codeInput.getText();
                String selectedLanguage = languageMenuButton.getText();
                String selectedExercise = exerciseMenuButton.getText();

                try {
                    // Check the selected language
                    switch (selectedLanguage) {
                        // If the selected language is C, Python, Java, PHP, or JavaScript
                        case "C", "Python", "Java", "PHP", "JavaScript":
                            // Check the selected exercise
                            switch (selectedExercise) {
                                // For each exercise, execute the corresponding method
                                case "Produit de nombres entiers":
                                    executeExerciseStdinStdout(code, "C:\\Users\\FiercePC\\Desktop\\JavaProject\\CodYnGames\\correctionFile\\generateNumberExo1.py",
                                            "C:\\Users\\FiercePC\\Desktop\\JavaProject\\CodYnGames\\correctionFile\\correctionExo1.py",
                                            selectedLanguage, resultLabel);
                                    break;
                                case "Calcul d'une factorielle":
                                    executeExerciseStdinStdout(code, "C:\\Users\\FiercePC\\Desktop\\JavaProject\\CodYnGames\\correctionFile\\generateNumberExo2.py",
                                            "C:\\Users\\FiercePC\\Desktop\\JavaProject\\CodYnGames\\correctionFile\\correctionExo2.py",
                                            selectedLanguage, resultLabel);
                                    break;
                                case "Calcul d'une moyenne":
                                    executeExerciseStdinStdout(code, "C:\\Users\\FiercePC\\Desktop\\JavaProject\\CodYnGames\\correctionFile\\generateNumberExo3.py",
                                            "C:\\Users\\FiercePC\\Desktop\\JavaProject\\CodYnGames\\correctionFile\\correctionExo3.py",
                                            selectedLanguage, resultLabel);
                                    break;
                                case "Inverse d'un nombre":
                                    executeExerciseStdinStdout(code, "C:\\Users\\FiercePC\\Desktop\\JavaProject\\CodYnGames\\correctionFile\\generateNumberExo4.py",
                                            "C:\\Users\\FiercePC\\Desktop\\JavaProject\\CodYnGames\\correctionFile\\correctionExo4.py",
                                            selectedLanguage, resultLabel);
                                    break;
                                case "Calculer nombre de chiffre d'un entier":
                                    executeExerciseInclude(code, "C:\\Users\\FiercePC\\Desktop\\JavaProject\\CodYnGames\\correctionFile\\generateNumberExo4.py",
                                            "C:\\Users\\FiercePC\\Desktop\\JavaProject\\CodYnGames\\mainInclude\\mainExo5",
                                            "C:\\Users\\FiercePC\\Desktop\\JavaProject\\CodYnGames\\correctionFile\\correctionExo5.py",
                                            selectedLanguage, selectedExercise, resultLabel);
                                    break;
                                default:
                                    defaultExecute(code, selectedLanguage);
                                    break;
                            }
                            break;
                        default:
                            resultLabel.setText("Please, select a language.");
                    }
                } catch (IOException | InterruptedException e) {
                    resultLabel.setText("Error executing the code : " + e.getMessage());
                    e.printStackTrace();
                }
            });

            VBox submissionBox = new VBox(submitButton, resultLabel);
            submissionBox.setAlignment(Pos.CENTER);
            submissionBox.setSpacing(40);

            // Add elements to the layout
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

    /**
     Updates the exercise menu with exercises available in the selected language.
     @param exerciseMenuButton The button to display the exercises.
     @param selectedLanguage The language chosen by the user.
     @param exercisesAndLanguages A list of exercise titles and their associated languages.
     @param codeInput The area where users input their code.
     @param resultLabel The label to show exercise results.
     @param completionMessageLabel The label to display completion messages.
     */
    private void updateExerciseMenu(MenuButton exerciseMenuButton, String selectedLanguage, List<Pair<String, String>> exercisesAndLanguages, CodeArea codeInput, Label resultLabel, Label completionMessageLabel) {
        exerciseMenuButton.getItems().clear();
        exerciseMenuButton.setText("Choisir un exercice");
        String selectedExercise = exerciseMenuButton.getText();
        exerciseCompletionCounts.put(selectedExercise, 0);

        // Iterate the list of exercises and languages
        for (Pair<String, String> pair : exercisesAndLanguages) {
            String exerciseTitle = pair.getKey();
            String languages = pair.getValue();
            // If the selected language is available for the exercise, add it to the menu
            if (languages.contains(selectedLanguage)) {
                MenuItem exerciseMenuItem = new MenuItem(exerciseTitle);
                exerciseMenuItem.setOnAction(event -> {
                    exerciseMenuButton.setText(exerciseTitle);
                    updateExerciseDescription(exerciseTitle);
                    codeInput.clear();
                    resultLabel.setText("");
                    exercise5Completed = false;
                    completionMessageLabel.setText("");
                });
                exerciseMenuButton.getItems().add(exerciseMenuItem);
            }
        }
    }

    /**
     Updates the exercise description based on the chosen exercise.
     @param exerciseTitle The title of the exercise.
     */
    private void updateExerciseDescription(String exerciseTitle) {
        ExerciseModel exerciseModel = null;
        try {
            exerciseModel = new ExerciseModel();
            List<Pair<String, String>> exercisesAndDescriptions = exerciseModel.getExerciseDescription();

            // Find the description for the selected exercise and update the label
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

    /**
     Highlights syntax in the provided text using predefined patterns.
     @param text The text that will be highlighted.
     @return Styled text with highlighted syntax.
     */
    private static StyleSpans<Collection<String>> computeHighlighting(String text) {
        Matcher matcher = PATTERN.matcher(text);
        int lastKwEnd = 0;
        StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<>();
        // Find patterns in the text and apply appropriate styles.
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
