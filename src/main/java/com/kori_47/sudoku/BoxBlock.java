/**
 * 
 */
package com.kori_47.sudoku;

/**
 * This represents a {@link Sudoku} {@link Block} that is box/rectangular shaped. A {@code BoxBlock} can
 * easily be described by the number of {@link Row}s and {@link Column}s it has.
 * 
 * @param <V> the type of value held by the {@link Symbol}s supported by this {@code BoxBlock}.
 * 
 * @author <a href="https://github.com/kennedykori">Kennedy Kori</a>
 *
 * @since Sun, 9 Feb 2020 00:34:55
 * 
 * @see Block
 * @see Row
 * @see Column
 * @see Sudoku
 */
public interface BoxBlock<V> extends Block<V> {
	
	/**
	 * Returns the number of {@link Row}s in this {@code BoxBlock}.
	 * 
	 * @return the number of {@code Row}s in this {@code BoxBlock}.
	 */
	int blockRows();

	/**
	 * Returns the number of {@link Column}s in this {@code BoxBlock}.
	 * 
	 * @return the number of {@code Column}s in this {@code BoxBlock}.
	 */
	int blockColumns();

}
