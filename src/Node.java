public class Node {
    
    Node left, right;
    int value;

    public Node(Node left, Node right, int value) {
        this.left = left;
        this.right = right;
        this.value = value;
    }
    
    public boolean isLeaf() {
        return left == null && right == null;
    }
    
}
