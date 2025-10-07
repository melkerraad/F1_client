package com.f1client.loader;
import com.f1client.model.Position;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Collections;

public class PositionLoader implements DataLoader<Position> {
    @Override
    public List<Position> loadData() throws IOException, InterruptedException { //Too large data set, use loadData(sessionId) instead
        String url = "https://api.openf1.org/v1/sessions";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String json = response.body();

        Gson gson = new Gson();
        Type listType = new TypeToken<List<Position>>(){}.getType();

        JsonElement element = JsonParser.parseString(json);
        if (element.isJsonArray()) {
            return gson.fromJson(element, listType);
        } else if (element.isJsonObject()) {
            // Try to parse as a single Position object
            Position position = gson.fromJson(element, Position.class);
            return Collections.singletonList(position);
        } else {
            // Unexpected format, print for debugging
            System.err.println("Unexpected JSON: " + json);
            throw new IOException("Unexpected JSON format from API");
        }
    }
    public List<Position> loadData(int sessionId) throws IOException, InterruptedException {
        String url = "https://api.openf1.org/v1/position?session_key=" + sessionId;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String json = response.body();

        Gson gson = new Gson();
        Type listType = new TypeToken<List<Position>>(){}.getType();

        JsonElement element = JsonParser.parseString(json);
        if (element.isJsonArray()) {
            return gson.fromJson(element, listType);
        } else if (element.isJsonObject()) {
            // Try to parse as a single Position object
            Position position = gson.fromJson(element, Position.class);
            return Collections.singletonList(position);
        } else {
            // Unexpected format, print for debugging
            System.err.println("Unexpected JSON: " + json);
            throw new IOException("Unexpected JSON format from API");
        }
    }
	
}
