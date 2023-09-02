package sing.app.semvery;

import java.io.IOException;

public class Semvery {
    public static void main(String[] args) throws IOException {
        Semvery app = new Semvery();
        System.exit(app.run(args).getCode());
    }

    Parameters parameters;

    ReturnValue run(String[] args) throws IOException {
        parameters = new Parameters();
        parameters.process(args);
        if (parameters.help) {
            Help.printHelp(parameters);
            return ReturnValue.OK;
        } else if (parameters.version) {
            Version.printVersion();
            return ReturnValue.OK;
        } else if (parameters.operation != null) {
            if (parameters.mainParameters.isEmpty()) {
                Console.error("Must specify a version.");
                return ReturnValue.WRONG_PARAMETER;
            }
            var result = parameters.operation.getProcessor().process(parameters.mainParameters);
            ResultPrinter.output(result);

            return result.getReturnValue();
        } else {
            Help.printHelp(parameters);
            return ReturnValue.OK;
        }
    }
}
