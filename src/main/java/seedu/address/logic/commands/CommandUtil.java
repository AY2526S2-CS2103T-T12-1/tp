package seedu.address.logic.commands;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.PersonListView;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Contains utility methods for the *Command classes.
 */
public class CommandUtil {

    /**
     * Checks if the user is currently viewing the kept persons list.
     *
     * @throws CommandException If the user is not viewing the kept persons list.
     */
    public static void requireViewingKeptPersons(PersonListView personListView) throws CommandException {
        if (personListView != PersonListView.KEPT_PERSONS) {
            throw new CommandException(Messages.MESSAGE_NOT_VIEWING_KEPT_PERSONS);
        }
    }

    /**
     * Checks if the user is currently viewing the deleted persons list.
     *
     * @throws CommandException If the user is not viewing the deleted persons list.
     */
    public static void requireViewingDeletedPersons(PersonListView personListView) throws CommandException {
        if (personListView != PersonListView.DELETED_PERSONS) {
            throw new CommandException(Messages.MESSAGE_NOT_VIEWING_DELETED_PERSONS);
        }
    }

    /**
     * Returns true if indices are in strictly increasing order.
     */
    public static boolean isStrictlyIncreasing(List<Index> indices) {
        for (int i = 0; i + 1 < indices.size(); i++) {
            if (indices.get(i).compareTo(indices.get(i + 1)) >= 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Validates that all target indices are within bounds of the target list.
     */
    public static void requireIndicesInRange(List<Index> targetIndices, List<?> targetList) throws CommandException {
        for (Index targetIndex : targetIndices) {
            if (targetIndex.getZeroBased() >= targetList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
        }
    }

    /**
     * Collects elements from a list, given indices.
     */
    public static <T> List<T> collectItemsByIndices(List<Index> targetIndices, List<T> targetList) {
        List<T> items = new ArrayList<>();
        for (Index index : targetIndices) {
            items.add(targetList.get(index.getZeroBased()));
        }
        return items;
    }
}
