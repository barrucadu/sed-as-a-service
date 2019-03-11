public class UnixUtilityBuilderFactoryImpl implements UnixUtilityBuilderFactory {
    private String command;

    public UnixUtilityBuilderFactoryImpl(String unixUtility) {
        command = unixUtility;
    }

    public UnixUtilityBuilder construct() {
        return new UnixUtilityBuilderImpl(command);
    }
}
