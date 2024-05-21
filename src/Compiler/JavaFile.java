package Compiler;

import java.io.*;
import java.util.Scanner;

import Compiler.factor.GeneralCompiler;

import Compiler.factor.GeneralCompiler;

public class JavaFile extends GeneralCompiler {
    private String fileName;

    public JavaFile() throws IOException {
        this.tempFile = File.createTempFile("temp", ".java", new File("C:\\Users\\Fayçal\\Desktop\\JavaProject\\CodYnGames\\tempFile"));
    }

    public void renameFile(){
        File newFileName = new File(tempFile.getParent(), fileName + ".java");
        tempFile.renameTo(newFileName);
        this.tempFile = newFileName;
    }

    @Override
    public String execute(String code) throws IOException, InterruptedException {
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

    @Override
    public String exercisesWNumber(String number1, String number2) throws IOException, InterruptedException {
        writeResponseInFile(response);

        ProcessBuilder pb = new ProcessBuilder("javac " , getPathFile());
        pb.redirectErrorStream(true);
        Process compilerProcess = pb.start();
        int compileResult = compilerProcess.waitFor();
        if (compileResult != 0) {
            deleteTempFile();
            return "Compilation Error:\n";
        }

        String className = getPathFile().replace(".java", "");
        pb = new ProcessBuilder("java", className);
        pb.redirectErrorStream(true);
        Process execProcess = pb.start();

        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(execProcess.getOutputStream()))) {
            writer.write(number1);
            writer.newLine();
            writer.write(number2);
            writer.newLine();
            writer.flush();
        }

        StringBuilder output = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(execProcess.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
        }

        deleteTempFile();

        return output.toString().trim();
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
}
