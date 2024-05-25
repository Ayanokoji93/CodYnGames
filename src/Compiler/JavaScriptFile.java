package Compiler;

import Compiler.factor.GeneralCompiler;

import java.io.*;
import java.util.List;

public class JavaScriptFile extends GeneralCompiler {
    private String fileName;

    public JavaScriptFile() throws IOException {
        super(".js");
    }

    @Override
    public String execute(String code, List<Integer> numbers) throws IOException, InterruptedException {
        writeResponseInFile(code);


        String nodeExecutable = "C:\\Program Files\\nodejs\\node.exe";


        String command = nodeExecutable + " " + getPathFileJs();


        Process execProcess = Runtime.getRuntime().exec(command);


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
    public String getPathFileJs() {
        return tempFile.getAbsolutePath();
    }

}
