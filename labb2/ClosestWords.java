import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class ClosestWords {
  LinkedList<String> closestWords = null;
  int closestDistance = -1;

  int distance(String w1, String w2, int maxDist) {
    int n = w1.length();
    int m = w2.length();

    if (Math.abs(n - m) > maxDist) return maxDist + 1;

    int[] prev = new int[m + 1];
    int[] curr = new int[m + 1];

    for (int j = 0; j <= m; j++) prev[j] = j;

    for (int i = 1; i <= n; i++) {
        curr[0] = i;
        int minInRow = curr[0];

        for (int j = 1; j <= m; j++) {
            int cost = (w1.charAt(i - 1) == w2.charAt(j - 1)) ? 0 : 1;
            curr[j] = Math.min(
                Math.min(curr[j - 1] + 1, prev[j] + 1),
                prev[j - 1] + cost
            );
            if (curr[j] < minInRow) minInRow = curr[j];
        }

        if (minInRow > maxDist) return maxDist + 1;

        int[] temp = prev;
        prev = curr;
        curr = temp;
    }

    return prev[m];
}


  public ClosestWords(String w, List<String> wordList) {
    Collections.sort(wordList);

    String prevWord = null;
    int[] prevRow = null;
    int prefixLen = 0;

    for (String s : wordList) {
      if (prevWord != null) {
        prefixLen = 0;
        int maxPrefix = Math.min(prevWord.length(), s.length());
        while (prefixLen < maxPrefix &&
               prevWord.charAt(prefixLen) == s.charAt(prefixLen)) {
          prefixLen++;
        }
      } else {
        prefixLen = 0;
      }

      if (prevRow == null || s.length() != prevWord.length()) {
        prevRow = new int[s.length() + 1];
      }

      int maxDist = (closestDistance == -1 ? Integer.MAX_VALUE : closestDistance);
      int dist = distance(w, s, maxDist); // no prefix reuse


      if (dist < closestDistance || closestDistance == -1) {
        closestDistance = dist;
        closestWords = new LinkedList<>();
        closestWords.add(s);
      } else if (dist == closestDistance) {
        closestWords.add(s);
      }

      prevWord = s;
    }
  }

  int getMinDistance() {
    return closestDistance;
  }

  List<String> getClosestWords() {
    return closestWords;
  }
}
