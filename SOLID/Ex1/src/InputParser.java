import java.util.*;

public class InputParser {
    public Map<String, String> parse(String raw) {
        Map<String, String> kv = new LinkedHashMap<>();
        for (String p : raw.split(";")) {
            String[] t = p.split("=", 2);
            if (t.length == 2) kv.put(t[0].trim(), t[1].trim());
        }
        return kv;
    }
}
