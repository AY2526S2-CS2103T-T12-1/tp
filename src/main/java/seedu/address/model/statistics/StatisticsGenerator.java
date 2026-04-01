package seedu.address.model.statistics;

import java.util.List;

import seedu.address.model.person.Person;

/**
 * Generates a {@link StatisticsReport} from a list of persons.
 */
public interface StatisticsGenerator {
    /**
     * Generates a statistics report from the provided persons list.
     *
     * @param persons Source persons.
     * @return Statistics report derived from the provided persons.
     */
    StatisticsReport generate(List<Person> persons);
}
