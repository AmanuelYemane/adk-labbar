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
}