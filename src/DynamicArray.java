import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class DynamicArray {
    Node root;

    public DynamicArray(Node root) {
        this.root = root;
    }
    
    public DynamicArray newarray() {
        return new DynamicArray(new Node(null, null, null, 0));
    }

    public DynamicArray set(DynamicArray a, int i, Integer value) {
        Node newRoot = updateNode(a.root, i, value, a.root.height);
        return new DynamicArray(newRoot);
    }

    private static Node updateNode(Node root, int i, Integer value, int height) {
        int bit = height;

        if (bit <= 0) { // måste ändras på något sätt (det här gör just nu att allt får samma värde)
            return new Node(value, null, null, 0); // New leaf is created
        }
        
        if (root == null) {
            return new Node(null, null, null, height);
        }
        
        boolean updateHeight = (i >>> height) > 0;
        boolean bitIsOne = ((i >>> height) & 1) == 1;

        // If index doesn't fit current height, grow tree upwards
        if (updateHeight) {
            // create new root one level higher
            Node newRoot = new Node(null, root, null, height + 1);
            return updateNode(newRoot.right, i, value, height);
        } else if (bitIsOne) {
            return new Node(root.value, root.left, updateNode(root.right, i, value, height - 1), height);
        } else {
            return new Node(root.value, updateNode(root.left, i, value, height - 1), root.right, height);
        }
    }

    public int get(DynamicArray a, int i) {
        Node node = a.root;

        if (node == null) {
            throw new IllegalArgumentException("Empty array.");
        }

        for (int bit = node.height; bit >= 0; bit--) {
            if (node == null) {
                throw new IllegalArgumentException("Index " + i + " not found.");
            }
            if (((i >>> bit) & 1) == 1) {
                if (node.isLeaf()) {
                    return node.value;
                }
                node = node.right;
            } else {
                if (node.isLeaf()) {
                    return node.value;
                }
                node = node.left;
            }
        }
        if (node == null) {
            throw new IllegalArgumentException("Index " + i + " not found.");
        }
        if (node.value == null) {
            throw new IllegalArgumentException("Index " + i + " not found.");
        }
        return node.value;
        }

    public static void main(String[] args) {
        DynamicArray tree = new DynamicArray(new Node(null, null, null, 0));
        tree = tree.set(tree, 2, 5);
        System.out.println(tree.get(tree, 2)); // 5
        System.out.println(tree.get(tree, 0)); 


        tree = tree.set(tree, 7, 42);
        System.out.println(tree.get(tree, 7)); // 42

        // BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        // String input = reader.lines().collect(Collectors.joining("\n"));
    }
}