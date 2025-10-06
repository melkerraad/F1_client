package com.f1client.loader;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import com.f1client.model.Lap;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class LapLoader implements DataLoader<Lap> {
    private static final String BASE_URL = "https://api.openf1.org/v1/laps";
    private final int sessionKey;

    public LapLoader(int sessionKey) {
        this.sessionKey = sessionKey;
    }

    @Override
    public List<Lap> loadData() throws IOException, InterruptedException {  //Cannot get all data at once, need to specify session_key
        String url = BASE_URL + "?session_key=" + sessionKey;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String json = response.body();

        Gson gson = new Gson();
        Type listType = new TypeToken<List<Lap>>() {}.getType();

        List<Lap> laps = gson.fromJson(json, listType);

        if (laps != null) {
            laps.forEach(Lap::postDeserialize);
        }

        return laps;
    }
}
