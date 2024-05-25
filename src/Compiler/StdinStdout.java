package Compiler;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StdinStdout {
    private String scriptPath;

    public StdinStdout(String scriptPath) {
        this.scriptPath = scriptPath;
    }

    public List<Integer> executeScript() {
        List<Integer> numbers = new ArrayList<>();
        try {
            Process process = Runtime.getRuntime().exec("python " + scriptPath);
            BufferedReader numberReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String line;

            while ((line = numberReader.readLine()) != null) {
                String[] numberStrings = line.split(" ");
                for (String numStr : numberStrings) {
                    try {
                        numbers.add(Integer.parseInt(numStr));
                    } catch (NumberFormatException e) {
                        System.err.println("Erreur de format de nombre : " + numStr);
                    }
                }
            }

            while ((line = errorReader.readLine()) != null) {
                System.err.println("Erreur du processus : " + line);
            }

            process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return numbers;
    }

    public String executeScriptWithArgs(List<Integer> numbers) {
        StringBuilder output = new StringBuilder();
        try {
            // Construction de la commande d'exécution
            ProcessBuilder pb = new ProcessBuilder("python", scriptPath);
            Process execProcess = pb.start();

            // Écrire les nombres dans l'entrée standard du processus
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(execProcess.getOutputStream()));
            for (Integer number : numbers) {
                writer.write(number.toString());
                writer.newLine();
            }
            writer.close();

            // Attendre que le processus se termine et récupérer la sortie
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(execProcess.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line).append(System.lineSeparator());
                }
            }

            // Attendre que le processus se termine
            execProcess.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return output.toString();
    }
}
