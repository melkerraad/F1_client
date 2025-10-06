package com.f1client.loader;

import com.f1client.model.Stint;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.List;

public class StintLoader {
    private static final Gson gson = new Gson();

    public static List<Stint> loadStintsForSession(int sessionKey) throws Exception {
        String url = "https://api.openf1.org/v1/stints?session_key=" + sessionKey;
        InputStreamReader reader = new InputStreamReader(new URL(url).openStream());

        Type listType = new TypeToken<List<Stint>>() {}.getType();
        return gson.fromJson(reader, listType);
    }
}
