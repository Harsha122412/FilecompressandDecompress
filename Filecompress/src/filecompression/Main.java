package filecompression;

public class Main {
    public static void main(String[] args) throws Exception {
        String inputFile = "input.txt";
        String compressedFile = "compressed.txt";
        String decompressedFile = "decompressed.txt";

        // Compress the file
        FileCompression.compressFile(inputFile, compressedFile);

        // Decompress the file
        FileCompression.decompressFile(compressedFile, decompressedFile);

        System.out.println("File compression and decompression completed.");
    }
}
