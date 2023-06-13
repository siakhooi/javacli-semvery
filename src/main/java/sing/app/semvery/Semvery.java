package sing.app.semvery;

import org.semver4j.Semver;

public class Semvery {
    public static void main(String[] args) {
        Semvery app = new Semvery();
        app.run(args);
    }

    Parameters parameters;

    private void run(String[] args) {
        parameters = new Parameters();
        parameters.process(args);
        if (parameters.help) {
            Version.printHelp(parameters);
        } else if (parameters.version) {
            Version.printVersion();
        } else if ("isValid".equals(parameters.operation)) {
            if (parameters.mainParameters.size() == 0) {
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
            Version.printHelp(parameters);
        }
    }
}
