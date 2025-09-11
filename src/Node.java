class Node {
    Integer value;
    Node left, right;
    int height;

    Node(Integer value, Node left, Node right, int height) {
        this.value = value;
        this.left = left;
        this.right = right;
        this.height = height;
    }

    public boolean isLeaf() {
        return height == -1;
    }

    public int getValue() {
        return value;
    }

    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public void setRight(Node right) {
        this.right = right;
    }
}