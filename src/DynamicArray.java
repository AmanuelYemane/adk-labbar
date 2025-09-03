import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Collectors;
import java.util.Stack;

public class DynamicArray {

    Tree tree;

    public DynamicArray() {
        tree = null;
    }
    
    public DynamicArray newarray() {
        DynamicArray newArray = new DynamicArray();
        return newArray;
    }

    public void set(DynamicArray a, int i, int value) {
        
    }

    public int get(DynamicArray a, int i) {
        return 0;
    }

    private int getHeight() {
        return 0;
    }

    public static void main(String[] args) {
        while (true) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String input = reader.lines().collect(Collectors.joining("\n"));

            Stack<DynamicArray> s = new Stack<>();
        }
    }
}
