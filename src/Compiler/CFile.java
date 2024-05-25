package Compiler;

import Compiler.factor.GeneralCompiler;

import java.io.*;
import java.util.List;

public class CFile extends GeneralCompiler {

    public CFile() throws IOException {
        super(".c");
    }

    private void deleteTempExecFile() {
        String execFilePath = "C:\\Users\\Fayçal\\Desktop\\JavaProject\\CodYnGames\\tempFile\\tempExecutable.exe";
        File execFile = new File(execFilePath);
        if (execFile.exists()) {
            execFile.delete();
        }
    }

    @Override
    public String execute(String code, List<Integer> numbers) throws IOException, InterruptedException {
        writeResponseInFile(code);


        String compileCommand = "C:\\msys64\\mingw64\\bin\\gcc.exe -o C:\\Users\\Fayçal\\Desktop\\JavaProject\\CodYnGames\\tempFile\\tempExecutable " + getPathFile();
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
            deleteTempFile();
            return "Compilation Error:\n" + compileErrorOut.toString();
        }


        Process execProcess = Runtime.getRuntime().exec("C:\\Users\\Fayçal\\Desktop\\JavaProject\\CodYnGames\\tempFile\\tempExecutable");


        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(execProcess.getOutputStream()));
        for (Integer number : numbers) {
            writer.write(number.toString());
            writer.newLine();
        }
        writer.close();


        StringBuilder inputOut = new StringBuilder();
        try (BufferedReader inputReader = new BufferedReader(new InputStreamReader(execProcess.getInputStream()))) {
            while ((line = inputReader.readLine()) != null) {
                inputOut.append(line).append("\n");
            }
        }


        deleteTempExecFile();
        deleteTempFile();

        return inputOut.toString();
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

