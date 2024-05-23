package Compiler;

import Compiler.factor.GeneralCompiler;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PhpFile extends GeneralCompiler {
    private String fileName;

    public PhpFile() throws IOException {
        super(".php");
    }

    @Override
    public String execute(String code, List<Integer> numbers) throws IOException, InterruptedException {
        writeResponseInFile(code);

        // Chemin vers l'exécutable PHP
        String phpExecutable = "C:\\PHP\\php.exe";

        // Construction de la commande d'exécution
        List<String> command = new ArrayList<>();
        command.add(phpExecutable);
        command.add(getPathFile());

        // Exécution de la commande
        ProcessBuilder pb = new ProcessBuilder(command);
        Process execProcess = pb.start();

        // Écrire les nombres dans l'entrée standard du processus
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(execProcess.getOutputStream()));
        for (Integer number : numbers) {
            writer.write(number.toString());
            writer.newLine();
        }
        writer.close();

        // Attendre que le processus se termine et récupérer la sortie
        StringBuilder output = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(execProcess.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append(System.lineSeparator());
            }
        }

        // Attendre que le processus se termine
        execProcess.waitFor();

        // Supprimer le fichier temporaire
        deleteTempFile();

        return output.toString();
    }

}