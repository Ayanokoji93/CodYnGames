package Compiler;

import Compiler.factor.GeneralCompiler;

import java.io.*;

public class JavaScriptFile extends GeneralCompiler {
    private String fileName;

    public JavaScriptFile() throws IOException {
        super(".js");
    }

    @Override
    public String execute(String code) throws IOException, InterruptedException {

        writeResponseInFile(code);

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

        BufferedReader errorReader = new BufferedReader(new InputStreamReader(execProcess.getErrorStream()));
        StringBuilder errorOut = new StringBuilder();
        while ((line = errorReader.readLine()) != null) {
            errorOut.append(line).append("\n");
        }
        errorReader.close();

        deleteTempFile();

        return inputOut.toString() + errorOut.toString();
    }
    public String getPathFileJs() {
        return tempFile.getAbsolutePath();
    }

}
