package Compiler;

import java.io.*;
import java.util.Scanner;

public class PythonFile {
    private final File tempFile;
    private String response;

    public PythonFile() throws IOException {
        this.tempFile = File.createTempFile("temp", ".py", new File("C:\\Users\\Fayçal\\Desktop\\JavaProject\\CodYnGames\\tempFile"));
    }

    public void askResponse(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Write your answer below :");
        StringBuilder str = new StringBuilder();
        String line;
        while(!(line = scanner.nextLine()).isEmpty()){
            str.append(line).append("\n");
        }
        response = str.toString();
        System.out.println("Python Input: " + response);
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

    public void deleteTempFile(){
        this.tempFile.delete();
    }

    public String executePython(String code) throws IOException, InterruptedException {

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


    }

    public String getResponse(){
        return response;
    }

    public File getTempFile(){
        return this.tempFile;
    }

    public String getPathFile(){
        return tempFile.getAbsolutePath();
    }
}




    /*public String executePython(String code) throws IOException, InterruptedException {
        askResponse();
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

