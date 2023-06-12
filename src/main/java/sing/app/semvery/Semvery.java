package sing.app.semvery;

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
        }else if(parameters.version){
            Version.printVersion();
        }
    }
}
