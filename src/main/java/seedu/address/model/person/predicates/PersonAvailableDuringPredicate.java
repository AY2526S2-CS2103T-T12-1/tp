package seedu.address.model.person.predicates;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;
import seedu.address.model.person.VolunteerAvailability;

/**
 * Tests that a {@code Person} has a {@code VolunteerAvailability} that fully covers the queried time period.
 * A volunteer's availability covers the query if it is on the same day of week,
 * starts at or before the query start time, and ends at or after the query end time.
 */
public class PersonAvailableDuringPredicate implements PersonPredicate {

    private final VolunteerAvailability query;

    /**
     * Constructs a {@code PersonAvailableDuringPredicate}.
     *
     * @param query The availability time window to search for.
     */
    public PersonAvailableDuringPredicate(VolunteerAvailability query) {
        requireNonNull(query);
        this.query = query;
    }

    @Override
    public boolean test(Person person) {
        return person.getAvailabilities().stream()
                .anyMatch(this::coversQuery);
    }

    /**
     * Returns true if the given availability fully covers the query time period.
     */
    private boolean coversQuery(VolunteerAvailability availability) {
        return availability.dayOfWeek.equals(query.dayOfWeek)
                && !availability.startTime.isAfter(query.startTime)
                && !availability.endTime.isBefore(query.endTime);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof PersonAvailableDuringPredicate)) {
            return false;
        }

        PersonAvailableDuringPredicate otherPredicate = (PersonAvailableDuringPredicate) other;
        return query.equals(otherPredicate.query);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("query", query)
                .toString();
    }
}
