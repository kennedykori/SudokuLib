/**
 * 
 */
package com.kori_47.sudoku;

/**
 * <p>This is a type of an {@link InterpolatableCellGroup} that represents a row in a {@link LatinSquare}.
 * This interface extends the {@code InterpolatableCellGroup} and servers to  identify {@code CellGroup}s
 * that are {@code LatinSquare} rows.
 * 
 * <p> Implementations must make sure that all the {@link Cell}s in this {@code Row} have the same y coordinate
 * as the {@link #y() index} of this {@code Row}.
 * 
 * @param <V> the type of value held by the {@link Symbol}s supported by this {@code Row}.
 * 
 * @author <a href="https://github.com/kennedykori">Kennedy Kori</a>
 *
 * @since Sat, 21 Dec 2019 20:03:16
 * 
 * @see InterpolatableCellGroup
 * @see Column
 */
public interface Row<V> extends UniqueCellGroup<V>, InterpolatableCellGroup<V> {

	/**
	 * Returns the index of this {@code Row} on the {@link LatinSquare} that this {@code Row} is
	 * part of, with {@code 0} being the first {@code Row} and <i>{@code s - 1}</i> being the the
	 * index of the last {@code Row} where <i>s</i> is the size of the {@code LatinSquare} that this
	 * {@code Row} belongs to.
	 * 
	 * @return the index of this {@code Row} on the {@code LatinSquare} that this {@code Row} is
	 *  of part.
	 */
	int y();
}
