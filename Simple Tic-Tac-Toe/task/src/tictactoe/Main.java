package tictactoe;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // write your code here
        play();
    }

    public static String[] createGameBoard() {
        String[] board = new String[9];
        Arrays.fill(board, " ");
        return board;
    }

    public static void play() {
        String[] gameBoard = createGameBoard();
        String gameStatus = getGameState(gameBoard);
        String nextPlayer = "X";

        while (gameStatus.equals("Game not finished")) {
            printBoard(gameBoard);
            processUserMove(gameBoard, nextPlayer);
            gameStatus = getGameState(gameBoard);
            nextPlayer = nextPlayer.equals("X") ? "O" : "X";
        }

        printBoard(gameBoard);
        System.out.println(gameStatus);
    }

    public static void printBoard(String[] gameBoard) {
        System.out.println("---------");
        for (int i = 0; i < 3; i++) {
            System.out.print("| ");
            for (int j = 0; j < 3; j++) {
                System.out.printf("%s ", gameBoard[3 * i + j]);
            }
            System.out.println("|");
        }
        System.out.println("---------");
    }

    public static String[][] getRows(String[] gameBoard) {
        String[][] rows = new String[3][3];
        for (int i = 0; i < 3; i++) {
            System.arraycopy(gameBoard, i * 3, rows[i], 0, 3);
        }
        return rows;
    }

    public static String[][] getCols(String[] gameBoard) {
        String[][] cols = new String[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                cols[i][j] = gameBoard[i + 3 * j];
            }
        }
        return cols;
    }
    
    public static boolean isLineOf(String player, String[] line) {
        String[] expectedLine = new String[] {player, player, player};
        return Arrays.equals(line, expectedLine);
    }

    public static boolean isLinesOf(String player, String[][] lines) {
        for (String[] line : lines) {
            if (isLineOf(player, line)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isRowOf(String player, String[] gameBoard) {
        return isLinesOf(player, getRows(gameBoard));
    }

    public static boolean isColOf(String player, String[] gameBoard) {
        return isLinesOf(player, getCols(gameBoard));
    }

    public static boolean isRowOfXs(String[] gameBoard) {
        return isRowOf("X", gameBoard);
    }

    public static boolean isRowOfOs(String[] gameBoard) {
        return isRowOf("O", gameBoard);
    }

    public static boolean isColOfXs(String[] gameBoard) {
        return isColOf("X", gameBoard);
    }

    public static boolean isColOfOs(String[] gameBoard) {
        return isColOf("O", gameBoard);
    }

    public static String[] getRightDiagonal(String[] gameBoard) {
        return new String[] {gameBoard[0], gameBoard[4], gameBoard[8]};
    }

    public static String[] getLeftDiagonal(String[] gameBoard) {
        return new String[] {gameBoard[2], gameBoard[4], gameBoard[6]};
    }

    public static boolean isRightDiagonalOf(String player, String[] gameBoard) {
        return isLineOf(player, getRightDiagonal(gameBoard));
    }

    public static boolean isLeftDiagonalOf(String player, String[] gameBoard) {
        return isLineOf(player, getLeftDiagonal(gameBoard));
    }

    public static boolean isRightDiagonalOfXs(String[] gameBoard) {
        return isRightDiagonalOf("X", gameBoard);
    }

    public static boolean isRightDiagonalOfOs(String[] gameBoard) {
        return isRightDiagonalOf("O", gameBoard);
    }

    public static boolean isLeftDiagonalOfXs(String[] gameBoard) {
        return isLeftDiagonalOf("X", gameBoard);
    }

    public static boolean isLeftDiagonalOfOs(String[] gameBoard) {
        return isLeftDiagonalOf("O", gameBoard);
    }

    public static boolean isDiagonalOfXs(String[] gameBoard) {
        return isRightDiagonalOfXs(gameBoard) || isLeftDiagonalOfXs(gameBoard);
    }

    public static boolean isDiagonalOfOs(String[] gameBoard) {
        return isRightDiagonalOfOs(gameBoard) || isLeftDiagonalOfOs(gameBoard);
    }

    public static boolean isXWins(String[] gameBoard) {
        return isRowOfXs(gameBoard) || isColOfXs(gameBoard) || isDiagonalOfXs(gameBoard);
    }

    public static boolean isOWins(String[] gameBoard) {
        return isRowOfOs(gameBoard) || isColOfOs(gameBoard) || isDiagonalOfOs(gameBoard);
    }

    public static boolean movesAvailable(String[] gameBoard) {
        return Arrays.asList(gameBoard).contains(" ");
    }

    public static boolean hasNoWinner(String[] gameBoard) {
        return !isXWins(gameBoard) && !isOWins(gameBoard);
    }

    public static int count(String player, String[] gameBoard) {
        int count = 0;
        for (String move : gameBoard) {
            if (move.equals(player)) {
                count++;
            }
        }
        return count;
    }

    public static boolean wrongNumberOfMoves(String[] gameBoard) {
        int movesDiff = count("X", gameBoard) - count("O", gameBoard) ;
        return movesDiff < -1 || movesDiff > 1;
    }

    public static boolean isDraw(String[] gameBoard) {
        return hasNoWinner(gameBoard) && !movesAvailable(gameBoard);
    }

    public static boolean isNotFinished(String[] gameBoard) {
        return hasNoWinner(gameBoard) && movesAvailable(gameBoard);
    }

    public static boolean isImpossible(String[] gameBoard) {
        return isXWins(gameBoard) && isOWins(gameBoard) || wrongNumberOfMoves(gameBoard);
    }

    public static String getGameState(String[] gameBoard) {

        if (isImpossible(gameBoard)) {
            return "Impossible";
        } else if (isNotFinished(gameBoard)) {
            return "Game not finished";
        } else if (isXWins(gameBoard)) {
            return "X wins";
        } else if (isOWins(gameBoard)) {
            return "O wins";
        } else if (isDraw(gameBoard)) {
            return "Draw";
        }
        else return "Unknown game state";

    }

    public static Coordinate readUserMove() throws CoordinateOutBoundException {
        Scanner scanner = new Scanner(System.in);
        int y = scanner.nextInt() - 1;
        int x = scanner.nextInt() - 1;
        return new Coordinate(x, y);
    }

    public static void makeUserMove(Coordinate coordinate, String[] gameBoard, String player)
            throws UsedCoordinateException {
        String target = gameBoard[coordinate.y * 3 + coordinate.x];

        if (target.equals("X") || target.equals("O")) {
            throw new UsedCoordinateException();
        }

        gameBoard[coordinate.y * 3 + coordinate.x] = player;
    }

    public static void processUserMove(String[] gameBoard, String player) {
        try {
            System.out.print("Enter the coordinates: ");
            Coordinate coordinate = readUserMove();
            makeUserMove(coordinate, gameBoard, player);
        } catch (CoordinateOutBoundException e) {
            System.out.println("Coordinates should be from 1 to 3!");
            processUserMove(gameBoard, player);
        } catch (UsedCoordinateException e) {
            System.out.println("This cell is occupied! Choose another one!");
            processUserMove(gameBoard, player);
        } catch (InputMismatchException e) {
            System.out.println("You should enter numbers!");
            processUserMove(gameBoard, player);
        }
    }


}
