package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.StatsCommand;
import seedu.address.model.statistics.StatisticsCategory;

public class StatsCommandParserTest {

    private final StatsCommandParser parser = new StatsCommandParser();

    @Test
    public void parse_emptyArgs_throwsParseException() {
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT, StatsCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "   ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, StatsCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "role extra", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                StatsCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "unknown", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                StatsCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsStatsCommand() {
        assertParseSuccess(parser, "role", new StatsCommand(StatisticsCategory.ROLE));
        assertParseSuccess(parser, "record", new StatsCommand(StatisticsCategory.RECORD));
        assertParseSuccess(parser, "  role  ", new StatsCommand(StatisticsCategory.ROLE));
    }
}
