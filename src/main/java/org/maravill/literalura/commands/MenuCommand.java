package org.maravill.literalura.commands;

import org.maravill.literalura.services.ILiteraluraService;
import org.springframework.shell.command.CommandRegistration;
import org.springframework.shell.command.annotation.Command;
import org.springframework.shell.command.annotation.Option;

@Command(command = "menu")
public class MenuCommand {

    private final ILiteraluraService literaluraService;

    public MenuCommand(ILiteraluraService literaluraService) {
        this.literaluraService = literaluraService;
    }

    @Command(command = "", description = "Muestra el menú principal, ejemplo: menu")
    public String showMainMenu(@Option(arity = CommandRegistration.OptionArity.ONE_OR_MORE) String... args) {
        if (args != null && args.length > 0) {
            return "❌ Comando no reconocido. Usa 'menu' para ver el menú principal.";
        }
        return literaluraService.showMainMenu();
    }

    @Command(command = "1", description = "Buscar libro por título, ejemplo: menu 1 <titulo>")
    public String searchBookByTitle(@Option(arity = CommandRegistration.OptionArity.ONE_OR_MORE) String... title) {
        if (title == null || title.length == 0) {
            return "❌ Por favor, ingresa el título del libro. Ejemplo: menu 1 Don Quijote";
        }
        String titleString = String.join(" ", title).trim();
        return literaluraService.searchBookByTitle(titleString);
    }

    @Command(command = "2", description = "Listar libros registrados, ejemplo: menu 2")
    public String listRegisteredBooks() {
        return literaluraService.listRegisteredBooks();
    }

    @Command(command = "3", description = "Listar autores registrados, ejemplo: menu 3")
    public String listRegisteredAuthors() {
        return literaluraService.listRegisteredAuthors();
    }
    @Command(command = "4", description = "Listar autores vivos en un determinado año, ejemplo: menu 4 <año>")
    public String listLivingAuthorsInYear(@Option(arity = CommandRegistration.OptionArity.ONE_OR_MORE) int... year) {
        if (year == null || year.length == 0) {
            return "❌ Por favor, proporciona un año. Ejemplo: menu 4 1850";
        }
        return literaluraService.listLivingAuthorsInYear(year[0]);
    }
    @Command(command = "5", description = "Listar libros por idioma, ejemplo: menu 5 <idioma>")
    public String listBooksByLanguage(@Option(arity = CommandRegistration.OptionArity.ONE_OR_MORE) String... language) {
        if (language == null || language.length == 0) {
            return "❌ Por favor, proporciona un idioma. Ejemplo: menu 5 es";
        }
        String languageString = String.join(" ", language).trim();
        return literaluraService.listBooksByLanguage(languageString);
    }

    @Command(command = "6",description = "Listar libros por autor, ejemplo: menu 6 <autor>")
    public String listBooksByAuthor(@Option(arity = CommandRegistration.OptionArity.ONE_OR_MORE) String... author) {
        if (author == null || author.length == 0) {
            return "❌ Por favor, proporciona un autor. Ejemplo: menu 6 Gabriel García Márquez";
        }
        String authorString = String.join(" ", author).trim();
        return literaluraService.listBooksByAuthor(authorString);
    }

    @Command(command = "7",description = "Listar los mejores 10 libros, ejemplo: menu 7")
    public String listTop10Books() {
        return literaluraService.listTop10Books();
    }

    @Command(command = "8",description = "Datos estadísticos locales, ejemplo: menu 8")
    public String localStatistics() {
        return literaluraService.localStatistics();
    }

    @Command(command = "9", description = "Salir del programa, ejemplo: menu 9")
    public void exit() {
        literaluraService.exit();
        System.exit(0);
    }

    @Command(command = "books", description = "Listar libros por página, ejemplo: menu books <page>")
    public String listPageBooks(@Option(arity = CommandRegistration.OptionArity.ZERO_OR_ONE) String args) {
        if (args != null && args.equals("next")) {
            return literaluraService.listNextBooks();
        } else if (args != null && args.equals("prev")) {
            return literaluraService.listPreviousBooks();
        }else if (args != null && args.equals("help")){
            return literaluraService.getCurrentPageBooksHelp();
        }else {
            return literaluraService.listPageBooks(args);
        }
    }

    @Command(command = "authors", description = "Listar autores por página, ejemplo: menu authors <page>")
    public String listPageAuthors(@Option(arity = CommandRegistration.OptionArity.ZERO_OR_ONE) String args) {
        if (args != null && args.equals("next")) {
            return literaluraService.listNextAuthors();
        } else if (args != null && args.equals("prev")) {
            return literaluraService.listPreviousAuthors();
        } else if (args != null && args.equals("help")) {
            return literaluraService.getCurrentPageAuhtorsHelp();
        } else {
            return literaluraService.listPageAuthors(args);
        }
    }

}
