/**
 * 
 */
package com.kori_47.sudoku;

/**
 * @author Kennedy Kori
 *
 * @since Oct 19, 2019, 12:57:40 AM
 */
@FunctionalInterface
public interface CellFactory<V> {
	
	/**
	 * Should return a new {@code Cell} with the given {@code Symbol} and x and y coordinates.
	 * 
	 * @param x the x coordinate that the new {@code Cell} should have.
	 * @param y the y coordinate that the new {@code Cell} should have.
	 * @param symbol the {@code Symbol} to assign to the new {@code Cell}.
	 * 
	 * @return the newly created cell.
	 */
	Cell<V> createCell(int x, int y, Symbol<V> symbol);

}
