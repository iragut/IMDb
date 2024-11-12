package org.example.request;

import org.example.IMDB;

public interface RequestsManager {
    void createRequest(IMDB imdb);
    void removeRequest(IMDB imdb);
}
