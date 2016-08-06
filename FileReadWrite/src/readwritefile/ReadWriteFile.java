package readwritefile;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Demonstrates reading and writing of text and binary files. Reference: https://www.caveofprogramming.com/java/java-file-reading-and-writing-files-in-java.html
 *
 * @author skorkmaz, 2015
 */
public class ReadWriteFile {

    public static void main(String[] args) throws IOException {
        String inFileName = "./inFile.txt";
        System.out.println("Read from text file (" + inFileName + "):");
        List<String> inFileContents = readFromTextFile(inFileName);
        for (String string : inFileContents) {
            System.out.println(string);
        }
        String outFileName = "./outFile.txt";
        System.out.println("\nWrite to text file (" + outFileName + "):");
        List<String> outFileContents = new ArrayList<>(Arrays.asList("samil", "korkmaz", "ankara"));
        writeToTextFile(outFileName, outFileContents);
        System.out.println("...success");

        System.out.println("----------------------------------------------------");
        String inFileNameBinary = "./inFile.bin";
        System.out.println("Read from binary file (" + inFileNameBinary + "):");
        List<String> inFileContentsBinary = readFromBinaryFile(inFileNameBinary);
        for (String string : inFileContentsBinary) {
            System.out.println(string);
        }
        String outFileNameBinary = "./outFile.bin";
        System.out.println("\nWrite to binary file (" + outFileNameBinary + "):");
        writeToBinaryFile(outFileNameBinary, "samil\nkorkmaz\nankara".getBytes());
        System.out.println("...success");

    }

    static List<String> readFromTextFile(String fileName) throws IOException {
        List<String> fileContents = new ArrayList<>();
        // FileReader reads text files in the default encoding. 
		// To specify encoding you need to use new InputStreamReader(new FileInputStream(pathToFile), encoding). For standard Turkish text files, enconding is "ISO-8859-9".
        FileReader fileReader = new FileReader(fileName);
        try (BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                fileContents.add(line);
            }
        }
        return fileContents;
    }

    static void writeToTextFile(String fileName, List<String> fileContents) throws IOException {
        // Assume default encoding.
        FileWriter fileWriter = new FileWriter(fileName);
        try (BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
            for (String string : fileContents) {
                bufferedWriter.write(string);
                // Note that write() does not automatically append a newline character.
                bufferedWriter.newLine();
            }
        }
    }

    static List<String> readFromBinaryFile(String fileName) throws IOException {
        List<String> fileContents = new ArrayList<>();
        int chunkSize = 3; //Note: You could use new File(fileName).length to read the whole file in one step.
        byte[] data = new byte[chunkSize];
        try (FileInputStream inputStream = new FileInputStream(fileName)) {
            // read fills buffer with data and returns the number of bytes read (which of course may be less than the buffer size, but it will never be more).
            while (true) {
                int bytesRead = inputStream.read(data);
                if (bytesRead == -1) {
                    break;
                }
                if (bytesRead != chunkSize) { //trim data buffer
                    byte[] smallerData = new byte[bytesRead];
                    System.arraycopy(data, 0, smallerData, 0, bytesRead);
                    data = smallerData;
                }
                fileContents.add(new String(data));
            }
        }
        return fileContents;
    }

    static void writeToBinaryFile(String fileName, byte[] buffer) throws IOException {
        try (FileOutputStream outputStream = new FileOutputStream(fileName)) {
            // write() writes as many bytes from the buffer as the length of the buffer. You can also use write(buffer, offset, length) if you want to write a specific 
            // number of bytes, or only part of the buffer.
            outputStream.write(buffer);
        }
    }

}
