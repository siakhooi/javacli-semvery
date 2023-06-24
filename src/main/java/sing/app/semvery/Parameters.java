package sing.app.semvery;

import java.util.ArrayList;
import java.util.List;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import sing.app.semvery.parameters.OperationParameterValidator;

public class Parameters {

    @Parameter(order = 1, names = {"--operation", "-o"},
            description = "Operation, value = [isValid, isStable]",
            validateWith = OperationParameterValidator.class)
    String operation;
    @Parameter(order = 98, names = {"--version", "-v"}, description = "Display version", help = true)
    boolean version;
    @Parameter(order = 99, names = {"--help", "-h"}, description = "Display help", help = true)
    boolean help;

    @Parameter
    List<String> mainParameters = new ArrayList<>();

    private JCommander jc;

    public void process(String[] args) throws ParameterException {
        jc = JCommander.newBuilder().addObject(this).build();
        jc.parse(args);
        jc.setProgramName(Version.getAPPLICATION_NAME());
    }

    public void printUsage() {
        jc.usage();
    }
}
