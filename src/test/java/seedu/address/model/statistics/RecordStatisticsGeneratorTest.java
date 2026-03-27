package seedu.address.model.statistics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class RecordStatisticsGeneratorTest {

    @Test
    public void constructor_nullBarChart_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new RecordStatisticsGenerator(null));
    }

    @Test
    public void generate_nullPersons_throwsNullPointerException() {
        RecordStatisticsGenerator generator = new RecordStatisticsGenerator(new AsciiBarChart(10));
        assertThrows(NullPointerException.class, () -> generator.generate(null));
    }

    @Test
    public void generate_emptyPersons_returnsNoVolunteersMessage() {
        RecordStatisticsGenerator generator = new RecordStatisticsGenerator(new AsciiBarChart(10));

        StatisticsReport report = generator.generate(List.of());

        assertEquals("Volunteer Record Counts (0 volunteers)\nNo volunteers to show.", report.render());
    }

    @Test
    public void generate_noRecords_returnsNoRecordsMessage() {
        RecordStatisticsGenerator generator = new RecordStatisticsGenerator(new AsciiBarChart(10));
        List<Person> persons = List.of(
                new PersonBuilder().withName("Alice").build(),
                new PersonBuilder().withName("Bob").build()
        );

        StatisticsReport report = generator.generate(persons);

        assertEquals("Volunteer Record Counts (2 volunteers)\nNo volunteer records found.", report.render());
    }

    @Test
    public void generate_sortsAndRendersRecordCounts() {
        RecordStatisticsGenerator generator = new RecordStatisticsGenerator(new AsciiBarChart(10));
        Person alice = new PersonBuilder().withName("Alice")
                .withRecords("2026-03-20T10:00,2026-03-20T11:00",
                        "2026-03-21T10:00,2026-03-21T11:00")
                .build();
        Person bob = new PersonBuilder().withName("Bob")
                .withRecords("2026-03-22T10:00,2026-03-22T11:00")
                .build();
        Person charlie = new PersonBuilder().withName("Charlie")
                .withRecords("2026-03-23T10:00,2026-03-23T11:00")
                .build();

        StatisticsReport report = generator.generate(List.of(bob, charlie, alice));

        assertEquals(String.join("\n",
                "Volunteer Record Counts (3 volunteers)",
                "Alice   | ########## 2 record(s)",
                "Bob     | ##### 1 record(s)",
                "Charlie | ##### 1 record(s)"
        ), report.render());
    }
}
