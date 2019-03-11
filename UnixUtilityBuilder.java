public interface UnixUtilityBuilder {
    public void addArgument(String arg);
    public void addLineOfInput(String input);
    public UnixUtility build();
}
