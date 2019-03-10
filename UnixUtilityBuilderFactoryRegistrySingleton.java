import java.util.HashMap;

public class UnixUtilityBuilderFactoryRegistrySingleton {
    private static final UnixUtilityBuilderFactoryRegistrySingleton instance = new UnixUtilityBuilderFactoryRegistrySingleton();

    private HashMap<String, UnixUtilityBuilderFactory> registry = new HashMap();

    private UnixUtilityBuilderFactoryRegistrySingleton() {}

    public static UnixUtilityBuilderFactoryRegistrySingleton getInstance() {
        return instance;
    }

    public synchronized void register(String unixUtility, UnixUtilityBuilderFactory factory) {
        registry.put(unixUtility, factory);
    }
}
