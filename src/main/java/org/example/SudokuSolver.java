package org.example;

import java.util.Scanner;
import java.util.stream.IntStream;

public class SudokuSolver {

    private static final int BOX_SIZE = 3;
    private static final int GRID_SIZE = BOX_SIZE * BOX_SIZE;

    public static void main(String[] args) {

        int[][] board;
//        = new int[GRID_SIZE][GRID_SIZE];
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter 0 for Empty Board");
        System.out.println("Enter 1 for Custom Board");
        int choice=sc.nextInt();
        if(choice==0){
            board=new int[GRID_SIZE][GRID_SIZE];
        }else {
            // fill the board
            System.out.println("To Keep Empty Cell Use 0");
            board=new int[GRID_SIZE][GRID_SIZE];
            for(int i=0;i<GRID_SIZE;i++){
                for(int j=0;j<GRID_SIZE;j++){
                    board[i][j]=sc.nextInt();
                }
            }
        }
        // You can also create your custom sudoku here
        if (solve(board)) {
            System.out.println("Good Job Here is your Sudoku Board, Cheers!");
            printResult(board);
        } else {
            System.out.println("Oops, Server Crashed, Let's play one more time! :(");
        }
    }

    private static void printResult(int[][] board) {
        for (int row = 0; row < GRID_SIZE; row++) {
            if ((row % BOX_SIZE == 0) && (row != 0)) {
                System.out.println("-----------------------------");
            }
            for (int col = 0; col < GRID_SIZE; col++) {
                if ((col % BOX_SIZE == 0) && (col != 0)) {
                    System.out.print("|");
                }
                final int cellValue = board[row][col];
                System.out.print(" ");
                if (cellValue == 0) {
                    System.out.print(" ");
                } else {
                    System.out.print(cellValue);
                }
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    private static boolean allowedInRow(int[][] board, int row, int number) {
        return IntStream.range(0, GRID_SIZE)
                //It basically checks is there no number in the stream that satisfies the condition
                .noneMatch(col -> board[row][col] == number);
    }

    private static boolean allowedInCol(int[][] board, int col, int number) {
        return IntStream.range(0, GRID_SIZE)
                .noneMatch(row -> board[row][col] == number);
    }

    private static boolean allowedInBox(int[][] board, int row, int col, int number) {
        // [5,2]
        // 2-2=0
        // 5-2=3
        // [5,5]
        // 5-2=3
        // 5-2=3
        final int boxCol = col - (col % BOX_SIZE);
        // since boxes measures in a length of 3 both height wise and length wise
        // So we are doing modulus to get the location of correct box
        final int boxRow = row - (row % BOX_SIZE);
        // In short box row and box col will contain the starting indexes of the box
        for (int i = 0; i < BOX_SIZE; i++) {
            for (int j = 0; j < BOX_SIZE; j++) {
                if (board[boxRow + i][boxCol + j] == number) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean isAllowed(int[][] board, int row, int col, int number) {
        return allowedInRow(board, row, number) &&
                allowedInCol(board, col, number) &&
                allowedInBox(board, row, col, number);
    }

    private static boolean solve(int[][] board) {
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                if (board[row][col] == 0) {
                    for (int num = 1; num <= GRID_SIZE; num++) {
                        if (isAllowed(board, row, col, num)) {
                            board[row][col] = num;
                            if (solve(board)) {
                                return true;
                            }
                            board[row][col] = 0;
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }
}