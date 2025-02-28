package seedu.address.logic.parser.contact;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.contact.CBookmarkCommand;


class CBookmarkCommandParserTest {
    private CBookmarkCommandParser parser = new CBookmarkCommandParser();

    @Test
    public void parse_validArgs_returnsBookmarkCommand() {
        List<Index> indexes = new ArrayList<>();
        indexes.addAll(Arrays.asList(Index.fromOneBased(1), Index.fromOneBased(2), Index.fromOneBased(8)));
        assertParseSuccess(parser, "1            2 8", new CBookmarkCommand(indexes));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                CBookmarkCommand.MESSAGE_USAGE));
    }
}
