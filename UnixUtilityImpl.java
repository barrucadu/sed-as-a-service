import java.util.Scanner;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.Process;
import java.lang.ProcessBuilder;
import java.lang.StringBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UnixUtilityImpl implements UnixUtility {
    private List<String> argv;
    private List<String> lines;

    public UnixUtilityImpl(List<String> arguments, List<String> linesOfInput) {
        argv = new ArrayList<>(arguments);
        lines = new ArrayList<>(linesOfInput);
    }
    
    public String execute() throws IOException {
        Process process = new ProcessBuilder(argv).start();

        OutputStream stdin = process.getOutputStream();
        InputStream stdout = process.getInputStream();

        BufferedReader reader = new BufferedReader(new InputStreamReader(stdout));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(stdin));

        for (String line : lines) {
            writer.write(line);
            writer.write("\n");
        }
        writer.close();

        StringBuilder combinedStdout = new StringBuilder();
        Scanner scanner = new Scanner(stdout);
        while (scanner.hasNextLine()) {
            combinedStdout.append(scanner.nextLine());
            combinedStdout.append("\n");
        }
        return combinedStdout.toString();
    }
}
