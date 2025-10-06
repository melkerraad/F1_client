package com.f1client;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.lang.reflect.Type;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Test {
    public static void main(String[] args) {
        String url = "https://api.openf1.org/v1/drivers";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String json = response.body();

            Gson gson = new Gson();
            Type listType = new TypeToken<List<Driver>>(){}.getType();
            List<Driver> drivers = gson.fromJson(json, listType);

            // Print the first few drivers as a test
            for (int i = 0; i < Math.min(5, drivers.size()); i++) {
                Driver d = drivers.get(i);
                System.out.println(d.getFull_name() + " (" + d.getTeam_name() + ")");
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
