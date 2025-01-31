package parser;

/**
 * Enums for commands
 */
public enum InputType {
    EXIT("bye"),
    LIST("list"),
    MARK("mark"),
    UNMARK("unmark"),
    TODO("todo"),
    DEADLINE("deadline"),
    EVENT("event"),
    DELETE("delete"),
    INVALID("invalid");

    private final String commandString;

    InputType(String commandString) {
        this.commandString = commandString;
    }

    /**
     * Returns enum type based on input String
     *
     * @param command input string from ui
     * @return specific enum type
     */
    public static InputType fromString(String command) {
        String[] parts = command.split("\\s+"); // Split by whitespace
        if (parts.length > 0) {
            String firstWord = parts[0];
            for (InputType type : InputType.values()) {
                if (type.commandString.equals(firstWord)) {
                    return type;
                }
            }
        }
        return INVALID;
    }
}
