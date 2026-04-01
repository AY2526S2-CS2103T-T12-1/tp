package seedu.address.logic;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Describes which list the user sees or should see.
 */
public enum PersonListView {
    KEPT_PERSONS,
    DELETED_PERSONS,
    SAME_AS_PREVIOUS;

    /**
     * Returns which list the user should see.
     *
     * @param oldList List that was shown previously. Cannot be {@code SAME_AS_PREVIOUS}.
     * @param updateInstruction Describes how to change the list that will be shown.
     * @return The new list to show.
     */
    public static PersonListView updatePersonListView(PersonListView oldList, PersonListView updateInstruction) {
        requireAllNonNull(oldList, updateInstruction);

        if (oldList == SAME_AS_PREVIOUS) {
            throw new IllegalArgumentException("oldList should not be SAME_AS_PREVIOUS");
        }

        if (updateInstruction == SAME_AS_PREVIOUS) {
            return oldList;
        }
        return updateInstruction;
    }
}
