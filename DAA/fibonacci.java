import java.util.Scanner;

public class Fibonacci {

    // Recursive method to calculate Fibonacci number
    public static int fibonacciRecursive(int n) {
        if (n <= 1) {
            return n;
        }
        return fibonacciRecursive(n - 1) + fibonacciRecursive(n - 2);
    }

    // Non-recursive (iterative) method to calculate Fibonacci number
    public static int fibonacciIterative(int n) {
        if (n <= 1) {
            return n;
        }
        int a = 0, b = 1, c = 0;
        for (int i = 2; i <= n; i++) {
            c = a + b;
            a = b;
            b = c;
        }
        return c;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a number to calculate Fibonacci: ");
        int n = scanner.nextInt();

        System.out.println("Recursive Fibonacci of " + n + " is: " + fibonacciRecursive(n));
        System.out.println("Iterative Fibonacci of " + n + " is: " + fibonacciIterative(n));

        scanner.close();
    }
}
