import java.util.PriorityQueue;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

// Huffman Node class
class HuffmanNode {
    int frequency;
    char character;
    HuffmanNode left, right;

    HuffmanNode(char character, int frequency) {
        this.character = character;
        this.frequency = frequency;
        this.left = null;
        this.right = null;
    }
}

// Comparator for the priority queue (min-heap)
class HuffmanComparator implements java.util.Comparator<HuffmanNode> {
    public int compare(HuffmanNode node1, HuffmanNode node2) {
        return node1.frequency - node2.frequency;
    }
}

public class HuffmanEncoding {

    // Function to generate Huffman codes
    public static void generateCodes(HuffmanNode root, String code, Map<Character, String> huffmanCode) {
        if (root == null) {
            return;
        }

        // If this is a leaf node, it contains a character
        if (root.left == null && root.right == null) {
            huffmanCode.put(root.character, code);
        }

        // Traverse the left and right subtree
        generateCodes(root.left, code + "0", huffmanCode);
        generateCodes(root.right, code + "1", huffmanCode);
    }

    // Function to build the Huffman tree and generate codes
    public static Map<Character, String> buildHuffmanTree(char[] charArray, int[] charFrequency) {
        // Step 1: Create a priority queue to store live nodes of the Huffman tree
        PriorityQueue<HuffmanNode> pq = new PriorityQueue<>(charArray.length, new HuffmanComparator());

        // Step 2: Create a leaf node for each character and add it to the priority queue
        for (int i = 0; i < charArray.length; i++) {
            pq.add(new HuffmanNode(charArray[i], charFrequency[i]));
        }

        // Step 3: Iterate while the size of the queue is greater than 1
        while (pq.size() > 1) {
            // Step 4: Remove the two nodes of the lowest frequency
            HuffmanNode left = pq.poll();
            HuffmanNode right = pq.poll();

            // Step 5: Create a new internal node with frequency equal to the sum of the two nodes' frequencies
            HuffmanNode newNode = new HuffmanNode('-', left.frequency + right.frequency);
            newNode.left = left;
            newNode.right = right;

            // Step 6: Add the new node to the priority queue
            pq.add(newNode);
        }

        // Step 7: The remaining node is the root of the Huffman tree
        HuffmanNode root = pq.poll();

        // Step 8: Traverse the Huffman tree and store the codes
        Map<Character, String> huffmanCode = new HashMap<>();
        generateCodes(root, "", huffmanCode);

        return huffmanCode;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Taking input for the number of characters
        System.out.print("Enter the number of characters: ");
        int n = scanner.nextInt();

        // Arrays to store characters and their corresponding frequencies
        char[] charArray = new char[n];
        int[] charFrequency = new int[n];

        // Taking input for each character and its frequency
        for (int i = 0; i < n; i++) {
            System.out.print("Enter character " + (i + 1) + ": ");
            charArray[i] = scanner.next().charAt(0);
            System.out.print("Enter frequency of " + charArray[i] + ": ");
            charFrequency[i] = scanner.nextInt();
        }

        // Build the Huffman Tree and get the codes
        Map<Character, String> huffmanCode = buildHuffmanTree(charArray, charFrequency);

        // Print the Huffman codes for each character
        System.out.println("Huffman Codes for each character:");
        for (Map.Entry<Character, String> entry : huffmanCode.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        scanner.close();
    }
}

/*
 * Time and Space Complexity Analysis:
 * 
 * 1. Building the Huffman Tree:
 *    - Time Complexity: O(n log n)
 *      - We insert `n` elements into the priority queue. Each insert operation takes O(log n), leading to a time complexity of O(n log n).
 *      - After each pair of nodes is removed and a new node is inserted, the priority queue is rebalanced, and this operation takes O(log n) time.
 *      - The number of operations is proportional to `n` (where `n` is the number of characters), hence the overall time complexity is O(n log n).
 *    - Space Complexity: O(n)
 *      - The priority queue stores `n` nodes, and the space required for this is O(n). Additionally, we use a map to store the generated Huffman codes, which requires O(n) space.
 * 
 * 2. Generating the Huffman codes:
 *    - Time Complexity: O(n)
 *      - Traversing the tree to generate the Huffman codes takes O(n), where `n` is the number of unique characters, since each node is visited exactly once.
 *    - Space Complexity: O(n)
 *      - The space required to store the Huffman codes is O(n), as we store one code per character.
 * 
 * Overall Time Complexity: O(n log n)
 * Overall Space Complexity: O(n)
 */
