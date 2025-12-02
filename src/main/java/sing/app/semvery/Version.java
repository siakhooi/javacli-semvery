package sing.app.semvery;

import lombok.Getter;

public class Version {
    private Version() {}

    @Getter
    private static final String APPLICATION_NAME = "semvery";
    @Getter
    private static final String APPLICATION_VERSION = "1.0.4";

    public static void printApplicationVersion() {
        Console.printf("%s %s%n", APPLICATION_NAME, APPLICATION_VERSION);
    }

    public static void printVersion() {
        Console.printf("%s%n", APPLICATION_VERSION);
    }
}
