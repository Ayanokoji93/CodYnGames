package Compiler;

import Compiler.factor.GeneralCompiler;

import java.io.*;

public class PhpFile extends GeneralCompiler {
    private String fileName;

    public PhpFile() throws IOException {
        super(".php");
    }

    @Override
    public String execute(String code) throws IOException, InterruptedException {

        writeResponseInFile(code);

        String phpExecutable = "C:\\PHP\\php.exe ";
        String command = phpExecutable + getPathFile();
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
        while((line = errorReader.readLine()) != null){
            errorOut.append(line).append("\n");
        }
        errorReader.close();

        deleteTempFile();

        return inputOut.toString() + errorOut.toString();
    }

    @Override
    public String exercisesWNumber(String number1, String number2) throws IOException, InterruptedException {
        writeResponseInFile(response);

        String phpExecutable = "C:\\PHP\\php.exe";
        ProcessBuilder pb = new ProcessBuilder(phpExecutable, getPathFile());
        pb.redirectErrorStream(true);
        Process p = pb.start();

        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()))) {
            writer.write(number1);
            writer.newLine();
            writer.write(number2);
            writer.newLine();
            writer.flush();
        }

        StringBuilder output = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
        }

        p.waitFor();
        deleteTempFile();

        return output.toString().trim();
    }
}