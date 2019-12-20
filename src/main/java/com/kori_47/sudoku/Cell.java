/**
 * 
 */
package com.kori_47.sudoku;

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
 * @author <a href="https://github.com/kennedykori">Kennedy Kori</a>
 *
 * @since Oct 17, 2019, 12:38:39 AM
 */
public interface Cell<V> extends Formattable {
	
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
