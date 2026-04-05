package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.CommandUtil.collectItemsByIndices;
import static seedu.address.logic.commands.CommandUtil.isStrictlyIncreasing;
import static seedu.address.logic.commands.CommandUtil.requireIndicesInRange;
import static seedu.address.logic.commands.CommandUtil.requireViewingDeletedPersons;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.PersonListView;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Restores persons identified using their displayed indices from the address book's deleted list.
 *
 * Assumption: Indices are provided in increasing order without duplicates.
 */
public class RestoreCommand extends Command {

    public static final String COMMAND_WORD = "restore";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Restores the persons identified by the index numbers used in the displayed deleted person list.\n"
            + "Parameters: INDEX [MORE_INDICES] (must be positive integers)\n"
            + "Example: " + COMMAND_WORD + " 1 2";

    public static final String MESSAGE_DUPLICATE_PERSONS_TO_RESTORE =
            "Two people that you want to restore have the same identity.";
    public static final String MESSAGE_PERSON_ALREADY_IN_KEPT_PERSONS =
            "A person that you want to restore is already in the address book.";

    public static final String MESSAGE_RESTORE_PERSON_SUCCESS_PREFIX = "Restored person(s):";
    public static final String MESSAGE_RESTORE_PERSON_SUCCESS_DELIMITER = "\n";

    private final List<Index> targetIndices;

    /**
     * Constructs a RestoreCommand object.
     *
     * @param targetIndices Indices of persons to restore in increasing order without duplicates.
     */
    public RestoreCommand(List<Index> targetIndices) {
        assert isStrictlyIncreasing(targetIndices) : "targetIndices should be in increasing order without duplicates";
        this.targetIndices = List.copyOf(targetIndices); //defensive copy to ensure immutability
    }

    @Override
    public CommandResult execute(Model model, PersonListView personListView) throws CommandException {
        requireNonNull(model);
        requireViewingDeletedPersons(personListView);

        List<Person> lastShownList = model.getFilteredDeletedPersonList();
        requireIndicesInRange(targetIndices, lastShownList);
        List<Person> personsToRestore = collectItemsByIndices(targetIndices, lastShownList);
        validatePersonsToRestore(model, personsToRestore);
        restorePersons(model, personsToRestore);

        return new CommandResult(buildSuccessMessage(personsToRestore), PersonListView.DELETED_PERSONS);
    }

    /**
     * Throws a CommandException if any person to restore has the same identity as a person in the kept list,
     * or if any two persons to restore have the same identity.
     */
    private void validatePersonsToRestore(Model model, List<Person> personsToRestore) throws CommandException {
        for (int i = 0; i < personsToRestore.size(); i++) {
            Person personToRestore = personsToRestore.get(i);

            if (model.hasPerson(personToRestore)) {
                throw new CommandException(MESSAGE_PERSON_ALREADY_IN_KEPT_PERSONS);
            }

            for (int j = i + 1; j < personsToRestore.size(); j++) {
                if (personToRestore.isSamePerson(personsToRestore.get(j))) {
                    throw new CommandException(MESSAGE_DUPLICATE_PERSONS_TO_RESTORE);
                }
            }
        }
    }

    private void restorePersons(Model model, List<Person> persons) {
        for (Person person : persons) {
            model.restorePerson(person);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RestoreCommand)) {
            return false;
        }

        RestoreCommand otherRestoreCommand = (RestoreCommand) other;
        return targetIndices.equals(otherRestoreCommand.targetIndices);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndices", targetIndices)
                .toString();
    }

    /**
     * Builds a success message for the RestoreCommand execution, listing all restored persons.
     *
     * @param restoredPersons The list of persons that were restored, in the order they were displayed in the list.
     * @return A formatted success message listing all restored persons.
     */
    public static String buildSuccessMessage(List<Person> restoredPersons) {
        StringBuilder sb = new StringBuilder(MESSAGE_RESTORE_PERSON_SUCCESS_PREFIX);
        for (Person person : restoredPersons) {
            sb.append(MESSAGE_RESTORE_PERSON_SUCCESS_DELIMITER).append(Messages.format(person));
        }
        return sb.toString();
    }
}

