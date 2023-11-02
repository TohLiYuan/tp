package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_UNIT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_UUID;

import java.util.stream.Stream;

import seedu.address.logic.commands.ModifyCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Name;
import seedu.address.model.ingredient.Ingredient;
import seedu.address.model.ingredient.Quantity;
import seedu.address.model.ingredient.Unit;

/**
 * Parses input arguments and creates a new ModifyCommand object
 */
public class ModifyCommandParser implements Parser<ModifyCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the ModifyCommand
     * and returns a ModifyCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ModifyCommand parse(String args) throws ParseException {
        int uuid;
        ArgumentMultimap argMultimap =
                    ArgumentTokenizer.tokenize(args, PREFIX_UUID, PREFIX_NAME, PREFIX_QUANTITY, PREFIX_UNIT);

        if (!arePrefixesPresent(argMultimap, PREFIX_UUID, PREFIX_NAME, PREFIX_QUANTITY, PREFIX_UNIT)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ModifyCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_UUID, PREFIX_NAME, PREFIX_QUANTITY, PREFIX_UNIT);

        try {
            uuid = Integer.parseInt(argMultimap.getValue(PREFIX_UUID).get());
        } catch (NumberFormatException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ModifyCommand.MESSAGE_USAGE), pe);
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        double amount = ParserUtil.parseAmount(argMultimap.getValue(PREFIX_QUANTITY).get());
        Unit unit = ParserUtil.parseUnitOfIngredient(argMultimap.getValue(PREFIX_UNIT).get());
        Quantity quantity = new Quantity(amount, unit);

        Ingredient newIngredient = new Ingredient(name, quantity);

        return new ModifyCommand(uuid, newIngredient);

    }
    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
