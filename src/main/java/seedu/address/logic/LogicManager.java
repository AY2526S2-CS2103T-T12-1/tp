package seedu.address.logic;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.EditPreviousCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Person;
import seedu.address.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_FORMAT = "Could not save data due to the following error: %s";

    public static final String FILE_OPS_PERMISSION_ERROR_FORMAT =
            "Could not save data to file %s due to insufficient permissions to write to the file or the folder.";

    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final AddressBookParser addressBookParser;

    private String lastExecutedCommandText;

    /**
     * Constructs a {@code LogicManager} with the given {@code Model} and {@code Storage}.
     */
    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        addressBookParser = new AddressBookParser();
    }

    @Override
    public CommandResult execute(String commandText, PersonListView personListView)
            throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        String trimmedCommandText = commandText.trim();
        Matcher commandMatcher = BASIC_COMMAND_FORMAT.matcher(trimmedCommandText);
        if (!commandMatcher.matches()) {
            return executeNormalCommand(commandText);
        }

        String commandWord = commandMatcher.group("commandWord");
        String arguments = commandMatcher.group("arguments").trim();
        if (!EDIT_PREVIOUS_COMMAND_WORD.equals(commandWord)) {
            return executeNormalCommand(commandText);
        }

        if (!arguments.isEmpty()) {
            throw new ParseException(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, EDIT_PREVIOUS_MESSAGE_USAGE));
        }
        if (lastExecutedCommandText == null) {
            throw new CommandException(EDIT_PREVIOUS_MESSAGE_NO_PREVIOUS_COMMAND);
        }
        return new CommandResult(
                String.format(EDIT_PREVIOUS_MESSAGE_SUCCESS, lastExecutedCommandText),
                personListView,
                false,
                false,
                lastExecutedCommandText);
    }

    private CommandResult executeNormalCommand(String commandText) throws CommandException, ParseException {
        String expandedCommandText = expandAlias(commandText);
        Command command = addressBookParser.parseCommand(expandedCommandText);
        CommandResult commandResult = command.execute(model);

        try {
            storage.saveAddressBook(model.getAddressBook());
            storage.saveUserPrefs(model.getUserPrefs());
        } catch (AccessDeniedException e) {
            throw new CommandException(String.format(FILE_OPS_PERMISSION_ERROR_FORMAT, e.getMessage()), e);
        } catch (IOException ioe) {
            throw new CommandException(String.format(FILE_OPS_ERROR_FORMAT, ioe.getMessage()), ioe);
        }

        if (!(command instanceof EditPreviousCommand)) {
            model.setLastCommandText(commandText);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return model.getAddressBook();
    }

    @Override
    public ObservableList<Person> getFilteredKeptPersonList() {
        return model.getFilteredKeptPersonList();
    }

    @Override
    public ObservableList<Person> getFilteredDeletedPersonList() {
        return model.getFilteredDeletedPersonList();
    }

    @Override
    public Path getAddressBookFilePath() {
        return model.getAddressBookFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }

    private String expandAlias(String commandText) {
        return ParserUtil.parseCommandComponents(commandText)
                .map(commandComponents -> expandAlias(commandText, commandComponents))
                .orElse(commandText);
    }

    private String expandAlias(String commandText, ParserUtil.CommandComponents commandComponents) {
        String aliasTemplate = model.getCommandAliases().get(commandComponents.getCommandWord());
        if (aliasTemplate == null) {
            return commandText;
        }

        return aliasTemplate + commandComponents.getArguments();
    }
}
