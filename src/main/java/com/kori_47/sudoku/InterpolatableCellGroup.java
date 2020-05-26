/**
 * 
 */
package com.kori_47.sudoku;

/**
 * This represents a {@link CellGroup} whose region <i>(the {@link Cell}s in the {@code CellGroup})</i> 
 * can easily be determined <i>(interpolated)</i> using a simple formula when given the {@link #startCell()
 * starting position} or {@link #endCell() end position} and the size of the {@code CellGroup}. {@link Row}s
 * and {@link Column}s are examples of {@code InterpolatableCellGroup}s. A {@link LatinSquare} is also 
 * a special type of an {@code InterpolatableCellGroup} since it's region can be easily calculated 
 * when given the first/last {@code Cell} and size of the {@code LatinSquare}.
 * 
 * @author <a href="https://github.com/kennedykori">Kennedy Kori</a>
 *
 * @since Oct 17, 2019, 3:00:43 AM
 * 
 * @see CellGroup
 * @see Row
 * @see Column
 */
public interface InterpolatableCellGroup<V> extends CellGroup<V> {
	
	/**
	 * Returns the first {@link Cell} at the beginning of the region covered by this {@code CellGroup}.
	 * This {@code Cell} can be passed at this {@code CellGroup}'s creation time or calculated later
	 * using a formula.
	 * 
	 * @return the first {@code Cell} at the beginning of the region covered by this {@code CellGroup}.
	 */
	Cell<V> startCell();

	/**
	 * Returns the last {@link Cell} at the end of the region covered by this {@code CellGroup}.
	 * This {@code Cell} can be passed at this {@code CellGroup}'s creation time or calculated later
	 * using a formula.
	 * 
	 * @return the last {@code Cell} at the end of the region covered by this {@code CellGroup}.
	 */
	Cell<V> endCell();
}
