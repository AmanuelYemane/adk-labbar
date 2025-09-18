import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class DynamicArray {
    private Node root;
    private static Stack stack = new Stack(100000);

    public DynamicArray(Node root) {
        this.root = root;
    }
    
    public static DynamicArray newarray() {
        return new DynamicArray(new Node(null, null, null, -1));
    }

    public DynamicArray set(DynamicArray a, int i, Integer value) {
        // Spara den tidigare versionen av arrayen på stacken
        stack.push(a);
        
        Node root = a.root;
        
        // Avgör hur högt trädet behöver vara genom att undersöka binära representationen av i
        int requiredHeight = 31 - Integer.numberOfLeadingZeros(i);
        // Lägg till en ny rot där den gamla roten blir vänsterdelträdet till den nya roten om trädet behöver bli högre
        while (root.height < requiredHeight) {
            root = new Node(null, root, null, root.height + 1);
        }

        // Lägg till value på givet index genom att kalla på den rekursiva funktionen updateNode()
        Node newRoot = updateNode(root, i, value, root.height);
        // Skapa en ny version av arrayen där newRoot är nya roten och value är inlagt på index i
        return new DynamicArray(newRoot);
    }

    private static Node updateNode(Node root, int i, Integer value, int height) {
        // Basfall. Om höjden på trädet är mindre än 0 (-1) i den nuvarande noden är noden det löv vi vill ge värdet value
        if (height < 0) {
            return new Node(value, null, null, height);
        }

        // Initialisera null-noder
        if (root == null) {
            root = new Node(null, null, null, height);
        }

        // Kolla om bit på plats height i i är 1 eller 0 för att avgöra om vi ska gå vänster eller höger nedåt i trädet
        boolean bitIsOne = ((i >>> height) & 1) == 1;

        // Vi vill inte ändra i det gamla trädet och skapar därför kopior av de gamla delträden
        Node newRight = root.right;
        Node newLeft = root.left;

        // Kör rekursivt på antingen höger eller vänster delträd
        if (bitIsOne) {
            newRight = updateNode(root.right, i, value, height - 1);
        } else {
            newLeft = updateNode(root.left, i, value, height - 1);
        }

        // Returnera den nuvarande noden (lägg till i trädet)
        return new Node(null, newLeft, newRight, height);
    }

    public int get(DynamicArray a, int i) {
        Node node = a.root;

        // Se till att index utanför arrayens nuvarande kapacitet returnerar 0
        int requiredHeight = (31 - Integer.numberOfLeadingZeros(i));
        if (requiredHeight > node.height) {
            return 0;
        }

        // Gå igenom trädet och sluta när height blir -1, det vill säga vi är vid ett löv
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
        // Skapa en instans av den beständiga arrayen
        DynamicArray tree = newarray();

        // Läs input fron terminalen
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while ((line = reader.readLine()) != null) {
            line = line.trim().toLowerCase();
            
            // Unset
            if (line.equals("unset")) {
                tree = stack.pop(tree);
            }

            // Get
            if (line.matches("^get \\d+")) {
                String[] parts = line.split(" ");
                int i = Integer.parseInt(parts[1]);
                int value = tree.get(tree, i);
                System.out.println(value);
            }

            // Set
            if (line.matches("^set \\d+ \\d+")) {
                String[] parts = line.split(" ");
                int i = Integer.parseInt(parts[1]);
                int value = Integer.parseInt(parts[2]);
                tree = tree.set(tree, i, value);
            }
        }

        // test 1

        // set 5 16
        // get 7
        // get 5
        // set 10 21
        // get 10
        // unset
        // get 10
        // unset
        // get 5


        // test 2

        // set 0 1
        // get 0
        // get 1
    }
}