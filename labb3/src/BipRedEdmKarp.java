
import java.util.ArrayList;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

public class BipRedEdmKarp {

    static class Edge {
        int to, rev, cap, flow;

        Edge(int to, int rev, int cap) {
            this.to = to;
            this.rev = rev;
            this.cap = cap;
            this.flow = 0;
        }
    }

    Kattio io;

    int x, y, v, s, t, e;
    ArrayList<int[]> bipEdges = new ArrayList<>();
    ArrayList<int[]> maxFlowEdges = new ArrayList<>();
    ArrayList<Edge>[] graph;
    ArrayList<Integer>[] adj; // grannlista (för restflödesgrafen)
    int totFlow = 0;

    void addEdge(int u, int v, int cap) {
        graph[u].add(new Edge(v, graph[v].size(), cap));
        graph[v].add(new Edge(u, graph[u].size() - 1, 0)); // reverse edge
    }

    void readBipartiteGraph() {
        // Läs antal hörn och kanter
        x = io.getInt(); // antal noder i vänstra mängden
        y = io.getInt(); // antal noder i högra mängden
        e = io.getInt(); // antal kanter

        // Läs in kanterna
        for (int i = 0; i < e; ++i) {
            int a = io.getInt();
            int b = io.getInt();
            int[] edge = { a, b };
            bipEdges.add(edge);
        }
    }

    void buildFlowGraph() {
        s = 1; // källa
        t = x + y + 2; // utlopp
        v = x + y + 2; // totala antalet noder

        graph = new ArrayList[v + 1];
        for (int i = 0; i <= v; i++) {
            graph[i] = new ArrayList<>();
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

    int bfs(int[] parent) {
        Arrays.fill(parent, -1);
        parent[s] = -2; // markerar källa som besökt
        int[] bottleneck = new int[v + 1];
        bottleneck[s] = Integer.MAX_VALUE;

        Queue<Integer> q = new ArrayDeque<>();
        q.add(s);

        while (!q.isEmpty()) {
            int u = q.poll();
            for (Edge e : graph[u]) {
                int next = e.to;
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
        return 0; // hittade ingen stig
    }

    int edmondsKarp() {
        int total = 0;
        int[] parent = new int[v + 1];

        while (true) {
            int bottleneck = bfs(parent);
            if (bottleneck == 0) {
                break;
            }

            // augmentera flödet längs vägen
            int cur = t;
            while (cur != s) {
                int prev = parent[cur];
                for (Edge e : graph[prev]) {
                    if (e.to == cur) {
                        e.flow += bottleneck;
                        graph[cur].get(e.rev).flow -= bottleneck; // bakflöde
                        break;
                    }
                }
                cur = prev;
            }
            total += bottleneck;
        }

        totFlow = total;
        return total;
    }

    void extractMatching() {
        maxFlowEdges.clear();
        for (int[] e : bipEdges) {
            int a = e[0];
            int b = e[1];
            int left = a + 1;
            int right = b + 1;
            for (Edge edge : graph[left]) {
                if (edge.to == right && edge.flow > 0) {
                    maxFlowEdges.add(new int[] { a, b });
                    break;
                }
            }
        }
    }

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

        int maxFlow = edmondsKarp();
        System.err.println("Computed max flow = " + maxFlow);

        extractMatching();

        writeBipMatchSolution();

        io.close();
    }

    public static void main(String[] args) {
        new BipRedEdmKarp();
    }
}
