package filecompression;

import java.io.*;
import java.util.HashMap;

public class FileCompression {

    public static void compressFile(String inputFile, String outputFile) throws Exception {
        String content = readFile(inputFile);
        HuffmanCoder huffmanCoder = new HuffmanCoder(content);

        String encodedContent = huffmanCoder.encode(content);
        writeFile(outputFile, encodedContent);

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(outputFile + ".meta"))) {
            oos.writeObject(huffmanCoder.getEncoder());
        }
    }

    public static void decompressFile(String inputFile, String outputFile) throws Exception {
        String encodedContent = readFile(inputFile);
        HashMap<Character, String> encoder;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(inputFile + ".meta"))) {
            encoder = (HashMap<Character, String>) ois.readObject();
        }

        // Reconstruct the HuffmanCoder using the new constructor
        HuffmanCoder huffmanCoder = new HuffmanCoder(encoder);

        String decodedContent = huffmanCoder.decode(encodedContent);
        writeFile(outputFile, decodedContent);
    }

    private static String readFile(String filename) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line);
            }
        }
        return content.toString();
    }

    private static void writeFile(String filename, String content) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            bw.write(content);
        }
    }
}
