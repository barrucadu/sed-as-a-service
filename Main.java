import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class Main {
    public static void main(String[] args) {
        int port = Integer.parseInt(System.getenv("PORT"));

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
            String uri = t.getRequestURI().getPath().toLowerCase();
            String response = "method: '" + method + "', uri: '" + uri + "'";
            System.out.println(response);

            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}
