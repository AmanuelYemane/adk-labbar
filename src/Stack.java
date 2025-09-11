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
        System.out.println("A new version has been pushed");
    }

    // Pop ett DynamicArray-objekt
    public DynamicArray pop() {
        if (isEmpty()) {
            System.out.println("There are no Dynamic arrays in this stack");
            return null;
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
