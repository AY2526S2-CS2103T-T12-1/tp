package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.Map;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.PersonListView;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.person.Person;

public class EditPreviousCommandTest {

    @Test
    public void execute_nullModel_throwsNullPointerException() {
        EditPreviousCommand command = new EditPreviousCommand();

        assertThrows(NullPointerException.class, () -> command.execute(null, PersonListView.KEPT_PERSONS));
    }

    @Test
    public void execute_noPreviousCommand_throwsCommandException() {
        EditPreviousCommand command = new EditPreviousCommand();
        Model model = new ModelStubWithLastCommandText(null);

        assertThrows(CommandException.class,
            EditPreviousCommand.MESSAGE_NO_PREVIOUS_COMMAND, () -> command.execute(
                model, PersonListView.DELETED_PERSONS
            )
        );
    }

    @Test
    public void execute_previousCommandExists_returnsExpectedCommandResult() throws Exception {
        String lastCommandText = "delete 1";
        EditPreviousCommand command = new EditPreviousCommand();
        Model model = new ModelStubWithLastCommandText(lastCommandText);

        CommandResult result = command.execute(model, PersonListView.DELETED_PERSONS);

        assertEquals(String.format(EditPreviousCommand.MESSAGE_SUCCESS, lastCommandText), result.getFeedbackToUser());
        assertEquals(PersonListView.KEPT_PERSONS, result.getPersonListView());
        assertFalse(result.shouldShowHelp());
        assertFalse(result.shouldExit());
        assertTrue(result.getCommandTextToPopulate().isPresent());
        assertEquals(lastCommandText, result.getCommandTextToPopulate().get());
    }

    /**
     * A default model stub that has all methods failing.
     */
    private static class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Map<String, String> getCommandAliases() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasCommandAlias(String shortName) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setCommandAlias(String shortName, String template) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void removeCommandAlias(String shortName) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook addressBook) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void restorePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteAllPersons() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredKeptPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getKeptPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredDeletedPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredKeptPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredDeletedPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateSortedPersonList(Comparator<Person> comparator) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String getLastCommandText() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setLastCommandText(String commandText) {
            throw new AssertionError("This method should not be called.");
        }
    }

    private static class ModelStubWithLastCommandText extends ModelStub {
        private final String lastCommandText;

        ModelStubWithLastCommandText(String lastCommandText) {
            this.lastCommandText = lastCommandText;
        }

        @Override
        public String getLastCommandText() {
            return lastCommandText;
        }
    }
}
