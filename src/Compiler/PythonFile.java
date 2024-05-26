package Compiler;

import Compiler.factor.GeneralCompiler;
import java.io.*;
import java.util.List;

/**
 * The PythonFile class extends GeneralCompiler and provides methods to write Python code to a file,
 * execute it using a Python interpreter, and manage temporary files.
 */
public class PythonFile extends GeneralCompiler {

    /**
     *Constructor that initializes a temporary file with the ".py" extension.
     *
     * @throws IOException if an I/O error occurs.
     */
    public PythonFile() throws IOException {
        super(".py");
    }

    /**
     * Executes the provided Python code using a Python interpreter, passing a list of numbers as input.
     *
     * @param code the Python code to execute.
     * @param numbers the list of numbers to pass as input to the program.
     * @return the output of the executed program.
     * @throws IOException if an I/O error occurs.
     * @throws InterruptedException if the process execution is interrupted.
     */
    @Override
    public String execute(String code, List<Integer> numbers) throws IOException, InterruptedException {
        writeResponseInFile(code);

        ProcessBuilder pb = new ProcessBuilder("python", getPathFile());
        pb.redirectErrorStream(true);
        Process execProcess = pb.start();

        // Pass the list of numbers to the program as input
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(execProcess.getOutputStream()));
        for (Integer number : numbers) {
            writer.write(number.toString());
            writer.newLine();
        }
        writer.close();

        // Capture the output of the executed program
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

