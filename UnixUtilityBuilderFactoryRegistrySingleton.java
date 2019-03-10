public class UnixUtilityBuilderFactoryRegistrySingleton {
    private static final UnixUtilityBuilderFactoryRegistrySingleton instance = new UnixUtilityBuilderFactoryRegistrySingleton();

    private UnixUtilityBuilderFactoryRegistrySingleton() {}

    public static UnixUtilityBuilderFactoryRegistrySingleton getInstance() {
        return instance;
    }

    public void register(String unixUtility, UnixUtilityBuilderFactory factory) {
    }
}
