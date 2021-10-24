package seedu.address.ui;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Comparator;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.model.contact.Contact;

/**
 * An UI component that displays information of a {@code Contact}.
 */
public class ContactCard extends UiPart<Region> {

    private static final String FXML = "ContactListCard.fxml";

    private static Logger logger = LogsCenter.getLogger(ContactCard.class);

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Contact contact;

    private MainWindow mainWindow;

    @FXML
    private HBox cardPane;

    @FXML
    private Label name;

    @FXML
    private Label id;

    @FXML
    private Label phone;

    @FXML
    private Label address;

    @FXML
    private Label email;

    @FXML
    private Label telegramHandleTitle;
    @FXML
    private Label telegramHandle;

    @FXML
    private Label zoomLinkTitle;
    @FXML
    private Label zoomLink;

    @FXML
    private FlowPane tags;

    @FXML
    private ImageView emailIcon;

    /**
     * Creates a {@code ContactCard} with the given {@code Contact} and index to display.
     */
    public ContactCard(Contact contact, int displayedIndex, MainWindow mainWindow) {
        super(FXML);
        requireAllNonNull(contact, displayedIndex, mainWindow);
        this.mainWindow = mainWindow;
        this.contact = contact;
        id.setText(displayedIndex + ". ");
        name.setText(contact.getName().fullName);
        // Compulsory fields
        if (Contact.isWillDisplayEmail()) {
            email.setText("email: " + contact.getEmail().value);
            email.setManaged(true);
        }
        // Optional fields
        if (contact.getPhone() != null && Contact.isWillDisplayPhone()) {
            phone.setText("phone: " + contact.getPhone().value);
            phone.setManaged(true);
        }
        if (contact.getAddress() != null && Contact.isWillDisplayAddress()) {
            address.setText("address: " + contact.getAddress().value);
            address.setManaged(true);
        }
        if (contact.getTelegramHandle() != null && Contact.isWillDisplayTelegramHandle()) {
            telegramHandleTitle.setText("telegram handle: ");
            telegramHandleTitle.setManaged(true);
            telegramHandle.setText(contact.getTelegramHandle().handle);
            telegramHandle.setManaged(true);
        }
        if (contact.getZoomLink() != null && Contact.isWillDisplayZoomLink()) {
            zoomLinkTitle.setText("zoom: ");
            zoomLinkTitle.setManaged(true);
            zoomLink.setText(contact.getZoomLink().link);
            zoomLink.setManaged(true);
        }
        if (Contact.isWillDisplayTags()) {
            contact.getTags().stream()
                    .sorted(Comparator.comparing(tag -> tag.tagName))
                    .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
            tags.setManaged(true);
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ContactCard)) {
            return false;
        }

        // state check
        ContactCard card = (ContactCard) other;
        return id.getText().equals(card.id.getText())
                && contact.equals(card.contact);
    }

    /**
     * Copies contact fields to the clipboard.
     */
    private void copy(String fieldContent, String fieldName) {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent content = new ClipboardContent();
        content.putString(fieldContent);
        clipboard.setContent(content);
        mainWindow.handleClick(String.format(Messages.MESSAGE_CONTACT_FIELD_COPIED, fieldName));
        logger.info(String.format(Messages.MESSAGE_CONTACT_FIELD_COPIED, fieldName));
    }

    /**
     * Copies contact name to the clipboard.
     */
    @FXML
    private void copyName() {
        copy(contact.getName().fullName, "name");
    }

    /**
     * Copies contact email to the clipboard.
     */
    @FXML
    private void copyEmail() {
        copy(contact.getEmail().value, "email");
    }

    /**
     * Copies contact phone number to the clipboard.
     */
    @FXML
    private void copyPhone() {
        copy(contact.getPhone().value, "phone");
    }

    /**
     * Copies contact address to the clipboard.
     */
    @FXML
    private void copyAddress() {
        copy(contact.getAddress().value, "address");
    }

    /**
     * Open contact links in browser
     */
    private void openLink(String link, String fieldName) {
        try {
            Desktop.getDesktop().browse(new URI(link));
            logger.info(String.format(Messages.MESSAGE_CONTACT_LINK_OPENED, fieldName));
            mainWindow.handleClick(String.format(Messages.MESSAGE_CONTACT_LINK_OPENED, fieldName));
        } catch (URISyntaxException | IOException e) {
            logger.info(String.format(Messages.MESSAGE_CONTACT_LINK_NOT_FOUND, fieldName));
            mainWindow.handleClick(String.format(Messages.MESSAGE_CONTACT_LINK_NOT_FOUND, fieldName));
        }
    }

    /**
     * Open contact zoom link in browser.
     */
    @FXML
    private void openZoomLink() {
        openLink(contact.getZoomLink().link, "zoom");
    }

    /**
     * Open telegram link in browser.
     */
    @FXML
    private void openTelegramHandle() {
        openLink(contact.getTelegramHandle().link, "telegram");
    }
}
