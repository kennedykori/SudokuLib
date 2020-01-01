/**
 * 
 */
package com.kori_47.sudoku;

import java.util.Map;

/**
 * <p> A {@code Sudoku} is a logic puzzle whose goal is to fill a grid with {@link Symbol}s such that,
 * each of the {@link Row}s, {@link Column}s and {@link Block}s that compose the grid contain all
 * the {@code Symbol}s in the set of {@code Symbol}s applicable to a given {@code Sudoku}.
 * 
 * <p> A {@code Sudoku} is an special case of a {@link LatinSquare}.
 * 
 * @param <V> the type of value held by the {@code Symbol}s supported by this {@code Sudoku}.
 * 
 * @author <a href="https://github.com/kennedykori">Kennedy Kori</a>
 *
 * @since Sat, 28 Dec 2019 11:29:31
 * 
 * @see LatinSquare
 * @see Block
 * @see SudokuVariant
 * @see SudokuVariants
 */
public interface Sudoku<V> extends LatinSquare<V> {

	/**
	 * Returns the hash code value for this {@code Sudoku}. The hash code of a {@code Sudoku} should
	 * be derived from the hash codes of the following properties of a {@code Sudoku}: 
	 * <ul>
	 * <li>The hash code value of this {@code Sudoku}'s <i>{@link #id() id}</i>.</li>
	 * <li>The hash code value of this {@code Sudoku}'s <i>{@link #variant() variant}</i>.</li>
	 * <li>The hash code value of this {@code Sudoku}'s <i>{@link #emptySymbol() emptySymbol}</i>.</li>
	 * <li>The hash code value of this {@code Sudoku}'s <i>{@link #cells() cells}</i> {@code Map}.</li>
	 * <li>The hash code value of this {@code Sudoku}'s <i>{@link #rows() rows}</i> {@code Map}.</li>
	 * <li>The hash code value of this {@code Sudoku}'s <i>{@link #blocks() blocks}</i> {@code Map}.</li>
	 * <li>The hash code value of this {@code Sudoku}'s <i>{@link #columns() columns}</i> {@code Map}.</li>
	 * <li>The hash code value of this {@code Sudoku}'s <i>{@link #symbols() symbols}</i> {@code Map}.</li>
	 * </ul>
	 * 
	 * @return the hash code value of this {@code Sudoku}.
	 */
	@Override
	int hashCode();

	/**
	 * Compares the specified object with this {@code Sudoku} for equality. Returns {@code true} if the
	 * given object is also a {@code Sudoku} and the two {@code Sudoku}s are identical. Two {@code Sudoku}s are
	 * said to be equal if each of the following of their properties are also equal:
	 * <ul>
	 * <li>The <i>{@link #id() ids}</i> of the {@code Sudoku}s.</li>
	 * <li>The <i>{@link #variant() variants}</i> of the {@code Sudoku}s.</li>
	 * <li>The <i>{@link #emptySymbol() emptySymbols}</i> of the {@code Sudoku}s.</li>
	 * <li>The <i>{@link #cells() cells}</i> {@code Map}s of the {@code Sudoku}s.</li>
	 * <li>The <i>{@link #rows() rows}</i> {@code Map}s of the {@code Sudoku}s.</li>
	 * <li>The <i>{@link #blocks() blocks}</i> {@code Map}s of the {@code Sudoku}s.</li>
	 * <li>The <i>{@link #columns() columns}</i> {@code Map}s of the {@code Sudoku}s.</li>
	 * <li>The <i>{@link #symbols() symbols}</i> {@code Map}s of the {@code Sudoku}s.</li>
	 * </ul>
	 * 
	 * @param obj the object to compare for equality with this {@code Sudoku}.
	 * 
	 * @return {@code true} if this {@code Sudoku} is equal to {@code obj} argument, {@code false} otherwise.
	 */
	@Override
	boolean equals(Object obj);
	
	/**
	 * Creates and returns a new {@code Sudoku} whose {@link Row}s and {@link Cell}s are permuted so
	 * that the new {@code Sudoku} is upside down in relation to this one.
	 * 
	 * @return a new identical but upside down {@code Sudoku} in relation to this one.
	 */
	Sudoku<V> flipHorizontally();

	/**
	 * Creates and returns a new {@code Sudoku} whose {@link Column}s and {@link Cell}s are permuted so
	 * that the new {@code Sudoku} is flipped sideways in relation to this one.
	 * 
	 * @return a new identical but flipped sideways {@code Sudoku} in relation to this one.
	 */
	Sudoku<V> flipVertically();

	/**
	 * Returns a new {@code Sudoku} that has the same properties, {@link Cell cells} and 
	 * cell values as this one.
	 * 
	 * @return a new {@code Sudoku} that has the same properties, {@code Cell}s and cell
	 * values as this one.
	 */
	Sudoku<V> copy();
	
	/**
	 * Returns the {@link SudokuVariant} that describes this {@code Sudoku}.
	 * 
	 * @return the {@code SudokuVariant} that describes this {@code Sudoku}.
	 */
	SudokuVariant variant();

	/**
	 * Returns the {@link BlockFactory} used by this {@code Sudoku} to create new {@link Block}s.
	 * 
	 * @return the {@code BlockFactory} used by this {@code Sudoku} to create new {@code Block}s.
	 */
	BlockFactory<V> blockFactory();
	
	/**
	 * Returns a {@code Map} of the {@link Block}s contained in this {@code Sudoku}. Modification of the 
	 * returned {@code Map} should not alter the {@code Block}s of this {@code Sudoku}. Implementations of
	 * this interface can also choose to return an unmodifiable {@code Map} instead to prevent modifications.
	 * 
	 * @return a {@code Map} of the {@code Block}s contained in this {@code Sudoku}.
	 */
	Map<String, Block<V>> blocks();
}
