package Compiler;

import Compiler.factor.GeneralCompiler;

import java.io.*;

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
    public String execute(String code) throws IOException, InterruptedException {
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

        BufferedReader inputReader = new BufferedReader(new InputStreamReader(execProcess.getInputStream()));
        StringBuilder inputOut = new StringBuilder();
        while ((line = inputReader.readLine()) != null) {
            inputOut.append(line).append("\n");
        }
        inputReader.close();

        deleteTempExecFile();
        deleteTempFile();

        return inputOut.toString();
    }

    public String getResponse(){
        return this.response;
    }


        StringBuilder output = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
        }

        p.waitFor();

        return output.toString().trim();
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

