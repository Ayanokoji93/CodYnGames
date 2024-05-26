package Compiler;

import Compiler.factor.GeneralCompiler;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The PhpFile class extends GeneralCompiler and provides methods to write PHP code to a file,
 * execute it using a PHP interpreter, and manage temporary files.
 */
public class PhpFile extends GeneralCompiler {

    /**
     * Constructor that initializes a temporary file with the ".php" extension.
     *
     * @throws IOException if an I/O error occurs.
     */
    public PhpFile() throws IOException {
        super(".php");
    }

    /**
     * Executes the provided PHP code using a PHP interpreter, passing a list of numbers as input.
     *
     * @param code the PHP code to execute.
     * @param numbers the list of numbers to pass as input to the program.
     * @return the output of the executed program.
     * @throws IOException if an I/O error occurs.
     * @throws InterruptedException if the process execution is interrupted.
     */
    @Override
    public String execute(String code, List<Integer> numbers) throws IOException, InterruptedException {
        writeResponseInFile(code);

        // Execute the PHP file
        String phpExecutable = "C:\\Users\\FiercePC\\Desktop\\php\\php.exe";
        List<String> command = new ArrayList<>();
        command.add(phpExecutable);
        command.add(getPathFile());

        ProcessBuilder pb = new ProcessBuilder(command);
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

        return output.toString();
    }
}
