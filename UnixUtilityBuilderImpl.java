import java.util.ArrayList;
import java.util.List;

public class UnixUtilityBuilderImpl implements UnixUtilityBuilder {
    private List<String> argv = new ArrayList<>();
    private List<String> stdin = new ArrayList<>();

    public UnixUtilityBuilderImpl(String command) {
        argv.add(command);
    }

    public void addArgument(String arg) {
        argv.add(arg);
    }

    public void addLineOfInput(String input) {
        stdin.add(input);
    }

    public UnixUtility build() {
        return new UnixUtilityImpl(argv, stdin);
    }
}
