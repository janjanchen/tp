package seedu.address.logic.commands.contact;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TELEGRAM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ZOOM;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.contact.ContactNameContainsKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class CFindCommand extends Command {

    public static final String COMMAND_WORD = "cfind";
    public static final String PARAMETERS = "[KEYWORD]… "
            + "[" + PREFIX_PHONE + "KEYWORD…] "
            + "[" + PREFIX_EMAIL + "KEYWORD…] "
            + "[" + PREFIX_ADDRESS + "KEYWORD…] "
            + "[" + PREFIX_TELEGRAM + "KEYWORD…] "
            + "[" + PREFIX_ZOOM + "KEYWORD…] "
            + "[" + PREFIX_TAG + "KEYWORD…]\n";
    public static final String SYNTAX = COMMAND_WORD + " " + PARAMETERS;


    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all contacts whose fields contains any of the "
            + "given keywords.\n"
            + "At least one field must be present, name keywords must follow directly after the command word\n"
            + "Parameters: " + PARAMETERS
            + "Example: " + COMMAND_WORD + " alice bob charlie "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

    private final ContactNameContainsKeywordsPredicate predicate;

    public CFindCommand(ContactNameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredContactList(predicate);
        model.commitAddressBook();
        return new CommandResult(
                String.format(Messages.MESSAGE_CONTACTS_LISTED_OVERVIEW, model.getFilteredContactList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CFindCommand // instanceof handles nulls
                && predicate.equals(((CFindCommand) other).predicate)); // state check
    }
}
