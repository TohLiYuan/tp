package seedu.address.logic;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.parser.RecipeAddCommandParser;

/**
 * Handles input in the recipe adding sequence.
 */
public class RecipeAddInputHandler {
    private static final String MESSAGE_PROMPT_NAME = "Input the name of the recipe.";
    private static final String MESSAGE_PROMPT_INGREDIENT = "Input an ingredient and it's quantity." + "\n"
        + "When you're done, type \"steps start\"";

    private static final String MESSAGE_PROMPT_STEP = "Input the next step in the recipe." + "\n"
            + "When you're done, type \"complete recipe\"";

    private final RecipeAddCommandParser recipeAddCommandParser;

    private RecipeAddInputStage stage;

    /**
     * Initialise the parser and stage
     */
    public RecipeAddInputHandler() {
        recipeAddCommandParser = new RecipeAddCommandParser();
        stage = RecipeAddInputStage.COMPLETE;
    }

    /**
     * Checks if the command is part of the adding recipe command sequence.
     * @param commandText The command to check
     */
    public boolean check(String commandText) {
        return (stage != RecipeAddInputStage.COMPLETE || commandText.equals("addrecipe"));
    }

    /**
     * Handles a command in the adding recipe sequence.
     * @param commandText The next command in the sequence
     * @return A command result
     */
    public CommandResult handle(String commandText) {
        String parseOutput = "";
        if (commandText.equals("addrecipe")) {
            stage = RecipeAddInputStage.NAME;
        } else if (stage == RecipeAddInputStage.NAME) {
            parseOutput = recipeAddCommandParser.addName(commandText);
            stage = RecipeAddInputStage.INGREDIENT;
        } else if (commandText.equals("steps start") || commandText.equals("step start")) {
            stage = RecipeAddInputStage.STEPS;
        } else if (commandText.equals("complete recipe")) {
            stage = RecipeAddInputStage.COMPLETE;
        } else {
            switch (stage) {
            case INGREDIENT:
                parseOutput = recipeAddCommandParser.addIngredient(commandText);
                break;
            case STEPS:
                parseOutput = recipeAddCommandParser.addStep(commandText);
                break;
            default:
                break;
            }
        }

        switch(stage) {
        case NAME:
            return new CommandResult(parseOutput + "\n" + MESSAGE_PROMPT_NAME);
        case INGREDIENT:
            return new CommandResult(parseOutput + "\n" + MESSAGE_PROMPT_INGREDIENT);
        case STEPS:
            return new CommandResult(parseOutput + "\n" + MESSAGE_PROMPT_STEP);
        default:
            return new CommandResult(MESSAGE_PROMPT_NAME);
        }
    }

    /**
     * Checks if the adding recipe command sequence is complete
     */
    public boolean isComplete() {
        return stage == RecipeAddInputStage.COMPLETE;
    }

    /**
     * Returns the completed RecipeAddCommand instance
     */
    public Command getCommand() {
        return recipeAddCommandParser.generateCommand();
    }
}