package seedu.address.model;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Stores the past history of the model manager. Meant for the undo and redo commands.
 */
public class ModelHistory {
    private static final ModelHistory THE_ONLY_HISTORY = new ModelHistory();

    private final List<HistoryInstance> allHistory = new ArrayList<>();
    private int currentSize = 0; // Size of the history/Number of undo commands allowed
    private int maxSize = 0; // The last point of redo
    // maxSize - currentSize = Number of redo commands allowed.

    private ModelHistory() {}

    /** Returns the {@code ModelHistory} object. */
    public static ModelHistory getHistory() {
        return THE_ONLY_HISTORY;
    }

    /** Resets the history. */
    public void clearHistory() {
        currentSize = 0;
        maxSize = 0;
    }

    /** Adds a commit to the history, with the given {@code AddressBook} and {@code ModelDisplaySetting}. */
    public void commit(AddressBook addressBook, ModelDisplaySetting displaySetting) {
        allHistory.add(currentSize, new HistoryInstance(displaySetting, addressBook));
        currentSize++;
        maxSize = currentSize;
    }

    /** Performs an undo operation to move the current pointer back one position. */
    public HistoryInstance undo() {
        if (!isUndoable()) {
            throw new ModelHistoryException("Trying to undo even though there is no history.");
        }
        // Moves pointer from the latest commit to the previous commit of the new state.
        currentSize--;
        return allHistory.get(currentSize - 1);
    }

    /** Performs a redo operation to move the current pointer forward by one position. */
    public HistoryInstance redo() {
        if (!isRedoable()) {
            throw new ModelHistoryException("Trying to redo even though it is impossible.");
        }
        currentSize++;
        return allHistory.get(currentSize - 1);
    }

    /** Removes the commit, as if nothing happened. This is different from undo because there is no re-doing. */
    public void removeCommit() {
        currentSize--;
        maxSize--;
        assert maxSize >= currentSize;
    }

    /** Returns true if it is possible to perform an undo operation here. */
    public boolean isUndoable() {
        return currentSize > 1;
    }

    /** Returns true if it is possible to perform a redo operation here. */
    public boolean isRedoable() {
        return maxSize > currentSize;
    }

    /** Encapsulates a point in history, with the address book and model display setting. */
    public static class HistoryInstance {
        private final ModelDisplaySetting displaySetting;
        private final AddressBook addressBook;

        /** Creates a new instance of history. */
        public HistoryInstance(ModelDisplaySetting displaySetting, AddressBook addressBook) {
            requireAllNonNull(displaySetting, addressBook);
            this.displaySetting = displaySetting;
            this.addressBook = addressBook;
        }

        public ModelDisplaySetting getDisplaySetting() {
            return displaySetting;
        }

        public AddressBook getAddressBook() {
            return addressBook;
        }
    }
}
