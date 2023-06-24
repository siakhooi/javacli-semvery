package sing.app.semvery;

import java.io.IOException;
import org.semver4j.Semver;

public class Semvery {
    public static void main(String[] args) throws IOException {
        Semvery app = new Semvery();
        System.exit(app.run(args));
    }

    Parameters parameters;

    int run(String[] args) throws IOException {
        parameters = new Parameters();
        parameters.process(args);
        if (parameters.help) {
            Help.printHelp(parameters);
            return 0;
        } else if (parameters.version) {
            Version.printVersion();
            return 0;
        } else if ("isValid".equals(parameters.operation)) {
            if (parameters.mainParameters.isEmpty()) {
                Console.error("Must specify a version.");
                return 2;
            } else {
                String value = parameters.mainParameters.get(0);
                Semver version = Semver.parse(value);
                if (version == null) {
                    Console.println("invalid");
                    return 1;
                } else {
                    Console.println("valid");
                    return 0;
                }
            }
        } else if ("isStable".equals(parameters.operation)) {
            if (parameters.mainParameters.isEmpty()) {
                Console.error("Must specify a version.");
                return 2;
            } else {
                String value = parameters.mainParameters.get(0);
                Semver version = Semver.parse(value);
                if (version == null) {
                    Console.println("invalid");
                    return 1;
                } else {
                    if (version.isStable()) {
                        Console.println("stable");
                        return 0;
                    } else {
                        Console.println("not stable");
                        return 1;
                    }
                }
            }
        } else {
            Help.printHelp(parameters);
            return 0;
        }
    }
}
