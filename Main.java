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
            OutputStream os = t.getResponseBody();

            System.out.println("method: '" + method + "', uri: '" + uri + "'");

            int code = 400;
            String response = "Bad request";

            if (method.equals("post")) {
                code = 404;
                response = "Not found";
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
