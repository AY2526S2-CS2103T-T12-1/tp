package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.CommandUtil.collectItemsByIndices;
import static seedu.address.logic.commands.CommandUtil.isStrictlyIncreasing;
import static seedu.address.logic.commands.CommandUtil.requireIndicesInRange;
import static seedu.address.logic.commands.CommandUtil.requireViewingKeptPersons;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.PersonListView;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Deletes persons identified using their displayed indices from the address book.
 *
 * Assumption: Indices are provided in increasing order without duplicates.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the persons identified by the index numbers used in the displayed person list.\n"
            + "Parameters: INDEX [MORE_INDICES] (must be positive integers)\n"
            + "Example: " + COMMAND_WORD + " 1 2";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS_PREFIX = "Deleted person(s):";
    public static final String MESSAGE_DELETE_PERSON_SUCCESS_DELIMITER = "\n";

    private final List<Index> targetIndices;

    /**
     * Constructs a DeleteCommand object.
     *
     * @param targetIndices Indices of persons to restore in increasing order without duplicates.
     */
    public DeleteCommand(List<Index> targetIndices) {
        assert isStrictlyIncreasing(targetIndices) : "targetIndices should be in increasing order without duplicates";
        this.targetIndices = List.copyOf(targetIndices); //defensive copy to ensure immutability
    }

    @Override
    public CommandResult execute(Model model, PersonListView personListView) throws CommandException {
        requireNonNull(model);
        requireViewingKeptPersons(personListView);

        List<Person> lastShownList = model.getFilteredKeptPersonList();
        requireIndicesInRange(targetIndices, lastShownList);
        List<Person> personsToDelete = collectItemsByIndices(targetIndices, lastShownList);
        deletePersons(model, personsToDelete);

        return new CommandResult(buildSuccessMessage(personsToDelete), PersonListView.KEPT_PERSONS);
    }

    private void deletePersons(Model model, List<Person> persons) {
        for (Person person : persons) {
            model.deletePerson(person);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteCommand)) {
            return false;
        }

        DeleteCommand otherDeleteCommand = (DeleteCommand) other;
        return targetIndices.equals(otherDeleteCommand.targetIndices);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndices", targetIndices)
                .toString();
    }

    /**
     * Builds a success message for the DeleteCommand execution, listing all deleted persons.
     *
     * @param deletedPersons The list of persons that were deleted, in the order they were displayed in the list.
     * @return A formatted success message listing all deleted persons.
     */
    public static String buildSuccessMessage(List<Person> deletedPersons) {
        StringBuilder sb = new StringBuilder(MESSAGE_DELETE_PERSON_SUCCESS_PREFIX);
        for (Person person : deletedPersons) {
            sb.append(MESSAGE_DELETE_PERSON_SUCCESS_DELIMITER).append(Messages.format(person));
        }
        return sb.toString();
    }
}
