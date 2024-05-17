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
        if (response != null) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.tempFile))) {
                writer.write(response);
            }
        } else {
            System.out.println("Response is null, skipping writing to file.");
        }
    }

    public void deleteTempFile() {
        this.tempFile.delete();
        String execFilePath = "C:\\Users\\Fayçal\\Desktop\\JavaProject\\CodYnGames\\tempFile\\tempExecutable";
        File execFile = new File(execFilePath);
        execFile.delete();
    }

    public String executeC(String code) throws IOException, InterruptedException {

        writeResponseInFile(code);

        String compileCommand = "C:\\msys64\\mingw64\\bin\\gcc -o C:\\Users\\Fayçal\\Desktop\\JavaProject\\CodYnGames\\tempFile\\tempExecutable " + getPathFileC();
        Process compileProcess = Runtime.getRuntime().exec(compileCommand);

        BufferedReader compileErrorReader = new BufferedReader(new InputStreamReader(compileProcess.getErrorStream()));
        StringBuilder compileErrorOut = new StringBuilder();
        String line;
        while ((line = compileErrorReader.readLine()) != null) {
            compileErrorOut.append(line).append("\n");
        }
        compileErrorReader.close();

        int compileResult = compileProcess.waitFor();
        if (compileResult != 0) {
            return "Compilation Error:\n" + compileErrorOut.toString();
        }

        Process execProcess = Runtime.getRuntime().exec("C:\\Users\\Fayçal\\Desktop\\JavaProject\\CodYnGames\\tempFile\\tempExecutable");


        BufferedReader inputReader = new BufferedReader(new InputStreamReader(execProcess.getInputStream()));
        StringBuilder inputOut = new StringBuilder();
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

        int execResult = execProcess.waitFor();
        if (execResult != 0) {
            return "Runtime Error:\n" + errorOut.toString();
        }

        return inputOut.toString();

    }

    public String getResponse(){
        return this.response;
    }

    public String getPathFileC(){
        return tempFile.getAbsolutePath();
    }
}

/*public String getResponseFromFile() throws IOException {
        StringBuilder responseFromFile = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(this.tempFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                responseFromFile.append(line).append("\n");
            }
        }
        return responseFromFile.toString();
    }*/

