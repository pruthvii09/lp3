import java.util.Scanner;

public class NQueens {

    private static boolean isSafe(int[][] board, int row, int col, int N) {
        // Check this column for any queen
        for (int i = 0; i < row; i++) {
            if (board[i][col] == 1) {
                return false;
            }
        }

        // Check upper diagonal on the left side
        for (int i = row, j = col; i >= 0 && j >= 0; i--, j--) {
            if (board[i][j] == 1) {
                return false;
            }
        }

        // Check upper diagonal on the right side
        for (int i = row, j = col; i >= 0 && j < N; i--, j++) {
            if (board[i][j] == 1) {
                return false;
            }
        }

        return true;
    }

    // Recursive function to solve the N-Queens problem
    private static boolean solveNQueens(int[][] board, int row, int N) {
        // Base case: If all queens are placed
        if (row >= N) {
            return true;
        }

        // Try placing a queen in all columns of the current row
        for (int col = 0; col < N; col++) {
            if (isSafe(board, row, col, N)) {
                // Place the queen
                board[row][col] = 1;

                // Recur to place the rest of the queens
                if (solveNQueens(board, row + 1, N)) {
                    return true;
                }

                // Backtrack if placing queen here doesn't lead to a solution
                board[row][col] = 0;
            }
        }

        // If no valid position is found, return false
        return false;
    }

    // Function to initialize the N-Queens board with the first queen placed
    public static boolean nQueensWithFirstQueen(int N, int row, int col) {
        int[][] board = new int[N][N];

        // Place the first queen at the given position
        board[row][col] = 1;

        // Start solving from the next row
        if (solveNQueens(board, row + 1, N)) {
            printBoard(board, N);
            return true;
        } else {
            System.out.println("No solution exists");
            return false;
        }
    }

    // Function to print the N-Queens solution matrix
    private static void printBoard(int[][] board, int N) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
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
