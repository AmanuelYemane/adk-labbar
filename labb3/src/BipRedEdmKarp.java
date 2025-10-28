import java.util.ArrayList;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

public class BipRedEdmKarp {
    Kattio io;

    int x, y, v, s, t, e;
    ArrayList<int[]> bipEdges = new ArrayList<>();
    ArrayList<int[]> maxFlowEdges = new ArrayList<>();
    int[][] capacity;
    int[][] flow;
    ArrayList<Integer>[] adj; // grannlista (för restflödesgrafen)
    // ArrayList<int[]> originalEdges = new ArrayList<>();
    int totFlow = 0;

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

        // int eFlow = x + y + e;

        capacity = new int[v + 1][v + 1];
        flow = new int[v + 1][v + 1];
        adj = new ArrayList[v + 1];
        for (int i = 0; i <= v; i++) {
            adj[i] = new ArrayList<>();
        }

        // Källa till vänster
        for (int a = 1; a <= x; a++) {
            int left = a + 1;
            capacity[s][left] = 1;
            adj[s].add(left);
            adj[left].add(s);
        }

        // Vänster till höger
        for (int[] e : bipEdges) {
            int a = e[0];
            int b = e[1];
            int left = a + 1;
            int right = b + 1;
            if (capacity[left][right] == 0) {
                adj[left].add(right);
                adj[right].add(left);
            }
            capacity[left][right] += 1;
        }


        // Höger till utlopp
        for (int b = x + 1; b <= x + y; b++) {  
            int right = b + 1;
            capacity[right][t] = 1;
            adj[right].add(t);
            adj[t].add(right);
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
            for (int next : adj[u]) {
                // restkapacitet
                int residual = capacity[u][next] - flow[u][next];
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
                flow[prev][cur] += bottleneck;
                flow[cur][prev] -= bottleneck; // bakflöde
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
            int f = flow[left][right];
            if (f > 0) {
                maxFlowEdges.add(new int[] { a, b });   
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