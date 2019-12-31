/**
 * 
 */
package com.kori_47.sudoku;

import java.util.Map;

/**
 * An object that creates new {@link Row}s. Using cell factories avoids hardwiring and enables clients
 * to use special {@code Row} subclasses.
 * 
 * <p>This is a functional interface whose functional method is {@link #createRow(String, int, Map, int)}.
 * 
 * @param <V> the type of value held by the {@link Symbol}s supported by this {@code Row}.
 * 
 * 
 * @author <a href="https://github.com/kennedykori">Kennedy Kori</a>
 *
 * @since Tue, 31 Dec 2019 14:23:53
 * 
 * @see ColumnFactory
 */
@FunctionalInterface
public interface RowFactory<V> {
	
	/**
	 * Creates and returns a new {@link Row} instance with the given properties.
	 * 
	 * @param id the identifier to assign to the new {@code Row}.
	 * @param size the size of the new {@code Row}.
	 * @param cells the {@link Cell}s of the new {@code Row}.
	 * @param y the index of the new {@code Row}.
	 * 
	 * @return a new {@code Row} with the given properties.
	 */
	Row<V> createRow(String id, int size, Map<String, Cell<V>> cells, int y);

}
