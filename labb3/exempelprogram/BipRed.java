/**
 * Exempel på in- och utdatahantering för maxflödeslabben i kursen
 * ADK.
 *
 * Använder Kattio.java för in- och utläsning.
 * Se http://kattis.csc.kth.se/doc/javaio
 *
 * @author: Per Austrin
 */

public class BipRed {
	Kattio io;

	int x, y, e;
	int[] a, b;

	void readBipartiteGraph() {
		// Läs antal hörn och kanter
		x = io.getInt(); // antal noder i vänstra mängden
		y = io.getInt(); // antal noder i högra mängden
		e = io.getInt(); // antal kanter

		a = new int[e];
		b = new int[e];

		// Läs in kanterna
		for (int i = 0; i < e; ++i) {
			a[i] = io.getInt();
			b[i] = io.getInt();
		}
	}

	void writeFlowGraph() {
		int s = 1; // källa
		int t = x + y + 2; // sänka
		int v = x + y + 2; // totala antalet noder

		int totEdges = x + y + e;

		// Skriv ut antal hörn och kanter samt källa och sänka
		io.println(v);
		io.println(s + " " + t);
		io.println(totEdges);

		// Kant från källa till a (vänstermängden)
		for (int i = 1; i <= x; i++) {
			io.println(s + " " + i + 1 + " 1");
		}

		// Kan från b till sänka
		for (int i = 1; i <= y; i++) {
			io.println((x + i + 1) + " " + t + " 1");
		}

		for (int i = 0; i < e; i++) {
			int left = a[i];
			int right = x + b[i];
			// Kant från a till b med kapacitet c
			io.println(left + " " + right + " 1");
		}
		// Var noggrann med att flusha utdata när flödesgrafen skrivits ut!
		io.flush();

		// Debugutskrift
		System.err.println("Skickade iväg flödesgrafen");
	}

	void readMaxFlowSolution() {
		// Läs in antal hörn, kanter, källa, sänka, och totalt flöde
		// (Antal hörn, källa och sänka borde vara samma som vi i grafen vi
		// skickade iväg)
		int v = io.getInt();
		int s = io.getInt();
		int t = io.getInt();
		int totflow = io.getInt();
		int e = io.getInt();

		for (int i = 0; i < e; ++i) {
			// Flöde f från a till b
			int a = io.getInt();
			int b = io.getInt();
			int f = io.getInt();
		}
	}

	void writeBipMatchSolution() {
		int x = 17, y = 4711, maxMatch = 0;

		// Skriv ut antal hörn och storleken på matchningen
		io.println(x + " " + y);
		io.println(maxMatch);

		for (int i = 0; i < maxMatch; ++i) {
			int a = 5, b = 2323;
			// Kant mellan a och b ingår i vår matchningslösning
			io.println(a + " " + b);
		}

	}

	BipRed() {
		io = new Kattio(System.in, System.out);

		readBipartiteGraph();

		writeFlowGraph();

		readMaxFlowSolution();

		writeBipMatchSolution();

		// debugutskrift
		System.err.println("Bipred avslutar\n");

		// Kom ihåg att stänga ner Kattio-klassen
		io.close();
	}

	public static void main(String args[]) {
		new BipRed();
	}
}
