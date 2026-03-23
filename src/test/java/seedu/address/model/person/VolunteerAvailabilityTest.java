package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.DayOfWeek;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;

public class VolunteerAvailabilityTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                new VolunteerAvailability(null, LocalTime.NOON, LocalTime.MIDNIGHT));
        assertThrows(NullPointerException.class, () ->
                new VolunteerAvailability(DayOfWeek.MONDAY, null, LocalTime.MIDNIGHT));
        assertThrows(NullPointerException.class, () ->
                new VolunteerAvailability(DayOfWeek.MONDAY, LocalTime.NOON, null));
    }

    @Test
    public void equals() {
        VolunteerAvailability availability1 =
                new VolunteerAvailability(DayOfWeek.MONDAY, LocalTime.of(14, 0), LocalTime.of(16, 0));
        VolunteerAvailability availability1Copy =
                new VolunteerAvailability(DayOfWeek.MONDAY, LocalTime.of(14, 0), LocalTime.of(16, 0));
        VolunteerAvailability availability2 =
                new VolunteerAvailability(DayOfWeek.TUESDAY, LocalTime.of(14, 0), LocalTime.of(16, 0));
        VolunteerAvailability availability3 =
                new VolunteerAvailability(DayOfWeek.MONDAY, LocalTime.of(10, 0), LocalTime.of(16, 0));
        VolunteerAvailability availability4 =
                new VolunteerAvailability(DayOfWeek.MONDAY, LocalTime.of(14, 0), LocalTime.of(18, 0));

        // same object -> returns true
        assertTrue(availability1.equals(availability1));

        // same values -> returns true
        assertTrue(availability1.equals(availability1Copy));

        // null -> returns false
        assertFalse(availability1.equals(null));

        // different types -> returns false
        assertFalse(availability1.equals(5));

        // different day -> returns false
        assertFalse(availability1.equals(availability2));

        // different start time -> returns false
        assertFalse(availability1.equals(availability3));

        // different end time -> returns false
        assertFalse(availability1.equals(availability4));
    }

    @Test
    public void hashCodeMethod() {
        VolunteerAvailability availability1 =
                new VolunteerAvailability(DayOfWeek.MONDAY, LocalTime.of(14, 0), LocalTime.of(16, 0));
        VolunteerAvailability availability1Copy =
                new VolunteerAvailability(DayOfWeek.MONDAY, LocalTime.of(14, 0), LocalTime.of(16, 0));

        assertEquals(availability1.hashCode(), availability1Copy.hashCode());
    }

    @Test
    public void toStringMethod() {
        VolunteerAvailability availability1 =
                new VolunteerAvailability(DayOfWeek.MONDAY, LocalTime.of(14, 0), LocalTime.of(16, 0));
        assertEquals("MONDAY 14:00 to 16:00", availability1.toString());
    }
}
