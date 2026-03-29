package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;

import seedu.address.model.ReadOnlyAddressBook;

/**
 * Contains helper methods for testing classes in the Storage component.
 */
public class StorageTestUtil {
    public static void assertSameKeptPersons(ReadOnlyAddressBook expected, ReadOnlyAddressBook actual) {
        assertEquals(expected.getKeptPersonList(), actual.getKeptPersonList());
    }
}
