/**
 * 
 */
package com.kori_47.sudoku;

/**
 * <p> This is a type of an {@link InterpolatableCellGroup} that represents a column in a {@link LatinSquare}.
 * This interface extends the {@code InterpolatableCellGroup} and servers to identify {@code CellGroup}s
 * that are {@code LatinSquare} columns.
 * 
 * <p> Implementations must make sure that all the {@link Cell}s in this {@code Column} have the same x 
 * coordinate as the {@link #x() index} of this {@code Column}.
 * 
 * @author <a href="https://github.com/kennedykori">Kennedy Kori</a>
 *
 * @since Sat, 21 Dec 2019 20:03:16
 * 
 * @see InterpolatableCellGroup
 * @see Row
 */
public interface Column<V> extends InterpolatableCellGroup<V> {

	/**
	 * Returns the index of this {@code Column} on the {@link LatinSquare} that this {@code Column} is
	 * part of, with {@code 0} being the first {@code Column} and <i>{@code s - 1}</i> being the the
	 * index of the last {@code Column} where <i>s</i> is the size of the {@code LatinSquare} that this
	 * {@code Column} belongs to.
	 * 
	 * @return the index of this {@code Column} on the {@code LatinSquare} that this {@code Column} is
	 *  of part.
	 */
	int x();
}
