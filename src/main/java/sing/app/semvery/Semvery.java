package sing.app.semvery;

import java.io.IOException;

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
        } else if (parameters.operation != null) {
            if (parameters.mainParameters.isEmpty()) {
                Console.error("Must specify a version.");
                return 2;
            }
            return parameters.operation.getProcessor().process(parameters.mainParameters);
        } else {
            Help.printHelp(parameters);
            return 0;
        }
    }
}
