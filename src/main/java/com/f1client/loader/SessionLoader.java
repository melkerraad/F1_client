package com.f1client.loader;

import com.f1client.model.Session;
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

public class SessionLoader implements DataLoader<Session> {
    @Override
    public List<Session> loadData() throws IOException, InterruptedException {
        String url = "https://api.openf1.org/v1/sessions";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String json = response.body();

        Gson gson = new Gson();
        Type listType = new TypeToken<List<Session>>(){}.getType();

        JsonElement element = JsonParser.parseString(json);
        if (element.isJsonArray()) {
            return gson.fromJson(element, listType);
        } else if (element.isJsonObject()) {
            // Try to parse as a single Session object
            Session session = gson.fromJson(element, Session.class);
            return Collections.singletonList(session);
        } else {
            // Unexpected format, print for debugging
            System.err.println("Unexpected JSON: " + json);
            throw new IOException("Unexpected JSON format from API");
        }
    }
	
}
