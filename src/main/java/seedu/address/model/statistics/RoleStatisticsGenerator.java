package seedu.address.model.statistics;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import seedu.address.model.person.Person;

/**
 * Generates statistics based on volunteer roles.
 */
public final class RoleStatisticsGenerator implements StatisticsGenerator {
    private static final String TITLE_FORMAT = "Role Distribution (%d volunteers)";
    private static final String UNASSIGNED_LABEL = "Unassigned";
    private static final String NO_VOLUNTEERS_MESSAGE = "No volunteers to show.";

    private final AsciiBarChart barChart;

    public RoleStatisticsGenerator(AsciiBarChart barChart) {
        this.barChart = requireNonNull(barChart);
    }

    @Override
    public StatisticsReport generate(List<Person> persons) {
        requireNonNull(persons);
        String title = String.format(TITLE_FORMAT, persons.size());

        if (persons.isEmpty()) {
            return new StatisticsReport(title, List.of(NO_VOLUNTEERS_MESSAGE));
        }

        Map<String, Integer> counts = new HashMap<>();
        for (Person person : persons) {
            String roleLabel = person.getRole().toString().trim();
            if (roleLabel.isEmpty()) {
                roleLabel = UNASSIGNED_LABEL;
            }
            counts.merge(roleLabel, 1, Integer::sum);
        }

        List<BarEntry> entries = counts.entrySet().stream()
                .sorted(Comparator.<Map.Entry<String, Integer>>comparingInt(Map.Entry::getValue)
                        .reversed()
                        .thenComparing(Map.Entry::getKey))
                .map(entry -> new BarEntry(entry.getKey(), entry.getValue()))
                .toList();

        List<String> lines = barChart.renderPercentage(entries, persons.size());
        return new StatisticsReport(title, lines);
    }
}
