public enum Color {
    ANSI_RESET("\u001B[0m"),
    ANSI_BLUE_BOLD_BRIGHT("\033[1;94m"),
    ANSI_CYAN_BRIGHT("\033[0;96m"),
    ANSI_GRAY_UNDERLINED("\033[4;90m"),
    ANSI_RED("\033[0;31m"),
    ANSI_RED_BOLD("\033[1;31m"),
    ANSI_BLUE("\033[0;34m"),
    ANSI_GREEN("\u001b[32m"),
    ANSI_MAGENTA("\u001b[35m");

    private final String colorCode;

    Color(String colorCode) {
        this.colorCode = colorCode;
    }

    @Override
    public String toString() {
        return colorCode;
    }
}
