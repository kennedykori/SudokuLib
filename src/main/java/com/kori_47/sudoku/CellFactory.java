/**
 * 
 */
package com.kori_47.sudoku;

/**
 * An object that creates new {@link Cell cells}. Using cell factories avoids hardwiring and
 * enables clients to use special {@code Cell} subclasses.
 * 
 * @author <a href="https://github.com/kennedykori">Kennedy Kori</a>
 *
 * @since Oct 19, 2019, 12:57:40 AM
 */
@FunctionalInterface
public interface CellFactory<V> {
	
	/**
	 * Should return a new {@link Cell} with the given {@link Symbol} and x and y coordinates.
	 * 
	 * @param x the x coordinate that the new {@code Cell} should have.
	 * @param y the y coordinate that the new {@code Cell} should have.
	 * @param symbol the {@code Symbol} to assign to the new {@code Cell}.
	 * 
	 * @return the newly created cell.
	 */
	Cell<V> createCell(int x, int y, Symbol<V> symbol);
}
