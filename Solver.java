// import java.util.ArrayList;
// import java.util.List;

public class Solver extends Game {

    MyPriorityQueue foundNodes = new MyPriorityQueue();
    MyPriorityQueue discoveredNodes = new MyPriorityQueue();

    Solver(byte[][] board) {
        foundNodes.add(new Node(null, board, manhattanDistance(board, winBoard)));
    }

    private byte[][] check(byte[][] givenBoard, int additionX, int additionY) {
        byte[][] newBoard = new byte[size][size];
        for (int y = 0; y < givenBoard.length; y++) {
            for (int x = 0; x < givenBoard.length; x++) {
                newBoard[y][x] = givenBoard[y][x];
            }
        }
        byte[] emptyArea = find(newBoard, (byte) 0);
        if (
            emptyArea[0]+additionY < 0      ||
            emptyArea[0]+additionY >= size  ||
            emptyArea[1]+additionX < 0      ||
            emptyArea[1]+additionX >= size
        ) return null;
        byte help = newBoard[emptyArea[0]][emptyArea[1]];
        newBoard[emptyArea[0]][emptyArea[1]] = newBoard[emptyArea[0]+additionY][emptyArea[1]+additionX];
        newBoard[emptyArea[0]+additionY][emptyArea[1]+additionX] = help;
        return newBoard;
    }

    private int manhattanDistance(byte[][] firstBoard, byte[][] secondBoard) {
        byte[] distA, distB;
        int finalDist, result = 0;
        for (byte i = 1; i < firstBoard.length*firstBoard.length; i++) {
            distA = find(firstBoard, i);
            distB = find(secondBoard, i);
            finalDist = 
                Math.abs((int)(distA[0] - distB[0])) + Math.abs((int)(distA[1]-distB[1]));
            result += finalDist;
        }
        return result;
    }

    private Node discover() {
        Node minNode = foundNodes.poll();
        byte[][] directions = { {-1, 0}, {0, -1}, {0, 1}, {1, 0} };
        byte[][] nextBoard;
        for (int i = 0; i < directions.length; i++) {
            nextBoard = check(minNode.getBoard(), directions[i][1], directions[i][0]);
            if (nextBoard != null &&
                !discoveredNodes.contains(minNode) &&
                !foundNodes.contains(minNode)
            ) {
                foundNodes.add(new Node(
                    minNode, 
                    nextBoard,
                    manhattanDistance(nextBoard, winBoard)
                ));
            }
        }
        discoveredNodes.add(minNode);
        return minNode;
    }

    private void moveToRoot(Node node) {
        int steps = 0;
        Node currNode = node;
        while (currNode.getParent() != null) {
            steps++;
            System.out.println("_-_-_-_-_-_-_");
            printBoard(currNode.getBoard());
            // System.out.println("Heuristic: "+node.getFCost());
            currNode = currNode.getParent();
        }
        System.out.println("_-_-_-_-_-_-_");
        printBoard(currNode.getBoard());
        System.out.println(steps+" moves");
    }

    public boolean isSolution() {
        Node currNode = discover();
        while (!isEqual(currNode.getBoard(), winBoard)) {
            currNode = discover();
            if (discoveredNodes.size() % 1000 == 0)
                System.out.println(discoveredNodes.size());
        }
        System.out.println("Solving. . .");
        moveToRoot(currNode);
        return true;
    }

    public void start() {
        if (isSolution()) {
            System.out.println("Discovered Nodes: "+discoveredNodes.size());
        }
    }

}
