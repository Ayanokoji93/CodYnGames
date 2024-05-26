package Compiler;

import Compiler.factor.GeneralCompiler;
import java.io.*;
import java.util.List;

/**
 * The CFile class extends GeneralCompiler and provides methods to write C code to a file,
 * compile it, execute it, and manage temporary files.
 */
public class CFile extends GeneralCompiler {

    /**
     *Constructor that initializes a temporary file with the ".c" extension.
     *
     * @throws IOException if an I/O error occurs.
     */
    public CFile() throws IOException {
        super(".c");
    }

    /**
     * Deletes the temporary executable file created during the compilation process.
     */
    private void deleteTempExecFile() {
        String execFilePath = "C:\\Users\\FiercePC\\Desktop\\JavaProject\\CodYnGames\\tempFile\\tempExecutable.exe";
        File execFile = new File(execFilePath);
        if (execFile.exists()) {
            execFile.delete();
        }
    }

    /**
     * Compiles and executes the provided C code, passing a list of numbers as input.
     *
     * @param code the C code to execute.
     * @param numbers the list of numbers to pass as input to the program.
     * @return the output of the executed program or compilation errors if any.
     * @throws IOException if an I/O error occurs.
     * @throws InterruptedException if the process execution is interrupted.
     */
    @Override
    public String execute(String code, List<Integer> numbers) throws IOException, InterruptedException {
        writeResponseInFile(code);

        // Compile the C code
        String compileCommand = "C:\\Program Files\\mingw64\\bin\\gcc.exe -o C:\\Users\\FiercePC\\Desktop\\JavaProject\\CodYnGames\\tempFile\\tempExecutable " + getPathFile();
        Process compileProcess = Runtime.getRuntime().exec(compileCommand);

        // This part display the errors of compilation
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

        // Execute the .exe program
        Process execProcess = Runtime.getRuntime().exec("C:\\Users\\FiercePC\\Desktop\\JavaProject\\CodYnGames\\tempFile\\tempExecutable");

        // Pass the numbers list as parameter
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(execProcess.getOutputStream()));
        for (Integer number : numbers) {
            writer.write(number.toString());
            writer.newLine();
        }
        writer.close();

        // This part display the input of the compilation
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
