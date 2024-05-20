package Compiler;

import java.io.*;

public class PhpFile {
    private final File tempFile;
    private String response;

    public PhpFile() throws IOException {
        this.tempFile = File.createTempFile("temp", ".php", new File("C:\\Users\\Fayçal\\Desktop\\JavaProject\\CodYnGames\\tempFile"));
    }

    public void askResponse() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Write your PHP code below (end input with an empty line):");
        StringBuilder str = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null && !line.isEmpty()) {
            str.append(line).append("\n");
        }
        response = str.toString();
    }

    public void writeResponseInFile(String response) throws IOException {
        if (response == null || response.isEmpty()) {
            throw new IllegalArgumentException("Response cannot be null or empty");
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.tempFile))) {
            writer.write(response);
        }
    }

    public void deleteTempFile() {
        if (this.tempFile.exists()) {
            this.tempFile.delete();
        }
    }

    public String executePhp(String code) throws IOException, InterruptedException {

        writeResponseInFile(code);

        String phpExecutable = "C:\\PHP\\php.exe"; // Chemin de l'exécutable PHP
        String command = phpExecutable + " " + getPathFilePhp();
        Process execProcess = Runtime.getRuntime().exec(command);
        execProcess.waitFor();

        BufferedReader inputReader = new BufferedReader(new InputStreamReader(execProcess.getInputStream()));
        StringBuilder inputOut = new StringBuilder();
        String line;
        while ((line = inputReader.readLine()) != null) {
            inputOut.append(line).append("\n");
        }
        inputReader.close();


        deleteTempFile();


        return inputOut.toString();
    }

    public String getPathFilePhp() {
        return tempFile.getAbsolutePath();
    }

    public String getResponse(){ return this.response; }
}