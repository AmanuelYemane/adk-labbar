import java.util.HashMap;
import java.util.Map;

public class datastructure<Integer> {
    private final Map<Integer, Integer> map;

    public datastructure() {
        map = new HashMap<>();
}

    private datastructure(Map<Integer, Integer> newMap) {
        map = newMap;
    }
    public int get(int i) {
        return map.get(i, 0);
    }
}
