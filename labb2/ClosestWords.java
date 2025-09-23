import java.util.LinkedList;
import java.util.List;

public class ClosestWords {
    LinkedList<String> closestWords = null;
    int closestDistance = -1;

    int partDist(String w1, String w2, int maximumDistance) {
        int n = w1.length();
        int m = w2.length();

        if (Math.abs(n - m) > maximumDistance) return maximumDistance + 1;


        if (n < m) {
            String tmp = w1; 
            w1 = w2; 
            w2 = tmp;
            int tmpLen = n; 
            n = m; 
            m = tmpLen;
        }

        int[] prev = new int[m + 1];
        int[] curr = new int[m + 1];

        for (int j = 0; j <= m; j++) prev[j] = j;

        for (int i = 1; i <= n; i++) {
            curr[0] = i;
            int rowMin = curr[0];

            for (int j = 1; j <= m; j++) {
                int cost = (w1.charAt(i - 1) == w2.charAt(j - 1)) ? 0 : 1;
                curr[j] = Math.min(Math.min(curr[j - 1] + 1, prev[j] + 1), prev[j - 1] + cost);
                if (curr[j] < rowMin) rowMin = curr[j];
            }


            if (rowMin > maximumDistance) return maximumDistance + 1;

            int[] tmp = prev; prev = curr; curr = tmp;
        }

        return prev[m];
    }

    public ClosestWords(String w, List<String> wordList) {
      int maximumDistance;
      for (String s : wordList) {
        if(closestDistance == -1){
          maximumDistance = Integer.MAX_VALUE;
        } else {
          maximumDistance = closestDistance;
        }
        int dist = partDist(w, s, maximumDistance);

        if (dist < closestDistance || closestDistance == -1) {
            closestDistance = dist;
            closestWords = new LinkedList<>();
            closestWords.add(s);
        } else if (dist == closestDistance) {
            closestWords.add(s);
        }
      }
    }

    int getMinDistance() {
        return closestDistance;
    }

    List<String> getClosestWords() {
        return closestWords;
    }
}
