/**
 * 
 */
package com.kori_47.sudoku;

import java.util.Map;

/**
 * An object that creates new {@link Column}s. Using cell factories avoids hardwiring and enables clients
 * to use special {@code Column} subclasses.
 * 
 * <p>This is a functional interface whose functional method is {@link #createColumn(String, int, Map, int)}.
 * 
 * @param <V> the type of {@code Symbol} values supported by the {@code Column} instances returned by this factory.
 * 
 * @author <a href="https://github.com/kennedykori">Kennedy Kori</a>
 *
 * @since Tue, 31 Dec 2019 14:06:10
 * 
 * @see RowFactory
 */
@FunctionalInterface
public interface ColumnFactory<V> {

	/**
	 * Creates and returns a new {@link Column} instance with the given properties.
	 * 
	 * @param id the identifier to assign to the new {@code Column}.
	 * @param size the size of the new {@code Column}.
	 * @param cells the {@link Cell}s of the new {@code Column}.
	 * @param x the index of the new {@code Column}.
	 * 
	 * @return a new {@code Column} with the given properties.
	 */
	Column<V> createColumn(String id, int size, Map<String, Cell<V>> cells, int x);
}
