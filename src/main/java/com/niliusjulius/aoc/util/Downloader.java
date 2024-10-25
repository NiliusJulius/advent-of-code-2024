package com.niliusjulius.aoc.util;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.CookieManager;
import java.net.HttpCookie;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;
import java.util.stream.IntStream;

public class Downloader {
    private final String sessionId;

    public Downloader(String sessionId) {
        this.sessionId = sessionId;
    }

    public void downloadInputs(String year) {
        HttpClient httpClient = createHttpClient();
        IntStream.rangeClosed(1, 25)
                .filter(i -> Instant.parse(String.format(year + "-12-%02dT05:00:00Z", i)).isBefore(Instant.now()))
                .forEach(i -> {
                    try {
                        System.out.println("Downloading Day " + i);
                        HttpRequest httpRequest = HttpRequest.newBuilder().GET()
                                .uri(new URI("https://adventofcode.com/" + year + "/day/" + i + "/input")).build();
                        String data = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString()).body();
                        File target = new File("src/main/resources/" + String.format("day%02d.txt", i));
                        try (PrintWriter pw = new PrintWriter(target)) {
                            pw.print(data);
                        }
                        System.out.println("Saved: " + target.getAbsolutePath());
                    } catch (IOException | InterruptedException | URISyntaxException e) {
                        throw new IllegalStateException("Could not download input: " + i, e);
                    }
                });
    }

    private HttpClient createHttpClient() {
        try {
            HttpCookie sessionCookie = new HttpCookie("session", sessionId);
            sessionCookie.setPath("/");
            sessionCookie.setVersion(0);
            CookieManager cookieManager = new CookieManager();
            cookieManager.getCookieStore().add(new URI("https://adventofcode.com/"), sessionCookie);
            return HttpClient.newBuilder().cookieHandler(cookieManager).build();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
