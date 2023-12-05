import java.util.Random;
import java.util.Scanner;

class MineSweeper {

    private static final int MIN_SIZE = 5;
    private static final int MAX_SIZE = 15;
    private static final float MIN_MINE_RATIO = 0.1f;
    private static final float MAX_MINE_RATIO = 0.2f;
    private static final int MINE = 1;
    private static final int MINE_NOT_EXIST = 0;
    private static final int MINE_EXIST = 1;
    private static final String EMPTY_SQUARE_SYMBOL = "O";
    private static final String MINE_SQUARE_SYMBOL = "X";

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        int size = readSize(scan);

        int mineCount = readMineCount(scan, size);

        int[][] board = createBoard(size);

        placeMines(board, mineCount);

        countMinesAround(board);

        printBoard(board);
    }

    private static int readSize(Scanner scan) {
        System.out.println("게임보드 크기를 입력하세요 (5~15): ");
        int size = scan.nextInt();

        if (!isSizeValid(size)) {
            System.out.println("입력값이 범위를 벗어났습니다.");
            size = readSize(scan);
        }

        return size;
    }

    private static int readMineCount(Scanner scan, int size) {
        System.out.println("지뢰 개수를 입력하세요 (게임보드 크기의 10~20% 사이): ");
        int mineCount = scan.nextInt();

        if (!isMineCountValid(size, mineCount)) {
            System.out.println("입력값이 범위를 벗어났습니다.");
            mineCount = readMineCount(scan, size);
        }

        return mineCount;
    }

    private static boolean isSizeValid(int size) {
        return size >= MIN_SIZE && size <= MAX_SIZE;
    }

    private static boolean isMineCountValid(int size, int mineCount) {
        return mineCount >= size * MIN_MINE_RATIO && mineCount <= size * MAX_MINE_RATIO;
    }

    private static int[][] createBoard(int size) {
        int[][] board = new int[size][size];
        return board;
    }

    private static void placeMines(int[][] board, int mineCount) {
        Random random = new Random();
        for (int i = 0; i < mineCount; i++) {
            int x = random.nextInt(board.length);
            int y = random.nextInt(board[0].length);
            board[x][y] = MINE;
        }
    }

    private static void countMinesAround(int[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                board[i][j] = countMinesAround(board, i, j);
            }
        }
    }

    private static int countMinesAround(int[][] board, int i, int j) {
        int count = 0;
        for (int x = i - 1; x <= i + 1; x++) {
            for (int y = j - 1; y <= j + 1; y++) {
                if (x < 0) {
                    continue;
                }
                if (x >= board.length) {
                    continue;
                }
                if (y < 0) {
                    continue;
                }
                if (y >= board[0].length) {
                    continue;
                }
                if (board[x][y] == 1) {
                    count++;
                }
            }
        }
        return count;
    }

    private static void printBoard(int[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == MINE_NOT_EXIST) {
                    System.out.print(EMPTY_SQUARE_SYMBOL);
                } else if (board[i][j] == MINE_EXIST) {
                    System.out.print(MINE_SQUARE_SYMBOL);
                } else {
                    System.out.print(board[i][j]);
                }
            }
            System.out.println();
        }
    }
}
