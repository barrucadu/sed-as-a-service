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
            OutputStream os = t.getResponseBody();

            System.out.println("method: '" + method + "', unix utility: '" + unixUtility + "'");

            int code = 400;
            String response = "Bad request";

            UnixUtilityBuilderFactoryRegistrySingleton registry = UnixUtilityBuilderFactoryRegistrySingleton.getInstance();

            if (method.equals("post")) {
                Optional<UnixUtilityBuilderFactory> builderFactory = registry.retrieve(unixUtility);
                if (builderFactory.isPresent()) {
                    code = 501;
                    response = "Not implemented";
                } else {
                    code = 404;
                    response = "Not found";
                }
            } else if (method.equals("put")) {
                code = 404;
                response = "Not found";
           }

            t.sendResponseHeaders(code, response.length());
            os.write(response.getBytes());
            os.close();
        }
    }
}
