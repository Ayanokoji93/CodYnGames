package Compiler;

import java.io.*;

public class CFile {
    private final File tempFile;
    private String response;

    public CFile() throws IOException {
        this.tempFile = File.createTempFile("temp", ".c", new File("C:\\Users\\Fayçal\\Desktop\\JavaProject\\CodYnGames\\tempFile"));
    }

    public void askResponse() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Write your answer below :");
        StringBuilder str = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null && !line.isEmpty()) {
            str.append(line).append("\n");
        }
        response = str.toString();
    }

    public void writeResponseInFile(String response) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.tempFile))) {
            writer.write(response);
        }
    }

    public String getResponseFromFile() throws IOException {
        StringBuilder responseFromFile = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(this.tempFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                responseFromFile.append(line).append("\n");
            }
        }
        return responseFromFile.toString();
    }

    public void deleteTempFile() {
        this.tempFile.delete();
    }

    public void executeC() throws IOException, InterruptedException {
        askResponse();
        writeResponseInFile(response);
        String command = "C:\\msys64\\mingw64\\bin\\gcc -o tempExecutable " + tempFile.getAbsolutePath();
        Process compileProcess = Runtime.getRuntime().exec(command);
        compileProcess.waitFor();
        System.out.println("Chemin du fichier exécutable : " + tempFile.getAbsolutePath());
        Process executionProcess = Runtime.getRuntime().exec("./tempExecutable");
        BufferedReader inputReader = new BufferedReader(new InputStreamReader(executionProcess.getInputStream()));
        StringBuilder inputOut = new StringBuilder();
        String line;
        while ((line = inputReader.readLine()) != null) {
            inputOut.append(line).append("\n");
        }
        inputReader.close();
        System.out.println(inputOut.toString());
        deleteTempFile();
    }


    public String getPathFileC(){
        return tempFile.getAbsolutePath();
    }
}



