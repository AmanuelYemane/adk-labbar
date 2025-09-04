import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Collectors;
import java.util.Stack;

public class DynamicArray {

    private Tree tree;
    private Stack s = new Stack();

    public DynamicArray() {
        tree = null;
    }
    
    public DynamicArray newarray() {
        DynamicArray newArray = new DynamicArray();
        return newArray;
    }

    public void set(DynamicArray a, int i, int value) {
        // push till stakcen
        // ändra
        // - pekar rätt, tidigare ver
        // s.push(a);
    }

    public int get(DynamicArray a, int i) {
        return 0;
    }

    private int getHeight() {
        return 0;
    }

    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String input = reader.lines().collect(Collectors.joining("\n"));
    }
}
