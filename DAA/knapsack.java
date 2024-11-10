import java.util.*;

// Class to represent a Knapsack item
class Item {
    int weight;
    int value;

    // Constructor
    public Item(int weight, int value) {
        this.weight = weight;
        this.value = value;
    }
}

// Class to represent a node in the decision tree
class Node {
    int level;       // Level of node in decision tree
    int profit;      // Profit value of node
    int weight;      // Weight of knapsack at this node
    double bound;    // Upper bound of maximum profit

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
        // If weight of current node exceeds capacity, return 0 as no solution is possible
        if (u.weight >= capacity) return 0;

        // Calculate the upper bound on profit by including fractional part of the next item
        double profitBound = u.profit;
        int j = u.level + 1;
        int totalWeight = u.weight;

        // Add items while weight allows
        while (j < n && totalWeight + items[j].weight <= capacity) {
            totalWeight += items[j].weight;
            profitBound += items[j].value;
            j++;
        }

        // If there are items left and the knapsack is not full, add fractional part of the last item
        if (j < n) {
            profitBound += (capacity - totalWeight) * items[j].value / (double) items[j].weight;
        }

        return profitBound;
    }

    // Function to solve 0/1 Knapsack problem using Branch and Bound
    public static int knapsackBranchBound(Item[] items, int n, int capacity) {
        // Step 1: Sort items based on value-to-weight ratio
        Arrays.sort(items, (a, b) -> Double.compare((double) b.value / b.weight, (double) a.value / a.weight));

        // Step 2: Create a queue for BFS (branching)
        Queue<Node> queue = new LinkedList<>();
        Node root = new Node(-1, 0, 0, 0);
        queue.add(root);

        // Step 3: Initialize the best value found so far
        int maxProfit = 0;

        // Step 4: BFS to explore decision tree
        while (!queue.isEmpty()) {
            Node u = queue.poll();

            // If this node is promising, calculate the bound
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

        return maxProfit;
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
