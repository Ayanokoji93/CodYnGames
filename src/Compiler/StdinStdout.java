package Compiler;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The StdinStdout class provides methods to execute a Python script,
 * capture its standard output and error streams, and handle input arguments.
 */
public class StdinStdout {
    private String scriptPath;

    /**
     * Constructor that initializes the script path.
     *
     * @param scriptPath the path to the Python script.
     */
    public StdinStdout(String scriptPath) {
        this.scriptPath = scriptPath;
    }

    /**
     * Executes the Python script and captures its output as a list of integers.
     *
     * @return a list of integers captured from the script's standard output.
     */
    public List<Integer> executeScript() {
        List<Integer> numbers = new ArrayList<>();
        try {
            Process process = Runtime.getRuntime().exec("python " + scriptPath);
            BufferedReader numberReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String line;

            // Read numbers from the script's output
            while ((line = numberReader.readLine()) != null) {
                String[] numberStrings = line.split(" ");
                for (String numStr : numberStrings) {
                    try {
                        numbers.add(Integer.parseInt(numStr));
                    } catch (NumberFormatException e) {
                        System.err.println("Number format error: " + numStr);
                    }
                }
            }

            // Read any errors from the script's error stream
            while ((line = errorReader.readLine()) != null) {
                System.err.println("Process error: " + line);
            }

            process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return numbers;
    }

    /**
     * Executes the Python script with a list of integers as input arguments and captures its output as a string.
     *
     * @param numbers the list of integers to pass as input arguments to the script.
     * @return the output of the executed script.
     */
    public String executeScriptWithArgs(List<Integer> numbers) {
        StringBuilder output = new StringBuilder();
        try {
            ProcessBuilder pb = new ProcessBuilder("python", scriptPath);
            Process execProcess = pb.start();

            // Pass the list of numbers to the script as input
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(execProcess.getOutputStream()));
            for (Integer number : numbers) {
                writer.write(number.toString());
                writer.newLine();
            }
            writer.close();

            // Capture the output of the executed script
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(execProcess.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line).append(System.lineSeparator());
                }
            }

            execProcess.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return output.toString();
    }
}
