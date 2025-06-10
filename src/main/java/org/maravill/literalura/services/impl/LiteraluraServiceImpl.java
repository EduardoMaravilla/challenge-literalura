package org.maravill.literalura.services.impl;

import org.maravill.literalura.config.LiteraluraShutdownHook;
import org.maravill.literalura.dto.BookDto;
import org.maravill.literalura.dto.PersonDto;
import org.maravill.literalura.services.*;
import org.maravill.literalura.utils.BookComparators;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

    private List<BookDto> allBooks = new ArrayList<>();
    private List<BookDto> currentBooks = new ArrayList<>();
    private List<PersonDto> allAuthors = new ArrayList<>();
    private List<PersonDto> currentAuthors = new ArrayList<>();
    private int pageBooks = 0;
    private int pageAuthors = 0;
    private int totalBookPages = 0;
    private int totalAuthorPages = 0;
    private static final int PAGE_SIZE = 10;

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
                %s\t %s\t\t Ejemplo: menu 1 <título del libro>%s
                %s\t %s2️⃣  Listar libros registrados%s
                %s\t %s\t\t Ejemplo: menu 2%s
                %s\t %s3️⃣  Listar autores registrados%s
                %s\t %s\t\t Ejemplo: menu 3%s
                %s\t %s4️⃣  Listar autores vivos en un año específico%s
                %s\t %s\t\t Ejemplo: menu 4 <año>%s
                %s\t %s5️⃣  Listar libros por idioma(en,fr,...)%s
                %s\t %s\t\t Ejemplo: menu 5 <idioma>%s
                %s\t %s6️⃣  Listar libros por autor%s
                %s\t %s\t\t Ejemplo: menu 6 <nombre del autor>%s
                %s\t %s7️⃣  Listar los 10 mejores libros%s
                %s\t %s\t\t Ejemplo: menu 7%s
                %s\t %s8️⃣  Datos estadísticos locales%s
                %s\t %s\t\t Ejemplo: menu 8%s
                %s\t %s9️⃣  Salir de la aplicación%s
                %s\t %s\t\t Ejemplo: menu 9%s
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
                SPACE_MENU, GREEN, RESET,
                SPACE_MENU, GREEN, RESET,
                SPACE_MENU, GREEN, RESET,
                SPACE_MENU, GREEN, RESET,
                SPACE_MENU, GREEN, RESET,
                SPACE_MENU, GREEN, RESET,
                SPACE_MENU, GREEN, RESET,
                SPACE_MENU, GREEN, RESET,
                SPACE_MENU, RED, RESET,
                SPACE_MENU, RED, RESET,
                SPACE_MENU, SEPARATOR
        );
    }


    @Transactional
    @Override
    public String searchBookByTitle(String title) {
        this.allBooks = new ArrayList<>();
        boolean isTitleInHistory = searchTitleHistoryService.isTitleInHistory(title);
        if (isTitleInHistory) {
            this.allBooks = new ArrayList<>(bookService.findBooksByTitle(title));
            if (this.allBooks.isEmpty()) {
                return RED + "❌ No se encontraron libros con el título: " + title + RESET;
            }
        } else {
            this.allBooks = new ArrayList<>(booksApiService.getBooksByTitle(title));
            searchTitleHistoryService.saveSearchTitleHistory(title);
            if (this.allBooks.isEmpty()) {
                return RED + "❌ No se encontraron libros con el título: " + title + RESET;
            }
            bookService.saveBooks(this.allBooks);
        }
        this.totalBookPages = (int) Math.floor(allBooks.size() / 10.0);
        this.currentBooks = allBooks.subList(0, Math.min(PAGE_SIZE, allBooks.size()));
        return getMenuBooks("📖 Libros encontrados para el título: \"" + title + "\"");

    }

    private String getMenuBooks(String title) {
        return GREEN +
                title + "\n\n" +
                "Total de libros encontrados: " + allBooks.size() + "\n" +
                "Páginas disponibles: " + (totalBookPages + 1) + " de 0 a " + totalBookPages + "\n\n" +

                "📌 Para ver los libros usa los siguientes comandos:\n" + RESET +
                YELLOW + "menu books <num>      → Muestra los libros de la página actual o la página que decidas\n" + RESET +
                YELLOW + "menu books-next       → Muestra los libros de la siguiente página\n" + RESET +
                YELLOW + "menu books-prev       → Muestra los libros de la página anterior\n\n" +

                GREEN + "📚 Opciones de ordenamiento:\n" + RESET +
                YELLOW + "title-asc             → Ordena por título de A a Z\n" + RESET +
                YELLOW + "title-desc            → Ordena por título de Z a A\n" +
                YELLOW + "author-asc            → Ordena por autor (A → Z)\n" +
                YELLOW + "author-desc           → Ordena por autor (Z → A)\n" +
                YELLOW + "copyright-first       → Muestra primero los libros con copyright\n" +
                YELLOW + "public-domain-first   → Muestra primero los libros en dominio público\n" +
                YELLOW + "download-asc          → Ordena por cantidad de descargas de menor a mayor\n" + RESET +
                YELLOW + "download-desc         → Ordena por cantidad de descargas de mayor a menor\n\n" + RESET +

                GREEN + "Ejemplo de uso:\n" + RESET +
                YELLOW + "menu books title-asc\n" +
                "──────────────────────────────────────────────\n\n" + RESET;
    }

    private String getMenuAuthors(String title) {
        return GREEN +
                title + "\n\n" +
                "Total de autores encontrados: " + allAuthors.size() + "\n" +
                "Páginas disponibles: " + (totalAuthorPages + 1) + " de 0 a " + totalAuthorPages + "\n\n" +

                "📌 Para ver los autores usa los siguientes comandos:\n" + RESET +
                YELLOW + "menu authors <num>      → Muestra los autores de la página actual o la página que decidas\n" + RESET +
                YELLOW + "menu authors-next       → Muestra los autores de la siguiente página\n" + RESET +
                YELLOW + "menu authors-prev       → Muestra los autores de la página anterior\n\n" +
                GREEN + "📚 Opciones de ordenamiento:\n" + RESET +
                YELLOW + "name-asc               → Ordena por nombre de autor de A a Z\n" + RESET +
                YELLOW + "name-desc              → Ordena por nombre de autor de Z a A\n\n" +

                GREEN + "Ejemplo de uso:\n" + RESET +
                YELLOW + "menu authors name-asc\n" +
                "──────────────────────────────────────────────\n\n" + RESET;
    }


    @Override
    public String listRegisteredBooks() {
        this.allBooks = new ArrayList<>(bookService.getAllBooks());
        if (allBooks.isEmpty()) {
            return RED + "❌ No hay libros registrados localmente." + RESET;
        }
        this.totalBookPages = (int) Math.floor(allBooks.size() / 10.0);
        this.currentBooks = allBooks.subList(0, Math.min(PAGE_SIZE, allBooks.size()));
        return getMenuBooks("📖 Libros registrados localmente:");
    }

    @Override
    public String listRegisteredAuthors() {
        this.allAuthors = new ArrayList<>(personService.getAllAuthors());
        if (this.allAuthors.isEmpty()) {
            return RED + "❌ No hay autores registrados." + RESET;
        }
        this.totalAuthorPages = (int) Math.floor(allAuthors.size() / 10.0);
        this.currentAuthors = allAuthors.subList(0, Math.min(PAGE_SIZE, allAuthors.size()));
        this.pageAuthors = 0;
        return getMenuAuthors("🖋️ Autores registrados:");
    }

    @Override
    public String listLivingAuthorsInYear(int year) {
        this.allAuthors = new ArrayList<>(personService.getLivingAuthorsInYear(year));
        if (this.allAuthors.isEmpty()) {
            return RED + "❌ No hay autores vivos en el año " + year + "." + RESET;
        }
        this.totalAuthorPages = (int) Math.floor(allAuthors.size() / 10.0);
        this.currentAuthors = allAuthors.subList(0, Math.min(PAGE_SIZE, allAuthors.size()));
        this.pageAuthors = 0;
        return getMenuAuthors("🖋️ Autores vivos en el año " + year + ":");
    }


    @Override
    public String listBooksByLanguage(String language) {
        this.allBooks = new ArrayList<>(bookService.getBooksByLanguage(language));
        if (this.allBooks.isEmpty()) {
            return RED + "❌ No hay libros registrados en el idioma: " + language + RESET;
        }
        this.totalBookPages = (int) Math.floor(allBooks.size() / 10.0);
        this.currentBooks = allBooks.subList(0, Math.min(PAGE_SIZE, allBooks.size()));
        return getMenuBooks("📖 Libros registrados en el idioma: " + language);
    }

    @Override
    public void exit() {
        literaluraShutdownHook.printShutdownMessage();
    }

    @Override
    public String listBooksByAuthor(String authorString) {
        this.allBooks = new ArrayList<>(bookService.getBooksByAuthor(authorString));
        if (this.allBooks.isEmpty()) {
            return RED + "❌ No hay libros registrados para el autor: " + authorString + RESET;
        }
        this.totalBookPages = (int) Math.floor(allBooks.size() / 10.0);
        this.currentBooks = allBooks.subList(0, Math.min(PAGE_SIZE, allBooks.size()));
        return getMenuBooks("📖 Libros registrados para el autor: " + authorString);
    }

    @Override
    public String listTop10Books() {
        List<BookDto> topBooks = booksApiService.searchTop10Books();
        return formatBooks(topBooks, "✨Los 10 mejores libros:", 0);
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

    @Override
    public String listPageBooks(String args) {
        if (this.allBooks.isEmpty()) {
            return RED + "❌ No hay libros registrados. Por favor, lista los libros registrados o haz una búsqueda." + RESET;
        }
        if (args == null || args.isEmpty()) {
            return formatBooks(currentBooks, "📖 Libros de la página: " + this.pageBooks + " de " + this.totalBookPages, this.pageBooks);
        }
        try {
            int requestedPage = Integer.parseInt(args.trim());
            if (requestedPage < 0 || requestedPage > totalBookPages) {
                return RED + "❌ El número de página debe ser mayor o igual a 0 o menor o igual a " + totalBookPages + RESET;
            }
            this.pageBooks = requestedPage;
            int fromIndex = requestedPage * PAGE_SIZE;
            int toIndex = Math.min(fromIndex + PAGE_SIZE, allBooks.size());
            this.currentBooks = allBooks.subList(fromIndex, toIndex);
            return formatBooks(currentBooks, "📖 Libros de la página: " + this.pageBooks + " de " + this.totalBookPages, this.pageBooks);
        } catch (NumberFormatException _) {
            return switch (args) {
                case "title-asc" -> {
                    this.allBooks.sort(BookComparators.TITLE_ASC);
                    this.pageBooks = 0;
                    this.currentBooks = allBooks.subList(0, 10);
                    yield formatBooks(currentBooks, "📖 Libros ordenados por título (A → Z):", 0);
                }
                case "title-desc" -> {
                    this.allBooks.sort(BookComparators.TITLE_DESC);
                    this.pageBooks = 0;
                    this.currentBooks = allBooks.subList(0, 10);
                    yield formatBooks(currentBooks, "📖 Libros ordenados por título (Z → A):", 0);
                }
                case "author-asc" -> {
                    this.allBooks.sort(BookComparators.AUTHOR_ASC);
                    this.pageBooks = 0;
                    this.currentBooks = allBooks.subList(0, 10);
                    yield formatBooks(currentBooks, "📖 Libros ordenados por autor (A → Z):", 0);
                }
                case "author-desc" -> {
                    this.allBooks.sort(BookComparators.AUTHOR_DESC);
                    this.pageBooks = 0;
                    this.currentBooks = allBooks.subList(0, 10);
                    yield formatBooks(currentBooks, "📖 Libros ordenados por autor (Z → A):", 0);
                }
                case "copyright-first" -> {
                    this.allBooks.sort(BookComparators.COPYRIGHT_FIRST);
                    this.pageBooks = 0;
                    this.currentBooks = allBooks.subList(0, 10);
                    yield formatBooks(currentBooks, "📖 Libros con copyright primero:", 0);
                }
                case "public-domain-first" -> {
                    this.allBooks.sort(BookComparators.PUBLIC_DOMAIN_FIRST);
                    this.pageBooks = 0;
                    this.currentBooks = allBooks.subList(0, 10);
                    yield formatBooks(currentBooks, "📖 Libros en dominio público primero:", 0);
                }
                case "download-asc" -> {
                    this.allBooks.sort(BookComparators.DOWNLOAD_ASC);
                    this.pageBooks = 0;
                    this.currentBooks = allBooks.subList(0, 10);
                    yield formatBooks(currentBooks, "📖 Libros ordenados por descargas (menor → mayor):", 0);
                }
                case "download-desc" -> {
                    this.allBooks.sort(BookComparators.DOWNLOAD_DESC);
                    this.pageBooks = 0;
                    this.currentBooks = allBooks.subList(0, 10);
                    yield formatBooks(currentBooks, "📖 Libros ordenados por descargas (mayor → menor):", 0);
                }
                default ->
                        "❌ Opción no válida. Por favor, usa un número de página o un comando de ordenamiento válido.";
            };
        }
    }

    @Override
    public String listNextBooks() {
        if (this.allBooks.isEmpty()) {
            return RED + "❌ No hay libros registrados. Por favor, lista los libros registrados o haz una búsqueda." + RESET;
        }
        if (pageBooks < totalBookPages) {
            pageBooks++;
            int fromIndex = pageBooks * PAGE_SIZE;
            int toIndex = Math.min(fromIndex + PAGE_SIZE, allBooks.size());
            currentBooks = allBooks.subList(fromIndex, toIndex);
            return formatBooks(currentBooks, "📖 Libros de la página: " + pageBooks + " de " + totalBookPages, pageBooks);
        } else {
            return RED + "❌ No hay más páginas disponibles." + RESET;
        }
    }

    @Override
    public String listPreviousBooks() {
        if (this.allBooks.isEmpty()) {
            return RED + "❌ No hay libros registrados. Por favor, lista los libros registrados o haz una búsqueda." + RESET;
        }
        if (pageBooks > 0) {
            pageBooks--;
            int fromIndex = pageBooks * PAGE_SIZE;
            int toIndex = Math.min(fromIndex + PAGE_SIZE, allBooks.size());
            currentBooks = allBooks.subList(fromIndex, toIndex);
            return formatBooks(currentBooks, "📖 Libros de la página: " + pageBooks + " de " + totalBookPages, pageBooks);
        } else {
            return RED + "❌ No hay páginas anteriores disponibles." + RESET;
        }
    }

    @Override
    public String listPageAuthors(String args) {
        if (this.allAuthors.isEmpty()) {
            return RED + "❌ No hay autores registrados. Por favor, lista los autores registrados o haz una búsqueda." + RESET;
        }
        if (args == null || args.isEmpty()) {
            return formatAuthors(currentAuthors, "🖋️ Autores de la página: " + this.pageAuthors + " de " + this.totalAuthorPages, this.pageAuthors);
        }
        try {
            int requestedPage = Integer.parseInt(args.trim());
            if (requestedPage < 0 || requestedPage > totalAuthorPages) {
                return RED + "❌ El número de página debe ser mayor o igual a 0 o menor o igual a " + totalAuthorPages + RESET;
            }
            this.pageAuthors = requestedPage;
            int fromIndex = requestedPage * PAGE_SIZE;
            int toIndex = Math.min(fromIndex + PAGE_SIZE, allAuthors.size());
            this.currentAuthors = allAuthors.subList(fromIndex, toIndex);
            return formatAuthors(currentAuthors, "🖋️ Autores de la página: " + this.pageAuthors + " de " + this.totalAuthorPages, this.pageAuthors);
        } catch (NumberFormatException _) {
            return switch (args) {
                case "name-asc" -> {
                    this.allAuthors.sort((a1, a2) -> a1.name().compareToIgnoreCase(a2.name()));
                    this.pageAuthors = 0;
                    this.currentAuthors = allAuthors.subList(0, Math.min(PAGE_SIZE, allAuthors.size()));
                    yield formatAuthors(currentAuthors, "🖋️ Autores ordenados por nombre (A → Z):", 0);
                }
                case "name-desc" -> {
                    this.allAuthors.sort((a1, a2) -> a2.name().compareToIgnoreCase(a1.name()));
                    this.pageAuthors = 0;
                    this.currentAuthors = allAuthors.subList(0, Math.min(PAGE_SIZE, allAuthors.size()));
                    yield formatAuthors(currentAuthors, "🖋️ Autores ordenados por nombre (Z → A):", 0);
                }
                default ->
                        RED + "❌ Opción no válida. Por favor, usa un número de página o un comando de ordenamiento válido." + RESET;
            };
        }
    }

    @Override
    public String listNextAuthors() {
        if (this.allAuthors.isEmpty()) {
            return RED + "❌ No hay autores registrados. Por favor, lista los autores registrados o haz una búsqueda." + RESET;
        }
        if (pageAuthors < totalAuthorPages) {
            pageAuthors++;
            int fromIndex = pageAuthors * PAGE_SIZE;
            int toIndex = Math.min(fromIndex + PAGE_SIZE, allAuthors.size());
            currentAuthors = allAuthors.subList(fromIndex, toIndex);
            return formatAuthors(currentAuthors, "🖋️ Autores de la página: " + pageAuthors + " de " + totalAuthorPages, pageAuthors);
        } else {
            return RED + "❌ No hay más páginas de autores disponibles." + RESET;
        }
    }

    @Override
    public String listPreviousAuthors() {
        if (this.allAuthors.isEmpty()) {
            return RED + "❌ No hay autores registrados. Por favor, lista los autores registrados o haz una búsqueda." + RESET;
        }
        if (pageAuthors > 0) {
            pageAuthors--;
            int fromIndex = pageAuthors * PAGE_SIZE;
            int toIndex = Math.min(fromIndex + PAGE_SIZE, allAuthors.size());
            currentAuthors = allAuthors.subList(fromIndex, toIndex);
            return formatAuthors(currentAuthors, "🖋️ Autores de la página: " + pageAuthors + " de " + totalAuthorPages, pageAuthors);
        } else {
            return RED + "❌ No hay páginas anteriores de autores disponibles." + RESET;
        }
    }

    private String formatBooks(List<BookDto> books, String header, int currentPage) {
        if (books == null || books.isEmpty()) {
            return RED + "📕 No se encontraron libros para mostrar." + RESET;
        }

        StringBuilder result = new StringBuilder(GREEN)
                .append("──────────────────────────────────────────────\n")
                .append("📚 ").append(header).append("\n")
                .append("──────────────────────────────────────────────\n\n");

        int count = currentPage * PAGE_SIZE + 1;
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
            if (book.downloadCount() != null) {
                result.append("\n")
                        .append("   📥 Descargas: ").append(book.downloadCount());
            }
            if (book.copyright() != null) {
                result.append("\n")
                        .append("   📜 Copyright: ").append(book.copyright() ? "Sí" : "No");
            }

            result.append("\n\n");
        }

        result.append("──────────────────────────────────────────────\n");
        return result.append(RESET).toString();
    }


    private String formatAuthors(List<PersonDto> authors, String header, int currentPage) {
        StringBuilder result = new StringBuilder(GREEN)
                .append("🧑‍🏫──────────────────────────────────────────────\n")
                .append("📜  ").append(header.toUpperCase()).append("\n")
                .append("🧑‍🏫──────────────────────────────────────────────\n\n");

        int count = currentPage * PAGE_SIZE + 1;
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