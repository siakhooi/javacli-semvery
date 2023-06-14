package sing.app.semvery;

import java.io.IOException;
import org.semver4j.Semver;

public class Semvery {
    public static void main(String[] args) throws IOException {
        Semvery app = new Semvery();
        app.run(args);
    }

    Parameters parameters;

    private void run(String[] args) throws IOException {
        parameters = new Parameters();
        parameters.process(args);
        if (parameters.help) {
            Help.printHelp(parameters);
        } else if (parameters.version) {
            Version.printVersion();
        } else if ("isValid".equals(parameters.operation)) {
            if (parameters.mainParameters.isEmpty()) {
                Console.error("Must specify a version.");
                Console.exit(2);
            } else {
                String value = parameters.mainParameters.get(0);
                Semver version = Semver.parse(value);
                if (version == null) {
                    Console.println("invalid");
                    Console.exit(1);

                } else {
                    Console.println("valid");
                    Console.exit(0);
                }

            }
        } else {
            Help.printHelp(parameters);
        }
    }
}
