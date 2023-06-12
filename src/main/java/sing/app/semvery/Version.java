package sing.app.semvery;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Version {
    @Getter
    private static final String APPLICATION_NAME = "semvery";
    @Getter
    private static final String APPLICATION_VERSION = "0.0.2";

    public static void printApplicationVersion() {
        Console.printf("%s %s%n", APPLICATION_NAME, APPLICATION_VERSION);
    }

    public static void printHelp(Parameters parameters) {
        printApplicationVersion();
        parameters.printUsage();
    }

    public static void printVersion() {
        Console.printf("%s%n", APPLICATION_VERSION);
    }
}
