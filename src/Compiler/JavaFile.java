package Compiler;

import java.io.*;
import java.util.Scanner;

public class JavaFile {
    private final File tempFile;
    private String response;

    public JavaFile() throws IOException {
        this.tempFile = File.createTempFile("temp", ".java", new File("C:\\Users\\Fayçal\\Desktop\\JavaProject\\CodYnGames\\tempFile"));
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
    }

    public void writeResponseInFile(String response) throws IOException{
        BufferedWriter writer = new BufferedWriter(new FileWriter(this.tempFile));
        writer.write(response);
        writer.close();
    }

    public String getResponseInFile() throws IOException {
        Process process = Runtime.getRuntime().exec("javac " + getPathFile());
        BufferedReader inputReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuilder inputOut = new StringBuilder();
        String line;
        while((line = inputReader.readLine()) != null){
            inputOut.append(line).append("\n");
        }
        inputReader.close();

        BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        StringBuilder errorOut = new StringBuilder();
        String line2;
        while((line2 = errorReader.readLine()) != null){
            errorOut.append(line2).append("\n");
        }
        errorReader.close();

        return inputOut.toString() + errorOut.toString();
    }

    public void deleteTempFile(){
        this.tempFile.delete();
    }

    public void executeJava() throws IOException, InterruptedException {
        askResponse();
        writeResponseInFile(getResponse());
        System.out.println(getResponseInFile());
        deleteTempFile();
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
