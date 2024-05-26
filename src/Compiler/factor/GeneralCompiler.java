package Compiler.factor;

import java.io.*;
import java.util.Scanner;
import java.util.List;

/**
 * The GeneralCompiler abstract class provides common functionality for compiler classes.
 */
public abstract class GeneralCompiler {
    protected File tempFile;
    protected String response;

    /**
     * Constructor that creates a temporary file with the specified extension.
     *
     * @param extension the file extension for the temporary file.
     * @throws IOException if an I/O error occurs while creating the temporary file.
     */
    public GeneralCompiler(String extension) throws IOException {
        this.tempFile = this.tempFile = File.createTempFile("temp", extension, new File("C:\\Users\\FiercePC\\Desktop\\JavaProject\\CodYnGames\\tempFile"));
    }

    /**
     * Prompt the user to write a response and store it.
     * These two following methods are never used for the moment. But they can be used for command lines directly.
     */
    public void askResponse(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Write your answer below :");
        StringBuilder str = new StringBuilder();
        String line;
        while(!(line = scanner.nextLine()).isEmpty()){
            str.append(line).append("\n");
        }
        response = str.toString();
    }

    /**
     * Get the stored response.
     *
     * @return the stored response.
     */
    public String getResponse(){
        return this.response;
    }

    /**
     * Write the provided response to the temporary file.
     *
     * @param response the response to write to the file.
     * @throws IOException if an I/O error occurs while writing to the file.
     */
    public void writeResponseInFile(String response) throws IOException {
        if (response != null) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.tempFile))) {
                writer.write(response);
            }
        } else {
            System.out.println("Response is null, skipping writing to file.");
        }
    }

    /**
     * Delete the temporary file.
     */
    public void deleteTempFile() {
        if(this.tempFile.exists()){
            this.tempFile.delete();
        }
    }


    /**
     * Get the absolute path of the temporary file.
     *
     * @return the absolute path of the temporary file.
     */
    public String getPathFile(){
        return tempFile.getAbsolutePath();
    }

    /**
     * Adds the content of a source file to the end of the current file.
     *
     * @param sourceFilePath the path to the source file.
     * @throws IOException if an I/O error occurs.
     */
    public void addContentToFile(String sourceFilePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.tempFile, true));
             BufferedReader reader = new BufferedReader(new FileReader(sourceFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line);
                writer.newLine();
            }
        }
    }

    /**
     * Abstract method to be implemented by subclasses to execute code.
     *
     * @param code the code to execute.
     * @param numbers a list of numbers to use as input.
     * @return the output of the execution.
     * @throws IOException if an I/O error occurs.
     * @throws InterruptedException if the execution is interrupted.
     */
    public abstract String execute(String code, List<Integer> numbers) throws IOException, InterruptedException;

}
