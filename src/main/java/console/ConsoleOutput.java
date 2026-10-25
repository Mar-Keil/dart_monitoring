package console;

public final class ConsoleOutput {

    private static final String SEPARATOR = "------------------------------------------------";

    private ConsoleOutput() {
    }

    public static void section(String title) {
        IO.println("\n" + SEPARATOR);
        IO.println(title);
        IO.println(SEPARATOR);
    }

    public static void prompt(String message) {
        IO.println("\n" + message);
        IO.print("> ");
    }

    public static void error(String message) {
        IO.println("\n  ! " + message);
    }
}
