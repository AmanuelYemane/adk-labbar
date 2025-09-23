
/* Labb 2 i DD2350 Algoritmer, datastrukturer och komplexitet    */
/* Se labbinstruktionerna i kursrummet i Canvas                  */
/* Ursprunglig författare: Viggo Kann KTH viggo@nada.kth.se      */
import java.util.LinkedList;
import java.util.List;

public class ClosestWords {
  LinkedList<String> closestWords = null;

  int closestDistance = -1;

  // int partDist(String w1, String w2, int w1len, int w2len) {
  // int[][] M = new int[w1len + 1][w2len + 1];

  // for (int i = 0; i < w1len + 1; i++) M[i][0] = i;
  // for (int j = 0; j < w2len + 1; j++) M[0][j] = j;

  // for (int i = 1; i < w1len + 1; i++) {
  // for (int j = 1; j < w2len + 1; j++) {
  // if (w1.charAt(i - 1) == w2.charAt(j - 1)) {
  // M[i][j] = M[i - 1][j - 1];
  // } else {
  // int shortest = Math.min(M[i][j - 1], M[i - 1][j - 1]);
  // shortest = Math.min(shortest, M[i - 1][j]);
  // M[i][j] = shortest + 1;
  // }
  // }
  // }
  // return M[w1len][w2len];
  // }

  int partDist(String w1, String w2, int w1len, int w2len, int[][] M) {
    if (M[w1len][w2len] != -1)
      return M[w1len][w2len];
    if (w1len == 0)
      return w2len;
    if (w2len == 0)
      return w1len;
    if (w1.charAt(w1len - 1) == w2.charAt(w2len - 1)) {
      M[w1len][w2len] = partDist(w1, w2, w1len - 1, w2len - 1, M);
      return M[w1len][w2len];
    }
    int shortest = Math.min(partDist(w1, w2, w1len, w2len - 1, M), partDist(w1, w2, w1len - 1, w2len - 1, M));
    shortest = Math.min(shortest, partDist(w1, w2, w1len - 1, w2len, M));

    M[w1len][w2len] = 1 + shortest;
    return M[w1len][w2len];
  }

  // int partDist(String w1, String w2, int w1len, int w2len) {
  // if (w1len == 0)
  // return w2len;
  // if (w2len == 0)
  // return w1len;
  // int res = partDist(w1, w2, w1len - 1, w2len - 1) +
  // (w1.charAt(w1len - 1) == w2.charAt(w2len - 1) ? 0 : 1);
  // int addLetter = partDist(w1, w2, w1len - 1, w2len) + 1;
  // if (addLetter < res)
  // res = addLetter;
  // int deleteLetter = partDist(w1, w2, w1len, w2len - 1) + 1;
  // if (deleteLetter < res)
  // res = deleteLetter;
  // return res;
  // }

  int distance(String w1, String w2, int maxDist) {
    int w1len = w1.length();
    int w2len = w2.length();
    // int[][] M = createMatrix(w1.length(), w2.length());
    if (Math.abs(w1len-w2len) > maxDist) return maxDist + 1;
    if (w1len < w2len) {
      String tmp = w1;
      w1 = w2;
      w2 = tmp;
      int tmpLen = w1len;
      w1len = w2len;
      w2len = tmpLen;
    }
    int[] prev = new int[w2len + 1];
    int[] curr = new int[w2len + 1];

    for (int j = 0; j < w2len + 1; j++)
      prev[j] = j;
    for (int i = 1; i < w1len + 1; i++) {
      curr[0] = i;
      for (int j = 1; j < w2len + 1; j++) {
        int cost = (w1.charAt(i - 1) == w2.charAt(j - 1)) ? 0 : 1;
        curr[j] = Math.min(Math.min(curr[j - 1] + 1, prev[j] + 1), prev[j - 1] + cost);
      }
      int[] tmp = prev;
      prev = curr;
      curr = tmp;
    }
    return prev[w2len];
    // return partDist(w1, w2, w1len, w2len, prev, curr);
  }

  int[][] createMatrix(int w1len, int w2len) {
    int[][] M = new int[w1len + 1][w2len + 1];

    for (int i = 0; i < w1len + 1; i++)
      M[i][0] = i;
    for (int j = 0; j < w2len + 1; j++)
      M[0][j] = j;

    for (int i = 1; i < w1len + 1; i++) {
      for (int j = 1; j < w2len + 1; j++) {
        M[i][j] = -1;
      }
    }
    return M;
  }

  public ClosestWords(String w, List<String> wordList) {
    for (String s : wordList) {
      int maxDist = (closestDistance == -1 ? Integer.MAX_VALUE : closestDistance);
      int dist = distance(w, s, maxDist);
      // System.out.println("d(" + w + "," + s + ")=" + dist);

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