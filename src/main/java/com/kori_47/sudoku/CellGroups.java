/**
 * 
 */
package com.kori_47.sudoku;

import static com.kori_47.utils.ObjectUtils.requireGreaterThanOrEqualTo;
import static com.kori_47.utils.ObjectUtils.requireInRange;

import static java.util.Objects.requireNonNull;

import java.util.Map;

/**
 * This class consists exclusively of static methods that return different implementations of {@link CellGroup}.
 * 
 * @author <a href="https://github.com/kennedykori">Kennedy Kori</a>
 *
 * @since Mon, 30 Dec 2019 19:19:08
 * 
 * @see CellGroup
 * @see Row
 * @see Column
 * @see Block
 */
public final class CellGroups {

	/**
	 * <p> Creates and returns an instance of a {@link Row} with the specified properties.
	 * 
	 * <p> This method throws an {@link IllegalArgumentException} if the following conditions aren't met.
	 * <ul>
	 * <li>The given size must be greater than or equal to {@code 1}.</li>
	 * <li>The number of cells given must be equal to the size given.</li>
	 * <li>The index provided must be greater than or equal to {@code 0} but less than the given size.</li>
	 * </ul>
	 * 
	 * @param <V> the type of value held by the {@link Symbol}s supported by this {@link Row}.
	 * 
	 * @param id the identifier of the {@code Row} instance to be created.
	 * @param size the size of the {@code Row} instance to be created. This is also the number of {@link Cell}s
	 * 		in the {@code Row}.
	 * @param cells a {@code Map} of {@link Cell}s that will be used as this {@code Row}'s {@code Cell}s. 
	 * @param y the index of this {@code Row}.
	 * 
	 * @return an instance of a {@code Row} with the specified properties.
	 * 
	 * @throws NullPointerException if either {@code id} or {@code cells} is {@code null}.
	 * @throws IllegalArgumentException if any of the conditions stated above isn't met.
	 */
	public static final <V> Row<V> rowOf(String id, int size, Map<String, Cell<V>> cells, int y) {
		return new SimpleRow<V>(id, size, cells, y);
	}

	/**
	 * <p> Creates and returns an instance of a {@link Column} with the specified properties.
	 * 
	 * <p> This method throws an {@link IllegalArgumentException} if the following conditions aren't met.
	 * <ul>
	 * <li>The given size must be greater than or equal to {@code 1}.</li>
	 * <li>The number of cells given must be equal to the size given.</li>
	 * <li>The index provided must be greater than or equal to {@code 0} but less than the given size.</li>
	 * </ul>
	 * 
	 * @param <V> the type of value held by the {@link Symbol}s supported by this {@link Column}.
	 * 
	 * @param id the identifier of the {@code Column} instance to be created.
	 * @param size the size of the {@code Column} instance to be created. This is also the number of {@link Cell}s
	 * 		in the {@code Column}.
	 * @param cells a {@code Map} of {@link Cell}s that will be used as this {@code Column}'s {@code Cell}s. 
	 * @param x the index of this {@code Column}.
	 * 
	 * @return an instance of a {@code Column} with the specified properties.
	 * 
	 * @throws NullPointerException if either {@code id} or {@code cells} is {@code null}.
	 * @throws IllegalArgumentException if any of the conditions stated above isn't met.
	 */
	public static final <V> Column<V> columnOf(String id, int size, Map<String, Cell<V>> cells, int x) {
		return new SimpleColumn<V>(id, size, cells, x);
	}
	
	/**
	 * Returns a {@link RowFactory} instance that can be used for instantiating {@link Row}s. The {@code Row}
	 * instances created by the returned {@code RowFactory} have the same properties as those returned by
	 * {@link CellGroups#rowOf(String, int, Map, int)}.
	 * 
	 * @param <V> the type of {@code Symbol} values supported by the {@code Row} instances returned by this factory. 
	 * 
	 * @return a {@code RowFactory} instance that can be used for instantiating {@code Row}s.
	 */
	public static final <V> RowFactory<V> defaultRowFactory() {
		return (id, size, cells, y) -> rowOf(id, size, cells, y);
	}
	
	/**
	 * Returns a {@link ColumnFactory} instance that can be used for instantiating {@link Column}s. The {@code Column}
	 * instances created by the returned {@code ColumnFactory} have the same properties as those returned by
	 * {@link CellGroups#columnOf(String, int, Map, int)}.
	 * 
	 * @param <V> the type of {@code Symbol} values supported by the {@code Column} instances returned by this factory. 
	 * 
	 * @return a {@code ColumnFactory} instance that can be used for instantiating {@code Column}s.
	 */
	public static final <V> ColumnFactory<V> defaultColumnFactory() {
		return (id, size, cells, x) -> columnOf(id, size, cells, x);
	}
	
	/**
	 * Returns a {@link BlockFactory} instance that can be used for instantiating {@link Block}s. The {@code Block}
	 * instances created by the returned {@code BlockFactory} have the same properties as those returned by
	 * {@link CellGroups#rowOf(String, int, Map, int)}.
	 * 
	 * @param <V> the type of {@code Symbol} values supported by the {@code Block} instances returned by this factory. 
	 * 
	 * @return a {@code BlockFactory} instance that can be used for instantiating {@code Block}s.
	 */
	public static final <V> BlockFactory<V> defaultBlockFactory() {
		return null;
	}

	/**
	 * A simple implementation of the {@link Row} interface.
	 * 
	 * @author <a href="https://github.com/kennedykori">Kennedy Kori</a>
	 *
	 * @since Mon, 30 Dec 2019 22:20:11
	 * 
	 * @param <V> the type of value held by the {@link Symbol}s supported by this {@link Row}.
	 */
	private static final class SimpleRow<V> extends AbstractUniqueCellGroup<V> implements Row<V> {

		/**
		 * the index of this {@code Row}.
		 */
		private final int y;
		
		SimpleRow(String id, int size, Map<String, Cell<V>> cells, int y) {
			super(id, size, validateRowAndColumnArgs(size, cells, y, "y"));
			this.y = y;
		}

		@Override
		public Cell<V> startCell() {
			return getCell(0, y).get();
		}

		@Override
		public Cell<V> endCell() {
			return getCell(size() - 1, y).get();
		}

		@Override
		public int y() {
			return y;
		}
	}

	/**
	 * A simple implementation of the {@link Column} interface.
	 * 
	 * @author <a href="https://github.com/kennedykori">Kennedy Kori</a>
	 *
	 * @since Mon, 30 Dec 2019 22:20:11
	 * 
	 * @param <V> the type of value held by the {@link Symbol}s supported by this {@link Row}.
	 */
	private static final class SimpleColumn<V> extends AbstractUniqueCellGroup<V> implements Column<V> {

		/**
		 * the index of this {@code Column}.
		 */
		private final int x;
		
		SimpleColumn(String id, int size, Map<String, Cell<V>> cells, int x) {
			super(id, size, validateRowAndColumnArgs(size, cells, x, "x"));
			this.x = x;
		}

		@Override
		public Cell<V> startCell() {
			return getCell(x, 0).get();
		}

		@Override
		public Cell<V> endCell() {
			return getCell(x, size() - 1).get();
		}

		@Override
		public int x() {
			return x;
		}
	}
	
	private static final int requireEquals(int baseValue, int value, String message) {
		if(value != baseValue) throw new IllegalArgumentException(message);
		return value;
	}
	
	private final static <V> Map<String, Cell<V>> validateRowAndColumnArgs(int size, Map<String, Cell<V>> cells, int xOry, String coordinate) {
		// validate method args
		requireGreaterThanOrEqualTo(1, size, "size must be greater than or equal to 1.");
		requireNonNull(cells, "cells cannot be null.");
		requireEquals(size, cells.size(), "You must provide axactly " + size + " cells");
		requireInRange(0, size, xOry, coordinate + " must be greater than 0 but less than " + size);
		// validate all the given cells have the given x or y coordinate
		if (coordinate.equals("y"))
			cells.forEach((id, cell) -> requireEquals(xOry, cell.y(), "The " + coordinate + " coordinate of " + cell + " must be " + xOry));
		else
			cells.forEach((id, cell) -> requireEquals(xOry, cell.x(), "The " + coordinate + " coordinate of " + cell + " must be " + xOry));
		return cells;
	}
	
	// private constructor to prevent instantiation of this class
	private CellGroups() {}

}
