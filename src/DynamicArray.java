import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class DynamicArray {
    Node root;

    public DynamicArray() {
        this.root = null;
    }

    private DynamicArray(Node root){
        this.root = root;
    }
    
    public DynamicArray newarray() {
        DynamicArray newArray = new DynamicArray();
        return newArray;
    }

    public DynamicArray set(int i, int value) {
        Node newRoot = updateNode(root, i, value, 31);
        return new DynamicArray(newRoot);
    }

    private static Node updateNode(Node node, int i, int value, int bit) {
        boolean bitIsOne = ((i >>> bit) & 1) == 1; // Shifts MSB to the LSB and masks that bit
        if (bit < 0) {
            return new Node(value, null, null); // New leaf is created
        }
        if (node == null){
            return new Node(null, null, null); // Creates path to index if there isn't an existing one
        } 
        if (bitIsOne) {
            return new Node(node.value, node.left, updateNode(node.right, i, value, bit- 1)); // Keeps the left side, and updates the right side
        } else {
            return new Node(node.value, updateNode(node.left, i, value, bit - 1), node.right);
        }
    }

    public int get(DynamicArray a, int i) {
        Node node=a.root;
        for (int bit = 31; bit >= 0; bit--) {
            if (((i >>> bit) & 1) == 1){
                node=node.right;
            } else {
                node= node.left;
            }
        }   return node.value;
    }

    public static void main(String[] args) {
        Stack treeStack;

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String input = reader.lines().collect(Collectors.joining("\n"));
    }
}
