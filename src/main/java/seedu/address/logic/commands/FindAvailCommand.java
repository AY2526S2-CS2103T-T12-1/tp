package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.predicates.PersonAvailableDuringPredicate;

/**
 * Finds and lists all persons whose availability fully covers the specified time period.
 */
public class FindAvailCommand extends Command {

    public static final String COMMAND_WORD = "findavail";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Finds all volunteers available during the specified time period "
            + "and displays them as a list with index numbers.\n"
            + "Parameters: DAY,START_TIME,END_TIME\n"
            + "Example: " + COMMAND_WORD + " MONDAY,14:00,17:00";

    private final PersonAvailableDuringPredicate predicate;

    public FindAvailCommand(PersonAvailableDuringPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredKeptPersonList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredKeptPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof FindAvailCommand)) {
            return false;
        }

        FindAvailCommand otherCommand = (FindAvailCommand) other;
        return predicate.equals(otherCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
