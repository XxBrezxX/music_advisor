package advisor.providers;

import advisor.models.*;
import advisor.utils.ProgramArguments;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SpotifyProvider {
    private final JsonObject tokenObject;
    private final HttpClient httpClient;
    private final ProgramArguments programArguments;

    public SpotifyProvider(JsonObject tokenObject, ProgramArguments programArguments) {
        this.tokenObject = tokenObject;
        this.programArguments = programArguments;
    }

    {
        httpClient = HttpClient.newBuilder().build();
    }

    public List<Category> getCategories() throws IOException, InterruptedException {
        String uri = this.programArguments.getApiPath()
                .concat("/v1/browse/categories?country=MX&locale=mx_MX&limit=10&offset=5");
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .header("Authorization", "Bearer " + tokenObject.get("access_token").getAsString())
                .GET()
                .build();
        HttpResponse<String> httpResponse = this.httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        JsonObject jsonObject = JsonParser.parseString(httpResponse.body()).getAsJsonObject();
        JsonObject categories = jsonObject.getAsJsonObject("categories");
        JsonArray items = categories.getAsJsonArray("items");

        List<Category> categoryList = new ArrayList<Category>();
        for (JsonElement item : items) {
            Category category = new Category();
            category.setName(item.getAsJsonObject().get("name").getAsString());
            categoryList.add(category);
        }
        return categoryList;
    }

    public List<New> getNew() throws IOException, InterruptedException {
        String uri = this.programArguments.getApiPath()
                .concat("/v1/browse/new-releases?country=MX&limit=10&offset=5");
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .header("Authorization", "Bearer " + tokenObject.get("access_token").getAsString())
                .GET()
                .build();
        HttpResponse<String> httpResponse = this.httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        JsonObject jsonObject = JsonParser.parseString(httpResponse.body()).getAsJsonObject();
        JsonObject albums = jsonObject.getAsJsonObject("albums");
        JsonArray items = albums.getAsJsonArray("items");

        List<New> newList = new ArrayList<New>();
        for (JsonElement item : items) {
            New newElement = new New();
            newElement.setName(item.getAsJsonObject().get("name").getAsString());
            JsonArray artists = item.getAsJsonObject().getAsJsonArray("artists");

            List<String> artistsList = new ArrayList<>();
            for (JsonElement artist : artists) {
                artistsList.add(artist.getAsJsonObject().get("name").getAsString());
            }
            newElement.setArtists(artistsList);

            JsonObject external_urls = item.getAsJsonObject()
                    .getAsJsonObject("external_urls");
            newElement.setLink(external_urls.get("spotify").getAsString());

            newList.add(newElement);
        }
        return newList;
    }

    public List<Featured> getFeatured() throws IOException, InterruptedException {
        String uri = this.programArguments.getApiPath()
                .concat("/v1/browse/featured-playlists?country=MX&locale=mx_MX&limit=10&offset=5&timestamp=");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyy-MM-dd'T'HH:mm:ss");
        String timeStamp = simpleDateFormat.format(new Date());

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(uri.concat(timeStamp)))
                .header("Authorization", "Bearer " + tokenObject.get("access_token").getAsString())
                .GET()
                .build();
        HttpResponse<String> httpResponse = this.httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        JsonObject jsonObject = JsonParser.parseString(httpResponse.body()).getAsJsonObject();
        JsonObject playlists = jsonObject.getAsJsonObject("playlists");
        JsonArray items = playlists.getAsJsonArray("items");

//        StringBuilder response = new StringBuilder();
        List<Featured> featuredList = new ArrayList<>();
        for (JsonElement item : items) {
            Featured featured = new Featured();
            featured.setName(item.getAsJsonObject().get("name").getAsString());
//            response.append(item.getAsJsonObject().get("name").getAsString()).append("\n");
            JsonObject external_urls = item.getAsJsonObject()
                    .getAsJsonObject("external_urls");
            featured.setLink(external_urls.get("spotify").getAsString());
//            response.append(external_urls.get("spotify").getAsString())
//                    .append("\n");
            featuredList.add(featured);
        }
        return featuredList;
//        return response.toString().strip();
    }

    public Pair<Boolean, Object> getPlaylists(String mood) throws IOException, InterruptedException {
        String uri = this.programArguments.getApiPath()
                .concat("/v1/browse/categories/")
                .concat(URLEncoder.encode(mood, StandardCharsets.UTF_8))
                .concat("/playlists")
                .concat("?country=MX&limit=10&offset=5");
        uri = uri.replace("+", "-");
        System.out.println(URI.create(uri));
        try {
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(uri))
                    .header("Authorization", "Bearer " + tokenObject.get("access_token").getAsString())
                    .GET()
                    .build();
            HttpResponse<String> httpResponse = this.httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            System.out.println(httpResponse);
            JsonObject jsonObject = JsonParser.parseString(httpResponse.body()).getAsJsonObject();

            if (httpResponse.statusCode() != 200) {
                JsonElement error = jsonObject.get("error");
                JsonElement message = error.getAsJsonObject().get("message");
                return new Pair<>(false, message.getAsString());
            }
            JsonObject playlists = jsonObject.getAsJsonObject("playlists");
            JsonArray items = playlists.getAsJsonArray("items");

            StringBuilder response = new StringBuilder();
            List<Playlist> playlistList = new ArrayList<>();
            for (JsonElement item : items) {
                JsonObject playlist = item.getAsJsonObject();
                Playlist pl = new Playlist();
                pl.setName(playlist.get("name").getAsString());
                pl.setLink(playlist.getAsJsonObject("external_urls").get("spotify").getAsString());
//            response.append(playlist.get("name").getAsString());
//            response.append("\n");
//            response.append(playlist.getAsJsonObject("external_urls").get("spotify").getAsString());
//            response.append("\n\n");
                playlistList.add(pl);
            }
            return new Pair<>(true, playlistList);
        } catch (Exception e) {
            return new Pair<>(false, "Webpage not found!\n---PAGE 1 OF 2---");
        }
    }

}
