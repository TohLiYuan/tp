package seedu.address.model.ingredient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIngredients.FLOUR;
import static seedu.address.testutil.TypicalIngredients.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.IngredientBuilder;

public class IngredientTest {

    /*
    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Ingredient ingredient = new IngredientBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> ingredient.getTags().remove(0));
    }
    */

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(FLOUR.isSameIngredient(FLOUR));

        // null -> returns false
        assertFalse(FLOUR.isSameIngredient(null));

        // same name, all other attributes different -> returns true
        Ingredient editedAlice = new IngredientBuilder(FLOUR).build();
        assertTrue(FLOUR.isSameIngredient(editedAlice));

        // different name, all other attributes same -> returns false
        editedAlice = new IngredientBuilder(FLOUR).withName(VALID_NAME_BOB).build();
        assertFalse(FLOUR.isSameIngredient(editedAlice));

        // name differs in case, all other attributes same -> returns false
        Ingredient editedBob = new IngredientBuilder(BOB).withName(VALID_NAME_BOB.toLowerCase()).build();
        assertFalse(BOB.isSameIngredient(editedBob));

        // name has trailing spaces, all other attributes same -> returns false
        String nameWithTrailingSpaces = VALID_NAME_BOB + " ";
        editedBob = new IngredientBuilder(BOB).withName(nameWithTrailingSpaces).build();
        assertFalse(BOB.isSameIngredient(editedBob));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Ingredient aliceCopy = new IngredientBuilder(FLOUR).build();
        assertTrue(FLOUR.equals(aliceCopy));

        // same object -> returns true
        assertTrue(FLOUR.equals(FLOUR));

        // null -> returns false
        assertFalse(FLOUR.equals(null));

        // different type -> returns false
        assertFalse(FLOUR.equals(5));

        // different person -> returns false
        assertFalse(FLOUR.equals(BOB));

        // different name -> returns false
        Ingredient editedAlice = new IngredientBuilder(FLOUR).withName(VALID_NAME_BOB).build();
        assertFalse(FLOUR.equals(editedAlice));
    }

    @Test
    public void toStringMethod() {
        String expected = Ingredient.class.getCanonicalName() + "{name=" + FLOUR.getName() + "}";
        assertEquals(expected, FLOUR.toString());
    }
}