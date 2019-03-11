public class UnixUtilityBuilderFactoryFactorySingleton {
    private static final UnixUtilityBuilderFactoryFactorySingleton instance = new UnixUtilityBuilderFactoryFactorySingleton();

    private UnixUtilityBuilderFactoryFactorySingleton() {}

    public static UnixUtilityBuilderFactoryFactorySingleton getInstance() {
        return instance;
    }

    public UnixUtilityBuilderFactory construct(String unixUtility) {
        return new UnixUtilityBuilderFactoryImpl(unixUtility);
    }
}
