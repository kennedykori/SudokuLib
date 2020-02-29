/**
 * 
 */
package com.kori_47.sudoku;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;
import java.util.Optional;
import java.util.Set;

/**
 * A {@code Cell} is an intersection between a column and a row in a {@code LatinSquare}. Each cell holds a 
 * {@link Symbol} and has an x and y coordinate which are the column and row respectfully, that this cell
 * belongs to. The x coordinate of a cell is the <strong><i>i<sup>th</sup></i></strong> column of the {@link LatinSquare}
 * <strong><i>L</i></strong> and the y coordinate of a cell is the <strong><i>j<sup>th</sup></i></strong> row 
 * of the {@code LatinSquare} <strong><i>L</i></strong> such that:
 * <pre>
 * 				x = i and 0 <= i < s
 * 		and		y = j and 0 <= j < s
 * 		where	s is the size of L
 * </pre>
 * 
 * <p>
 * A {@code Cell} can be marked as a clue cell making it non editable until it is changed back to a normal cell.
 * Attempting to modify an clue cell should result in the {@link ClueCellModificationException} being thrown.
 * Modification in this context refers to changing a cell's {@code Symbol} and making or removing notes on the cell.
 * </p>
 * 
 * <p>
 * Apart from having a {@code Symbol}, a cell can also have notes which is a collection of possible symbols
 * for the cell and are typically populated by the player.
 * </p>
 * 
 * @param <V> the type of value held by the {@code Symbol}s supported by this {@code Cell}.
 * 
 * @author <a href="https://github.com/kennedykori">Kennedy Kori</a>
 *
 * @since Oct 17, 2019, 12:38:39 AM
 */
public interface Cell<V> extends Formattable, Comparable<Cell<V>>, Unique<String> {

	/**
	 * {@inheritDoc}
	 * 
	 * @implSpec
	 * The default implementation uses {@link FormattableUtils#toXY(Cell)} to construct the desired
	 * representation.
	 */
	@Override
	default String toXY() {
		return FormattableUtils.toXY(this);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @implSpec
	 * The default implementation uses {@link FormattableUtils#toXYV(Cell)} to construct the desired
	 * representation.
	 */
	@Override
	default String toXYV() {
		return FormattableUtils.toXYV(this);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @implSpec
	 * The default implementation uses {@link FormattableUtils#toXYI(Cell)} to construct the desired
	 * representation.
	 */
	@Override
	default String toXYI() {
		return FormattableUtils.toXYI(this);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @implSpec
	 * The default implementation uses {@link FormattableUtils#toV(Cell)} to construct the desired
	 * representation.
	 */
	@Override
	default String toV() {
		return FormattableUtils.toV(this);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @implSpec
	 * The default implementation uses {@link FormattableUtils#toI(Cell)} to construct the desired
	 * representation.
	 */
	@Override
	default String toI() {
		return FormattableUtils.toI(this);
	}

	/**
	 * Performs order and equality comparisons on two {@code Cell}s.
	 * 
	 * @param other another {@code Cell} to compare to this one.
	 * 
	 * @return the value {@code 0} if this {@code Cell} is
     *         equal to the argument {@code Cell}; a value less than
     *         {@code 0} if this {@code Cell} is less than the argument
     *         {@code Cell}; and a value greater than {@code 0} if this
     *         {@code Cell} is greater than the argument {@code Cell}.
     * 
     * @throws NullPointerException if {@code other} is {@code null}.
     * 
     * @see Cells#defaultComparator()
     * 
     * @implSpec
     * The default implementation uses the {@link Comparator} returned by {@link Cells#defaultComparator()}
     * to perform the needed comparisons. 
	 */
	@Override
	default int compareTo(Cell<V> other) { 
		requireNonNull(other, "other cannot be null.");
		return Cells.defaultComparator().compare(this, other);
	}
	
	/**
	 * Clears this {@code Cell} by changing the {@code Cell}s {@link Symbol} to a {@code null}
	 * value, clears any notes that this {@code Cell} might have and reverts this {@code Cell} to
	 * a normal {@code Cell} if this is marked as a clue {@code Cell}.
	 * 
	 * @implSpec
	 * The default implementation is equivalent to, for this {@code cell}:
	 * <pre> {@code
	 * cell.reset(null);
	 * }
	 * </pre>
	 */
	default void clear() {
		reset(null);
	}
	
	/**
	 * This method is meant to be used as alternative to {@link #equals(Object)} and concrete
	 * implementations of this interface can provide a more detailed and comprehensive comparison
	 * of two {@code Cell}s. This method exists because this interface imposes a strict specification
	 * on the {@code equals} method that might not be comprehensive enough for some implementations.
	 * 
	 * <p>
	 * <i>
	 * <strong>NOTE:</strong> Since this is meant to be used as an alternative to {@code equals},
	 * implementations of this method should maintain the general contracts of the {@code equals} method
	 * to ensure correct and predictable results.
	 * </i>
	 * 
	 * @param otherObj the other object to compare for equality with this one.
	 * 
	 * @return {@code true} if this {@code Cell} is equal to {@code otherObj}, {@code false} otherwise.
	 * 
	 * @see #equals(Object)
	 * 
	 * @implSpec
	 * The default implementation of this method simply calls the {@code equals} method of this {@code Cell}.
	 * That is, the default implementation is equivalent to:
	 * <pre> {@code
	 * return this.equals(otherObj);
	 * }
	 * </pre>
	 */
	default boolean deepEquals(Object otherObj) {
		return this.equals(otherObj);
	}
	
	/**
	 * Returns the hash code value for this {@link Cell}. Since a {@code Cell} is mutable, the
	 * hash code value of a {@code Cell} is derived from the following three properties of a
	 * {@code Cell} that are not expected to mutate within the lifetime of a {@code Cell}:
	 * <ul>
	 * <li>The hash code of the {@link #id()} of this {@code Cell}.</li>
	 * <li>The hash code of the {@link #x() x coordinate} of this {@code Cell}.</li>
	 * <li>The hash code of the {@link #y() y coordinate} of this {@code Cell}.</li>
	 * </ul>
	 * 
	 * @return the hash code value of this {@code Cell}.
	 */
	@Override
	int hashCode();
	
	/**
	 * Compares the given object with this {@code Cell} for equality. Returns {@code true} if the
	 * given object is also a {@code Cell} and the two {@code Cell}s are identical. Since {@code Cell}s
	 * are mutable, two {@code Cell}s are said to be equal only if each of the following of their properties
	 * are also equal:
	 * <ul>
	 * <li>The <i>{@link #id() ids}</i> of the {@code Cell}s</li>
	 * <li>The <i>{@link #x() x coordinates}</i> of the {@code Cell}s.</li>
	 * <li>The <i>{@link #y() y coordinates}</i> of the {@code Cell}s.</li>
	 * </ul>
	 * 
	 * <p>
	 * Each of the properties above is not expected to mutate within the lifetime of a {@code Cell} and
	 * each {@code Cell} within a given {@link CellGroup} should have a unique combination of these properties.
	 * Using non mutable properties for equals comparisons helps to ensure that a {@link Set} of {@code Cell}s
	 * works correctly even if it's cells are mutated and helps to ensure that the general contract of the
	 * {@code hashCode} method is maintained. For a more comprehensive comparisons of {@code Cell}s
	 * {@link #deepEquals(Object)} should be used instead.
	 * 
	 * @param obj the object to compare for equality with this {@code Cell}.
	 * 
	 * @return {@code true} if this {@code Cell} is equal to {@code obj} argument, {@code false} otherwise.
	 * 
	 * @see #deepEquals(Object)
	 */
	@Override
	boolean equals(Object obj);

	/**
	 * <p> Changes the {@link Symbol} of this cell to a new value. If this is an initial cell, then this call 
	 * will fail with an {@link ClueCellModificationException}. {@code null} values are allowed.
	 * 
	 * @param value the new {@code Symbol} to set on this cell.
	 * 
	 * @throws ClueCellModificationException if this is a clue cell.
 	 */
	void changeSymbol(Symbol<V> value);
	
	/**
	 * <p>
	 * Marks this cell as a clue cell and sets the given {@link Symbol} as the {@code Symbol} for this cell. Any
	 * modifications attempts on this cell after this call will result in an {@link ClueCellModificationException}
	 * being thrown until this cell is changed back to an normal cell.
	 * </p>
	 * 
	 * <p>
	 * Multiple calls of this method on a cell that is already marked as a clue cell are allowed and should not
	 * throw a {@code ClueCellModificationException}.
	 * </p>
	 * 
	 * @param initialValue the {@code Symbol} to set to this clue cell.
	 * 
	 * @throws NullPointerException if {@code initialValue} is {@code null}.
	 */
	void makeClueCell(Symbol<V> initialValue);
	
	/**
	 * Marks this cell as a normal cell allowing modification of the cell. 
	 * 
	 * <p>
	 * Multiple calls of this method on a normal cell are allowed and should return cleanly.
	 * </p>
	 */
	void makeNormalCell();
	
	/**
	 * Adds the given {@link Symbol} as a possible value for this cell. Adding an already noted {@code Symbol} should
	 * have no effect and should result in a clean return.
	 * 
	 * @param note the {@code Symbol} to mark as a possible value for this cell.
	 * 
	 * @throws NullPointerException if {@code note} is {@code null}.
	 * @throws ClueCellModificationException if this a clue cell.
	 */
	void makeNote(Symbol<V> note);
	
	/**
	 * Removes the given {@link Symbol} from the {@code Set} of noted possible values of this
	 * cell. Removing a non noted {@code Symbol} should have no effect and should result in a
	 * clean return.
	 * 
	 * @param note the {@code Symbol} to remove from the {@code Set} of noted possible values
	 * of this cell.
	 * 
	 * @throws NullPointerException if {@code note} is {@code null}.
	 * @throws ClueCellModificationException if this a clue cell.
	 */
	void removeNote(Symbol<V> note);

	/**
	 * Resets this {@code Cell} by changing the {@code Cell}s {@link Symbol} to a given value,
	 * clears any notes that this {@code Cell} might have and reverts this {@code Cell} to
	 * a normal {@code Cell} if this is marked as a clue {@code Cell}.
	 * 
	 * @param symbol the {@code Symbol} to set this {@code Cell} to after the reset. {@code null}
	 * is allowed and should not result in a {@code NullPointerException}.
	 */
	void reset(Symbol<V> symbol);
	
	/**
	 * Returns the x coordinate of this cell. This is also the index of the {@link Column} in which
	 * this cell belongs and thus should always be a positive {@code Integer}.
	 * 
	 * @return the x coordinate of this cell.
	 */
	int x();
	
	/**
	 * Returns the y coordinate of this cell. This is also the index of the {@link Row} in which
	 * this cell belongs and thus should always be a positive {@code Integer}.
	 * 
	 * @return the y coordinate of this cell.
	 */
	int y();

	/**
	 * Returns a unique {@code String} that can be used to identify this cell in a {@code CellGroup}.
	 * 
	 * @return a unique identifier of this cell in a {@code CellGroup}.
	 */
	String id();
	
	/**
	 * Returns an {@link Optional} describing the {@link Symbol} set on this cell or an empty if no
	 * {@code Symbol} is currently set on the cell.
	 * 
	 * @return an {@code Optional} describing the {@code Symbol} set on this cell or an empty if no
	 * {@code Symbol} is currently set on the cell.
	 */
	Optional<Symbol<V>> value();
	
	/**
	 * Returns a {@link Set} of {@code Symbol}s marked as possible values for this cell. Modification
	 * of the returned {@code Set} should not alter the notes this {@code Cell}.
	 * 
	 * @return a {@code Set} of {@code Symbol}s marked as possible values for this cell.
	 */
	Set<Symbol<V>> notes();
	
	/**
	 * Indicates whether this cell is marked as a clue cell or not. Returns {@code true} if this
	 * is an clue cell or {@code false} otherwise.
	 * 
	 * @return {@code true} if this is a clue cell or {@code false} otherwise.
	 */
	boolean isClueCell();
}
