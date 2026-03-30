package seedu.address.model.statistics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;

public class AsciiBarChartTest {

    @Test
    public void constructor_invalidWidth_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new AsciiBarChart(0));
        assertThrows(IllegalArgumentException.class, () -> new AsciiBarChart(-1));
    }

    @Test
    public void renderRelativeCounts_nullEntries_throwsNullPointerException() {
        AsciiBarChart barChart = new AsciiBarChart(5);
        assertThrows(NullPointerException.class, () -> barChart.renderRelativeCounts(null, 1, "unit"));
    }

    @Test
    public void renderRelativeCounts_nullUnitLabel_throwsNullPointerException() {
        AsciiBarChart barChart = new AsciiBarChart(5);
        List<BarEntry> entries = List.of(new BarEntry("A", 1));
        assertThrows(NullPointerException.class, () -> barChart.renderRelativeCounts(entries, 1, null));
    }

    @Test
    public void renderRelativeCounts_scalesValuesAndEnsuresMinimumBar() {
        AsciiBarChart barChart = new AsciiBarChart(10);
        List<BarEntry> entries = List.of(
                new BarEntry("A", 1),
                new BarEntry("B", 100)
        );

        List<String> lines = barChart.renderRelativeCounts(entries, 100, "unit");

        assertEquals(List.of(
                "A | # 1 unit",
                "B | ########## 100 unit"
        ), lines);
    }

    @Test
    public void renderRelativeCounts_zeroMaxValue_rendersEmptyBars() {
        AsciiBarChart barChart = new AsciiBarChart(5);
        List<BarEntry> entries = List.of(new BarEntry("Alpha", 3));

        List<String> lines = barChart.renderRelativeCounts(entries, 0, "items");

        assertEquals(List.of("Alpha |  3 items"), lines);
    }

    @Test
    public void renderPercentage_totalZero_rendersZeroPercentAndEmptyBars() {
        AsciiBarChart barChart = new AsciiBarChart(5);
        List<BarEntry> entries = List.of(new BarEntry("Alpha", 2));

        List<String> lines = barChart.renderPercentage(entries, 0);

        assertEquals(List.of("Alpha |  0.0% (2)"), lines);
    }

    @Test
    public void renderPercentage_nullEntries_throwsNullPointerException() {
        AsciiBarChart barChart = new AsciiBarChart(5);
        assertThrows(NullPointerException.class, () -> barChart.renderPercentage(null, 1));
    }

    @Test
    public void renderPercentage_negativeTotal_throwsIllegalArgumentException() {
        AsciiBarChart barChart = new AsciiBarChart(5);
        List<BarEntry> entries = List.of(new BarEntry("Alpha", 2));

        assertThrows(IllegalArgumentException.class, () -> barChart.renderPercentage(entries, -1));
    }

    @Test
    public void renderPercentage_scalesBarsAndFormatsPercentages() {
        AsciiBarChart barChart = new AsciiBarChart(10);
        List<BarEntry> entries = List.of(
                new BarEntry("Cat", 1),
                new BarEntry("Dogs", 3)
        );

        List<String> lines = barChart.renderPercentage(entries, 4);

        assertEquals(List.of(
                "Cat  | ### 25.0% (1)",
                "Dogs | ######## 75.0% (3)"
        ), lines);
    }
}
