package seedu.address.model.statistics;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;
import java.util.List;

import seedu.address.model.person.Person;

/**
 * Generates statistics based on volunteer record counts.
 */
public final class RecordStatisticsGenerator implements StatisticsGenerator {
    private static final String TITLE_FORMAT = "Volunteer Record Counts (%d volunteers)";
    private static final String UNIT_LABEL = "record(s)";
    private static final String NO_VOLUNTEERS_MESSAGE = "No volunteers to show.";
    private static final String NO_RECORDS_MESSAGE = "No volunteer records found.";

    private final AsciiBarChart barChart;

    public RecordStatisticsGenerator(AsciiBarChart barChart) {
        this.barChart = requireNonNull(barChart);
    }

    @Override
    public StatisticsReport generate(List<Person> persons) {
        requireNonNull(persons);
        String title = String.format(TITLE_FORMAT, persons.size());

        if (persons.isEmpty()) {
            return new StatisticsReport(title, List.of(NO_VOLUNTEERS_MESSAGE));
        }

        List<BarEntry> entries = persons.stream()
                .map(person -> new BarEntry(person.getName().fullName, person.getRecords().size()))
                .sorted(Comparator.<BarEntry>comparingInt(BarEntry::getValue)
                        .reversed()
                        .thenComparing(BarEntry::getLabel))
                .toList();

        int maxRecords = entries.stream().mapToInt(BarEntry::getValue).max().orElse(0);
        if (maxRecords == 0) {
            return new StatisticsReport(title, List.of(NO_RECORDS_MESSAGE));
        }

        List<String> lines = barChart.renderRelativeCounts(entries, maxRecords, UNIT_LABEL);
        return new StatisticsReport(title, lines);
    }
}
