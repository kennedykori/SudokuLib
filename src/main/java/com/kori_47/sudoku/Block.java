/**
 * 
 */
package com.kori_47.sudoku;

/**
 * This represents a region in a {@link Sudoku}. For example, the famous <i>9x9</i> {@code Sudoku}
 * has nine <i>3x3</i> regions.
 * 
 * @author <a href="https://github.com/kennedykori">Kennedy Kori</a>
 *
 * @since Sat, 28 Dec 2019 11:40:23
 * 
 * @see Sudoku
 * @see CellGroup
 * @see InterpolatableCellGroup
 */
public interface Block<V> extends InterpolatableCellGroup<V> {

	/**
	 * Returns the number of {@link Row}s in this {@code Block}.
	 * 
	 * @return the number of {@code Row}s in this {@code Block}.
	 */
	int blockRows();

	/**
	 * Returns the number of {@link Column}s in this {@code Block}.
	 * 
	 * @return the number of {@code Column}s in this {@code Block}.
	 */
	int blockColumns();
}
