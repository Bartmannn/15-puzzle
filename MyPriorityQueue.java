import java.util.Arrays;
import java.util.PriorityQueue;

public class MyPriorityQueue extends PriorityQueue<Node> {

    MyPriorityQueue() {
        super(new MyComparator());
    }

    private boolean isEqual(byte[][] board1, byte[][] board2) {
        for (int i = 0; i < board1.length; i++) {
            if (!Arrays.equals(board1[i], board2[i])) return false;
        }
        return true;
    }

    @Override
    public boolean contains(Object obj) {
        if (!(obj instanceof Node)) {
            return false;
        }
        Node node = (Node) obj;
        Node[] elements = super.toArray(new Node[0]);
        
        for (int i = 0; i < elements.length; i++) {
            if (elements[i].getFCost() > node.getFCost()) break;
            if (elements[i].getHCost() != node.getHCost()) continue;
            if (isEqual(elements[i].getBoard(), node.getBoard())) 
                return true;
        }

        return false;
    }

}
