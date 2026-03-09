import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Game {
    
    Solver solver;
    protected byte size;
    protected byte[][] winBoard;
    // private byte[][] startBoard = {
    //     {3, 2, 7},
    //     {5, 0, 4},
    //     {6, 8, 1}
    // };
    private byte[][] startBoard = {
        {9, 8, 0, 2},
        {13, 3, 7, 14},
        {6, 5, 1, 10},
        {4, 15, 11, 12}
    };

    public Game() {
        this(4);
    }

    public Game(int size) {
        this.size = (byte) size;

        initWinBoard();
        initStartBoard();

        // initSimpleStartBoard(150);
    }

    private void initWinBoard() {
        winBoard = new byte[size][size];
        byte current = 1;
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                winBoard[y][x] = (byte) (current % (size*size));
                current++;
            }
        }
    }

    private void initSimpleStartBoard(int moves) {
        startBoard = new byte[size][size];
        Random rand = new Random();
        byte current = 1;
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                startBoard[y][x] = (byte) (current % (size*size));
                current++;
            }
        }
        for (int i = 0; i < moves; i++) {
            makeMove((byte) ((int) rand.nextInt(4)));
        }
    }

    private void initStartBoard() {
        startBoard = new byte[size][size];
        Random rand = new Random();
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < size*size; i++) list.add(i);
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++)
                startBoard[y][x] = (byte) ((int) list.remove(rand.nextInt(list.size())));
        }

        if (!isSolvable(startBoard)) initStartBoard();

    }

    public void start() {
        Scanner scan = new Scanner(System.in);
        String direction;
        System.out.println("Game has been started. Lets Play!");
        System.out.println("Available moves directions: 0-UP 1-RIGHT 2-BOTTOM 3-LEFT 4-SOLUTION 5-EXIT");
        while (!isWin()) {
            printBoard(startBoard);
            System.out.print("Direction: ");
            direction = scan.next();
            try {
                makeMove(Byte.valueOf(direction));
            } catch (NumberFormatException e) {
                System.out.println("Wrong Direction!");
            }
        }
        System.out.println("Congratulations! You find solution!");
        scan.close();
    }

    private String toTwoLetters(byte value) {
        String strVal = String.valueOf(value); 
        return (strVal.length() < 2) ? "0"+strVal : strVal;
    }

    protected void printBoard(byte[][] givenBorad) {
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                System.out.print(
                    (givenBorad[y][x] == 0) ? "   " : toTwoLetters(givenBorad[y][x])+" "
                );
            }
            System.out.println("");
        }
    }

    protected byte[] find(byte[][] givenBoard, byte value) {
        for (byte y = 0; y < givenBoard.length; y++) {
            for (byte x = 0; x < givenBoard.length; x++) {
                if (givenBoard[y][x] == value) return new byte[] {y, x};
            }
        }
        return null;
    }

    private boolean isWin() {
        return isEqual(startBoard, winBoard);
    }

    protected boolean isEqual(byte[][] firstBoard, byte[][] secondBoard) {
        for (int i = 0; i < firstBoard.length; i++) {
            if (!Arrays.equals(firstBoard[i], secondBoard[i])) return false;
        }
        return true;
    }

    private void move(int additionX, int additionY) {
        byte[] emptyArea = find(startBoard, (byte) 0);
        if (
            emptyArea[0]+additionY < 0      ||
            emptyArea[0]+additionY >= size  ||
            emptyArea[1]+additionX < 0      ||
            emptyArea[1]+additionX >= size
        ) return;
        byte help = startBoard[emptyArea[0]][emptyArea[1]];
        startBoard[emptyArea[0]][emptyArea[1]] = startBoard[emptyArea[0]+additionY][emptyArea[1]+additionX];
        startBoard[emptyArea[0]+additionY][emptyArea[1]+additionX] = help;
    }

    public void makeMove(byte direction) {
        switch(direction) {
            case 0:
                move(0, -1);
                // System.out.println("UP");
                break;
            case 1:
                move(1, 0);
                // System.out.println("RIGHT");
                break;
            case 2:
                move(0, 1);
                // System.out.println("BOTTOM");
                break;
            case 3:
                move(-1, 0);
                // System.out.println("LEFT");
                break;
            case 4:
                solver = new Solver(startBoard);
                solver.start();
                System.exit(0);
                break;
            case 5:
                System.exit(0);
                break;
        }
    }

    // GeeksForGeeks
    private int getInvCount(int[] arr, int size) {
        int inv_count = 0;
        for (int i = 0; i < size * size - 1; i++) {
            for (int j = i + 1; j < size * size; j++) {
                // count pairs(arr[i], arr[j]) such that
                // i < j but arr[i] > arr[j]
                if (arr[j] != 0 && arr[i] != 0
                    && arr[i] > arr[j])
                    inv_count++;
            }
        }
        return inv_count;
    }

    private int findXPosition(byte[][] board, int size) {
        // start from bottom-right corner of matrix
        for (int i = size - 1; i >= 0; i--)
            for (int j = size - 1; j >= 0; j--)
                if (board[i][j] == 0) return size - i;
        return -1;
    }

    private boolean isSolvable(byte[][] board) {
        // Count inversions in given puzzle
        int[] arr = new int[board.length * board.length];
        int k = 0;
        for (int i = 0; i < board.length; i++)
            for (int j = 0; j < board.length; j++)
                arr[k++] = board[i][j];
        int invCount = getInvCount(arr, board.length);

        if (board.length % 2 == 1) return invCount % 2 == 0;

        int pos = findXPosition(board, board.length);
        if (pos % 2 == 1) return invCount % 2 == 0;
        return invCount % 2 == 1;
    }
    // This code is contributed by rutikbhosale.

}
