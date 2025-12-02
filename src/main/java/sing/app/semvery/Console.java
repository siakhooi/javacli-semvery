package sing.app.semvery;

@SuppressWarnings("java:S106")
public class Console {
    private Console() {}

    public static void printf(String format, Object... objects) {
        System.out.printf(format, objects);
    }

    public static void println(Object object) {
        System.out.println(object);
    }

    public static void error(Object object) {
        System.err.println(object);
    }

    public static void printResult(String value, String result) {
        printf("%-20s %-20s%n", value, result);
    }
}
