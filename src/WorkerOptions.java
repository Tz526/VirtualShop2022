import java.util.HashMap;
import java.util.Map;

public enum WorkerOptions {
    PRINT_ALL_CUSTOMERS(1),
    PRINT_ALL_CLUB_MEMBERS(2),
    PRINT_ALL_BUYERS(3),
    PRINT_MAX_BUYER(4),
    ADD_PRODUCT(5),
    CHANGE_PRODUCT_STATUS(6),
    BUY_PRODUCT(7),
    EXIT(8);

    private final int value;
    private static final Map<Integer, WorkerOptions> map = new HashMap<>();

    WorkerOptions(int value) {
        this.value = value;
    }

    static {
        for (WorkerOptions choiceType : WorkerOptions.values()) {
            map.put(choiceType.value, choiceType);
        }
    }

    public static WorkerOptions valueOf(int choiceType) {
        return map.get(choiceType);
    }

    public int getValue() {
        return value;
    }
}