package sing.app.semvery;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Help {
    public static void printHelp(Parameters parameters) throws IOException {
        Version.printApplicationVersion();
        parameters.printUsage();
        printAdditionalHelp();
    }

    private static void printAdditionalHelp() throws IOException {
        try (InputStream ioStream =
                Help.class.getClassLoader().getResourceAsStream("additional-help.txt")) {
            try (InputStreamReader isr = new InputStreamReader(ioStream);
                    BufferedReader br = new BufferedReader(isr);) {
                String line;
                while ((line = br.readLine()) != null)
                    Console.println(line);
            }
        }
    }
}
