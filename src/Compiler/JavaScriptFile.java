package Compiler;

import Compiler.factor.GeneralCompiler;
import java.io.*;
import java.util.List;

/**
 * The JavaScriptFile class extends GeneralCompiler and provides methods to write JavaScript code to a file,
 * execute it using Node.js, and manage temporary files.
 */
public class JavaScriptFile extends GeneralCompiler {
    private String fileName;

    /**
     *Constructor that initializes a temporary file with the ".js" extension.
     *
     * @throws IOException if an I/O error occurs.
     */
    public JavaScriptFile() throws IOException {
        super(".js");
    }

    /**
     * Executes the provided JavaScript code using Node.js, passing a list of numbers as input.
     *
     * @param code the JavaScript code to execute.
     * @param numbers the list of numbers to pass as input to the program.
     * @return the output of the executed program.
     * @throws IOException if an I/O error occurs.
     * @throws InterruptedException if the process execution is interrupted.
     */
    @Override
    public String execute(String code, List<Integer> numbers) throws IOException, InterruptedException {
        writeResponseInFile(code);

        // Path to the Node.js executable
        String nodeExecutable = "C:\\Program Files\\nodejs\\node.exe";

        // Command to execute the JavaScript file
        String command = nodeExecutable + " " + getPathFileJs();

        // Execute the JavaScript file
        Process execProcess = Runtime.getRuntime().exec(command);

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

        // Wait for the process to finish
        execProcess.waitFor();

        // Delete the temporary file
        deleteTempFile();

        return output.toString();
    }

    /**
     * Gets the absolute path of the temporary JavaScript file.
     *
     * @return the absolute path of the temporary JavaScript file.
     */
    public String getPathFileJs() {
        return tempFile.getAbsolutePath();
    }
}
