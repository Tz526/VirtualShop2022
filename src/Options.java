import java.util.HashMap;
import java.util.Map;

public enum Options {
    OP_1(1),
    OP_2(2),
    OP_3(3);

    private final int value;
    private static final Map<Integer, Options> map = new HashMap<>();

    Options(int value) {
        this.value = value;
    }

    static {
        for (Options choiceType : Options.values()) {
            map.put(choiceType.value, choiceType);
        }
    }

    public static Options valueOf(int choiceType) {
        return map.get(choiceType);
    }

    public int getValue() {
        return value;
    }
}