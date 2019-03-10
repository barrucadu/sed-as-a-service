import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        int port = Integer.parseInt(System.getenv("PORT"));

        UnixUtilityBuilderFactory sedBuilderFactory = UnixUtilityBuilderFactoryFactorySingleton.getInstance().construct("sed");
        UnixUtilityBuilderFactoryRegistrySingleton.getInstance().register("sed", sedBuilderFactory);

        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(port), 1024);
            server.createContext("/", new UnixUtilityAsAServiceServer());
            server.setExecutor(null);
            server.start();
        } catch (IOException e) {
            System.out.println("Couldn't start server.");
        }
    }

    private static class UnixUtilityAsAServiceServer implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String method = t.getRequestMethod().toLowerCase();
            String unixUtility = t.getRequestURI().getPath().toLowerCase().substring(1);

            System.out.println("method: '" + method + "', unix utility: '" + unixUtility + "'");

            if (method.equals("post")) {
                handlePOST(t, unixUtility);
            } else if (method.equals("put")) {
                handlePUT(t, unixUtility);
           } else {
                respond(t, 400, "Bad request");
            }
        }

        private void handlePOST(HttpExchange t, String unixUtility) throws IOException {
            UnixUtilityBuilderFactoryRegistrySingleton registry =
                UnixUtilityBuilderFactoryRegistrySingleton.getInstance();
            Optional<UnixUtilityBuilderFactory> builderFactory =
                registry.retrieve(unixUtility);

            if (builderFactory.isPresent()) {
                respond(t, 501, "Not implemented");
            } else {
                respond(t, 404, "Not found");
            }
        }

        private void handlePUT(HttpExchange t, String unixUtility) throws IOException {
            UnixUtilityBuilderFactoryFactorySingleton factory =
                UnixUtilityBuilderFactoryFactorySingleton.getInstance();
            UnixUtilityBuilderFactoryRegistrySingleton registry =
                UnixUtilityBuilderFactoryRegistrySingleton.getInstance();

            registry.register(unixUtility, factory.construct(unixUtility));

            respond(t, 201, "Created");
        }

        private void respond(HttpExchange t, int code, String response) throws IOException {
            OutputStream os = t.getResponseBody();
            t.sendResponseHeaders(code, response.length());
            os.write(response.getBytes());
            os.close();
        }
    }
}
