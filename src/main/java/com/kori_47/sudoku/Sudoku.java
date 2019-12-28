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
	 * Returns the {@link SudokuVariant} that describes this {@code Sudoku}.
	 * 
	 * @return the {@code SudokuVariant} that describes this {@code Sudoku}.
	 */
	SudokuVariant variant();

	/**
	 * Returns a {@code Map} of the {@link Block}s contained in this {@code Sudoku}. Modification of the 
	 * returned {@code Map} should not alter the {@code Block}s of this {@code Sudoku}. Implementations of
	 * this interface can also choose to return an unmodifiable {@code Map} instead to prevent modifications.
	 * 
	 * @return a {@code Map} of the {@code Block}s contained in this {@code Sudoku}.
	 */
	Map<String, Block<V>> blocks();
}
