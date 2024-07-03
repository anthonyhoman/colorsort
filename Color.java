import java.util.HashMap;
import java.util.Map;

public enum Color {

    EMPTY("emp"),
    RED("r"),
    LIGHT_GREEN("lg"),
    LIGHT_PURPLE("lp"),
    LIGHT_BLUE("lb"),
    DARK_BLUE("db"),
    PUKE_GREEN("pg"),
    GRAY("gy"),
    ORANGE("o"),
    YELLOW("y"),
    PINK("pk"),
    DARK_PURPLE("dp"),
    DARK_GREEN("dg");

    public final String label;

    private static final Map<String, Color> BY_LABEL = new HashMap<>();
    
    static {
        for (Color e: values()) {
            BY_LABEL.put(e.label, e);
        }
    }

    public static Color fromString(String label) {
        return BY_LABEL.get(label);
    }

    private Color(String label) {
        this.label = label;
    }

    public String toString() {
        return this.label;
    }
}
