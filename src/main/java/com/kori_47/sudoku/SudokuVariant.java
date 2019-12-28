/**
 * 
 */
package com.kori_47.sudoku;

/**
 * This represent the properties needed to describe a valid {@link Sudoku}, i.e. size, the number of
 * {@link Row}s and {@link Column}s in a {@link Block} and the number of {@code Block}s in a 
 * {@code Sudoku}.
 * 
 * @author <a href="https://github.com/kennedykori">Kennedy Kori</a>
 *
 * @since Sat, 28 Dec 2019 11:30:27
 * 
 * @see Sudoku
 * @see SudokuVariants
 */
public interface SudokuVariant {

	/**
	 * Returns the size of the {@link Sudoku} described by this variant.
	 * 
	 * @return the size of the {@code Sudoku} described by this variant.
	 */
	int size();

	/**
	 * Returns the number of {@link Row}s in each {@link Block} inside the {@link Sudoku} described by
	 * this variant.
	 * 
	 * @return the number of {@code Row}s in each {@code Block} inside the {@code Sudoku} described
	 * by this variant.
	 */
	int blockRows();

	/**
	 * Returns the number of {@link Column}s in each {@link Block} inside the {@link Sudoku} described by
	 * this variant.
	 * 
	 * @return the number of {@code Column}s in each {@code Block} inside the {@code Sudoku} described
	 * by this variant.
	 */
	int blockColumns();

	/**
	 * Returns the number of horizontal {@link Block}s that make up the {@link Sudoku} described by this
	 * variant. 
	 * 
	 * @return the number of horizontal {@code Block}s that make up the {@code Sudoku} described by this
	 * variant. 
	 */
	int xBlocks();

	/**
	 * Returns the number of vertical {@link Block}s that make up the {@link Sudoku} described by this
	 * variant. 
	 * 
	 * @return the number of vertical {@code Block}s that make up the {@code Sudoku} described by this
	 * variant. 
	 */
	int yBlocks();
}
