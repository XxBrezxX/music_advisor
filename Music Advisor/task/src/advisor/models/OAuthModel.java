package advisor.models;

import advisor.providers.SharedData;
import advisor.server.AuthServer;
import advisor.utils.HttpUtilQueries;
import advisor.utils.ProgramArguments;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URLEncoder;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;

public class OAuthModel {
    private final SharedData sharedData;
    private final ProgramArguments programArguments;
    private final HttpUtilQueries httpUtilQueries;
    private AuthServer authServer;
    private JsonObject tokenObject;

    private Boolean authorization;

    {
        this.sharedData = new SharedData();
    }

    public OAuthModel(ProgramArguments programArguments) {
        this.programArguments = programArguments;
        this.httpUtilQueries = new HttpUtilQueries();
        this.initializeServer();
    }

    private void initializeServer() {
        try {
            authServer = new AuthServer(this.sharedData);
            authServer.startServer();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void getUserAuth() {
        System.out.println(MessageFormat.format("use this link to request the access code:\n{0}/authorize?client_id=309d98be14e14df9bdfe3bf3a4366ffd&redirect_uri=http://127.0.0.1:8080/login&response_type=code\nwaiting for code...", programArguments.getServerPath()));
    }

    public void waitAuth() {
        do {
            synchronized (sharedData) {
                try {
                    sharedData.wait();
                    authorization = sharedData.isHasCode();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } while (!authorization);
        this.stopServer();
        System.out.println(sharedData.isHasCode() ? "code received" : "code denied");
    }

    public void stopServer(){
        authServer.stopServer();
    }

    public void reqToken() {
        if (authorization) {
            try {
                System.out.println("making http request for access_token...");
                String requestBody =
                        "code=" + URLEncoder.encode(sharedData.getAuthCode(), StandardCharsets.UTF_8)
                                + "&redirect_uri=" + URLEncoder.encode("http://127.0.0.1:8080/login", StandardCharsets.UTF_8)
                                + "&grant_type=authorization_code";
                HttpResponse<String> response = httpUtilQueries.postRequest(
                        programArguments.getServerPath() + "/api/token",
                        requestBody
                );
                if (response.statusCode() == 200) {
//                    System.out.println("response:\n" + response.body());
                    this.tokenObject = JsonParser.parseString(response.body()).getAsJsonObject();
                }
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public boolean getAuth() {
        return this.authorization;
    }

    public JsonObject getToken() {
        return tokenObject;
    }
}
