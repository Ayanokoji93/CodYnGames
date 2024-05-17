package Compiler;

import java.io.*;
import java.util.Scanner;

public class JavaFile {
    private File tempFile;
    private String response;
    private String fileName;

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

        int indexBrace = response.indexOf("{");
        int indexSpace = response.lastIndexOf(" ", indexBrace);
        String word = response.substring(indexSpace + 1, indexBrace);
        fileName = word;
    }

    public boolean renameFile(){
        File newFileName = new File(tempFile.getParent() + "\\" + fileName + ".java");
        this.tempFile = newFileName;
        return tempFile.renameTo(newFileName);
    }

    public void writeResponseInFile(String response) throws IOException{
        if (response != null) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.tempFile))) {
                writer.write(response);
            }
        } else {
            System.out.println("Response is null, skipping writing to file.");
        }
    }

  /*  public String getResponseInFile() throws IOException {
        Process compilerProcess = Runtime.getRuntime().exec("javac " + getPathFile());

        Process execProcess = Runtime.getRuntime().exec("java " + getPathFile());
        BufferedReader inputReader = new BufferedReader(new InputStreamReader(execProcess.getInputStream()));
        StringBuilder inputOut = new StringBuilder();
        String line;
        while((line = inputReader.readLine()) != null){
            inputOut.append(line).append("\n");
        }
        inputReader.close();

        BufferedReader errorReader = new BufferedReader(new InputStreamReader(execProcess.getErrorStream()));
        StringBuilder errorOut = new StringBuilder();
        String line2;
        while((line2 = errorReader.readLine()) != null){
            errorOut.append(line2).append("\n");
        }
        errorReader.close();

        deleteTempFile();

        return inputOut.toString() + errorOut.toString();
    }*/

    public void deleteTempFile(){
        this.tempFile.delete();
    }

    public String executeJava(String code) throws IOException, InterruptedException {
        renameFile();
        writeResponseInFile(code);
        Process compilerProcess = Runtime.getRuntime().exec("javac " + getPathFile());

        Process execProcess = Runtime.getRuntime().exec("java " + getPathFile());
        BufferedReader inputReader = new BufferedReader(new InputStreamReader(execProcess.getInputStream()));
        StringBuilder inputOut = new StringBuilder();
        String line;
        while((line = inputReader.readLine()) != null){
            inputOut.append(line).append("\n");
        }
        inputReader.close();

        BufferedReader errorReader = new BufferedReader(new InputStreamReader(execProcess.getErrorStream()));
        StringBuilder errorOut = new StringBuilder();
        String line2;
        while((line2 = errorReader.readLine()) != null){
            errorOut.append(line2).append("\n");
        }
        errorReader.close();

        deleteTempFile();

        return inputOut.toString() + errorOut.toString();
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
