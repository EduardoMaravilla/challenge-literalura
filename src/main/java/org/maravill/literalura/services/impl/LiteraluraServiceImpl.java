package org.maravill.literalura.services.impl;

import org.maravill.literalura.config.LiteraluraShutdownHook;
import org.maravill.literalura.dto.BookDto;
import org.maravill.literalura.dto.PersonDto;
import org.maravill.literalura.services.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.IntSummaryStatistics;
import java.util.List;

@Service
public class LiteraluraServiceImpl implements ILiteraluraService {

    private static final String SPACE_MENU = "\t".repeat(11);
    public static final String RESET = "\u001B[0m";
    public static final String CYAN = "\u001B[36m";
    public static final String YELLOW = "\u001B[33m";
    public static final String GREEN = "\u001B[32m";
    public static final String RED = "\u001B[31m";
    public static final String SEPARATOR = CYAN + "═══════════════════════════════════════════════════════" + RESET;

    private final LiteraluraShutdownHook literaluraShutdownHook;
    private final IBooksApiService booksApiService;
    private final IBookService bookService;
    private final IPersonService personService;
    private final ISearchTitleHistoryService searchTitleHistoryService;

    public LiteraluraServiceImpl(LiteraluraShutdownHook literaluraShutdownHook,
                                 IBooksApiService booksApiService,
                                 IBookService bookService,
                                 IPersonService personService,
                                 ISearchTitleHistoryService searchTitleHistoryService) {
        this.literaluraShutdownHook = literaluraShutdownHook;
        this.booksApiService = booksApiService;
        this.bookService = bookService;
        this.personService = personService;
        this.searchTitleHistoryService = searchTitleHistoryService;
    }


    @Override
    public String showMainMenu() {
        return """
                %s📚%s📚
                %s\t\t\t%s🧭  MENÚ PRINCIPAL - LITERALURA CLI%s
                %s📚%s📚
                %s\t %s1️⃣  Buscar libro por título(Api Gutendex)%s
                %s\t %s2️⃣  Listar libros registrados%s
                %s\t %s3️⃣  Listar autores registrados%s
                %s\t %s4️⃣  Listar autores vivos en un año específico%s
                %s\t %s5️⃣  Listar libros por idioma%s
                %s\t %s6️⃣  Listar libros por autor%s
                %s\t %s7️⃣  Listar los diez mejores libros%s
                %s\t %s8️⃣  Datos estadísticos locales%s
                %s\t %s9️⃣  Salir de la aplicación%s
                %s📕%s📕
                """.formatted(
                SPACE_MENU, SEPARATOR,
                SPACE_MENU, YELLOW, RESET,
                SPACE_MENU, SEPARATOR,
                SPACE_MENU, GREEN, RESET,
                SPACE_MENU, GREEN, RESET,
                SPACE_MENU, GREEN, RESET,
                SPACE_MENU, GREEN, RESET,
                SPACE_MENU, GREEN, RESET,
                SPACE_MENU, GREEN, RESET,
                SPACE_MENU, GREEN, RESET,
                SPACE_MENU, GREEN, RESET,
                SPACE_MENU, RED, RESET,
                SPACE_MENU, SEPARATOR
        );
    }


    @Transactional(readOnly = true)
    @Override
    public String searchBookByTitle(String title) {

        boolean isTitleInHistory = searchTitleHistoryService.isTitleInHistory(title);
        List<BookDto> books;
        if (isTitleInHistory) {
           books = bookService.findBooksByTitle(title);
            if (books.isEmpty()) {
                return RED + "❌ No se encontraron libros con el título: " + title + RESET;
            }
        } else {
            books = booksApiService.getBooksByTitle(title);
            searchTitleHistoryService.saveSearchTitleHistory(title);
            if (books.isEmpty()) {
                return RED + "❌ No se encontraron libros con el título: " + title + RESET;
            }
            bookService.saveBooks(books);

        }
        return formatBooks(books, "📖 Libros encontrados para el título: " + title);
    }


    @Override
    public String listRegisteredBooks() {
        List<BookDto> books = bookService.getAllBooks();
        return formatBooks(books, "📖 Libros registrados:");
    }

    @Override
    public String listRegisteredAuthors() {
        List<PersonDto> authors = personService.getAllAuthors();
        if (authors.isEmpty()) {
            return RED + "❌ No hay autores registrados." + RESET;
        }
        return formatAuthors(authors, "🖋️ Autores registrados:");
    }

    @Override
    public String listLivingAuthorsInYear(int year) {
        List<PersonDto> authors = personService.getLivingAuthorsInYear(year);
        if (authors.isEmpty()) {
            return RED + "❌ No hay autores vivos en el año " + year + "." + RESET;
        }
        return formatAuthors(authors, "🖋️ Autores vivos en el año " + year + ":");
    }


    @Override
    public String listBooksByLanguage(String language) {
        List<BookDto> books = bookService.getBooksByLanguage(language);
        if (books.isEmpty()) {
            return RED + "❌ No hay libros registrados en el idioma: " + language + RESET;
        }
        return formatBooks(books, "📖 Libros registrados en el idioma: " + language);
    }

    @Override
    public void exit() {
        literaluraShutdownHook.printShutdownMessage();
    }

    @Override
    public String listBooksByAuthor(String authorString) {
        List<BookDto> books = bookService.getBooksByAuthor(authorString);
        if (books.isEmpty()) {
            return RED + "❌ No hay libros registrados para el autor: " + authorString + RESET;
        }
        return formatBooks(books, "📖 Libros registrados para el autor: " + authorString);
    }

    @Override
    public String listTop10Books() {
        List<BookDto> topBooks = booksApiService.searchTop10Books();
        return formatBooks(topBooks, "✨Los 10 mejores libros:");
    }

    @Transactional(readOnly = true)
    @Override
    public String localStatistics() {
        List<BookDto> books = bookService.getAllBooks();
        List<PersonDto> authors = books.stream().parallel()
                .flatMap(b -> b.authors().stream()).distinct().toList();
        List<PersonDto> translators = books.stream().parallel()
                .flatMap(b -> b.translators().stream()).distinct().toList();
        IntSummaryStatistics statisticsDownload = books.stream()
                .parallel().mapToInt(BookDto::downloadCount).summaryStatistics();
        return GREEN +
                "📊──────────────────────────────────────────────\n" +
                "📈  ESTADÍSTICAS LOCALES DE LITERALURA CLI\n" +
                "📊──────────────────────────────────────────────\n\n" +
                "📚 Total de libros registrados:        " + books.size() + "\n" +
                "🖋️  Total de autores registrados:      " + authors.size() + "\n" +
                "🌍 Total de traductores registrados:   " + translators.size() + "\n\n" +
                "📥 Libros con al menos una descarga:   " + statisticsDownload.getCount() + "\n" +
                "📦 Descargas totales acumuladas:       " + statisticsDownload.getSum() + "\n" +
                "📐 Promedio de descargas por libro:    " +
                (statisticsDownload.getCount() > 0 ? String.format("%.2f", statisticsDownload.getAverage()) : "N/A") + "\n" +
                "📈 Máximo de descargas por libro:      " +
                (statisticsDownload.getCount() > 0 ? statisticsDownload.getMax() : "N/A") + "\n" +
                "📉 Mínimo de descargas por libro:      " +
                (statisticsDownload.getCount() > 0 ? statisticsDownload.getMin() : "N/A") + "\n\n" +
                RESET;
    }

    private String formatBooks(List<BookDto> books, String header) {
        if (books == null || books.isEmpty()) {
            return RED + "📕 No se encontraron libros para mostrar." + RESET;
        }

        StringBuilder result = new StringBuilder(GREEN)
                .append("──────────────────────────────────────────────\n")
                .append("📚 ").append(header).append("\n")
                .append("──────────────────────────────────────────────\n\n");

        int count = 1;
        for (BookDto book : books) {
            result.append(YELLOW).append(count++).append(". 📘 ").append(book.title()).append(RESET).append("\n")
                    .append("   ✍️  Autores: ");

            if (book.authors().isEmpty()) {
                result.append("Desconocido");
            } else {
                result.append(book.authors().stream()
                        .map(PersonDto::name)
                        .reduce((a, b) -> a + ", " + b)
                        .orElse("Autor desconocido"));
            }

            result.append("\n")
                    .append("   🗣️  Idiomas: ").append(String.join(", ", book.languages()));

            if (book.sumaries() != null && !book.sumaries().isEmpty()) {
                result.append("\n")
                        .append("   📝 Resumen: ")
                        .append(String.join(", ", book.sumaries()));
            }

            result.append("\n\n");
        }

        result.append("──────────────────────────────────────────────\n");
        return result.append(RESET).toString();
    }


    private String formatAuthors(List<PersonDto> authors, String header) {
        StringBuilder result = new StringBuilder(GREEN)
                .append("🧑‍🏫──────────────────────────────────────────────\n")
                .append("📜  ").append(header.toUpperCase()).append("\n")
                .append("🧑‍🏫──────────────────────────────────────────────\n\n");

        int count = 1;
        for (PersonDto author : authors) {
            result.append("📖 ").append(count++).append(". ").append(author.name()).append("\n")
                    .append("   📅 Años de vida: ")
                    .append(author.birthYear() != null ? author.birthYear() : "Nacimiento desconocido")
                    .append(" - ")
                    .append(author.deathYear() != null ? author.deathYear() : "Posiblemente vivo")
                    .append("\n\n");
        }

        return result.append(RESET).toString();
    }

}