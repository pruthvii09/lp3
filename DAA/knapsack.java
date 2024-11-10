import java.util.*;

// Class to represent a Knapsack item
class Item {
    int weight;  // Weight of the item
    int value;   // Value of the item

    // Constructor to initialize an item
    public Item(int weight, int value) {
        this.weight = weight;
        this.value = value;
    }
}

// Class to represent a node in the decision tree for Branch and Bound
class Node {
    int level;       // Level of the node in the decision tree
    int profit;      // Profit accumulated at this node
    int weight;      // Weight of the items selected so far
    double bound;    // Upper bound on the maximum profit for this node

    // Constructor to initialize a node
    public Node(int level, int profit, int weight, double bound) {
        this.level = level;
        this.profit = profit;
        this.weight = weight;
        this.bound = bound;
    }
}

public class KnapsackBranchBound {

    // Function to calculate the upper bound (greedy approach) of a node
    public static double bound(Node u, int n, int capacity, Item[] items) {
        // If the weight of current node exceeds capacity, no solution is possible
        if (u.weight >= capacity) return 0;

        // Calculate the upper bound on profit by including fractional part of the next item
        double profitBound = u.profit;
        int j = u.level + 1;
        int totalWeight = u.weight;

        // Add items while their weight is within the capacity of the knapsack
        while (j < n && totalWeight + items[j].weight <= capacity) {
            totalWeight += items[j].weight;
            profitBound += items[j].value;
            j++;
        }

        // If there are items left, add fractional part of the last item
        if (j < n) {
            profitBound += (capacity - totalWeight) * items[j].value / (double) items[j].weight;
        }

        return profitBound;  // Return the upper bound
    }

    // Function to solve the 0/1 Knapsack problem using Branch and Bound
    public static int knapsackBranchBound(Item[] items, int n, int capacity) {
        // Step 1: Sort items based on value-to-weight ratio in descending order
        Arrays.sort(items, (a, b) -> Double.compare((double) b.value / b.weight, (double) a.value / a.weight));

        // Step 2: Create a queue for BFS (Breadth-First Search) to explore the decision tree
        Queue<Node> queue = new LinkedList<>();
        Node root = new Node(-1, 0, 0, 0);  // Root node has level -1, profit 0, and weight 0
        queue.add(root);

        // Step 3: Initialize the best value found so far
        int maxProfit = 0;

        // Step 4: Explore the decision tree using BFS
        while (!queue.isEmpty()) {
            Node u = queue.poll();

            // If this node is promising, calculate the upper bound
            if (u.level + 1 < n) {
                // Explore the branch where the current item is included
                Node include = new Node(u.level + 1, u.profit + items[u.level + 1].value,
                        u.weight + items[u.level + 1].weight, 0);
                if (include.weight <= capacity) {
                    // Calculate bound and check if it's promising
                    include.bound = bound(include, n, capacity, items);
                    if (include.bound > maxProfit) {
                        maxProfit = Math.max(maxProfit, include.profit);
                        queue.add(include);
                    }
                }

                // Explore the branch where the current item is not included
                Node exclude = new Node(u.level + 1, u.profit, u.weight, 0);
                exclude.bound = bound(exclude, n, capacity, items);
                if (exclude.bound > maxProfit) {
                    queue.add(exclude);
                }
            }
        }

        return maxProfit;  // Return the maximum profit found
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Taking the number of items as input
        System.out.print("Enter the number of items: ");
        int n = scanner.nextInt();

        // Taking item weights and values as input
        Item[] items = new Item[n];
        for (int i = 0; i < n; i++) {
            System.out.print("Enter weight of item " + (i + 1) + ": ");
            int weight = scanner.nextInt();
            System.out.print("Enter value of item " + (i + 1) + ": ");
            int value = scanner.nextInt();
            items[i] = new Item(weight, value);
        }

        // Taking the knapsack capacity as input
        System.out.print("Enter the knapsack capacity: ");
        int capacity = scanner.nextInt();

        // Solve the problem using Branch and Bound
        int maxValue = knapsackBranchBound(items, n, capacity);

        // Print the result
        System.out.println("Maximum value in Knapsack = " + maxValue);

        scanner.close();
    }
}

/*
Time Complexity:
- Sorting the items: O(n log n)
- Exploring the decision tree: In the worst case, the number of nodes in the tree can be 2^n. For each node, we calculate the upper bound which takes O(n) time.
  Thus, the worst-case time complexity is O(2^n * n).

Space Complexity:
- Storing the items requires O(n) space.
- The queue can store up to 2^n nodes in the worst case, leading to a space complexity of O(2^n).

Overall Time Complexity: O(2^n * n)
Overall Space Complexity: O(2^n)
*/
