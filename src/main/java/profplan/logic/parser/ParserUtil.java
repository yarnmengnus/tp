package profplan.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import profplan.commons.core.Settings;
import profplan.commons.core.index.Index;
import profplan.commons.util.StringUtil;
import profplan.logic.parser.exceptions.ParseException;
import profplan.model.tag.Tag;
import profplan.model.task.DueDate;
import profplan.model.task.Link;
import profplan.model.task.Name;
import profplan.model.task.Priority;
import profplan.model.task.Status;
import profplan.model.task.Task;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String priority} into a {@code Priority}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code priority} is invalid.
     */
    public static Priority parsePriority(String priority) throws ParseException {
        requireNonNull(priority);
        String trimmedPriority = priority.trim();
        if (!Priority.isValidPriority(trimmedPriority)) {
            throw new ParseException(Priority.MESSAGE_CONSTRAINTS);
        }
        return new Priority(trimmedPriority);
    }

    /**
    * Parses a {@code String status} into a {@code Status}.
    * Leading and trailing whitespaces will be trimmed.
    *
    * @throws ParseException if the given {@code status} is invalid.
    */
    public static Status parseStatus(String status) throws ParseException {
        requireNonNull(status);
        String trimmedStatus = status.trim();
        if (!Status.isValidStatus(trimmedStatus)) {
            throw new ParseException(Status.MESSAGE_CONSTRAINTS);
        }
        return new Status(trimmedStatus);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     * Parses a {@code String link} into an {@code Link}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code link} is invalid.
     */
    public static Link parseLink(String link) throws ParseException {
        requireNonNull(link);
        String trimmedLink = link.trim();
        if (!Link.isValidLink(trimmedLink)) {
            throw new ParseException(Link.MESSAGE_CONSTRAINTS);
        }
        return new Link(trimmedLink);
    }

    /**
    * Parses a {@code String date} into a {@code DueDate}.
    * Leading and trailing whitespaces will be trimmed.
    *
    * @throws ParseException if the given {@code date} is invalid.
    */
    public static DueDate parseDueDate(String date) throws ParseException {
        requireNonNull(date);
        String trimmedDate = date.trim();
        if (!DueDate.isValidDate(trimmedDate)) {
            throw new ParseException(DueDate.MESSAGE_CONSTRAINTS);
        }
        return new DueDate(trimmedDate);
    }

    /**
     * Parses a {@code RecurringTask}.
     *
     * @throws ParseException if the given {@code String} is invalid.
     */
    public static Task.RecurringType parseRecurringType(String input) throws ParseException {
        requireNonNull(input);
        String processedInput = input.trim().toLowerCase();

        switch (processedInput) {

        case "daily":
        case "d":
            return Task.RecurringType.DAILY;

        case "weekly":
        case "w":
            return Task.RecurringType.WEEKLY;

        case "monthly":
        case "m":
            return Task.RecurringType.MONTHLY;

        case "semesterly":
        case "s":
            return Task.RecurringType.SEMESTERLY;

        case "none":
            return null;

        default:
            throw new ParseException("The input should be one of the following:\n"
                    + "'daily', 'weekly', 'monthly', 'semesterly', or the shortforms 'd', 'w', 'm', 's'\n"
                    + "case insensitive.");

        }
    }

    /**
     * Checks if a String is a valid integer and returns the int if so.
     */
    public static int parseInteger(String input) throws ParseException {
        try {
            return Integer.parseInt(input.trim());
        } catch (NumberFormatException e) {
            throw new ParseException(e.getMessage());
        }
    }

    /**
     * Checks if a String is a valid input value for the semesterDays setting and returns it if so.
     */
    public static int parseSemesterDays(String input) throws ParseException {
        int parsedInteger = -1;
        try {
            parsedInteger = Integer.parseInt(input.trim());
        } catch (NumberFormatException e) {
            throw new ParseException("Could not parse an integer. " + e.getMessage());
        }
        if (parsedInteger < 0) {
            throw new ParseException(Settings.SEMESTER_DAYS_CONSTRAINTS);
        }
        return parsedInteger;
    }
}
