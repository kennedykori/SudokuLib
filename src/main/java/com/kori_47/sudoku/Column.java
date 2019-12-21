/**
 * 
 */
package com.kori_47.sudoku;

/**
 * This is a type of an {@link InterpolatableCellGroup} that represents a column in a 
 * {@link LatinSquare}. This interface extends the {@code InterpolatableCellGroup} and has no
 * methods of its own. It servers to identify {@code CellGroup}s that are {@code LatinSquare}
 * columns.
 * 
 * @author <a href="https://github.com/kennedykori">Kennedy Kori</a>
 *
 * @since Sat, 21 Dec 2019 20:03:16
 * 
 * @see InterpolatableCellGroup
 * @see Row
 */
public interface Column<V> extends InterpolatableCellGroup<V> {

}