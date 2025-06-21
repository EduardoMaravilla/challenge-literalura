package org.maravill.literalura.config;

import org.jline.terminal.Terminal;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class LiteraluraShutdownHook implements ApplicationRunner {

    private final Terminal terminal;
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_CYAN = "\u001B[36m";

    private static final String SHUTDOWN_MESSAGE = """
           -----------------------------------------------------👋 ¡Gracias por usar Literalura CLI! -----------------------------------------------------
           ------------------------------------------------🌟 Sigue explorando las obras del conocimiento.------------------------------------------------
           ---------------------------------------------------------------¡Hasta la próxima! 🚀-----------------------------------------------------------
           """;

    public LiteraluraShutdownHook(Terminal terminal) {
        this.terminal = terminal;
    }


    @Override
    public void run(ApplicationArguments args) {
        Runtime.getRuntime().addShutdownHook(new Thread(this::printShutdownMessage));

    }

    public void printShutdownMessage() {
        terminal.writer().println(ANSI_CYAN + SHUTDOWN_MESSAGE + ANSI_RESET);
        terminal.flush();
    }

}
