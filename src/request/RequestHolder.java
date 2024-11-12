package org.example.request;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class RequestHolder {
    @Getter
    private static final List<Request> requests = new ArrayList<>();

    private RequestHolder() {
    }

    public static void printRequests() {
        for (Request request : requests) {
            request.printRequest();
        }
    }

    public static void addRequest(Request request) {
        requests.add(request);
    }

    public static void removeRequest(Request request) {
        requests.remove(request);
    }

}
