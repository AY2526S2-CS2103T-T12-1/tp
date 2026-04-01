package seedu.address.model.statistics;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.function.Function;

/**
 * Renders ASCII bar charts for statistics output.
 */
public final class AsciiBarChart {
    private static final String BAR_CHARACTER = "#";

    private final int maxBarWidth;

    /**
     * Constructs an {@code AsciiBarChart} with the specified maximum bar width.
     *
     * @param maxBarWidth Maximum width for each bar.
     */
    public AsciiBarChart(int maxBarWidth) {
        if (maxBarWidth <= 0) {
            throw new IllegalArgumentException("Bar width must be positive.");
        }
        this.maxBarWidth = maxBarWidth;
    }

    /**
     * Renders a percentage bar chart for the given entries.
     * Each line includes the label, a scaled bar, and a percentage label derived from {@code total}.
     * The {@code total} is an explicit scaling base and may differ from the sum of entry values,
     * allowing percentages and bar lengths to be computed against a larger population or fixed baseline.
     *
     * @param entries Entries to render. Must not be null.
     * @param total Baseline used to compute percentages and bar scaling. Must be non-negative, but zero is allowed.
     * @return Rendered bar chart lines in entry order.
     */
    public List<String> renderPercentage(List<BarEntry> entries, int total) {
        requireNonNull(entries);
        if (total < 0) {
            throw new IllegalArgumentException("Total must be non-negative.");
        }
        return render(entries, total, entry -> {
            double percent = total == 0 ? 0.0 : (entry.getValue() * 100.0 / total);
            return String.format("%.1f%% (%d)", percent, entry.getValue());
        });
    }

    /**
     * Renders a relative-count bar chart for the given entries.
     * Each line includes the label, a scaled bar, and a count label with the provided unit.
     * The {@code maxValue} is an explicit scaling cap and may differ from the max entry value,
     * allowing consistent scaling across charts or against a known upper bound.
     *
     * @param entries Entries to render. Must not be null.
     * @param maxValue Baseline used to scale bars. Must be non-negative, but zero is allowed.
     * @param unitLabel Unit label to append to each count. Must not be null.
     * @return Rendered bar chart lines in entry order.
     */
    public List<String> renderRelativeCounts(List<BarEntry> entries, int maxValue, String unitLabel) {
        requireNonNull(entries);
        requireNonNull(unitLabel);
        if (maxValue < 0) {
            throw new IllegalArgumentException("Max value must be non-negative.");
        }
        return render(entries, maxValue, entry -> String.format("%d %s", entry.getValue(), unitLabel));
    }

    /**
     * Renders the given entries with a shared format using the provided label formatter.
     */
    private List<String> render(List<BarEntry> entries, int maxValue,
            Function<BarEntry, String> labelFormatter) {
        int labelWidth = entries.stream()
                .map(BarEntry::getLabel)
                .mapToInt(String::length)
                .max()
                .orElse(0);

        return entries.stream()
                .map(entry -> formatLine(entry, labelWidth, maxValue, labelFormatter))
                .toList();
    }

    /**
     * Formats a single entry line with the label, scaled bar, and formatted value label.
     */
    private String formatLine(BarEntry entry, int labelWidth, int maxValue,
            Function<BarEntry, String> labelFormatter) {
        String bar = BAR_CHARACTER.repeat(scaleToWidth(entry.getValue(), maxValue));
        return String.format("%-" + labelWidth + "s | %s %s",
                entry.getLabel(), bar, labelFormatter.apply(entry));
    }

    /**
     * Scales the given value into a bar width between 0 and {@code maxBarWidth}.
     * Ensures positive values still render a minimum bar length of 1 when {@code maxValue} is positive.
     */
    private int scaleToWidth(int value, int maxValue) {
        if (maxValue < 0) {
            throw new IllegalArgumentException("Max value must be non-negative.");
        }
        if (value < 0) {
            throw new IllegalArgumentException("Value must be non-negative.");
        }
        if (maxValue == 0 || value == 0) {
            return 0;
        }
        int length = (int) Math.round((value * 1.0 / maxValue) * maxBarWidth);
        length = Math.max(1, length);
        return Math.min(length, maxBarWidth);
    }
}
