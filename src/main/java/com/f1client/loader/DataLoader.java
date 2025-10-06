package com.f1client.loader;

import java.io.IOException;
import java.util.List;

public interface DataLoader<T> {
    List<T> loadData() throws IOException, InterruptedException;
}