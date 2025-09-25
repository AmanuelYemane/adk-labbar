
/* Labb 2 i DD2350 Algoritmer, datastrukturer och komplexitet    */
/* Se labbinstruktionerna i kursrummet i Canvas                  */
/* Ursprunglig författare: Viggo Kann KTH viggo@nada.kth.se      */
import java.util.LinkedList;
import java.util.List;

public class ClosestWords {
  LinkedList<String> closestWords = null;

  int closestDistance = -1;

  // skapa en dynprogmatris av storlek 40x40 (denna storlek har visat sig räcka
  // för att klara alla test)
  int[][] M = new int[40][40];

  String prev = "";

  int distance(String w1, String w2, int maxDist) {
    int w1len = w1.length();
    int w2len = w2.length();

    // för ord vars längd skiljer sig på x bokstäver i längd kommer avståndet vara
    // minst x
    // vi avbryter därför sökningen för ett ord om x är större än det kortaste
    // avståndet hittils
    // returnerar maxDist+1 för att bara ignorera ordet i konstruktorn för
    // ClosestWords
    if (Math.abs(w1len - w2len) > maxDist)
      return maxDist + 1;

    return partDist(w1, w2, w1len, w2len);
  }

  int partDist(String w1, String w2, int w1len, int w2len) {
    int row = 1;

    // kolla om det är några bokstäver i början av det nya ordet som är samma som i
    // det föregående ordet
    // om vi har p bokstäver som är samma kan vi återanvända de p+1 första raderna i
    // matrisen
    for (int i = 1; i < Math.min(prev.length(), w2len) + 1; i++) {
      if (prev.charAt(i - 1) == w2.charAt(i - 1)) {
        row++;
      } else {
        break;
      }
    }

    // fyll i matrisen
    for (int i = 1; i < w1len + 1; i++) { // varje kolumn utom den första
      for (int j = row; j < w2len + 1; j++) { // varje rad utom de p+1 första

        // samma värde som på diagonalen om samma bokstav
        if (w1.charAt(i - 1) == w2.charAt(j - 1)) {
          M[i][j] = M[i - 1][j - 1];
        } else {
          // annars det minsta av värdena i diagonalen, till vänster och ovanför plus ett
          int shortest = Math.min(Math.min(M[i][j - 1], M[i - 1][j - 1]), M[i - 1][j]);
          M[i][j] = shortest + 1;
        }
      }
    }

    prev = w2;

    return M[w1len][w2len];
  }

  public ClosestWords(String w, List<String> wordList) {

    // fyll i värden för första raden och första kolumnen
    for (int i = 0; i < 40; i++) {
      M[i][0] = i;
      M[0][i] = i;
    }

    for (String s : wordList) {

      // spara kortaste avståndet hittills, använd för att sluta söka, sätter -1 till
      // största integer-värdet för att kunna använda i jämförelse
      int maxDist = (closestDistance == -1 ? Integer.MAX_VALUE : closestDistance);

      int dist = distance(w, s, maxDist);

      if (dist < closestDistance || closestDistance == -1) {
        closestDistance = dist;
        closestWords = new LinkedList<String>();
        closestWords.add(s);
      } else if (dist == closestDistance)
        closestWords.add(s);
    }
  }

  int getMinDistance() {
    return closestDistance;
  }

  List<String> getClosestWords() {
    return closestWords;
  }
}

/*
 * Testkörningar för large (test: testmedordlista4) med olika optimeringar för programmet
 * Alla optimeringar: 32 ms
 * Med jämförelse av de första bokstäverna i ett ord (och global dynprogmatris): 42 ms
 * Med jämförelse med maxDist för att avbryta sökning tidigare (och global dynprogmatris): 47 ms
 * Med bara en global dynprogmatris: 66 ms
 * Med bara en lokal dynprogmatris: 178 ms
 */