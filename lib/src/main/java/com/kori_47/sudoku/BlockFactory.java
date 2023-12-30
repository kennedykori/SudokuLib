/**
 * 
 */
package com.kori_47.sudoku;

import java.util.Map;

/**
 * An object that creates new {@link Block}s. Using cell factories avoids hardwiring and enables clients
 * to use special {@code Block} subclasses.
 * 
 * <p>This is a functional interface whose functional method is {@link #createBlock(String, int, Map, Cell, Cell)}.
 * 
 * @param <V> the type of {@code Symbol} values supported by the {@code Block} instances returned by this factory.
 * 
 * @author <a href="https://github.com/kennedykori">Kennedy Kori</a>
 *
 * @since Wed, 1 Jan 2020 13:05:49
 */
@FunctionalInterface
public interface BlockFactory<V> {

	/**
	 * Creates and returns a new {@link Block} instance with the given properties.
	 * 
	 * @param id the identifier to assign to the new {@code Block}.
	 * @param size the size of the new {@code Block}.
	 * @param cells the {@link Cell}s of the new {@code Block}.
	 * @param startCell the first {@code Cell} of this {@code Block}.
	 * @param endCell the last {@code Cell} of this {@code Block}.
	 * 
	 * @return a new {@code Block} with the given properties.
	 */
	Block<V> createBlock(String id, int size, Map<String, Cell<V>> cells, Cell<V> startCell, Cell<V> endCell);
}
