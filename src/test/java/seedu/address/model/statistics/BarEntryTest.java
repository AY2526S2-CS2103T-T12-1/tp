package seedu.address.model.statistics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class BarEntryTest {

    @Test
    public void constructor_nullLabel_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new BarEntry(null, 1));
    }

    @Test
    public void constructor_negativeValue_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, "Value must be non-negative.", () -> new BarEntry("Label", -1));
    }

    @Test
    public void constructor_zeroValue_allowed() {
        BarEntry entry = new BarEntry("Label", 0);
        assertEquals("Label", entry.getLabel());
        assertEquals(0, entry.getValue());
    }

    @Test
    public void getLabelAndValue() {
        BarEntry entry = new BarEntry("Label", 7);
        assertEquals("Label", entry.getLabel());
        assertEquals(7, entry.getValue());
    }
}
