package Compiler;

import Compiler.factor.GeneralCompiler;

import java.io.*;
import java.util.List;
import java.util.Scanner;

public class PythonFile extends GeneralCompiler {

    private String fileName;

    public PythonFile() throws IOException {
        super(".py");
    }

    @Override
    public String execute(String code, List<Integer> numbers) throws IOException, InterruptedException {
        writeResponseInFile(code);


        ProcessBuilder pb = new ProcessBuilder("python", getPathFile());
        pb.redirectErrorStream(true);
        Process execProcess = pb.start();


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


        String userResult = output.toString().trim();

        return userResult;
    }
}

   


   

