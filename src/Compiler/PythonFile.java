package Compiler;

import Compiler.factor.GeneralCompiler;

import java.io.*;
import java.util.Scanner;

public class PythonFile extends GeneralCompiler {

    private String fileName;

    public PythonFile() throws IOException {
        super(".py");
    }

    @Override
    public String execute(String code, List<Integer> numbers) throws IOException, InterruptedException {
        writeResponseInFile(code);

        // Construction de la commande d'exécution
        ProcessBuilder pb = new ProcessBuilder("python", getPathFile());
        pb.redirectErrorStream(true); // Rediriger les erreurs vers la sortie standard
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

        // Récupérer la sortie standard du processus Python
        String userResult = output.toString().trim();

        return userResult;
    }
}

    @Override
    public String execute(String code) throws IOException, InterruptedException {

        writeResponseInFile(code);

        // First part displays normal input of the python code
        Process execProcess = Runtime.getRuntime().exec("python " + getPathFile());
        BufferedReader inputReader = new BufferedReader(new InputStreamReader(execProcess.getInputStream()));
        StringBuilder inputOut = new StringBuilder();
        String line;
        while ((line = inputReader.readLine()) != null) {
            inputOut.append(line).append("\n");
        }
        inputReader.close();

        // Second part displays errors of the python code
        BufferedReader errorReader = new BufferedReader(new InputStreamReader(execProcess.getErrorStream()));
        StringBuilder errorOut = new StringBuilder();
        while((line = errorReader.readLine()) != null){
            errorOut.append(line).append("\n");
        }
        errorReader.close();

        deleteTempFile();

        return inputOut.toString() + errorOut.toString();
    }
}*/





/*public String executePython(String code) throws IOException, InterruptedException {

    writeResponseInFile(code);

    // First part displays normal input of the python code
    Process execProcess = Runtime.getRuntime().exec("python " + getPathFile());
    BufferedReader inputReader = new BufferedReader(new InputStreamReader(execProcess.getInputStream()));
    StringBuilder inputOut = new StringBuilder();
    String line;
    while ((line = inputReader.readLine()) != null) {
        inputOut.append(line).append("\n");
    }
    inputReader.close();

    // Second part displays errors of the python code
    BufferedReader errorReader = new BufferedReader(new InputStreamReader(execProcess.getErrorStream()));
    StringBuilder errorOut = new StringBuilder();
    String line2;
    while((line2 = errorReader.readLine()) != null){
        errorOut.append(line2).append("\n");
    }
    errorReader.close();

    return inputOut.toString() + errorOut.toString();

    // deleteTempFile();


}*/
    /*public String executePython(String code) throws IOException, InterruptedException {

        writeResponseInFile(getResponse());
        System.out.println(getResponseInFile());
        deleteTempFile();
        return code;
    }*/

 /*public String getResponseInFile() throws IOException {
        // First part display the output of python if there is no error.
        Process process = Runtime.getRuntime().exec("python " + getPathFile());
        BufferedReader inputReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuilder inputOut = new StringBuilder();
        String line;
        while((line = inputReader.readLine()) != null){
            inputOut.append(line).append("\n");
        }
        inputReader.close();

        // Second part displays errors of the python code
        BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        StringBuilder errorOut = new StringBuilder();
        String line2;
        while((line2 = errorReader.readLine()) != null){
            errorOut.append(line2).append("\n");
        }
        errorReader.close();

        return inputOut.toString() + errorOut.toString();
    }*/

