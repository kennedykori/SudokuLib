/**
 * 
 */
package com.kori_47.sudoku;

import static com.kori_47.utils.ObjectUtils.requireGreaterThanOrEqualTo;
import static com.kori_47.utils.ObjectUtils.requireInRange;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;

import java.util.Comparator;
import java.util.List;
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
	 * default {@code UniqueCellGroup} comparator
	 */
	private final static Comparator<UniqueCellGroup<?>> DEFAULT_UNIQUE_CELL_GROUP_COMPARATOR = Comparator.comparing(UniqueCellGroup::id);
	
	/**
	 * default {@code Row} comparator
	 */
	private final static Comparator<Row<?>> DEFAULT_ROW_COMPARATOR = Comparator.comparingInt(Row::y);
	
	/**
	 * default {@code Column} comparator
	 */
	private final static Comparator<Column<?>> DEFAULT_COLUMN_COMPARATOR = Comparator.comparingInt(Column::x);

	/**
	 * default {@code BoxBlock} comparator
	 */
	private final static Comparator<BoxBlock<?>> DEFAULT_BOX_BLOCK_COMPARATOR = Comparator.<BoxBlock<?>, Cell<?>>comparing(
				BoxBlock::startCell, Cells.defaultComparator()).thenComparing(BoxBlock::endCell);

	/**
	 * <p> Creates and returns an instance of a {@link Row} with the specified properties. The coordinates of the
	 * {@link InterpolatableCellGroup#startCell() startCell} and {@link InterpolatableCellGroup#endCell() endCell}
	 * are derived as follows:
	 * <pre>
	 * 	{@code int startCellXCoordinate = 0, endCellXCoordinate = (size - 1);}
	 * 	{@code int startCellYCoordinate = y, endCellXCoordinate = y;}
	 * 	
	 * 	where {@code size} and {@code y} are the provided {@code size} and {@code y} values respectively. 
	 * </pre>
	 * 
	 * <p> This method throws an {@link IllegalArgumentException} if the following conditions aren't met.
	 * <ul>
	 * <li>The given size must be greater than or equal to {@code 1}.</li>
	 * <li>The number of cells given must be equal to the size given.</li>
	 * <li>The y index provided must be greater than or equal to {@code 0} but less than the given size.</li>
	 * <li>The cells provided must have an x coordinate ranging from {@code 0}, 1, 2, ..., to {@code size - 1} when ordered.</li>
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
	public static <V> Row<V> rowOf(String id, int size, Map<String, Cell<V>> cells, int y) {
		return new SimpleRow<V>(id, size, cells, y);
	}

	/**
	 * <p> Creates and returns an instance of a {@link Column} with the specified properties. The coordinates of the
	 * {@link InterpolatableCellGroup#startCell() startCell} and {@link InterpolatableCellGroup#endCell() endCell}
	 * are derived as follows:
	 * <pre>
	 * 	{@code int startCellXCoordinate = x, endCellXCoordinate = x;}
	 * 	{@code int startCellYCoordinate = 0, endCellXCoordinate = (size - 1);}
	 * 	
	 * 	where {@code size} and {@code x} are the provided {@code size} and {@code x} values respectively. 
	 * </pre>
	 * 
	 * <p> This method throws an {@link IllegalArgumentException} if the following conditions aren't met.
	 * <ul>
	 * <li>The given size must be greater than or equal to {@code 1}.</li>
	 * <li>The number of cells given must be equal to the size given.</li>
	 * <li>The x index provided must be greater than or equal to {@code 0} but less than the given size.</li>
	 * <li>The cells provided must have a y coordinate ranging from {@code 0}, 1, 2, ..., to {@code size - 1} when ordered.</li>
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
	public static <V> Column<V> columnOf(String id, int size, Map<String, Cell<V>> cells, int x) {
		return new SimpleColumn<V>(id, size, cells, x);
	}
	
	/**
	 * <p> Creates and returns an instance of a {@link BoxBlock} with the specified properties. The coordinates of the
	 * {@link BoxBlock#endCell() endCell} of the returned {@code BoxBlock} are calculated by adding the provided {@code blockColumns}
	 * and {@code blockRows} to the given {@code startCell}'s {@code x coordinate - 1} and {@code y coordinate - 1} respectively.
	 * That is :-
	 * <pre> {@code
	 * int endCellXCoordinate = startCell.x() + (blockColumns - 1);
	 * int endCellYCoordinate = startCell.y() + (blockRows - 1);
	 * }
	 * </pre>
	 * 
	 * <p>
	 * If a {@link Cell} with the calculated coordinates isn't present on the provided {@code cells Map}, then a {@link SudokuException}
	 * is thrown. This method also throws an {@link IllegalArgumentException} if the following conditions aren't met.
	 * <ul>
	 * <li>The given size must be greater than or equal to {@code 1}.</li>
	 * <li>The number of cells given must be equal to the size given.</li>
	 * <li>The start {@code Cell} given must be in the provided cells {@code Map}.</li>
	 * <li>The given block {@link Row}s must be greater than or equal to {@code 1}.</li>
	 * <li>The given block {@link Column}s must be greater than or equal to {@code 1}.</li>
	 * <li>The product of the given block {@code Row}s and {@code Column}s must be equal to the provided size.</li>
	 * </ul>
	 * 
	 * @param <V> the type of value held by the {@link Symbol}s supported by this {@link BoxBlock}.
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
	 * @throws SudokuException if the endCell can't be calculated from the given properties.
	 * 
	 * @see #boxBlockOf(String, int, Map, Cell, Cell)
	 */
	public static <V> BoxBlock<V> boxBlockOf(String id, int size, Map<String, Cell<V>> cells, Cell<V> startCell, int blockRows, int blockColumns) {
		return new SimpleBoxBlock<V>(id, size, cells, startCell, blockRows, blockColumns);
	}
	
	/**
	 * <p> Creates and returns an instance of a {@link BoxBlock} with the specified properties. The {@link BoxBlock#blockRows() boxRows}
	 * and {@link BoxBlock#blockColumns() boxColumns} of the returned {@code BoxBlock} are calculated by subtracting the given {@code endCell}'s
	 * and {@code startCell}'s {@code y coordinates + 1} and {@code x coordinates + 1} respectively. That is :-
	 * <pre> {@code
	 * int blockRows = (endCell.y() - startCell.y()) + 1;
	 * int blockColumns = (endCell.x() - startCell.x()) + 1;
	 * }
	 * </pre>
	 * 
	 * <p>If the product of the calculated {@code blockRows} and {@code blockColumns} is <i>not</i> equal to the provided {@code size}, then
	 * a {@link SudokuException} is thrown. This method also throws an {@link IllegalArgumentException} if the following conditions aren't met.
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
	 * @throws SudokuException if the product of the calculated {@code blockRows} and {@code blockColumns} is <i>not</i> equal
	 * 		to the provided {@code size}.
	 * 
	 * @see #boxBlockOf(String, int, Map, Cell, int, int)
	 */
	public static <V> BoxBlock<V> boxBlockOf(String id, int size, Map<String, Cell<V>> cells, Cell<V> startCell, Cell<V> endCell) {
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
	public static <V> RowFactory<V> defaultRowFactory() {
		return CellGroups::rowOf;
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
	public static <V> ColumnFactory<V> defaultColumnFactory() {
		return CellGroups::columnOf;
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
	public static <V> BlockFactory<V> defaultBoxBlockFactory() {
		return CellGroups::boxBlockOf;
	}
	
	/**
	 * Returns a default {@link Comparator} that can be used to compare two {@link UniqueCellGroup}s for equality and
	 * ordering. The comparator returned performs comparisons based on the {@code id}'s of the {@code UniqueCellGroup}'s
	 * and is thus <i>inconsistent with equals</i> as the specification of the {@link UniqueCellGroup#equals(Object) equals}
	 * method on the {@code UniqueCellGroup} interface dictates that equality comparisons on {@code UniqueCellGroup}'s
	 * must use other properties of the {@code UniqueCellGroup}'s other than just their identifiers. This implementation
	 * is however justifiable for {@code UniqueCellGroup} implementations whose identifiers are part of a sequence as their
	 * ordering can easily be determined from their identifiers.
	 * 
	 * @return a {@code Comparator} that can be used for {@code UniqueCellGroup} comparisons.
	 * 
	 * @see UniqueCellGroup#equals(Object)
	 */
	public static Comparator<UniqueCellGroup<?>> defaultUniqueCellGroupComparator() {
		return DEFAULT_UNIQUE_CELL_GROUP_COMPARATOR;
	}
	
	/**
	 * Returns a default {@link Comparator} that can be used to compare two {@link Row}s for equality and ordering. The
	 * comparator returned performs comparisons based on the index of the {@code Row} in a {@link LatinSquare} and is thus
	 * <i>inconsistent with equals</i> as the specification of the {@link Row#equals(Object) equals} method on the {@code Row}
	 * interface dictates that equality comparisons on {@code Row}'s must use other properties of the {@code Row}'s other than
	 * just their indexes. This implementation is however justifiable for {@code Row}'s as their natural ordering in a
	 * {@code LatinSquare} can easily be determined from their indexes.
	 * 
	 * @return a {@code Comparator} that can be used for {@code Row} comparisons.
	 * 
	 * @see Row#equals(Object)
	 */
	public static Comparator<Row<?>> defaultRowComparator() {
		return DEFAULT_ROW_COMPARATOR;
	}
	
	/**
	 * Returns a default {@link Comparator} that can be used to compare two {@link Column}s for equality and ordering. The
	 * comparator returned performs comparisons based on the index of the {@code Column} in a {@link LatinSquare} and is thus
	 * <i>inconsistent with equals</i> as the specification of the {@link Column#equals(Object) equals} method on the {@code Column}
	 * interface dictates that equality comparisons on {@code Column}'s must use other properties of the {@code Column}'s other than
	 * just their indexes. This implementation is however justifiable for {@code Column}'s as their natural ordering in a {@code LatinSquare}
	 * can easily be determined from their indexes.
	 * 
	 * @return a {@code Comparator} that can be used for {@code Column} comparisons.
	 * 
	 * @see Column#equals(Object)
	 */
	public static Comparator<Column<?>> defaultColumnComparator() {
		return DEFAULT_COLUMN_COMPARATOR;
	}

	/**
	 * Returns a default {@link Comparator} that can be used to compare two {@link BoxBlock}s for equality and ordering. The
	 * comparator returned performs comparisons based on the {@link BoxBlock#startCell() startCell} and {@link BoxBlock#endCell() endCell}
	 * of the {@code BoxBlock}s and is thus <i>inconsistent with equals</i> as the specification of the {@link BoxBlock#equals(Object) equals}
	 * method on the {@code BoxBlock} interface dictates that equality comparisons on {@code BoxBlock}'s must use other properties in
	 * addition to the start and end {@code Cell}s. This implementation is however justifiable for {@code BoxBlock}'s as their natural
	 * ordering in a {@link Sudoku} can easily be determined from their start and end {@code Cell}s.
	 * 
	 * @return a {@code Comparator} that can be used for {@code BoxBlock} comparisons.
	 * 
	 * @see BoxBlock#equals(Object)
	 */
	public static Comparator<BoxBlock<?>> defaultBoxBlockComparator() {
		return DEFAULT_BOX_BLOCK_COMPARATOR;
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
	public static int hashCode(CellGroup<?> cellGroup) {
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
	public static boolean equals(CellGroup<?> cellGroup, Object obj) {
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
	public static int hashCode(UniqueCellGroup<?> uniqueCellGroup) {
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
	public static boolean equals(UniqueCellGroup<?> uniqueCellGroup, Object obj) {
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
		
		@Override
		public int compareTo(UniqueCellGroup<V> other) {
			requireNonNull(other, "other cannot be null.");
			return CellGroups.defaultRowComparator().compare(this, (Row<?>) other);
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
		
		@Override
		public int compareTo(UniqueCellGroup<V> other) {
			requireNonNull(other, "other cannot be null.");
			return CellGroups.defaultColumnComparator().compare(this, (Column<?>) other);
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
			requireEquals(size, blockRows * blockColumns, 
					"The product(" + blockRows * blockColumns + ") of the given blockRows and blockColumns should be equal to the given size(" + size + ").");
			this.startCell = validateCellInCellsMap(requireNonNull(startCell, "startCell cannot be null."), cells, startCell + " must be in " + cells);
			this.blockRows = requireGreaterThanOrEqualTo(1, blockRows);
			this.blockColumns = requireGreaterThanOrEqualTo(1, blockColumns);
			this.endCell = this.cells.values().stream()
					.filter(cell -> cell.x() == (this.startCell.x() + (blockColumns - 1)))
					.filter(cell -> cell.y() == (this.startCell.y() + (blockRows - 1)))
					.findAny().orElseThrow(() -> new SudokuException("The endCell of this BoxBlock could not be determined from the given properties."));
		}
		
		/* this constructor is needed because of BlockFactory.createBlock() method */ 
		SimpleBoxBlock(String id, int size, Map<String, Cell<V>> cells, Cell<V> startCell, Cell<V> endCell) {
			super(id, size, cells);
			this.startCell = validateCellInCellsMap(requireNonNull(startCell, "startCell cannot be null."), cells, startCell + " must be in " + cells);
			this.endCell = validateCellInCellsMap(requireNonNull(endCell, "endCell cannot be null."), cells, startCell + " must be in " + cells);
			this.blockRows = (this.endCell.y() - this.startCell.y()) + 1;
			this.blockColumns = (this.endCell.x() - this.startCell.x()) + 1;
			if (size != (blockRows * blockColumns))
				throw new SudokuException("The product(" + blockRows * blockColumns + ") of the interpolated blockRows(" + blockRows + ") and blockColumns(" + blockColumns +
					") should be equal to the given size(" + size + ").");
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

		@Override
		public int compareTo(UniqueCellGroup<V> other) {
			requireNonNull(other, "other cannot be null.");
			return CellGroups.defaultBoxBlockComparator().compare(this, (BoxBlock<?>) other);
		}
	}
	
	private static int requireEquals(int baseValue, int value, String message) {
		if(value != baseValue) throw new IllegalArgumentException(message);
		return value;
	}
	
	private static <V> Map<String, Cell<V>> validateRowAndColumnArgs(int size, Map<String, Cell<V>> cells, int xOry, String coordinate) {
		// validate method args
		requireGreaterThanOrEqualTo(1, size, "size must be greater than or equal to 1.");
		requireNonNull(cells, "cells cannot be null.");
		requireEquals(size, cells.size(), "You must provide axactly " + size + " cells");
		requireInRange(0, size, xOry, coordinate + " must be greater than or equal to 0 but less than size(" + size + ").");

		List<Cell<V>> cellsList = cells.values().stream().sorted(Cells.defaultComparator()).distinct().collect(toList());

		for (int index = 0; index < size; index++) {
			Cell<V> cell = cellsList.get(index);

			// validate all the given cells have the given x or y coordinate
			if (coordinate.equals("y")) {
				requireEquals(index, cell.x(),
						"The provided cells must have an x coordinate ranging from 0 to " + (size - 1) +
						". " + cell + "'s x coordinates was expected to be " + index + ".");
				requireEquals(xOry, cell.y(), "The " + coordinate + " coordinate of " + cell + " must be " + xOry);
			} else {
				requireEquals(index, cell.y(),
						"The provided cells must have a y coordinate ranging from 0 to " + (size - 1) +
						". " + cell + "'s y coordinates was expected to be " + index + ".");
				requireEquals(xOry, cell.x(), "The " + coordinate + " coordinate of " + cell + " must be " + xOry);
			}
		}

		return cells;
	}
	
	private static <V> Cell<V> validateCellInCellsMap(Cell<V> cell, Map<String, Cell<V>> cells, String errorMessage) {
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
