package com.f1client.loader;

import com.f1client.model.RaceControlEvent;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.lang.reflect.Type;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class RaceControlEventLoader implements DataLoader<RaceControlEvent> {
    @Override
    public java.util.List<RaceControlEvent> loadData() throws IOException, InterruptedException {
        String url = "https://api.openf1.org/v1/race_control";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String json = response.body();

        Gson gson = new Gson();
        Type listType = new TypeToken<List<RaceControlEvent>>(){}.getType();
        return gson.fromJson(json, listType);
    }
}