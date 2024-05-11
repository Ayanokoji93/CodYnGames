package Classes;

import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.util.Pair;

import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList; // Import de List
import java.util.HashMap;
import java.util.Map;
import javafx.scene.Node; // Import de Node pour ObservableList<Node>


public class MainView2 extends VBox {
    public MainView2(double spacing) {
        super(spacing);

        ObservableList<Node> components = this.getChildren();

        TextArea codeInput = new TextArea();
        codeInput.setWrapText(true);

        MenuButton exerciseMenuButton = new MenuButton("Choisir un exercice");
        MenuButton languageMenuButton = new MenuButton("Choisir un langage");

        ExerciseModel exerciseModel = null;
        try {
            exerciseModel = new ExerciseModel();
            List<Pair<String, String>> exercisesAndLanguages = exerciseModel.getLanguagesForExercise();

            // Création du menu d'exercices
            for (Pair<String, String> pair : exercisesAndLanguages) {
                String exerciseTitle = pair.getKey();
                String language = pair.getValue();

                MenuItem exerciseMenuItem = new MenuItem(exerciseTitle);
                exerciseMenuItem.setOnAction(event -> {
                    exerciseMenuButton.setText(exerciseTitle);
                    updateLanguageMenu(languageMenuButton, exerciseTitle, exercisesAndLanguages);
                });
                exerciseMenuButton.getItems().add(exerciseMenuItem);
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

        Button submitButton = new Button("Soumettre");
        submitButton.setOnAction(event -> {
            String code = codeInput.getText();
            System.out.println("Code soumis : " + code);
        });

        components.addAll(exerciseMenuButton, languageMenuButton, codeInput, submitButton);
    }

    private void updateLanguageMenu(MenuButton languageMenuButton, String selectedExercise, List<Pair<String, String>> exercisesAndLanguages) {
        languageMenuButton.getItems().clear(); // Efface les éléments actuels du menu

        // Recherche des langages associés à l'exercice sélectionné
        for (Pair<String, String> pair : exercisesAndLanguages) {
            String exerciseTitle = pair.getKey();
            String language = pair.getValue();
            if (exerciseTitle.equals(selectedExercise)) {
                // Divisez la chaîne de langages en une liste de langages
                String[] languages = language.split(",");

                // Ajoutez chaque langage comme élément de menu
                for (String lang : languages) {
                    MenuItem languageMenuItem = new MenuItem(lang);
                    languageMenuButton.getItems().add(languageMenuItem);
                }
                // Sortez de la boucle dès que vous avez trouvé les langages pour l'exercice sélectionné
                break;
            }
        }
    }

}
