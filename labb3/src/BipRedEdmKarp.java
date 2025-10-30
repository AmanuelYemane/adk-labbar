
import java.util.ArrayList;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

public class BipRedEdmKarp {

    /* Skapar klass för kanterna */
    static class Edge {
        int to, rev, cap, flow;

        Edge(int to, int rev, int cap) {
            this.to = to;
            this.rev = rev; // Index till den motsvarande bakåtkanten
            this.cap = cap;
            this.flow = 0;
        }
    }

    Kattio io;

    int x, y, v, s, t, e;
    ArrayList<int[]> bipEdges = new ArrayList<>();
    ArrayList<int[]> maxFlowEdges = new ArrayList<>();
    ArrayList<Edge>[] flowGraph; // Restflödesgrafen
    int totFlow = 0;

    void addEdge(int u, int v, int cap) {
        flowGraph[u].add(new Edge(v, flowGraph[v].size(), cap)); // rev = size eftersom det är på det indexet vi kommer
                                                                 // lägga till bakåtkanten på nästa rad
        flowGraph[v].add(new Edge(u, flowGraph[u].size() - 1, 0)); // Bakåtkant, rev = size-1 eftersom vi har lagt till
                                                                   // den på raden innan och det är 0-indexerat
    }

    void readBipartiteGraph() {
        // Läs antal hörn och kanter
        x = io.getInt(); // Antal noder i vänstra mängden
        y = io.getInt(); // Antal noder i högra mängden
        e = io.getInt(); // Antal kanter

        // Läs in kanterna
        for (int i = 0; i < e; ++i) {
            int a = io.getInt();
            int b = io.getInt();
            int[] edge = { a, b };
            bipEdges.add(edge);
        }
    }

    void buildFlowGraph() {
        s = 1; // Källa
        t = x + y + 2; // Utlopp
        v = x + y + 2; // Totala antalet noder

        flowGraph = new ArrayList[v + 1];
        for (int i = 0; i <= v; i++) {
            flowGraph[i] = new ArrayList<>();
        }

        // Källa till vänster
        for (int a = 1; a <= x; a++) {
            int left = a + 1;
            addEdge(s, left, 1);
        }

        // Vänster till höger
        for (int[] edge : bipEdges) {
            int a = edge[0];
            int b = edge[1];
            int left = a + 1;
            int right = b + 1;
            addEdge(left, right, 1);
        }

        // Höger till utlopp
        for (int b = x + 1; b <= x + y; b++) {
            int right = b + 1;
            addEdge(right, t, 1);
        }

    }

    /* Edmonds-Karps algoritm */
    void edmondsKarp() {
        int total = 0;
        int[] parent = new int[v + 1];

        while (true) {
            // Hitta stig från s till t med bfs
            int bottleneck = bfs(parent);

            // Om flaskhalsen är 0 har vi undersökt alla stigar och avbryter algoritmen
            if (bottleneck == 0) {
                break;
            }

            // Augmentera flödet längs stigen: gå igenom den stig vi tagit, minska flödet
			// för de kanter vi tagit, öka för kanterna åt motsatt håll
            int cur = t;
            while (cur != s) {
                int prev = parent[cur];

                // Gå igenom alla kanter till nuvarande noden
                for (Edge edge : flowGraph[prev]) {
                    // Om kanten går till nuvarande noden, ändra flödet
                    if (edge.to == cur) {
                        edge.flow += bottleneck;

                        // Bakflöde, ifall vi vill utesluta kanten från matchningen
                        flowGraph[cur].get(edge.rev).flow -= bottleneck;
                        break;
                    }
                }
                cur = prev;
            }

            // Lägg till flaskhalsen till totala flödet
            total += bottleneck;
        }

        totFlow = total;
    }

    /* Utför breddenförstsökning för att hitta stig från s till t */
    int bfs(int[] parent) {
        Arrays.fill(parent, -1);
        parent[s] = -2; // Markerar källa som besökt
        int[] bottleneck = new int[v + 1];
        bottleneck[s] = Integer.MAX_VALUE;

        Queue<Integer> q = new ArrayDeque<>();
        q.add(s);

        while (!q.isEmpty()) {
            int u = q.poll();
            for (Edge e : flowGraph[u]) {
                int next = e.to;

                // Restkapacitet
                int residual = e.cap - e.flow;

                if (parent[next] == -1 && residual > 0) {
                    parent[next] = u;
                    bottleneck[next] = Math.min(bottleneck[u], residual);
                    if (next == t) {
                        return bottleneck[t];
                    }
                    q.add(next);
                }
            }
        }
        return 0; // Hittade ingen stig
    }

    /* Lägger till alla kanter i maximala matchningen */
    void extractMatching() {
        maxFlowEdges.clear();

        // Gå igenom varje kant i bapartita grafen
        for (int[] e : bipEdges) {
            int a = e[0];
            int b = e[1];
            int left = a + 1;
            int right = b + 1;

            // Lägg till kanten i maximala matchningen om kanten har flöde
            for (Edge edge : flowGraph[left]) {
                if (edge.to == right && edge.flow > 0) {
                    maxFlowEdges.add(new int[] { a, b });
                    break;
                }
            }
        }
    }

    /* Skriver matchningen till standard output */
    void writeBipMatchSolution() {
        // Skriv ut antal hörn och storleken på matchningen
        io.println(x + " " + y);
        io.println(totFlow);

        for (int[] edge : maxFlowEdges) {
            int a = edge[0];
            int b = edge[1];
            io.println(a + " " + b);
        }

        io.flush();
    }

    BipRedEdmKarp() {
        io = new Kattio(System.in, System.out);

        readBipartiteGraph();

        buildFlowGraph();

        edmondsKarp();

        extractMatching();

        writeBipMatchSolution();

        io.close();
    }

    public static void main(String[] args) {
        new BipRedEdmKarp();
    }
}
