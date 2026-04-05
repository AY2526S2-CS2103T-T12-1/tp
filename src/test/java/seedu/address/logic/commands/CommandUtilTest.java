package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.PersonListView;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Contains unit tests for CommandUtil.
 */
public class CommandUtilTest {

    @Test
    public void requireViewingKeptPersons_whenViewingKept_doesNotThrow() throws Exception {
        CommandUtil.requireViewingKeptPersons(PersonListView.KEPT_PERSONS);
    }

    @Test
    public void requireViewingKeptPersons_whenViewingDeleted_throwsCommandException() {
        // Using a lambda as an argument on a new line
        // CHECKSTYLE.OFF: SeparatorWrap
        assertThrows(CommandException.class, Messages.MESSAGE_NOT_VIEWING_KEPT_PERSONS,
                () -> CommandUtil.requireViewingKeptPersons(PersonListView.DELETED_PERSONS));
        // CHECKSTYLE.ON: SeparatorWrap
    }

    @Test
    public void requireViewingDeletedPersons_whenViewingDeleted_doesNotThrow() throws Exception {
        CommandUtil.requireViewingDeletedPersons(PersonListView.DELETED_PERSONS);
    }

    @Test
    public void requireViewingDeletedPersons_whenViewingKept_throwsCommandException() {
        // Using a lambda as an argument on a new line
        // CHECKSTYLE.OFF: SeparatorWrap
        assertThrows(CommandException.class, Messages.MESSAGE_NOT_VIEWING_DELETED_PERSONS,
                () -> CommandUtil.requireViewingDeletedPersons(PersonListView.KEPT_PERSONS));
        // CHECKSTYLE.ON: SeparatorWrap
    }

    @Test
    public void isStrictlyIncreasing_emptyList_returnsTrue() {
        assertTrue(CommandUtil.isStrictlyIncreasing(List.of()));
    }

    @Test
    public void isStrictlyIncreasing_singleIndex_returnsTrue() {
        assertTrue(CommandUtil.isStrictlyIncreasing(List.of(INDEX_FIRST_PERSON)));
    }

    @Test
    public void isStrictlyIncreasing_increasingIndices_returnsTrue() {
        List<Index> consecutiveIndices = List.of(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON, INDEX_THIRD_PERSON);
        assertTrue(CommandUtil.isStrictlyIncreasing(consecutiveIndices));

        List<Index> nonConsecutiveIndices = List.of(INDEX_FIRST_PERSON, INDEX_THIRD_PERSON);
        assertTrue(CommandUtil.isStrictlyIncreasing(nonConsecutiveIndices));
    }

    @Test
    public void isStrictlyIncreasing_duplicateOrDecreasingIndices_returnsFalse() {
        List<Index> duplicateIndices = List.of(INDEX_FIRST_PERSON, INDEX_FIRST_PERSON);
        List<Index> decreasingIndices = List.of(INDEX_SECOND_PERSON, INDEX_FIRST_PERSON);

        assertFalse(CommandUtil.isStrictlyIncreasing(duplicateIndices));
        assertFalse(CommandUtil.isStrictlyIncreasing(decreasingIndices));
    }

    @Test
    public void requireIndicesInRange_allIndicesInRange_doesNotThrow() throws Exception {
        List<Index> emptyTargetIndices = List.of();
        List<Index> validTargetIndices = List.of(INDEX_FIRST_PERSON, INDEX_THIRD_PERSON);
        List<String> targetList = List.of("Amy", "Bob", "Cid");

        CommandUtil.requireIndicesInRange(emptyTargetIndices, targetList);
        CommandUtil.requireIndicesInRange(validTargetIndices, targetList);
    }

    @Test
    public void requireIndicesInRange_anyIndexOutOfRange_throwsCommandException() {
        List<Index> indicesWithOneInvalid = List.of(INDEX_FIRST_PERSON, INDEX_THIRD_PERSON);
        List<Index> indicesWithAllInvalid = List.of(INDEX_THIRD_PERSON);
        List<String> targetList = List.of("Amy", "Bob");

        // Using a lambda as an argument on a new line
        // CHECKSTYLE.OFF: SeparatorWrap
        assertThrows(CommandException.class, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX,
                () -> CommandUtil.requireIndicesInRange(indicesWithOneInvalid, targetList));
        assertThrows(CommandException.class, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX,
                () -> CommandUtil.requireIndicesInRange(indicesWithAllInvalid, targetList));
        // CHECKSTYLE.ON: SeparatorWrap
    }

    @Test
    public void collectItemsByIndices_nonConsecutiveIndices_returnsExpectedItemsInOrder() {
        List<Index> targetIndices = List.of(INDEX_FIRST_PERSON, INDEX_THIRD_PERSON);
        List<String> targetList = List.of("Amy", "Bob", "Cid");

        assertEquals(List.of("Amy", "Cid"), CommandUtil.collectItemsByIndices(targetIndices, targetList));
    }

    @Test
    public void collectItemsByIndices_emptyIndices_returnsEmptyList() {
        List<Index> targetIndices = List.of();
        List<String> targetList = List.of("Amy", "Bob", "Cid");

        assertTrue(CommandUtil.collectItemsByIndices(targetIndices, targetList).isEmpty());
    }
}
