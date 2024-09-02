package filecompression;

import java.util.HashMap;
import java.util.Map;

public class HuffmanCoder {

    private HashMap<Character, String> encoder;
    private HashMap<String, Character> decoder;

    private class Node implements Comparable<Node> {
        Character data;
        int cost;
        Node left;
        Node right;

        public Node(Character data, int cost) {
            this.data = data;
            this.cost = cost;
            this.left = null;
            this.right = null;
        }

        @Override
        public int compareTo(Node other) {
            return this.cost - other.cost;
        }
    }

    // Original constructor for encoding
    public HuffmanCoder(String feeder) throws Exception {
        HashMap<Character, Integer> fmap = new HashMap<>();

        for (int i = 0; i < feeder.length(); i++) {
            char cc = feeder.charAt(i);
            fmap.put(cc, fmap.getOrDefault(cc, 0) + 1);
        }

        Heap<Node> minHeap = new Heap<>();
        for (Map.Entry<Character, Integer> entry : fmap.entrySet()) {
            Node node = new Node(entry.getKey(), entry.getValue());
            minHeap.insert(node);
        }

        while (minHeap.size() != 1) {
            Node first = minHeap.remove();
            Node second = minHeap.remove();

            Node newNode = new Node('\0', first.cost + second.cost);
            newNode.left = first;
            newNode.right = second;

            minHeap.insert(newNode);
        }

        Node root = minHeap.remove();
        this.encoder = new HashMap<>();
        this.decoder = new HashMap<>();

        initEncoderDecoder(root, "");
    }

    // New constructor for decoding
    public HuffmanCoder(HashMap<Character, String> encoder) {
        this.encoder = encoder;
        this.decoder = new HashMap<>();

        for (Map.Entry<Character, String> entry : encoder.entrySet()) {
            this.decoder.put(entry.getValue(), entry.getKey());
        }
    }

    private void initEncoderDecoder(Node node, String osf) {
        if (node == null) return;
        if (node.left == null && node.right == null) {
            this.encoder.put(node.data, osf);
            this.decoder.put(osf, node.data);
        }
        initEncoderDecoder(node.left, osf + "0");
        initEncoderDecoder(node.right, osf + "1");
    }

    public String encode(String source) {
        StringBuilder ans = new StringBuilder();
        for (int i = 0; i < source.length(); i++) {
            ans.append(encoder.get(source.charAt(i)));
        }
        return ans.toString();
    }

    public String decode(String codedString) {
        StringBuilder key = new StringBuilder();
        StringBuilder ans = new StringBuilder();
        for (int i = 0; i < codedString.length(); i++) {
            key.append(codedString.charAt(i));
            if (decoder.containsKey(key.toString())) {
                ans.append(decoder.get(key.toString()));
                key.setLength(0);
            }
        }
        return ans.toString();
    }

    public HashMap<Character, String> getEncoder() {
        return encoder;
    }

    public HashMap<String, Character> getDecoder() {
        return decoder;
    }
}
