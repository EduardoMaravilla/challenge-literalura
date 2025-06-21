package org.maravill.literalura.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jline.terminal.Terminal;
import org.maravill.literalura.dto.BookDto;
import org.maravill.literalura.dto.GutenbergResponse;
import org.maravill.literalura.services.IBooksApiService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GutendexApiServiceImpl implements IBooksApiService {

    private static final String URL_API = "https://gutendex.com/books/";
    private static final int OK_VALUE = 200;

    private final Terminal terminal;
    private final ObjectMapper objectMapper;

    public GutendexApiServiceImpl(ObjectMapper objectMapper,Terminal terminal) {
        this.objectMapper = objectMapper;
        this.terminal = terminal;
    }

    @Override
    public List<BookDto> getBooksByTitle(String title) {
        String encodedTitle = URLEncoder.encode(title, StandardCharsets.UTF_8);
        URI uri = URI.create(URL_API + "?search=" + encodedTitle);

        List<BookDto> allBooks = new ArrayList<>();

        while (uri != null) {
            Optional<String> response = getBookInfo(uri);

            if (response.isEmpty()) {
                break;
            }

            try {
                GutenbergResponse gutenbergResponse = objectMapper.readValue(response.get(), new TypeReference<>() {});
                allBooks.addAll(gutenbergResponse.results());

                // Imprimir la página actual y cuántos libros lleva acumulados
                terminal.writer().printf("📘 Página procesada: %s | Libros acumulados: %d de %d%n", uri, allBooks.size(),gutenbergResponse.count());
                terminal.writer().flush();

                // Obtener la siguiente URL
                uri = gutenbergResponse.next() != null ? URI.create(gutenbergResponse.next()) : null;

            } catch (JsonProcessingException e) {
                terminal.writer().println("❌ Error al procesar la respuesta JSON: " + e.getMessage());
                break;
            }
        }

        return allBooks;
    }

    @Override
    public List<BookDto> searchTop10Books() {
        URI uri = URI.create(URL_API + "?sort=popular");

        Optional<String> response = getBookInfo(uri);

        if (response.isEmpty()) {
            return List.of();
        }

        try {
            GutenbergResponse gutenbergResponse = objectMapper.readValue(response.get(), new TypeReference<>() {});
            return gutenbergResponse.results().stream().limit(10L).toList();
        } catch (JsonProcessingException e) {
            terminal.writer().println("❌ Error al procesar la respuesta JSON: " + e.getMessage());
            return List.of();
        }
    }


    private static Optional<String> getBookInfo(URI bookUrl) {
        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(bookUrl).GET().build();
            HttpResponse<String> response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != OK_VALUE) {
                return Optional.empty();
            }
            return Optional.of(response.body());
        } catch (IOException | InterruptedException _) {
            Thread.currentThread().interrupt();
        }
        return Optional.empty();
    }
}
