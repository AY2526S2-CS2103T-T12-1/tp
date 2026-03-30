package seedu.address.model.statistics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;

public class StatisticsReportTest {

    @Test
    public void constructor_nullTitle_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new StatisticsReport(null, List.of("line")));
    }

    @Test
    public void constructor_nullLines_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new StatisticsReport("Title", null));
    }

    @Test
    public void render_emptyLines_returnsTitleOnly() {
        StatisticsReport report = new StatisticsReport("Title", List.of());
        assertEquals("Title", report.render());
    }

    @Test
    public void render_withLines_returnsJoinedOutput() {
        StatisticsReport report = new StatisticsReport("Title", List.of("A", "B"));
        assertEquals("Title\nA\nB", report.render());
    }
}
