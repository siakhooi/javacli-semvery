package sing.app.semvery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.params.provider.Arguments;

public class TestData {
    public TestData() {}

    public static final String[] OPERATIONS = new String[] {"-o", "--operation"};

    public static final String[] ALL_OPERATORS = new String[] {"isValid", "isStable", "isGreater", "isLower"};
    public static final String[] OPERATORS_NEED_REFVERSION = new String[] {"isGreater", "isLower"};

    public static final String[][] ALL_VALID = {{"0.3.0"}, {"1.0.0"}, {"243434.234233.234223"},
            {"0.0.2"}, {"33.5454.54353-pre"}, {"1.0.1", "3.0.0", "0.0.2"}};
    public static final String[][] ALL_NOT_VALID =
            {{"1.0.A"}, {"adfadfasjdfsd"}, {"23423"}, {"2423.2342342"}, {"24234.234234."},
                    {"1.0.B", "adfadfasjdfsd", "23423", "2423.2342342", "24234.234234."}};
    public static final String[][] VALID_NOT_VALID =
            {{"1.0.A", "1.1.0", "0.3.0"}, {"0.3.0", "1.1.A", "1.3.0"}, {"1.0.0", "0.1.0", "3.0.A"}};

    public static final String[][] ALL_STABLE = {{"1.0.0"}, {"23434.24343.2343"},
            {"1.2.3+sHa.0nSFGKjk"}, {"1.2.3", "24434.24233.23433", "1.2.3+sHa.0nSFGjkf"}};
    public static final String[][] ALL_NOT_STABLE = {{"0.2.0"}, {"1.2.3-4"}, {"1.2.3-BETA"},
            {"33.5454.54353-pre"}, {"0.1.0", "1.2.3-4", "1.2.3-BETA", "33.5454.54353-pre"}};

    public static final String[][] STABLE_NOT_VALID =
            {{"1.0.A", "1.1.0", "1.3.0"}, {"1.3.0", "1.1.A", "1.3.0"}, {"1.0.0", "1.1.0", "3.0.A"}};
    public static final String[][] STABLE_NOT_STABLE =
            {{"0.1.0", "1.1.0", "3.0.0"}, {"1.1.0", "0.1.0", "3.0.0"}, {"1.1.0", "3.0.0", "0.1.0"}};
    public static final String[][] STABLE_NOT_STABLE_NOT_VALID =
            {{"0.1.0", "1.1.A", "3.0.0"}, {"1.1.A", "0.1.0", "3.0.0"}, {"1.1.0", "3.0.A", "0.1.0"}};
    public static final String[][] NOT_STABLE_NOT_VALID = {{"0.1.0", "1.1.A", "3.0.0-BETA"},
            {"1.1.A", "0.1.0", "3.B.0"}, {"1.1.0-pre", "3.0.A", "5.1.0-4"}};

    public static final String[][] ALL_GREATER_2_0_0 =
            {{"2.0.1"}, {"2.1.0"}, {"3.1.2"}, {"2.0.3", "4.5.7", "8.7.8"}};
    public static final String[][] ALL_NOT_GREATER_2_0_0 =
            {{"1.0.2"}, {"2.0.0"}, {"0.1.2"}, {"1.0.3", "0.5.7", "2.0.0"}};
    public static final String[][] GREATER_NOT_GREATER_2_0_0 =
            {{"0.1.1", "1.1.0", "3.0.0"}, {"1.1.0", "0.1.2", "3.0.0"}, {"1.1.0", "3.0.2", "0.1.0"}};

    public static final String[][] GREATER_2_0_0_NOT_VALID =
            {{"1.0.A", "2.1.0", "3.3.0"}, {"2.3.0", "1.1.A", "3.3.0"}, {"2.0.0", "3.1.0", "3.0.A"}};
    public static final String[][] NOT_GREATER_2_0_0_NOT_VALID =
            {{"1.0.A", "1.1.0", "1.3.0"}, {"1.3.0", "1.1.A", "1.3.0"}, {"1.0.0", "1.1.0", "3.0.A"}};
    public static final String[][] GREATER_NOT_GREATER_2_0_0_NOT_VALID =
            {{"0.1.A", "1.1.0", "3.0.0"}, {"1.1.A", "0.1.2", "3.0.0"}, {"1.1.0", "3.0.2", "A.1.0"}};

    public static final String[][] ALL_LOWER_2_0_0 =
            {{"1.0.1"}, {"1.1.0"}, {"1.1.2"}, {"1.0.3", "1.5.7", "0.7.8"}};
    public static final String[][] ALL_NOT_LOWER_2_0_0 =
            {{"2.0.2"}, {"2.0.1"}, {"2.1.2"}, {"2.0.3", "9.5.7", "2.0.1"}};
    public static final String[][] LOWER_NOT_LOWER_2_0_0 = GREATER_NOT_GREATER_2_0_0;

    public static final String[][] LOWER_2_0_0_NOT_VALID =
            {{"1.0.A", "1.1.0", "0.3.0"}, {"0.3.0", "1.1.A", "1.3.1"}, {"1.0.0", "0.1.0", "3.0.A"}};
    public static final String[][] NOT_LOWER_2_0_0_NOT_VALID =
            {{"1.0.A", "2.1.0", "3.3.0"}, {"2.3.0", "1.1.A", "3.3.1"}, {"2.0.0", "4.1.0", "3.0.A"}};
    public static final String[][] LOWER_NOT_LOWER_2_0_0_NOT_VALID =
            GREATER_NOT_GREATER_2_0_0_NOT_VALID;

    private static Arguments createArgument(String scenario, ReturnValue returnValue,
            String operation, String operator, String[] values, String[] extra) {
        List<String> l = new ArrayList<>();
        l.add(operation);
        l.add(operator);
        l.addAll(Arrays.asList(values));
        l.addAll(Arrays.asList(extra));
        return Arguments.of(scenario, returnValue, l.toArray(new String[0]));
    }

    public static List<Arguments> createArguments(String scenarioName, ReturnValue returnValue,
            String operation, String operator, String[] extra, String[][] scenarios) {
        List<Arguments> l = new ArrayList<>();

        for (String[] values : scenarios)
            l.add(createArgument(scenarioName + ":" + String.join(",", values), returnValue,
                    operation, operator, values, extra));

        return l;
    }
}
