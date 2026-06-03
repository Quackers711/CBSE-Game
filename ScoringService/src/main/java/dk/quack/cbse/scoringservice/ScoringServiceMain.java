package dk.quack.cbse.scoringservice;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class ScoringServiceMain {
    private static final AtomicInteger SCORE = new AtomicInteger();

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/score", ScoringServiceMain::handleScore);
        server.createContext("/score/increment", ScoringServiceMain::handleIncrement);
        server.createContext("/score/reset", ScoringServiceMain::handleReset);
        server.setExecutor(Executors.newSingleThreadExecutor());
        server.start();
        System.out.println("Scoring service listening on http://127.0.0.1:8080");
    }

    private static void handleScore(HttpExchange exchange) throws IOException {
        if (!"GET".equals(exchange.getRequestMethod())) {
            respond(exchange, 405, "{\"error\":\"method not allowed\"}");
            return;
        }
        respond(exchange, 200, scoreJson());
    }

    private static void handleIncrement(HttpExchange exchange) throws IOException {
        if (!"POST".equals(exchange.getRequestMethod())) {
            respond(exchange, 405, "{\"error\":\"method not allowed\"}");
            return;
        }
        int points = queryInt(exchange.getRequestURI(), "points", 0);
        if (points > 0) {
            SCORE.addAndGet(points);
        }
        respond(exchange, 200, scoreJson());
    }

    private static void handleReset(HttpExchange exchange) throws IOException {
        if (!"POST".equals(exchange.getRequestMethod())) {
            respond(exchange, 405, "{\"error\":\"method not allowed\"}");
            return;
        }
        SCORE.set(0);
        respond(exchange, 200, scoreJson());
    }

    static int queryInt(URI uri, String name, int defaultValue) {
        String query = uri.getRawQuery();
        if (query == null || query.isBlank()) {
            return defaultValue;
        }
        for (String pair : query.split("&")) {
            String[] parts = pair.split("=", 2);
            if (parts.length == 2 && name.equals(parts[0])) {
                try {
                    return Integer.parseInt(parts[1]);
                } catch (NumberFormatException ignored) {
                    return defaultValue;
                }
            }
        }
        return defaultValue;
    }

    private static String scoreJson() {
        return "{\"score\":" + SCORE.get() + "}";
    }

    private static void respond(HttpExchange exchange, int status, String body) throws IOException {
        byte[] response = body.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(status, response.length);
        try (OutputStream outputStream = exchange.getResponseBody()) {
            outputStream.write(response);
        }
    }
}
