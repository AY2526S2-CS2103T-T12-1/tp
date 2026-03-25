package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    /** The bin of recently deleted contacts should be shown to the user. */
    private final boolean isShowBin;

    /** Help information should be shown to the user. */
    private final boolean isShowHelp;

    /** The application should exit. */
    private final boolean isExit;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean isShowBin, boolean isShowHelp, boolean isExit) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.isShowBin = isShowBin;
        this.isShowHelp = isShowHelp;
        this.isExit = isExit;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false, false);
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public boolean isShowBin() {
        return isShowBin;
    }

    public boolean isShowHelp() {
        return isShowHelp;
    }

    public boolean isExit() {
        return isExit;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CommandResult)) {
            return false;
        }

        CommandResult otherCommandResult = (CommandResult) other;
        return feedbackToUser.equals(otherCommandResult.feedbackToUser)
                && isShowBin == otherCommandResult.isShowBin
                && isShowHelp == otherCommandResult.isShowHelp
                && isExit == otherCommandResult.isExit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, isShowBin, isShowHelp, isExit);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("feedbackToUser", feedbackToUser)
                .add("isShowBin", isShowBin)
                .add("isShowHelp", isShowHelp)
                .add("isExit", isExit)
                .toString();
    }

}
