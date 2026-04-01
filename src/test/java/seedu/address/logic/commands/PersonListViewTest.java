package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class PersonListViewTest {
    @Test
    public void updatePersonListView_argumentsAreNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> PersonListView.updatePersonListView(
                null, PersonListView.KEPT_PERSONS));
        assertThrows(NullPointerException.class, () -> PersonListView.updatePersonListView(
                PersonListView.KEPT_PERSONS, null));
    }

    @Test
    public void updatePersonListView_oldPersonListIsSameAsPrevious_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> PersonListView.updatePersonListView(
                PersonListView.SAME_AS_PREVIOUS, PersonListView.KEPT_PERSONS));
    }

    @Test
    public void updatePersonListView_argumentsAreValid_success() {
        // newList is not SAME_AS_PREVIOUS
        assertEquals(PersonListView.KEPT_PERSONS,
                PersonListView.updatePersonListView(PersonListView.KEPT_PERSONS, PersonListView.KEPT_PERSONS));
        assertEquals(PersonListView.DELETED_PERSONS,
                PersonListView.updatePersonListView(PersonListView.KEPT_PERSONS, PersonListView.DELETED_PERSONS));

        // newList is SAME_AS_PREVIOUS
        assertEquals(PersonListView.DELETED_PERSONS,
                PersonListView.updatePersonListView(PersonListView.DELETED_PERSONS, PersonListView.SAME_AS_PREVIOUS));
    }
}
