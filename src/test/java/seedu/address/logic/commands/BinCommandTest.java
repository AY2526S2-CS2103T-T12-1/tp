package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.PersonListView;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class BinCommandTest {

    private Model model;
    private Model expectedModel;
    private CommandResult expectedCommandResult = new CommandResult(
            BinCommand.MESSAGE_SUCCESS, PersonListView.DELETED_PERSONS, false, false);

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedCommandResult = new CommandResult(
                BinCommand.MESSAGE_SUCCESS, PersonListView.DELETED_PERSONS, false, false);
    }

    @Test
    public void execute_listIsNotFiltered_showsFullList() {
        assertCommandSuccess(new BinCommand(), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsFullList() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        assertCommandSuccess(new BinCommand(), model, expectedCommandResult, expectedModel);
    }
}
