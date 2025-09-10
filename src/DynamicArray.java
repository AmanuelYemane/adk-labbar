import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class DynamicArray {
    Node root;

    public DynamicArray(Node root) {
        this.root = new Node(null, null, null, 0);
    }
    
    public DynamicArray newarray() {
        DynamicArray newArray = new DynamicArray(root);
        return newArray;
    }

    // public static DynamicArray newarray() {
    //     return new DynamicArray(null);
    // }
    

    public DynamicArray set(DynamicArray a, int i, int value) {
        Node newRoot = updateNode(a.root, i, value, a.root.height, a.root.height);
        return new DynamicArray(newRoot);
    }

    private static Node updateNode(Node root, int i, int value, int height, int bit) {
        if (bit < 0) {
            return new Node(value, null, null, height); // New leaf is created
        }
        
        boolean updateHeight = (Math.pow(2, height) < i);
        boolean bitIsOne = ((i >>> bit) & 1) == 1; // Shifts MSB to the LSB and masks that bit

        if (root == null && bit == 0) {
            return new Node(value, null, null, height); // skapar löv med rätt värde
        }

        if (root == null){
          return new Node(null, null, null, height); // Creates path to index if there isn't an existing one
        } // ATT HA KOLL PÅ: Ett nytt löv kan hamna på en lägre nivå än de löv som finns då de inte updateras till en lägre nivå

        if (updateHeight) {
            Node newRoot = new Node(null, root, null, height + 1);
            return newRoot;
            Node(newRoot.value, root, updateNode(newRoot.right, i, value, height + 1, bit), height + 1);
        } else if (bitIsOne) {
            return new Node(root.value, root.left, updateNode(root.right, i, value, height, bit - 1)); // Keeps the left side, and updates the right side
        } else {
            return new Node(root.value, updateNode(root.left, i, value, height, bit - 1), root.right);
        }
    }

    public int get(DynamicArray a, int i) {
        Node node = a.root;

        if (node == null) {
            throw new IllegalArgumentException("Index " + i + " finns inte i arrayen.");
        }

        for (int bit = node.height; bit >= 0; bit--) {
            if (((i >>> bit) & 1) == 1){
                node = node.right;
            } else {
                node = node.left;
            }
        }   
        return node.value;
    }

    public static void main(String[] args) {

        DynamicArray tree = new DynamicArray(null);
        DynamicArray tree2 = tree.set(tree, 0, 5);
        tree2.get(tree2, 0);

        // Stack treeStack;

        // BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        // String input = reader.lines().collect(Collectors.joining("\n"));
    }
}
