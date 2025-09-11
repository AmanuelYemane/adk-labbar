import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class DynamicArray {
    Node root;

    public DynamicArray(Node root) {
        this.root = root;
    }
    
    public static DynamicArray newarray() {
        return new DynamicArray(new Node(null, null, null, 0));
    }

    public DynamicArray set(DynamicArray a, int i, Integer value) {
        int requiredHeight = 31 - Integer.numberOfLeadingZeros(i);
        Node root = a.root;

        while (root.height < requiredHeight) {
            root = new Node(null, root, null, root.height + 1);
        }

        Node newRoot = updateNode(root, i, value, root.height);
        return new DynamicArray(newRoot);
    }

    private static Node updateNode(Node root, int i, Integer value, int height) {
        if (height < 0) {
            if (root == null) {
                return new Node(value, null, null, -1);
            } else {
                root.value = value;
                return root;
            }
        }

        if (root == null) {
            root = new Node(null, null, null, height);
        }

        boolean bitIsOne = ((i >>> height) & 1) == 1;

        if (bitIsOne) {
            root.right = updateNode(root.right, i, value, height - 1);
        } else {
            root.left = updateNode(root.left, i, value, height - 1);
        }

        return root;
    }

    public int get(DynamicArray a, int i) {
        Node node = a.root;
        
        int requiredHeight = 0;
        if (i != 0) {
            requiredHeight = (31 - Integer.numberOfLeadingZeros(i));
        }
        if (requiredHeight > node.height) {
            return 0;
        }

        for (int bit = node.height; bit >= 0; bit--) {
            if (node == null) {
                return 0;
            }
            if (((i >>> bit) & 1) == 1) {
                // if (node.isLeaf()) {
                //     return node.value;
                // }
                node = node.right;
            } else {
                // if (node.isLeaf()) {
                //     return node.value;
                // }
                node = node.left;
            }
        }
        if (node == null || node.value == null) {
            return 0;
        }
        return node.value;
    }


    public static void main(String[] args) {
        DynamicArray tree = newarray();

        tree = tree.set(tree, 0, 5);
        System.out.println(tree.get(tree, 0));
        System.out.println(tree.root.height);
        System.out.println(tree.get(tree, 2));
        System.out.println(tree.get(tree, 10));

        tree = tree.set(tree, 2, 5);
        System.out.println(tree.get(tree, 2));
        System.out.println(tree.get(tree, 1));
        System.out.println(tree.get(tree, 10));
        
        tree = tree.set(tree, 7, 10);
        System.out.println(tree.get(tree, 2));
        System.out.println(tree.get(tree, 7));

        tree = tree.set(tree, 7, 42);
        System.out.println(tree.get(tree, 7));
        
        tree = tree.set(tree, 200, 13);
        System.out.println(tree.get(tree, 200));

        // BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        // String input = reader.lines().collect(Collectors.joining("\n"));
    }
}