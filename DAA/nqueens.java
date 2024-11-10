import java.util.Scanner;

public class NQueens {

    // Function to check if it's safe to place a queen at board[row][col]
    private static boolean isSafe(int[][] board, int row, int col, int N) {
        // Check this column for any queen
        for (int i = 0; i < row; i++) {
            if (board[i][col] == 1) {
                return false;  // There's a queen in the same column
            }
        }

        // Check upper diagonal on the left side
        for (int i = row, j = col; i >= 0 && j >= 0; i--, j--) {
            if (board[i][j] == 1) {
                return false;  // There's a queen in the left diagonal
            }
        }

        // Check upper diagonal on the right side
        for (int i = row, j = col; i >= 0 && j < N; i--, j++) {
            if (board[i][j] == 1) {
                return false;  // There's a queen in the right diagonal
            }
        }

        return true;  // It's safe to place the queen
    }

    // Recursive function to solve the N-Queens problem
    private static boolean solveNQueens(int[][] board, int row, int N) {
        // Base case: If all queens are placed
        if (row >= N) {
            return true;  // All queens are successfully placed
        }

        // Try placing a queen in all columns of the current row
        for (int col = 0; col < N; col++) {
            if (isSafe(board, row, col, N)) {
                // Place the queen at (row, col)
                board[row][col] = 1;

                // Recur to place the rest of the queens
                if (solveNQueens(board, row + 1, N)) {
                    return true;  // If the next queen placement was successful, return true
                }

                // Backtrack if placing queen here doesn't lead to a solution
                board[row][col] = 0;
            }
        }

        // If no valid position is found in this row, return false
        return false;
    }

    // Function to initialize the N-Queens board with the first queen placed
    public static boolean nQueensWithFirstQueen(int N, int row, int col) {
        int[][] board = new int[N][N];

        // Place the first queen at the given position
        board[row][col] = 1;

        // Start solving from the next row
        if (solveNQueens(board, row + 1, N)) {
            printBoard(board, N);  // Print the board if a solution is found
            return true;
        } else {
            System.out.println("No solution exists");
            return false;  // If no solution is found, print a message
        }
    }

    // Function to print the N-Queens solution matrix
    private static void printBoard(int[][] board, int N) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();  // Print each row of the board
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Take user input for the size of the board
        System.out.print("Enter the size of the board (N): ");
        int N = sc.nextInt();

        // Take user input for the position of the first queen
        System.out.print("Enter the row index (0 to N-1) for the first queen: ");
        int firstQueenRow = sc.nextInt();

        System.out.print("Enter the column index (0 to N-1) for the first queen: ");
        int firstQueenCol = sc.nextInt();

        // Call the function to solve the N-Queens problem
        nQueensWithFirstQueen(N, firstQueenRow, firstQueenCol);

        sc.close();
    }
}

/*
Time Complexity:
- The recursive function `solveNQueens` tries to place a queen in each of the N rows, and for each row, it tries N columns. In the worst case, it explores all N^N possible configurations, making the time complexity of this algorithm O(N^N).

Space Complexity:
- The space complexity is determined by the recursion stack, which in the worst case, can go as deep as N (for each row in the board). Thus, the space complexity is O(N), which accounts for the space used by the recursion call stack and the storage required for the board.
*/
