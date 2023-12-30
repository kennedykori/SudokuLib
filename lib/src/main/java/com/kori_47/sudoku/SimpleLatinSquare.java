/**
 * 
 */
package com.kori_47.sudoku;

import static java.util.Collections.unmodifiableMap;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toMap;

import static com.kori_47.utils.ObjectUtils.requireGreaterThanOrEqualTo;

import java.util.LinkedHashMap;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * <p>This is a simple implementation of the {@link LatinSquare} interface.
 * 
 * @author <a href="https://github.com/kennedykori">Kennedy Kori</a>
 *
 * @since Mon, 30 Dec 2019 23:42:00
 */
class SimpleLatinSquare<V> implements LatinSquare<V> {	

	// ================================================
	// PRIMARY FIELDS
	// ================================================
	private final int size;
	private final CellFactory<V> cellFactory;
	private final RowFactory<V> rowFactory;
	private final ColumnFactory<V> columnFactory;
	// protected values that concrete classes need to access
	protected final Map<Integer, Symbol<V>> symbols;
	protected final Map<String, Cell<V>> cells;
	protected final Map<String, Row<V>> rows;
	protected final Map<String, Column<V>> columns;
	
	// ================================================
	// COLLECTION VIEWS
	// ================================================
	private final Map<Integer, Symbol<V>> symbolsView;
	private final Map<String, Cell<V>> cellsView;
	private final Map<String, Row<V>> rowsView;
	private final Map<String, Column<V>> columnsView;

	/**
	 * Creates a new {@link LatinSquare} with the given properties.
	 * 
	 * @param size the size that the new {@code LatinSquare} should be.
	 * @param symbols the {@link Set} of {@link Symbol}s to use when filling this {@code LatinSquare} {@link Cell}s.
	 * @param cellFactory the {@link CellFactory} that the new {@code LatinSquare} will use when creating new {@code Cell}s.
	 * @param rowFactory the {@link RowFactory} that the new {@code LatinSquare} will use when creating new {@link Row}s.
	 * @param columnFactory the {@link ColumnFactory} that the new {@code LatinSquare} will use when creating new {@link Column}s.
	 * 
	 * @throws NullPointerException if any of the following arguments to this constructor are/is {@code null}.
	 * @throws IllegalArgumentException if {@code size} is less than {@code 1} and/or the size of {@code symbols} {@code Set}
	 * 			is less than {@code size};
	 */
	public SimpleLatinSquare(int size, Set<Symbol<V>> symbols, CellFactory<V> cellFactory, RowFactory<V> rowFactory,
			ColumnFactory<V> columnFactory) {
		this(size, validateSymbols(symbols, size), cellFactory, rowFactory, columnFactory);
	}
	
	/**
	 * Creates a new {@link LatinSquare} from the properties of the given {@code LatinSquare}. The {@link Cell} values of
	 * the given {@code LatinSquare} are <strong><i>NOT</i></strong> copied into the new {@code LatinSquare}.
	 * 
	 * @param latinSquare the {@code LatinSquare} from whose properties to create a new {@code LatinSquare} from.
	 * 
	 * @throws NullPointerException if {@code latinSquare} is {@code null}.
	 */
	public SimpleLatinSquare(LatinSquare<V> latinSquare) {
		this(
			requireNonNull(latinSquare, "latinSquare cannot be null.").size(),
			new LinkedHashMap<>(latinSquare.symbols()),
			latinSquare.cellFactory(),
			latinSquare.rowFactory(),
			latinSquare.columnFactory());
	}
	
	/**
	 * This constructor does the actual work of initializing the instance fields and is mostly used internally to
	 * create {@link LatinSquare} copies.
	 * 
	 * @param size the size that the new {@code LatinSquare} should be.
	 * @param symbols the {@link Map} of {@link Symbol}s to use when filling this {@code LatinSquare} {@link Cell}s.
	 * @param cellFactory the {@link CellFactory} that the new {@code LatinSquare} will use when creating new {@code Cell}s.
	 * @param rowFactory the {@link RowFactory} that the new {@code LatinSquare} will use when creating new {@link Row}s.
	 * @param columnFactory the {@link ColumnFactory} that the new {@code LatinSquare} will use when creating new {@link Column}s.
	 */
	private SimpleLatinSquare(int size, Map<Integer, Symbol<V>> symbols, CellFactory<V> cellFactory, RowFactory<V> rowFactory,
			ColumnFactory<V> columnFactory) {
		this.size = requireGreaterThanOrEqualTo(1, size, "size must be greater than or equal to 1.");
		this.cellFactory = requireNonNull(cellFactory, "cellFactory cannot be null.");
		this.rowFactory = requireNonNull(rowFactory, "rowFactory cannot be null.");
		this.columnFactory = requireNonNull(columnFactory, "columnFactory cannot be null.");
		this.symbols = symbols;
		this.cells = new LinkedHashMap<String, Cell<V>>(this.size * this.size);
		this.rows = new LinkedHashMap<String, Row<V>>(this.size);
		this.columns = new LinkedHashMap<String, Column<V>>(this.size);

		// Initialize Views
		this.symbolsView = unmodifiableMap(this.symbols);
		this.cellsView = unmodifiableMap(cells);
		this.rowsView = unmodifiableMap(rows);
		this.columnsView = unmodifiableMap(columns);
		
		// create cells, rows and columns
		init();
	}

	@Override
	public Optional<Cell<V>> getCell(int x, int y) {
		// construct the id from x and y and use getCell(String cellId)
		return getCell(x + "/" + y);
	}
	
	@Override
	public Cell<V> startCell() {
		return getCell(0, 0).get();
	}

	@Override
	public Cell<V> endCell() {
		return getCell(size() - 1, size() - 1).get();
	}

	@Override
	public void flipHorizontally() {
		int midPoint = size / 2;
		for (int index = 0, index2 = (size - 1); index < midPoint; index++, index2--) {
			Row<V> cr = rows.get(Integer.toString(index));
			Row<V> nr = rows.get(Integer.toString(index2));
			// swap the Cell values of the two Rows   
			for (int x = 0; x < size; x++) {
				// get the Cells we are swaping
				Cell<V> cell1 = cr.getCell(x, cr.y()).get();
				Cell<V> cell2 = nr.getCell(x, nr.y()).get();
				
				Cells.swapCellSymbols(cell1, cell2);
			}
		}		
	}

	@Override
	public void flipVertically() {
		int midPoint = size / 2;
		for (int index = 0, index2 = (size - 1); index < midPoint; index++, index2--) {
			Column<V> cc = columns.get(Integer.toString(index));
			Column<V> nc = columns.get(Integer.toString(index2));
			// swap the Cell values of the two Columns
			for (int y = 0; y < size; y++) {
				// get the Cells we are swaping
				Cell<V> cell1 = cc.getCell(cc.x(), y).get();
				Cell<V> cell2 = nc.getCell(nc.x(), y).get();
				
				Cells.swapCellSymbols(cell1, cell2);
			}
		}		
	}

	@Override
	public LatinSquare<V> copy() {
		SimpleLatinSquare<V> newLatinSquare = new SimpleLatinSquare<>(this);
		// copy the current Latin square's cell values to the new Latin square
		cells.values().forEach(cell -> {
			newLatinSquare.cells.get(cell.id()).changeSymbol(cell.symbol().orElse(null));
		});
		return newLatinSquare;
	}
	
	@Override
	public int size() {
		return size;
	}

	@Override
	public CellFactory<V> cellFactory() {
		return cellFactory;
	}

	@Override
	public RowFactory<V> rowFactory() {
		return rowFactory;
	}

	@Override
	public ColumnFactory<V> columnFactory() {
		return columnFactory;
	}

	@Override
	public Map<Integer, Symbol<V>> symbols() {
		return symbolsView;
	}
	
	@Override
	public Map<String, Cell<V>> cells() {
		return cellsView;
	}

	@Override
	public Map<String, Row<V>> rows() {
		return rowsView;
	}

	@Override
	public Map<String, Column<V>> columns() {
		return columnsView;
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * @implSpec
	 * This implementation is equivalent to, for this {@code ls}:
	 * <pre> {@code
	 * return LatinSquares.hashCode(ls);
	 * }
	 * </pre>
	 */
	@Override
	public int hashCode() {
		return LatinSquares.hashCode(this);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @implSpec
	 * This implementation is equivalent to, for this {@code ls}:
	 * <pre> {@code
	 * return LatinSquares.equals(ls, obj);
	 * }
	 */
	@Override
	public boolean equals(Object obj) {
		return LatinSquares.equals(this, obj);
	}
	
	@Override
	public String toString() {
		return toXYV();
	}

	private void init() {
		// create cells
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				Cell<V> cell = cellFactory.createCell(x + "/" + y , x, y, null);
				cells.put(cell.id(), cell);
			}
		}
	
		Supplier<Stream<Cell<V>>> cellStreamSupplier = () -> cells.values().stream();
		// create rows and columns
		for (int index = 0; index < size; index++) {
			final int y = index, x = index;
			// extract row and column cells
			Map<String, Cell<V>> rowCells = cellStreamSupplier.get()
					.filter(cell -> cell.y() == y)
					.collect(toMap(cell -> cell.id(), cell -> cell));
			Map<String, Cell<V>> columnCells = cellStreamSupplier.get()
					.filter(cell -> cell.x() == x)
					.collect(toMap(cell -> cell.id(), cell -> cell));
			
			// create a row with index y and a column with index x
			Row<V> row = rowFactory.createRow(Integer.toString(y), size, rowCells, y);
			Column<V> column = columnFactory.createColumn(Integer.toString(x), size, columnCells, x);
			
			// add the row and column to the Latin square
			rows.put(row.id(), row);
			columns.put(column.id(), column);
		}
	}

	private static final <V> Map<Integer, Symbol<V>> validateSymbols(Set<Symbol<V>> symbols, int size) {
		requireNonNull(symbols, "symbols must not be null.");
		requireGreaterThanOrEqualTo(size, symbols.size(), "You must provide atleast " + size + " symbols.");
		// sort the symbols and create a defensive copy of the symbols Map
		return symbols.stream()
				.sorted()
				.collect(toMap(
						symbol -> symbol.id(),
						symbol -> symbol,
						(oldValue, newValue) -> newValue,
						() -> new LinkedHashMap<Integer, Symbol<V>>(size)));
	}
}
