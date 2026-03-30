package seedu.address.model.statistics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class RoleStatisticsGeneratorTest {

    @Test
    public void constructor_nullBarChart_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new RoleStatisticsGenerator(null));
    }

    @Test
    public void generate_nullPersons_throwsNullPointerException() {
        RoleStatisticsGenerator generator = new RoleStatisticsGenerator(new AsciiBarChart(10));
        assertThrows(NullPointerException.class, () -> generator.generate(null));
    }

    @Test
    public void generate_emptyPersons_returnsNoVolunteersMessage() {
        RoleStatisticsGenerator generator = new RoleStatisticsGenerator(new AsciiBarChart(10));

        StatisticsReport report = generator.generate(List.of());

        assertEquals("Role Distribution (0 volunteers)\nNo volunteers to show.", report.render());
    }

    @Test
    public void generate_assignsUnassignedAndSortsByCountThenName() {
        RoleStatisticsGenerator generator = new RoleStatisticsGenerator(new AsciiBarChart(10));
        Person unassigned = new PersonBuilder().withName("Una").withRole("").build();
        Person helper1 = new PersonBuilder().withName("Helper One").withRole("Helper").build();
        Person helper2 = new PersonBuilder().withName("Helper Two").withRole("Helper").build();
        Person leader = new PersonBuilder().withName("Leader").withRole("Leader").build();

        StatisticsReport report = generator.generate(List.of(leader, unassigned, helper1, helper2));

        assertEquals(String.join("\n",
                "Role Distribution (4 volunteers)",
                "Helper     | ##### 50.0% (2)",
                "Leader     | ### 25.0% (1)",
                "Unassigned | ### 25.0% (1)"
        ), report.render());
    }
}
