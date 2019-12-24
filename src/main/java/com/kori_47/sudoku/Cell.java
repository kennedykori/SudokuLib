/**
 * 
 */
package com.kori_47.sudoku;

import static java.util.Objects.requireNonNull;

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
 * A {@code Cell} can be marked as an initial cell making it non editable until it is changed back to a 
 * normal cell. Attempting to modify an initial cell should result in the {@link InitialCellModificationException}
 * being thrown. Modification in this context refers to changing a cell's {@code Symbol} and making or
 * removing notes on the cell.
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
public interface Cell<V> extends Formattable, Comparable<Cell<V>> {

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
     * @implSpec
     * The default implementation performs comparison based on the {@link #x() x}
     * and {@link #y() y} coordinates of the two {@code Cell}s with the {@code y}
     * coordinate having more weight over the {@code x} coordinate. Consider the
     * following:
     * 
     * <p>
     * Assume we have a {@code Cell} implementation {@code CellImp} with the following
     * constructor:
     * <pre> 
     * <code>
     * public CellImp(int x, int y) {
     * 	this.x = x; // where x is the x coordinate of this cell
     * 	this.y = y; // where y is the y coordinate of this cell
     * }
     * </code>
     * </pre>
     * 
     * Then consider the following code snippet:
     * 
     * <pre> {@code
	 * CellImp<V> a = new CellImp<>(2, 3);
	 * CellImp<V> b = new CellImp<>(3, 2);
	 * CellImp<V> c = new CellImp<>(3, 3);
	 * CellImp<V> d = new CellImp<>(2, 0);
	 * CellImp<V> e = new CellImp<>(2, 0);
	 * 
	 * System.out.println(a.compare(b));
	 * System.out.println(a.compare(c));
	 * System.out.println(b.compare(c));
	 * System.out.println(c.compare(d));
	 * System.out.println(e.compare(d));
	 * }
	 * </pre>
	 * 
	 * Then the code snippet above should produce the following output:
	 * <pre> {@code
	 * 1
	 * -1
	 * -1
	 * 0
	 * }
	 * </pre>
	 * 
	 * where {@code 1} can be any positive {@code Integer} and {@code -1} can be
	 * any negative {@code Integer}.
	 * </p>
	 */
	@Override
	default int compareTo(Cell<V> other) {
		// TODO Needs further testing to ensure the comparison gives excepted results 
		requireNonNull(other, "other cannot be null.");
		return Integer.compare(
				Integer.compare(y(), other.y()),
				Integer.compare(x(), other.x()));
	}
	
	/**
	 * Changes the {@link Symbol} of this cell to a new value. If this is an initial cell, then this call 
	 * will fail with an {@link InitialCellModificationException}. A {@code NullPointerException} will also
	 * be thrown if a {@code null} reference is passed to this method. 
	 * 
	 * @param value the new {@code Symbol} to set on this cell.
	 * 
	 * @throws InitialCellModificationException if this is an initial cell.
	 * @throws NullPointerException if @ {@code value} is {@code null}.
 	 */
	void changeSymbol(Symbol<V> value);
	
	/**
	 * <p>
	 * Marks this cell as an initial cell and sets the given {@link Symbol} as the {@code Symbol} for this
	 * cell. Any modifications attempts on this cell after this call will result in an {@link InitialCellModificationException}
	 * being thrown until this cell is changed back to an normal cell.
	 * </p>
	 * 
	 * <p>
	 * Multiple calls of this method on an initial cell are allowed and should not throw an
	 * {@code InitialCellModificationException}.
	 * </p>
	 * 
	 * @param initialValue the {@code Symbol} to set to this initial cell.
	 * 
	 * @throws NullPointerException if {@code initialValue} is {@code null}.
	 */
	void markInitial(Symbol<V> initialValue);
	
	/**
	 * Marks this cell as a normal cell allowing modification of the cell. 
	 * 
	 * <p>
	 * Multiple calls of this method on a normal cell are allowed and should return cleanly.
	 * </p>
	 */
	void markNormal();
	
	/**
	 * Adds the given {@link Symbol} as a possible value for this cell. Adding an already
	 * noted {@code Symbol} should have no effect and should result in a clean return.
	 * 
	 * @param note the {@code Symbol} to mark as a possible value for this cell.
	 * 
	 * @throws NullPointerException if {@code note} is {@code null}.
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
	 */
	void removeNote(Symbol<V> note);
	
	/**
	 * Returns the x coordinate of this cell. This is also the index of the column in which
	 * this cell belongs.
	 * 
	 * @return the x coordinate of this cell.
	 */
	int x();
	
	/**
	 * Returns the y coordinate of this cell. This is also the index of the row in which
	 * this cell belongs.
	 * 
	 * @return the y coordinate of this cell.
	 */
	int y();

	/**
	 * Returns a unique {@code String} that can be used to identify this cell in a {@code LatinSquare}.
	 * 
	 * @return a unique identifier of this cell in a {@code LatinSquare}.
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
	 * Returns a {@link Set} of {@code Symbol}s marked as possible values for this cell.
	 * 
	 * @return a {@link Set} of {@code Symbol}s marked as possible values for this cell.
	 */
	Set<Symbol<V>> notes();
	
	/**
	 * Indicates whether this cell is marked as an initial cell or not. Returns {@code true} if this
	 * is an initial cell or {@code false} otherwise.
	 * 
	 * @return {@code true} if this is an initial cell or {@code false} otherwise.
	 */
	boolean isInitial();
}
