package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIngredients.FLOUR;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.Messages;
import seedu.address.model.*;
import seedu.address.model.ingredient.Ingredient;
import seedu.address.model.ingredient.Quantity;
import seedu.address.model.ingredient.Unit;
import seedu.address.model.recipe.Recipe;
import seedu.address.testutil.IngredientBuilder;

public class AddCommandTest {

    @Test
    public void constructor_nullIngredient_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_ingredientAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingIngredientAdded modelStub = new ModelStubAcceptingIngredientAdded();
        Ingredient validIngredient = new IngredientBuilder().build();

        CommandResult commandResult = new AddCommand(validIngredient).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(validIngredient)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validIngredient), modelStub.ingredientsAdded);
    }


    @Test
    public void equals() {
        Ingredient alice = new IngredientBuilder().withName("Alice").build();
        Ingredient bob = new IngredientBuilder().withName("Bob").build();
        AddCommand addAliceCommand = new AddCommand(alice);
        AddCommand addBobCommand = new AddCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different ingredient -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    @Test
    public void toStringMethod() {
        AddCommand addCommand = new AddCommand(FLOUR);
        String expected = AddCommand.class.getCanonicalName() + "{toAdd=" + FLOUR + "}";
        assertEquals(expected, addCommand.toString());
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getInventoryFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setInventoryFilePath(Path inventoryFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addIngredient(Ingredient ingredient) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setInventory(ReadOnlyInventory newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyInventory getInventory() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasIngredient(Name ingredient) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteIngredient(Name target) {
            throw new AssertionError("This method should not be called.");
        }

        //  @Override
        //  public void setIngredient(Ingredient target, Ingredient editedIngredient) {
        //      throw new AssertionError("This method should not be called.");
        //  }

        @Override
        public ObservableList<Ingredient> getFilteredIngredientList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredIngredientList(Predicate<Ingredient> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Quantity getQuantityOf(Name ingredient) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteIngredients() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void useIngredient(Name name, Quantity quantity) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setRecipeBook(ReadOnlyRecipeBook recipeBook) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyRecipeBook getRecipeBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasRecipe(Name recipeName) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteRecipe(int recipeId) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addRecipe(Recipe recipe) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String getFullRecipe(int recipeId) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Recipe> getFilteredRecipeList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredRecipeList(Predicate<Recipe> predicate) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single ingredient.
     */
    private class ModelStubWithIngredient extends ModelStub {
        private final Ingredient ingredient;

        ModelStubWithIngredient(Ingredient ingredient) {
            requireNonNull(ingredient);
            this.ingredient = ingredient;
        }

        @Override
        public boolean hasIngredient(Name ingredient) {
            requireNonNull(ingredient);
            return this.ingredient.getName().equals(ingredient);
        }
    }

    /**
     * A Model stub that always accept the ingredient being added.
     */
    private class ModelStubAcceptingIngredientAdded extends ModelStub {
        final ArrayList<Ingredient> ingredientsAdded = new ArrayList<>();

        @Override
        public boolean hasIngredient(Name ingredient) {
            requireNonNull(ingredient);
            return true;
            // ingredientsAdded.stream().anyMatch(ingredient::isSameIngredient);
        }

        @Override
        public void addIngredient(Ingredient ingredient) {
            requireNonNull(ingredient);
            ingredientsAdded.add(ingredient);
        }

        @Override
        public Quantity getQuantityOf(Name ingredientName) {
            requireNonNull(ingredientName);
            for (Ingredient ingredient : ingredientsAdded) {
                if (ingredient.getName().equals(ingredientName)) {
                    return ingredient.getQuantity();
                }
            }
            //If ingredient can't be found, then return 0 quantity
            return new Quantity(0, Unit.GRAM);
        }

        // check this
        @Override
        public ReadOnlyInventory getInventory() {
            return new Inventory();
        }
    }

}
