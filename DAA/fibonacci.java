import java.util.Scanner;

public class Fibonacci {

    // Recursive method to calculate Fibonacci number
    public static int fibonacciRecursive(int n) {
        // Base case: if n is 0 or 1, return n
        if (n <= 1) {
            return n;
        }
        // Recursive case: sum of the two previous Fibonacci numbers
        return fibonacciRecursive(n - 1) + fibonacciRecursive(n - 2);
    }

    // Non-recursive (iterative) method to calculate Fibonacci number
    public static int fibonacciIterative(int n) {
        // Base case: if n is 0 or 1, return n
        if (n <= 1) {
            return n;
        }
        int a = 0, b = 1, c = 0;
        // Iterative approach to calculate Fibonacci number
        for (int i = 2; i <= n; i++) {
            c = a + b;  // Fibonacci calculation
            a = b;      // Update previous values
            b = c;      // Update current value
        }
        return c;
    }

    public static void main(String[] args) {
        // Create a scanner object to read input from the user
        Scanner scanner = new Scanner(System.in);

        // Prompt the user to enter a number for Fibonacci calculation
        System.out.print("Enter a number to calculate Fibonacci: ");
        int n = scanner.nextInt(); // Read the input number

        // Display results of both recursive and iterative Fibonacci calculations
        System.out.println("Recursive Fibonacci of " + n + " is: " + fibonacciRecursive(n));
        System.out.println("Iterative Fibonacci of " + n + " is: " + fibonacciIterative(n));

        // Close the scanner to avoid resource leak
        scanner.close();
    }
}

/*
 * Time and Space Complexity Analysis:
 * 
 * 1. Recursive Fibonacci (fibonacciRecursive):
 *    - Time Complexity: O(2^n)
 *      - The recursive method has an exponential time complexity because it makes two recursive calls for each non-base case, leading to an exponential number of calls.
 *      - This results in many redundant calculations.
 *    - Space Complexity: O(n)
 *      - The space complexity is proportional to the height of the recursion tree, which can go up to `n` calls deep.
 *      - Thus, the space complexity is O(n) due to the recursive call stack.
 * 
 * 2. Iterative Fibonacci (fibonacciIterative):
 *    - Time Complexity: O(n)
 *      - The iterative method loops through `n` times to calculate the Fibonacci number, so it has linear time complexity.
 *    - Space Complexity: O(1)
 *      - The space complexity is constant, as the iterative method only uses a fixed number of variables (`a`, `b`, `c`), regardless of `n`.
 */
