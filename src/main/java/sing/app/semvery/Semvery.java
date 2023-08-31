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
                int returnValue = 0;
                for (String value : parameters.mainParameters) {
                    Semver version = Semver.parse(value);
                    if (version == null) {
                        Console.println("invalid");
                        returnValue = 1;
                    } else {
                        Console.println("valid");
                    }
                }
                return returnValue;
            }
        } else if ("isStable".equals(parameters.operation)) {
            if (parameters.mainParameters.isEmpty()) {
                Console.error("Must specify a version.");
                return 2;
            } else {
                int returnValue = 0;
                for (String value : parameters.mainParameters) {
                    Semver version = Semver.parse(value);
                    if (version == null) {
                        Console.println("invalid");
                        returnValue = 1;
                    } else {
                        if (version.isStable()) {
                            Console.println("stable");
                        } else {
                            Console.println("not stable");
                            returnValue = 1;
                        }
                    }
                }
                return returnValue;
            }
        } else {
            Help.printHelp(parameters);
            return 0;
        }
    }
}
