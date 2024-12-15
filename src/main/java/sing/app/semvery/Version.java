package sing.app.semvery;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Version {
    @Getter
    private static final String APPLICATION_NAME = "semvery";
    @Getter
    private static final String APPLICATION_VERSION = "0.11.0";

    public static void printApplicationVersion() {
        Console.printf("%s %s%n", APPLICATION_NAME, APPLICATION_VERSION);
    }

    public static void printVersion() {
        Console.printf("%s%n", APPLICATION_VERSION);
    }
}
