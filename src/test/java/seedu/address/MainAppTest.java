package seedu.address;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.storage.UserPrefsStorage;

public class MainAppTest {

    @Test
    public void initPrefs_saveFailure_logsPreferenceFileMessage() {
        MainApp mainApp = new MainApp();
        UserPrefsStorage storage = new UserPrefsStorageStub(Paths.get("preferences.json"), new UserPrefs(), true);

        Logger mainAppLogger = LogsCenter.getLogger(MainApp.class);
        RecordingHandler recordingHandler = new RecordingHandler();
        mainAppLogger.addHandler(recordingHandler);

        try {
            mainApp.initPrefs(storage);
        } finally {
            mainAppLogger.removeHandler(recordingHandler);
        }

        assertTrue(recordingHandler.contains("Failed to save preference file :"));
        assertFalse(recordingHandler.contains("Failed to save config file :"));
    }

    private static class UserPrefsStorageStub implements UserPrefsStorage {
        private final Path prefsFilePath;
        private final UserPrefs userPrefs;
        private final boolean shouldThrowOnSave;

        UserPrefsStorageStub(Path prefsFilePath, UserPrefs userPrefs, boolean shouldThrowOnSave) {
            this.prefsFilePath = prefsFilePath;
            this.userPrefs = userPrefs;
            this.shouldThrowOnSave = shouldThrowOnSave;
        }

        @Override
        public Path getUserPrefsFilePath() {
            return prefsFilePath;
        }

        @Override
        public Optional<UserPrefs> readUserPrefs() throws DataLoadingException {
            return Optional.of(userPrefs);
        }

        @Override
        public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
            if (shouldThrowOnSave) {
                throw new IOException("disk full");
            }
        }
    }

    private static class RecordingHandler extends Handler {
        private final List<LogRecord> records = new ArrayList<>();

        @Override
        public void publish(LogRecord record) {
            if (record != null && record.getLevel().intValue() >= Level.WARNING.intValue()) {
                records.add(record);
            }
        }

        @Override
        public void flush() {
            // No-op for in-memory handler.
        }

        @Override
        public void close() {
            // No-op for in-memory handler.
        }

        boolean contains(String snippet) {
            return records.stream()
                    .map(LogRecord::getMessage)
                    .anyMatch(message -> message != null && message.contains(snippet));
        }
    }
}
