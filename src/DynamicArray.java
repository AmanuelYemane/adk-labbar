import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
            return new Node(value, null, null, -1);
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

        return new Node(null, root.left, root.right, height);
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
                node = node.right;
            } else {
                node = node.left;
            }
        }
        if (node == null || node.value == null) {
            return 0;
        }
        return node.value;
    }


    public static void main(String[] args) throws IOException {
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

        Stack stack = new Stack(1000);

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while ((line = reader.readLine()) != null) {
            line = line.trim().toLowerCase();

            if (line.equals("unset")) {
                System.out.println("Du skrev unset");
                stack.pop();
            }
            if (line.matches("^get \\d+")) {
                String[] parts = line.split(" ");
                int i = Integer.parseInt(parts[1]);
                //DynamicArray tree = stack.pop();
                tree.get(tree, i);
            }
            if (line.matches("^set \\d+ \\d+")) {
                String[] parts = line.split(" ");
                int i = Integer.parseInt(parts[1]);
                int value = Integer.parseInt(parts[2]);
                //DynamicArray tree = stack.pop();
                tree.set(tree, i, value);
            }

        }
    }
}