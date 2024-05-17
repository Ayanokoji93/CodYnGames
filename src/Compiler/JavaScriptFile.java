package Compiler;

import java.io.*;

public class JavaScriptFile {
    private final File tempFile;
    private String response;

    public JavaScriptFile() throws IOException {
        this.tempFile = File.createTempFile("temp", ".js", new File("C:\\Users\\Fayçal\\Desktop\\JavaProject\\CodYnGames\\tempFile"));
    }

    public void askResponse() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Write your JavaScript code below (end input with an empty line):");
        StringBuilder str = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null && !line.isEmpty()) {
            str.append(line).append("\n");
        }
        response = str.toString();
    }

    public void writeResponseInFile(String response) throws IOException {
        if (response != null) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.tempFile))) {
                writer.write(response);
            }
        } else {
            System.out.println("Response is null, skipping writing to file.");
        }
    }

    public void deleteTempFile() {
        if (this.tempFile.exists()) {
            this.tempFile.delete();
        }
    }

    public String executeJavaScript(String code) throws IOException, InterruptedException {
        writeResponseInFile(code);


        String nodeExecutable = "C:\\Program Files\\nodejs\\node.exe";
        String command = nodeExecutable + " " + getPathFileJs();


        Process execProcess = Runtime.getRuntime().exec(command);
        execProcess.waitFor();

        BufferedReader inputReader = new BufferedReader(new InputStreamReader(execProcess.getInputStream()));
        StringBuilder inputOut = new StringBuilder();
        String line;
        while ((line = inputReader.readLine()) != null) {
            inputOut.append(line).append("\n");
        }
        inputReader.close();

        BufferedReader errorReader = new BufferedReader(new InputStreamReader(execProcess.getErrorStream()));
        StringBuilder errorOut = new StringBuilder();
        while ((line = errorReader.readLine()) != null) {
            errorOut.append(line).append("\n");
        }
        errorReader.close();

        deleteTempFile();

        return inputOut.toString() + errorOut.toString();
    }


    public String getPathFileJs() {
        return tempFile.getAbsolutePath();
    }

}
