public class Stack {
    private int maxSize;
    private DynamicArray[] stackArray;
    private int top;

    public Stack(int size) {
        maxSize = size;
        stackArray = new DynamicArray[maxSize];
        top = -1;
    }

    // Push ett DynamicArray-objekt
    public void push(DynamicArray array) {
        stackArray[++top] = array;
    }

    // Pop ett DynamicArray-objekt
    public DynamicArray pop(DynamicArray tree) {
        if (isEmpty()) {
            return tree;
        }
        return stackArray[top--];
    }

    // Titta på toppen utan att poppa
    public DynamicArray peek() {
        return stackArray[top];
    }

    // Kolla om stacken är tom
    public boolean isEmpty() {
        return top == -1;
    }
}
