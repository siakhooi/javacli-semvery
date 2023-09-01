package sing.app.semvery;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@SuppressWarnings("java:S106")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Console {
    static public void printf(String format, Object... objects) {
        System.out.printf(format, objects);
    }

    static public void println(Object object) {
        System.out.println(object);
    }

    static public void error(Object object) {
        System.err.println(object);
    }
}
