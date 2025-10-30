
/**
 * Exempel på in- och utdatahantering för maxflödeslabben i kursen
 * ADK.
 *
 * Använder Kattio.java för in- och utläsning.
 * Se http://kattis.csc.kth.se/doc/javaio
 *
 * @author: Per Austrin
 */

import java.util.ArrayList;

public class BipRed {
	Kattio io;

	int x, y, e, s, t, v;

	ArrayList<int[]> bipEdges = new ArrayList<>();
	ArrayList<int[]> maxFlowEdges = new ArrayList<>();
	int totFlow;

	/* Läser den bipartita grafen från standard input */
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

	/* Skapar flödesgrafen och skriver till standard output */
	void writeFlowGraph() {
		s = 1; // källa
		t = x + y + 2; // utlopp
		v = x + y + 2; // totala antalet noder

		int eFlow = x + y + e;

		// Skriv ut antal hörn och kanter samt källa och utlopp
		io.println(v);
		io.println(s + " " + t);
		io.println(eFlow);

		// Kant från källa till a (vänstermängden)
		for (int i = 1; i <= x; i++) {
			io.println(s + " " + (i + 1) + " 1");
		}

		for (int i = 0; i < e; i++) {
			int left = bipEdges.get(i)[0] + 1;
			int right = bipEdges.get(i)[1] + 1;
			// Kant från a till b med kapacitet c
			io.println(left + " " + right + " 1");
		}
		
		// Kant från b till utlopp
		for (int i = 1; i <= y; i++) {
			io.println((x + i + 1) + " " + t + " 1");
		}

		// Var noggrann med att flusha utdata när flödesgrafen skrivits ut!
		io.flush();

		// Debugutskrift
		System.err.println("Skickade iväg flödesgrafen");
	}

	/* Läser svarta lådaas lösning till flödesproblemet från standard input */
	void readMaxFlowSolution() {
		// Läs in antal hörn, kanter, källa, utlopp, och totalt flöde
		// (Antal hörn, källa och utlopp borde vara samma som vi i grafen vi
		// skickade iväg)
		v = io.getInt();
		s = io.getInt();
		t = io.getInt();
		totFlow = io.getInt();
		e = io.getInt();

		for (int i = 0; i < e; ++i) {
			// Flöde f från a till b
			int a = io.getInt();
			int b = io.getInt();
			int f = io.getInt();

			// Lägg till kant om flödet är större än 0 och om a och b tillhör vänster- repsektive högermängden
			if (f > 0 && a > s && a <= x + 1 && b >= x + 2 && b < t) {
				// Vi vill ha 1-indexerat i den nya bipartita grafen, just nu är minsta a=2
				int left = a - 1;
				int right = b - 1;
				int[] edge = { left, right };
				maxFlowEdges.add(edge);
			}
		}
	}

	/* Skriv största matchningen till standard output */
	void writeBipMatchSolution() {
		// Skriv ut antal hörn och storleken på matchningen
		io.println(x + " " + y);
		io.println(totFlow);

		// Skriv ut alla kanter i matchningen
		for (int[] edge : maxFlowEdges) {
			int a = edge[0];
			int b = edge[1];
			io.println(a + " " + b);
			
		}

		io.flush();
	}

	BipRed() {
		io = new Kattio(System.in, System.out);

		readBipartiteGraph();

		writeFlowGraph();

		readMaxFlowSolution();

		writeBipMatchSolution();

		// Debugutskrift
		System.err.println("Bipred avslutar\n");

		// Kom ihåg att stänga ner Kattio-klassen
		io.close();
	}

	public static void main(String args[]) {
		new BipRed();
	}
}
