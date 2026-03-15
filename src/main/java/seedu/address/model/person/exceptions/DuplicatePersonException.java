package seedu.address.model.person.exceptions;

/**
 * Signals that the operation will result in duplicate Persons.
 * Persons are considered duplicates if their phone numbers match exactly,
 * or their email addresses match case-insensitively.
 */
public class DuplicatePersonException extends RuntimeException {
    public DuplicatePersonException() {
        super("Operation would result in duplicate persons: phone already exists or email already exists ");
    }
}
