import java.util.ArrayList;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

public class EdmKarp {
    Kattio io;

    int v, s, t, e;
    int[][] capacity;
    int[][] flow;
    ArrayList<Integer>[] adj; // grannlista (för restflödesgrafen)
    ArrayList<int[]> originalEdges = new ArrayList<>();
    int totFlow = 0;

    void readFlowGraph() {
        v = io.getInt();
        s = io.getInt();
        t = io.getInt();
        e = io.getInt();

        capacity = new int[v + 1][v + 1];
        flow = new int[v + 1][v + 1];
        adj = new ArrayList[v + 1];
        for (int i = 0; i <= v; i++) {
			adj[i] = new ArrayList<>();
		}

        for (int i = 0; i < e; i++) {
            int a = io.getInt();
            int b = io.getInt();
            int c = io.getInt();

			
            originalEdges.add(new int[]{a, b, c});

            capacity[a][b] = c;

			// Går åt båda  hållen
			adj[a].add(b);
			adj[b].add(a);

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

    void writeFlowSolution() {
        io.println(v);
        io.println(s + " " + t);
        io.println(totFlow);

		ArrayList<int[]> edgesWithFlow = new ArrayList<>(); 
		for (int[] edge : originalEdges) {
			int u = edge[0];
			int w = edge[1];
			int f = flow[u][w];

			int[] newEdge = { u, w, f };
			if (f > 0) {
				edgesWithFlow.add(newEdge);
			}
		}
    
    	io.println(edgesWithFlow.size()); // antal kanter med flöde > 0
    
		for (int[] edge : edgesWithFlow) {
			io.println(edge[0] + " " + edge[1] + " " + edge[2]);
		}
			io.flush();
		}

    EdmKarp() {
        io = new Kattio(System.in, System.out);

        readFlowGraph();

        int maxFlow = edmondsKarp();
        System.err.println("Computed max flow = " + maxFlow);

        writeFlowSolution();

        io.close();
    }

    public static void main(String[] args) {
        new EdmKarp();
    }
}
