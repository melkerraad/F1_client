package com.f1client.loader;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import com.f1client.model.Driver;
import com.f1client.model.Pitstop;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class PitstopLoader implements DataLoader<Pitstop> {
    @Override
    public List<Pitstop> loadData() throws IOException, InterruptedException {
        String url = "https://api.openf1.org/v1/pit";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String json = response.body();

        Gson gson = new Gson();
        Type listType = new TypeToken<List<Pitstop>>(){}.getType();
        return gson.fromJson(json, listType);
    }
    
}
