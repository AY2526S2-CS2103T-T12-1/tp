package seedu.address.model.statistics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;

public class StatisticsCategoryTest {

    @Test
    public void getToken_returnsToken() {
        assertEquals("role", StatisticsCategory.ROLE.getToken());
        assertEquals("record", StatisticsCategory.RECORD.getToken());
    }

    @Test
    public void fromToken_caseInsensitiveMatch_returnsCategory() {
        assertEquals(Optional.of(StatisticsCategory.ROLE), StatisticsCategory.fromToken("ROLE"));
        assertEquals(Optional.of(StatisticsCategory.RECORD), StatisticsCategory.fromToken("record"));
    }

    @Test
    public void fromToken_unknownToken_returnsEmpty() {
        assertTrue(StatisticsCategory.fromToken("unknown").isEmpty());
    }
}
