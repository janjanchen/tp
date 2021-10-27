package seedu.address.logic.parser.event;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.general.CommandTestUtil.EMPTY_PREFIX_CONTACT;
import static seedu.address.logic.commands.general.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.general.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.general.CommandTestUtil.VALID_INDEX_ONE;
import static seedu.address.logic.commands.general.CommandTestUtil.VALID_INDEX_TWO;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.event.ELinkCommand;
import seedu.address.logic.commands.event.EUnlinkCommand;

class ELinkCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT = String.format(
        MESSAGE_INVALID_COMMAND_FORMAT,
        ELinkCommand.MESSAGE_USAGE);
    private final ELinkCommandParser parser = new ELinkCommandParser();

    @Test
    public void parse_multipleContacts_success() {
        // whitespace only preamble
        Set<Index> contactIndexes = new HashSet<>();
        contactIndexes.add(INDEX_FIRST_PERSON);
        contactIndexes.add(INDEX_SECOND_PERSON);
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + VALID_INDEX_ONE + EMPTY_PREFIX_CONTACT
                + VALID_INDEX_ONE + EMPTY_PREFIX_CONTACT + VALID_INDEX_TWO,
            new ELinkCommand(INDEX_FIRST_EVENT, contactIndexes));
    }

    @Test
    public void parse_singleContact_success() {
        // whitespace only preamble
        Set<Index> contactIndexes = new HashSet<>();
        contactIndexes.add(INDEX_FIRST_PERSON);
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + VALID_INDEX_ONE + EMPTY_PREFIX_CONTACT
                + VALID_INDEX_ONE,
            new ELinkCommand(INDEX_FIRST_EVENT, contactIndexes));
    }

    @Test
    public void parse_invalidInput_failure() {
        // No contacts to link
        assertParseFailure(parser, PREAMBLE_WHITESPACE + VALID_INDEX_ONE, MESSAGE_INVALID_FORMAT);

        // No arguments
        assertParseFailure(parser, PREAMBLE_WHITESPACE, MESSAGE_INVALID_FORMAT);

        // No event index
        assertParseFailure(parser, PREAMBLE_WHITESPACE + EMPTY_PREFIX_CONTACT
            + VALID_INDEX_ONE, MESSAGE_INVALID_FORMAT);

        // Invalid index
        assertParseFailure(parser, PREAMBLE_WHITESPACE + VALID_ADDRESS_AMY + EMPTY_PREFIX_CONTACT
            + VALID_INDEX_ONE, MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, PREAMBLE_WHITESPACE + VALID_INDEX_ONE + EMPTY_PREFIX_CONTACT
            + VALID_INDEX_ONE + EMPTY_PREFIX_CONTACT + VALID_ADDRESS_AMY, MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, PREAMBLE_WHITESPACE + VALID_INDEX_ONE + EMPTY_PREFIX_CONTACT
            + "*", MESSAGE_INVALID_FORMAT);
    }
}
