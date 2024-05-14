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

    public void writeResponseInFile() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.tempFile))) {
            writer.write(response);
        }
    }

    public void deleteTempFile() {
        if (this.tempFile.exists()) {
            this.tempFile.delete();
        }
    }

    public void executeJavaScript() throws IOException, InterruptedException {
        askResponse();
        writeResponseInFile();
        String nodeExecutable = "C:\\Program Files\\nodejs\\node.exe ";
        String command = nodeExecutable + getPathFileJs();
        Process execProcess = Runtime.getRuntime().exec(command);
        execProcess.waitFor();
        BufferedReader inputReader = new BufferedReader(new InputStreamReader(execProcess.getInputStream()));
        StringBuilder inputOut = new StringBuilder();
        String line;
        while ((line = inputReader.readLine()) != null) {
            inputOut.append(line).append("\n");
        }
        inputReader.close();
        System.out.println(inputOut.toString());
        deleteTempFile();
    }

    public String getPathFileJs() {
        return tempFile.getAbsolutePath();
    }

}
