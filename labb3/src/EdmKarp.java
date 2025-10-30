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

	/* Läser flödesgrafen från standard input */
	void readFlowGraph() {
		v = io.getInt();
		s = io.getInt();
		t = io.getInt();
		e = io.getInt();

		// Skapar matris för kantkapaciteter och flöden
		capacity = new int[v + 1][v + 1];
		flow = new int[v + 1][v + 1];

		// Skapar grannlista för flödesgrafen, array av typ ArrayList
		adj = new ArrayList[v + 1];
		for (int i = 0; i <= v; i++) {
			adj[i] = new ArrayList<>();
		}

		// Läser in kanterna
		for (int i = 0; i < e; i++) {
			int a = io.getInt();
			int b = io.getInt();
			int c = io.getInt();

			// Spara kanterna
			originalEdges.add(new int[] { a, b, c });

			// Kant a till b har kapacitet c
			capacity[a][b] = c;

			// Går åt båda hållen
			adj[a].add(b);
			adj[b].add(a);
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
				flow[prev][cur] += bottleneck;
				flow[cur][prev] -= bottleneck; // Bakflöde
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
			for (int next : adj[u]) {
				// Restkapacitet
				int residual = capacity[u][next] - flow[u][next];

				// Kolla om nästa nod är besökt och om kapacitet kvarstår
				if (parent[next] == -1 && residual > 0) {
					// Den nod vi varit på blir parent till nästa nod
					parent[next] = u;

					// Uppdatera flaskhals för nästa nod
					bottleneck[next] = Math.min(bottleneck[u], residual);

					// Returnera flaskhalsen om vi når t
					if (next == t) {
						return bottleneck[t];
					}

					// Lägg till i kön
					q.add(next);
				}
			}
		}
		return 0; // Hittade ingen stig
	}

	/* Skriver lösningen till standard output */
	void writeFlowSolution() {
		io.println(v);
		io.println(s + " " + t);
		io.println(totFlow);

		// Vi vill bara ha de kanter som vi har använt, det vill säga där flödet är större än 0
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

		io.println(edgesWithFlow.size()); // Antal kanter med flöde > 0

		for (int[] edge : edgesWithFlow) {
			io.println(edge[0] + " " + edge[1] + " " + edge[2]);
		}
		io.flush();
	}

	EdmKarp() {
		io = new Kattio(System.in, System.out);

		readFlowGraph();

		edmondsKarp();

		writeFlowSolution();

		io.close();
	}

	public static void main(String[] args) {
		new EdmKarp();
	}
}
