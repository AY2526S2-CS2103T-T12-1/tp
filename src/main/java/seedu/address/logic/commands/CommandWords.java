package seedu.address.logic.commands;

import java.util.Set;

/**
 * Utility methods for command words.
 */
public final class CommandWords {

    public static final String EDIT_PREVIOUS_COMMAND_WORD = "editprev";

    /**
     * Top-level command words handled by {@code AddressBookParser}.
     * Keep this list in sync with {@code AddressBookParser} when adding new top-level commands.
     */
    public static final Set<String> TOP_LEVEL_COMMAND_WORDS = Set.of(
            AddCommand.COMMAND_WORD,
            AliasCommand.COMMAND_WORD,
            AliasesCommand.COMMAND_WORD,
            ClearCommand.COMMAND_WORD,
            DeleteCommand.COMMAND_WORD,
            EditCommand.COMMAND_WORD,
            ExitCommand.COMMAND_WORD,
            ExportCommand.COMMAND_WORD,
            FindCommand.COMMAND_WORD,
            HelpCommand.COMMAND_WORD,
            ImportCommand.COMMAND_WORD,
            ListCommand.COMMAND_WORD,
            UnaliasCommand.COMMAND_WORD);

    private static final Set<String> RESERVED_COMMAND_WORDS = Set.of(
            AddCommand.COMMAND_WORD,
            AliasCommand.COMMAND_WORD,
            AliasesCommand.COMMAND_WORD,
            ClearCommand.COMMAND_WORD,
            DeleteCommand.COMMAND_WORD,
            EditCommand.COMMAND_WORD,
            EDIT_PREVIOUS_COMMAND_WORD,
            ExitCommand.COMMAND_WORD,
            ExportCommand.COMMAND_WORD,
            FindCommand.COMMAND_WORD,
            HelpCommand.COMMAND_WORD,
            ImportCommand.COMMAND_WORD,
            ListCommand.COMMAND_WORD,
            UnaliasCommand.COMMAND_WORD);

    private static final Set<String> ALIAS_TARGET_COMMAND_WORDS = Set.of(
            AddCommand.COMMAND_WORD,
            ClearCommand.COMMAND_WORD,
            DeleteCommand.COMMAND_WORD,
            EditCommand.COMMAND_WORD,
            ExitCommand.COMMAND_WORD,
            ExportCommand.COMMAND_WORD,
            FindCommand.COMMAND_WORD,
            HelpCommand.COMMAND_WORD,
            ImportCommand.COMMAND_WORD,
            ListCommand.COMMAND_WORD);

    private CommandWords() {}

    /**
     * Returns true if the given command word is reserved by the application.
     */
    public static boolean isBuiltInCommandWord(String commandWord) {
        return RESERVED_COMMAND_WORDS.contains(commandWord);
    }

    /**
     * Returns true if the given command word can be used as an alias target.
     */
    public static boolean isAllowedAliasTarget(String commandWord) {
        return ALIAS_TARGET_COMMAND_WORDS.contains(commandWord);
    }
}
