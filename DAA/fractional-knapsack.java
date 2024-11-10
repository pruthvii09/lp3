import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

class Item {
    int weight;
    int value;

    // Constructor
    public Item(int weight, int value) {
        this.weight = weight;
        this.value = value;
    }
}

public class FractionalKnapsack {

    // Method to solve the fractional knapsack problem
    public static double fractionalKnapsack(Item[] items, int capacity) {
        // Step 1: Sort items by their value-to-weight ratio in descending order
        Arrays.sort(items, new Comparator<Item>() {
            @Override
            public int compare(Item o1, Item o2) {
                double r1 = (double) o1.value / o1.weight;
                double r2 = (double) o2.value / o2.weight;
                return Double.compare(r2, r1); // Descending order
            }
        });

        double totalValue = 0.0; // Total value in the knapsack
        int currentCapacity = capacity;

        // Step 2: Take items as much as possible
        for (Item item : items) {
            if (currentCapacity == 0) break; // Knapsack is full

            if (item.weight <= currentCapacity) {
                // We can take the whole item
                currentCapacity -= item.weight;
                totalValue += item.value;
            } else {
                // We can only take a fraction of the item
                double fraction = (double) currentCapacity / item.weight;
                totalValue += item.value * fraction;
                currentCapacity = 0; // Knapsack is full
            }
        }

        return totalValue;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Take input for the number of items
        System.out.print("Enter the number of items: ");
        int n = scanner.nextInt();

        // Create an array to store the items
        Item[] items = new Item[n];

        // Input each item's weight and value
        for (int i = 0; i < n; i++) {
            System.out.print("Enter weight of item " + (i + 1) + ": ");
            int weight = scanner.nextInt();
            System.out.print("Enter value of item " + (i + 1) + ": ");
            int value = scanner.nextInt();
            items[i] = new Item(weight, value);
        }

        // Input the capacity of the knapsack
        System.out.print("Enter the knapsack capacity: ");
        int knapsackCapacity = scanner.nextInt();

        // Calculate the maximum value we can carry
        double maxValue = fractionalKnapsack(items, knapsackCapacity);

        // Print the result
        System.out.println("Maximum value in Knapsack = " + maxValue);

        scanner.close();
    }
}
