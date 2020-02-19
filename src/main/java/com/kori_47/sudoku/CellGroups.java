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
	 * <p> Creates and returns an instance of a {@link BoxBlock} with the specified properties.
	 * 
	 * <p> This method throws an {@link IllegalArgumentException} if the following conditions aren't met.
	 * <ul>
	 * <li>The given size must be greater than or equal to {@code 1}.</li>
	 * <li>The number of cells given must be equal to the size given.</li>
	 * <li>The start {@code Cell} given must be in the provided cells {@code Map}.</li>
	 * <li>The given block {@link Row}s must be greater than or equal to {@code 1}.</li>
	 * <li>The given block {@link Column}s must be greater than or equal to {@code 1}.</li>
	 * </ul>
	 * 
	 * @param <V> the type of value held by the {@link Symbol}s supported by this {@link Column}.
	 * 
	 * @param id the identifier of the {@code BoxBlock} instance to be created.
	 * @param size the size of the {@code BoxBlock} instance to be created. This is also the number of {@link Cell}s
	 * 		in the {@code BoxBlock}.
	 * @param cells a {@code Map} of {@link Cell}s that will be used as this {@code BoxBlock}'s {@code Cell}s.
	 * @param startCell the first {@code Cell} at the beginning of the region to be covered by this {@code BoxBlock}. This
	 * 		{@code Cell} must be in the given {@code cells Map}.
	 * @param blockRows the number of {@code Row}s that the returned {@code BoxBlock} instance will have.
	 * @param blockColumns the number of {@code Column}s that the returned {@code BoxBlock} instance will have.
	 * 
	 * @return an instance of a {@code BoxBlock} with the specified properties.
	 * 
	 * @throws NullPointerException if any of the given arguments is {@code null}.
	 * @throws IllegalArgumentException if any of the conditions stated above isn't met.
	 * 
	 * @see #boxBlockOf(String, int, Map, Cell, Cell)
	 */
	public static final <V> BoxBlock<V> boxBlockOf(String id, int size, Map<String, Cell<V>> cells, Cell<V> startCell, int blockRows, int blockColumns) {
		return new SimpleBoxBlock<V>(id, size, cells, startCell, blockRows, blockColumns);
	}
	
	/**
	 * <p> Creates and returns an instance of a {@link BoxBlock} with the specified properties.
	 * 
	 * <p> This method throws an {@link IllegalArgumentException} if the following conditions aren't met.
	 * <ul>
	 * <li>The given size must be greater than or equal to {@code 1}.</li>
	 * <li>The number of cells given must be equal to the size given.</li>
	 * <li>The start and end {@code Cell}s given must be in the provided cells {@code Map}.</li>
	 * </ul>
	 * 
	 * @param <V> the type of value held by the {@link Symbol}s supported by this {@link Column}.
	 * 
	 * @param id the identifier of the {@code BoxBlock} instance to be created.
	 * @param size the size of the {@code BoxBlock} instance to be created. This is also the number of {@link Cell}s
	 * 		in the {@code BoxBlock}.
	 * @param cells a {@code Map} of {@link Cell}s that will be used as this {@code BoxBlock}'s {@code Cell}s.
	 * @param startCell the first {@code Cell} at the beginning of the region to be covered by this {@code BoxBlock}. This
	 * 		{@code Cell} must be in the given {@code cells Map}.
	 * @param endCell the last {@code Cell} at the end of the region to be covered by this {@code BoxBlock}. This {@code Cell}
	 * 		must be in the given {@code cells Map}.  
	 * 
	 * @return an instance of a {@code BoxBlock} with the specified properties.
	 * 
	 * @throws NullPointerException if any of the given arguments is {@code null}.
	 * @throws IllegalArgumentException if any of the conditions stated above isn't met.
	 * 
	 * @see #boxBlockOf(String, int, Map, Cell, int, int)
	 */
	public static final <V> BoxBlock<V> boxBlockOf(String id, int size, Map<String, Cell<V>> cells, Cell<V> startCell, Cell<V> endCell) {
		return new SimpleBoxBlock<V>(id, size, cells, startCell, endCell);
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
	 * Returns a {@link BlockFactory} instance that can be used for instantiating {@link BoxBlock}s. The {@code BoxBlock}
	 * instances created by the returned {@code BlockFactory} have the same properties as those returned by
	 * {@link CellGroups#boxBlockOf(String, int, Map, Cell, Cell)}.
	 * 
	 * @param <V> the type of {@code Symbol} values supported by the {@code BoxBlock} instances returned by this factory. 
	 * 
	 * @return a {@code BlockFactory} instance that can be used for instantiating {@code BoxBlock}s.
	 */
	public static final <V> BlockFactory<V> defaultBoxBlockFactory() {
		return (id, size, cells, startCell, endCell) -> boxBlockOf(id, size, cells, startCell, endCell);
	}

	/**
	 * Returns the hash code of the given {@link CellGroup}. The hash code of the {@code CellGroup} should be derived from the
	 * hash codes of the following properties of a {@code CellGroup}:
	 * <ul>
	 * <li>The hash code value of this {@code CellGroup}'s <i>{@link CellGroup#size() size}</i> as returned by {@link Integer#hashCode(int)}</li>
	 * <li>The hash code value of this {@code CellGroup}'s <i>{@link CellGroup#cells() cells}</i> {@code Map}.</li>
	 * </ul>
	 * 
	 * <p>
	 * The hash code value returned by this method is guaranteed to obey the contract of the {@link CellGroup#hashCode hashCode}
	 * method as defined in the {@code CellGroup} interface.
	 * 
	 * @param cellGroup the {@code CellGroup} whose hash code we are interested in.
	 * 
	 * @return the hash code of the given {@code CellGroup}.
	 * 
	 * @throws NullPointerException if {@code cellGroup} is {@code null}.
	 * 
	 * @see CellGroup#hashCode()
	 */
	public static final int hashCode(CellGroup<?> cellGroup) {
		requireNonNull(cellGroup, "cellGroup cannot be null.");
		int hashCode = Integer.hashCode(cellGroup.size());
		return 31 * hashCode + cellGroup.cells().hashCode();
	}

	/**
	 * Compares the given {@code CellGroup} with the given {@code Object} for equality. Returns {@code true} if the
	 * given object is also a {@code CellGroup} and the two {@code CellGroup}s are identical. Two {@code CellGroup}s
	 * are said to be equal if each of the following of their properties are also equal:
	 * <ul>
	 * <li>The <i>{@link CellGroup#size() sizes}</i> of the {@code CellGroup}s</li>
	 * <li>The <i>{@link CellGroup#cells() cells}</i> {@code Map}s of the {@code CellGroup}s.</li>
	 * </ul>
	 * 
	 * <p>
	 * This {@code equals} implementation is guaranteed to obey the contract of the {@link CellGroup#equals(Object) equals}
	 * method as defined in the {@code CellGroup} interface.
	 * 
	 * @param cellGroup the {@code CellGroup} to compare to the given object for equality. Must <b>NOT</b> be {@code null}.
	 * @param obj the object to compare for equality with the given {@code CellGroup}. Maybe {@code null}.
	 * 
	 * @return {@code true} if the given {@code CellGroup} is equal to the given object, {@code false} otherwise.
	 * 
	 * @throws NullPointerException if {@code cellGroup} is {@code null}.
	 * 
	 * @see CellGroup#equals(Object)
	 */
	public static final boolean equals(CellGroup<?> cellGroup, Object obj) {
		requireNonNull(cellGroup, "cellGroup cannot be null.");
		if (cellGroup == obj) return true;
		if (!(obj instanceof CellGroup)) return false;
		CellGroup<?> _obj = (CellGroup<?>) obj;
		return cellGroup.size() == _obj.size() && cellGroup.cells().equals(_obj.cells());
	}
	
	/**
	 * Returns the hash code of the given {@link UniqueCellGroup}. The hash code of the {@code UniqueCellGroup} should be
	 * derived from the hash codes of the following properties of a {@code UniqueCellGroup}:
	 * <ul>
	 * <li>The hash code value of this {@code UniqueCellGroup}'s <i>{@link UniqueCellGroup#id() id}</i>.</li>
	 * <li>The hash code value of this {@code UniqueCellGroup}'s <i>{@link UniqueCellGroup#size() size}</i> as returned by {@link Integer#hashCode(int)}</li>
	 * <li>The hash code value of this {@code UniqueCellGroup}'s <i>{@link UniqueCellGroup#cells() cells}</i> {@code Map}.</li>
	 * </ul>
	 * 
	 * <p>
	 * The hash code value returned by this method is guaranteed to obey the contract of the {@link UniqueCellGroup#hashCode hashCode}
	 * method as defined in the {@code UniqueCellGroup} interface.
	 * 
	 * @param uniqueCellGroup the {@code UniqueCellGroup} whose hash code we are interested in.
	 * 
	 * @return the hash code of the given {@code UniqueCellGroup}.
	 * 
	 * @throws NullPointerException if {@code uniqueCellGroup} is {@code null}.
	 * 
	 * @see UniqueCellGroup#hashCode()
	 */
	public static final int hashCode(UniqueCellGroup<?> uniqueCellGroup) {
		requireNonNull(uniqueCellGroup, "uniqueCellGroup cannot be null.");
		int hashCode = hashCode(((CellGroup<?>) uniqueCellGroup));
		return 31 * hashCode + uniqueCellGroup.id().hashCode();
	}

	/**
	 * Compares the given {@code UniqueCellGroup} with the given {@code Object} for equality. Returns {@code true} if the
	 * given object is also a {@code UniqueCellGroup} and the two {@code UniqueCellGroup}s are identical. Two
	 * {@code UniqueCellGroup}s are said to be equal if each of the following of their properties are also equal:
	 * <ul>
	 * <li>The <i> {@link UniqueCellGroup#id() ids}</i> of the {@code UniqueCellGroup}s.</li>
	 * <li>The <i>{@link UniqueCellGroup#size() sizes}</i> of the {@code UniqueCellGroup}s</li>
	 * <li>The <i>{@link UniqueCellGroup#cells() cells}</i> {@code Map}s of the {@code UniqueCellGroup}s.</li>
	 * </ul>
	 * 
	 * <p>
	 * This {@code equals} implementation is guaranteed to obey the contract of the {@link UniqueCellGroup#equals(Object) equals}
	 * method as defined in the {@code UniqueCellGroup} interface.
	 * 
	 * @param uniqueCellGroup the {@code UniqueCellGroup} to compare to the given object for equality. Must <b>NOT</b> be {@code null}.
	 * @param obj the object to compare for equality with the given {@code UniqueCellGroup}. Maybe {@code null}.
	 * 
	 * @return {@code true} if the given {@code UniqueCellGroup} is equal to the given object, {@code false} otherwise.
	 * 
	 * @throws NullPointerException if {@code uniqueCellGroup} is {@code null}.
	 * 
	 * @see UniqueCellGroup#equals(Object)
	 */
	public static final boolean equals(UniqueCellGroup<?> uniqueCellGroup, Object obj) {
		requireNonNull(uniqueCellGroup, "uniqueCellGroup cannot be null.");
		if (uniqueCellGroup == obj) return true;
		if (!(obj instanceof UniqueCellGroup)) return false;
		UniqueCellGroup<?> _obj = (UniqueCellGroup<?>) obj;
		return equals(((CellGroup<?>) uniqueCellGroup), _obj) && uniqueCellGroup.id().equals(_obj.id());
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
	
	/**
	 * A simple implementation of the {@link BoxBlock} interface.
	 * 
	 * @param <V> the type of value held by the {@link Symbol}s supported by this {@link Block}.
	 * 
	 * @author <a href="https://github.com/kennedykori">Kennedy Kori</a>
	 *
	 * @since Sun, 9 Feb 2020 00:10:38
	 */
	private static final class SimpleBoxBlock<V> extends AbstractUniqueCellGroup<V> implements BoxBlock<V> {

		private final Cell<V> startCell;
		private final Cell<V> endCell;
		private final int blockRows;
		private final int blockColumns;
		
		SimpleBoxBlock(String id, int size, Map<String, Cell<V>> cells, Cell<V> startCell, int blockRows, int blockColumns) {
			super(id, size, cells);
			this.startCell = validateCellInCellsMap(requireNonNull(startCell, "startCell cannot be null."), cells, startCell + " must be in " + cells);
			this.blockRows = requireGreaterThanOrEqualTo(1, blockRows);
			this.blockColumns = requireGreaterThanOrEqualTo(1, blockColumns);
			this.endCell = this.cells.values().stream()
					.filter(cell -> cell.x() == (this.startCell.x() + blockRows))
					.filter(cell -> cell.y() == (this.startCell.y() + blockColumns))
					.findAny().get();
		}
		
		/* this constructor is useful because of BlockFactory.createBlock() method */ 
		SimpleBoxBlock(String id, int size, Map<String, Cell<V>> cells, Cell<V> startCell, Cell<V> endCell) {
			super(id, size, cells);
			this.startCell = validateCellInCellsMap(requireNonNull(startCell, "startCell cannot be null."), cells, startCell + " must be in " + cells);
			this.endCell = validateCellInCellsMap(requireNonNull(endCell, "endCell cannot be null."), cells, startCell + " must be in " + cells);
			this.blockRows = (this.endCell.x() - this.startCell.x()) + 1;
			this.blockColumns = (this.endCell.y() - this.startCell.y()) + 1;
		}

		@Override
		public Cell<V> startCell() {
			return startCell;
		}

		@Override
		public Cell<V> endCell() {
			return endCell;
		}

		@Override
		public int blockRows() {
			return blockRows;
		}

		@Override
		public int blockColumns() {
			return blockColumns;
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
	
	private final static <V> Cell<V> validateCellInCellsMap(Cell<V> cell, Map<String, Cell<V>> cells, String errorMessage) {
		// validate that the args aren't null.
		requireNonNull(cell, "cell cannot be null.");
		requireNonNull(cells, "cells cannot be null.");
		
		// validate that the given cells is inside the provided cells Map.
		if (!cells.containsKey(cell.id()))
			throw new IllegalArgumentException(errorMessage);
		
		return cell;
	}
	
	// private constructor to prevent instantiation of this class
	private CellGroups() {}
}
