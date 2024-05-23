package Compiler;

import java.io.*;
import java.util.List;
import java.util.Scanner;

import Compiler.factor.GeneralCompiler;

import Compiler.factor.GeneralCompiler;

public class JavaFile extends GeneralCompiler {
    private String fileName;

    public JavaFile() throws IOException {
        super(".java");
    }

    public void renameFile(){
        File newFileName = new File(tempFile.getParent(), fileName + ".java");
        tempFile.renameTo(newFileName);
        this.tempFile = newFileName;
    }

    @Override
    public String execute(String code, List<Integer> numbers) throws IOException, InterruptedException {
        renameFile();
        writeResponseInFile(code);


        Process compilerProcess = Runtime.getRuntime().exec("javac " + getPathFile());


        Process execProcess = Runtime.getRuntime().exec("java " + getPathFile());


        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(execProcess.getOutputStream()));
        for (Integer number : numbers) {
            writer.write(number.toString());
            writer.newLine();
        }
        writer.close();


        StringBuilder output = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(execProcess.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append(System.lineSeparator());
            }
        }


        execProcess.waitFor();


        deleteTempFile();

        return output.toString();
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
