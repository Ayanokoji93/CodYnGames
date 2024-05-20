package Compiler.factor;

import java.io.*;
import java.util.Scanner;

public abstract class GeneralCompiler {
    protected File tempFile;
    protected String response;

    public GeneralCompiler(String extension) throws IOException {
        this.tempFile = File.createTempFile("temp", extension, new File("C:\\Users\\Fayçal\\Desktop\\JavaProject\\CodYnGames\\tempFile"));
    }

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

    public void writeResponseInFile(String response) throws IOException {
        if (response != null) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.tempFile))) {
                writer.write(response);
            }
        } else {
            System.out.println("Response is null, skipping writing to file.");
        }
    }

    public void deleteTempFile() {
        if(this.tempFile.exists()){
            this.tempFile.delete();
        }
    }

    public String getResponse(){
        return this.response;
    }

    public String getPathFile(){
        return tempFile.getAbsolutePath();
    }

    public abstract String execute(String code) throws IOException, InterruptedException;

    public abstract String exercisesWNumber(String number1, String number2) throws IOException, InterruptedException;

}
