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

        ReturnValue returnValue = ReturnValue.OK;

        if (parameters.help) {
            if (!parameters.silent)
                Help.printHelp(parameters);

        } else if (parameters.version) {
            if (!parameters.silent)
                Version.printVersion();

        } else if (parameters.operation != null) {
            returnValue = processOperation();

        } else if (!parameters.silent) {
            Help.printHelp(parameters);
        }

        return returnValue;
    }

    private ReturnValue processOperation() {
        if (parameters.mainParameters.isEmpty()) {
            if (!parameters.silent)
                Console.error("Must specify a version.");
            return ReturnValue.WRONG_PARAMETER;
        }
        if (parameters.operation.requireRefVersion() && parameters.refVersion == null) {
            if (!parameters.silent)
                Console.error("Must specify a refVersion.");
            return ReturnValue.WRONG_PARAMETER;
        }
        var result = parameters.operation.getProcessor().process(parameters.mainParameters,
                parameters.refVersion);
        if (!parameters.silent)
            ResultPrinter.output(result, parameters.outputFormat);

        return result.getReturnValue(parameters.any);
    }
}
