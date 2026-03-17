package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s fields match any of the keywords given.
 * Fields include name, phone, email, address, role, notes, and tags.
 */
public class PersonContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public PersonContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        return keywords.stream()
                .anyMatch(keyword -> matchesAnyField(person, keyword));
    }

    private boolean matchesAnyField(Person person, String keyword) {
        Stream<String> simpleFields = Stream.of(
                person.getName().fullName,
                person.getPhone().value,
                person.getEmail().value,
                person.getAddress().value,
                person.getRole().value,
                person.getNotes().value
        );
        Stream<String> tagFields = person.getTags().stream()
                .map(tag -> tag.tagName);
        Stream<String> allFields = Stream.concat(simpleFields, tagFields);
        return allFields.anyMatch(field -> StringUtil.containsWordIgnoreCase(field, keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonContainsKeywordsPredicate)) {
            return false;
        }

        PersonContainsKeywordsPredicate otherPredicate = (PersonContainsKeywordsPredicate) other;
        return keywords.equals(otherPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
