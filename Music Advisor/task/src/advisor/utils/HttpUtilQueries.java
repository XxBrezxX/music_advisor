package advisor.utils;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;

public class HttpUtilQueries {

    HttpClient httpClient;

    public HttpUtilQueries() {
    }

    {
        httpClient = HttpClient.newBuilder().build();
    }

    public void getRequest(String uri) throws IOException, InterruptedException {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .GET()
                .build();
        HttpResponse<String> httpResponse = this.httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        System.out.println(httpResponse.body());
    }

    public HttpResponse<String> postRequest(String uri, String request) throws IOException, InterruptedException {
        String client_id = "309d98be14e14df9bdfe3bf3a4366ffd";
        String client_secret = "5ea58477b4e44e73b806061cf767afa3";
        String credentials = Base64.getEncoder().encodeToString((client_id + ":" + client_secret).getBytes());
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .header("Authorization", "Basic " + credentials)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(request))
                .build();
        return this.httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
    }

}
