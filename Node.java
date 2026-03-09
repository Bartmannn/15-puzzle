
public class Node {

    private int hCost = 0;
    private int gCost = 0;
    private int fCost = 0;
    private byte[][] board;
    private Node parent;

    Node(Node parent, byte[][] givenBoard, int hCost) {
        this.parent = parent;
        this.board = new byte[givenBoard.length][givenBoard.length];
        for (int y = 0; y < givenBoard.length; y++) {
            for (int x = 0; x < givenBoard.length; x++) {
                this.board[y][x] = givenBoard[y][x];
            }
        }
        this.hCost = hCost;
        if (parent != null) this.gCost = parent.getGCost() + 1;
        this.fCost = hCost + gCost;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public boolean isTheSame(byte[][] diffBoard) {
        for (int y = 0; y < board.length; y++) {
            for (int x = 0; x < board.length; x++) {
                if (board[y][x] != diffBoard[y][x]) return false;
            }
        }
        return true;
    }

    public byte[][] getBoard() {
        return board;
    }

    public Node getParent() {
        return parent;
    }

    public int getGCost() {
        return gCost;
    }

    public int getHCost() {
        return hCost;
    }

    public int getFCost() {
        return fCost;
    }

    public void update(Node fromNode) {
        this.parent = fromNode;
        this.gCost = parent.gCost + 1;
        this.fCost = this.hCost + this.gCost;
    }

}
