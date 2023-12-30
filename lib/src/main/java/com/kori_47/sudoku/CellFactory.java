/**
 * 
 */
package com.kori_47.sudoku;

/**
 * An object that creates new {@link Cell}s. Using cell factories avoids hardwiring and
 * enables clients to use special {@code Cell} subclasses.
 * 
 * <p>This is a functional interface whose functional method is {@link #createCell(String, int, int, Symbol)}.
 * 
 * @param <V> the type of value held by the {@link Symbol}s supported by the {@code Cell}s created
 * using this {@code CellFactory}.
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
	 * @param id the identifier to assign to the new {@code Cell}.
	 * @param x the x coordinate that the new {@code Cell} should have.
	 * @param y the y coordinate that the new {@code Cell} should have.
	 * @param symbol the {@code Symbol} to assign to the new {@code Cell}.
	 * 
	 * @return the newly created {@code Cell} with the given properties.
	 */
	Cell<V> createCell(String id, int x, int y, Symbol<V> symbol);
}
