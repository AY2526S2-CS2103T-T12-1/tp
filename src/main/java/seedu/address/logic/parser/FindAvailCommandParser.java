package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.FindAvailCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.VolunteerAvailability;
import seedu.address.model.person.predicates.PersonAvailableDuringPredicate;

/**
 * Parses input arguments and creates a new FindAvailCommand object.
 */
public class FindAvailCommandParser implements Parser<FindAvailCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindAvailCommand
     * and returns a FindAvailCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindAvailCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindAvailCommand.MESSAGE_USAGE));
        }

        if (!VolunteerAvailability.isValidAvailability(trimmedArgs)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindAvailCommand.MESSAGE_USAGE));
        }

        VolunteerAvailability query = VolunteerAvailability.fromString(trimmedArgs);
        PersonAvailableDuringPredicate predicate = new PersonAvailableDuringPredicate(query);
        return new FindAvailCommand(predicate);
    }
}
