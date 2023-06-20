package advisor.server;

import advisor.server.handlers.SpotifyHandler;
import advisor.providers.SharedData;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class AuthServer {
    final static int port = 8080;
    final private HttpServer server;

    public AuthServer(SharedData sharedData) throws IOException {
        server = HttpServer.create(new InetSocketAddress(port), 0);

        SpotifyHandler spotifyHandler = new SpotifyHandler(sharedData);
        server.createContext("/login", spotifyHandler);
    }

    public void startServer() {
        server.start();
//        System.out.println("Servidor iniciado en el puerto " + port);
    }

    public void stopServer() {
//        System.out.println("Server fin");
        server.stop(1);
    }
}
