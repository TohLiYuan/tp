package seedu.address.model.ingredient;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.ingredient.exceptions.DuplicateIngredientException;
import seedu.address.model.ingredient.exceptions.IngredientNotFoundException;

/**
 * A list of ingredients that enforces uniqueness between its elements and does not allow nulls.
 * An ingredient is considered unique by comparing using {@code Ingredient#isSameIngredient(Ingredient)}. As such, adding and updating of
 * ingredients uses Ingredient#isSameIngredient(Ingredient) for equality so as to ensure that the ingredient being added or updated is
 * unique in terms of identity in the UniqueIngredientlist.
 *
 * Supports a minimal set of list operations.
 *
 * @see Ingredient#isSameIngredient(Ingredient)
 */
public class UniqueIngredientList implements Iterable<Ingredient> {

    private final ObservableList<Ingredient> internalList = FXCollections.observableArrayList();
    private final ObservableList<Ingredient> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains a specified ingredient as the given argument.
     */
    public boolean contains(Ingredient toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameIngredient);
    }

    /**
     * Returns the quantity of the specified Ingredient.
     */
    public Quantity getQuantityOf(Ingredient ingredient) {
        requireNonNull(ingredient);
        if (internalList.contains(ingredient)) {
            int index = internalList.indexOf(ingredient);
            return internalList.get(index).getQuantity();
        }
        else {
            //If ingredient is not in list, return quantity of zero
            return new Quantity(0, Unit.GRAM);
        }
    }

    /**
     * Adds an ingredient to the list.
     * If the ingredient is already in the list, then add the quantity to the existing quantity.
     * @param The ingredient to add to the list.
     */
    public void add(Ingredient toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            internalList.get(internalList.indexOf(toAdd)).combineWith(toAdd);
        }
        internalList.add(toAdd);
    }

    /**
     * Subtracts the ingredient {@code ingredient} quantity by {@code quantity}.
     * {@code ingredient} must exist in the list.
     */
    public void useIngredient(Ingredient target, Quantity quantity) {
        requireAllNonNull(target, quantity);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new IngredientNotFoundException();
        }

        internalList.get(index).use(quantity);
    }

    /**
     * Removes the equivalent ingredient from the list.
     * The ingredient must exist in the list.
     */
    public void remove(Ingredient toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new IngredientNotFoundException();
        }
    }

    /**
     * Empties the list.
     */
    public void clear(){
        internalList.clear();
    }


    public void setIngredients(UniqueIngredientList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setIngredients(List<Ingredient> ingredients) {
        requireAllNonNull(ingredients);
        if (!ingredientsAreUnique(ingredients)) {
            throw new DuplicateIngredientException();
        }

        internalList.setAll(ingredients);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Ingredient> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Ingredient> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UniqueIngredientList)) {
            return false;
        }

        UniqueIngredientList otherUniqueIngredientList = (UniqueIngredientList) other;
        return internalList.equals(otherUniqueIngredientList.internalList);
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    @Override
    public String toString() {
        return internalList.toString();
    }

    /**
     * Returns true if {@code persons} contains only unique persons.
     */
    private boolean ingredientsAreUnique(List<Ingredient> ingredients) {
        for (int i = 0; i < ingredients.size() - 1; i++) {
            for (int j = i + 1; j < ingredients.size(); j++) {
                if (ingredients.get(i).isSameIngredient(ingredients.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}