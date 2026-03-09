import java.util.Comparator;

public class MyComparator implements Comparator<Node> {

        @Override
        public int compare(Node node1, Node node2) {
            if (node1.getFCost() > node2.getFCost()) {
                return 1;
            } else if (node1.getFCost() == node2.getFCost()) {
                if (node1.getHCost() > node2.getHCost()) {
                    return 1;
                } else if (node1.getHCost() < node2.getHCost()) {
                    return -1;
                }
                return 0;
            } else {
                return -1;
            }
            
        }

    }