package seedu.address.model.person.predicates;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;

/**
 * A predicate that combines multiple {@code PersonPredicate}s with AND logic.
 * All component predicates must be satisfied for the combined predicate to return true.
 */
public class CombinedAndPersonPredicate implements PersonPredicate {

    private final List<PersonPredicate> predicates;

    /**
     * Constructs a {@code CombinedAndPersonPredicate} that ANDs the given predicates together.
     */
    public CombinedAndPersonPredicate(List<PersonPredicate> predicates) {
        requireNonNull(predicates);
        this.predicates = List.copyOf(predicates);
    }

    @Override
    public boolean test(Person person) {
        return predicates.stream().allMatch(p -> p.test(person));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof CombinedAndPersonPredicate)) {
            return false;
        }

        CombinedAndPersonPredicate otherPredicate = (CombinedAndPersonPredicate) other;
        return predicates.equals(otherPredicate.predicates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(predicates);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicates", predicates)
                .toString();
    }
}
