package advisor.server.handlers;

import advisor.providers.SharedData;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

public class SpotifyHandler implements HttpHandler {
    private final SharedData sharedData;

    public SpotifyHandler(SharedData sharedData) {
        this.sharedData = sharedData;
    }

    protected String code;

    @Override
    public void handle(HttpExchange exchange) throws IOException {
//        System.out.println("Entre al handler");
//        System.out.println(exchange);
        String response = "";
//        System.out.println(exchange.getRequestURI());

        String query = exchange.getRequestURI().getQuery();
//        System.out.println(query);
//        if(query.equals(null)) throw new RuntimeException("Error null");
        if(query == null || !query.contains("code")){
            response = "Authorization code not found. Try again.";
//            System.out.println(response);
            exchange.sendResponseHeaders(400, response.getBytes().length);

            OutputStream outputStream = exchange.getResponseBody();
            outputStream.write(response.getBytes());
            outputStream.flush();
            outputStream.close();

            sharedData.setHasCode(false);

        } else {
            response = "Got the code. Return back to your program.";
//            System.out.println(response);
            exchange.sendResponseHeaders(200, response.getBytes().length);

            OutputStream outputStream = exchange.getResponseBody();
            outputStream.write(response.getBytes());
            outputStream.flush();
            outputStream.close();
            String code = query.split("=")[1];

            sharedData.setAuthCode(code);
            sharedData.setHasCode(true);

        }
//        exchange.getResponseHeaders().set("Content-Type", "text/plain");

        synchronized (sharedData) {
            sharedData.notify();
        }
//        else if(requestPath.equals("/"))
    }

}
