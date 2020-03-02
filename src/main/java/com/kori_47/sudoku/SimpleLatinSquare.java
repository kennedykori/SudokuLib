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
	private final Symbol<V> emptySymbol;
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
	 * @param emptySymbol the empty {@code Symbol} that the new {@code LatinSquare} should use.
	 * @param cellFactory the {@link CellFactory} that the new {@code LatinSquare} will use when creating new {@code Cell}s.
	 * @param rowFactory the {@link RowFactory} that the new {@code LatinSquare} will use when creating new {@link Row}s.
	 * @param columnFactory the {@link ColumnFactory} that the new {@code LatinSquare} will use when creating new {@link Column}s.
	 * 
	 * @throws NullPointerException if any of the following arguments to this constructor are/is {@code null}.
	 * @throws IllegalArgumentException if {@code size} is less than {@code 1} and/or the size of {@code symbols} {@code Set}
	 * 			is less than {@code size};
	 */
	public SimpleLatinSquare(int size, Set<Symbol<V>> symbols, Symbol<V> emptySymbol, CellFactory<V> cellFactory,
							 RowFactory<V> rowFactory, ColumnFactory<V> columnFactory) {
		this(size, validateSymbols(symbols, size), emptySymbol, cellFactory, rowFactory, columnFactory);
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
			latinSquare.emptySymbol(),
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
	 * @param emptySymbol the empty {@code Symbol} that the new {@code LatinSquare} should use.
	 * @param cellFactory the {@link CellFactory} that the new {@code LatinSquare} will use when creating new {@code Cell}s.
	 * @param rowFactory the {@link RowFactory} that the new {@code LatinSquare} will use when creating new {@link Row}s.
	 * @param columnFactory the {@link ColumnFactory} that the new {@code LatinSquare} will use when creating new {@link Column}s.
	 */
	private SimpleLatinSquare(int size, Map<Integer, Symbol<V>> symbols, Symbol<V> emptySymbol, CellFactory<V> cellFactory,
							  RowFactory<V> rowFactory, ColumnFactory<V> columnFactory) {
		this.size = requireGreaterThanOrEqualTo(1, size, "size must be greater than or equal to 1.");
		this.emptySymbol = requireNonNull(emptySymbol, "emptySymbol cannot be null.");
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
				
				swapCellProperties(cell1, cell2);
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
				
				swapCellProperties(cell1, cell2);
			}
		}		
	}

	@Override
	public LatinSquare<V> copy() {
		SimpleLatinSquare<V> newLatinSquare = new SimpleLatinSquare<>(this);
		// copy the current Latin square's cell values to the new Latin square
		cells.values().forEach(cell -> {
			newLatinSquare.cells.get(cell.id()).changeSymbol(cell.value().orElse(null));
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
	public Symbol<V> emptySymbol() {
		return emptySymbol;
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
	 * return Integer.hashCode(ls.size()) + ls.emptySymbol().hashCode() + ls.cells().hashCode()
			+ ls.columns().hashCode() + ls.rows().hashCode() + ls.symbols().hashCode();
	 * }
	 * </pre>
	 */
	@Override
	public int hashCode() {
		return Integer.hashCode(size) + emptySymbol.hashCode() + cells.hashCode()
			+ columns.hashCode() + rows.hashCode() + symbols.hashCode();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @implSpec
	 * This implementation first checks if the specified object is this {@code LatinSquare}; if so it
	 * returns {@code true}. Then it checks if the specified object is a {@code LatinSquare} whose size,
	 * empty {@code Symbol}, symbols {@code Map}, columns {@code Map}, rows {@code Map} and cells {@code Map}
	 * are equal to the equivalent properties of this {@code LatinSquare}; if not, it returns {@code false}. If so,
	 * it returns {@code true}.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (!(obj instanceof LatinSquare)) return false;
		LatinSquare<?> _obj = (LatinSquare<?>) obj;
		return size == _obj.size() && emptySymbol.equals(_obj.emptySymbol()) && symbols.equals(_obj.symbols())
				&& columns.equals(_obj.columns()) && rows.equals(_obj.rows()) && cells.equals(_obj.cells());
	}
	
	@Override
	public String toString() {
		return toXYV();
	}

	private void init() {
		// create cells
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				Cell<V> cell = cellFactory.createCell(x + "/" + y , x, y, emptySymbol);
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
	
	private static final <V> void swapCellProperties(Cell<V> cell1, Cell<V> cell2) {
		requireNonNull(cell1, "cell1 cannot be null.");
		requireNonNull(cell2, "cell2 cannot be null.");
		
		// get the clue status of the Cells
		boolean clue1 = cell1.isClueCell();
		boolean clue2 = cell2.isClueCell();
		// get the Symbols of the Cells
		Symbol<V> symbol1 = cell1.value().orElse(null);
		Symbol<V> symbol2 = cell2.value().orElse(null);
		// get the notes of the Cells
		@SuppressWarnings("unchecked")
		Symbol<V>[] notes1 = cell1.notes().toArray(new Symbol[cell1.notes().size()]);
		@SuppressWarnings("unchecked")
		Symbol<V>[] notes2 = cell2.notes().toArray(new Symbol[cell2.notes().size()]);
		
		// reset the cells
		cell1.reset(null);
		cell2.reset(null);
		
		// copy the notes
		for (Symbol<V> symbol : notes1) cell2.makeNote(symbol);
		for (Symbol<V> symbol : notes2) cell1.makeNote(symbol);
		
		/* ======== Cover the 4 Possibilities ======== */
		// 1. Both Cells are clue Cells
		if (clue1 && clue2) {
			// make the Cells clue Cells
			cell1.makeClueCell(symbol2);
			cell2.makeClueCell(symbol1);
		}
		// 2. cell1 is a clue Cell but cell2 isn't
		else if (clue1 && !clue2) {
			// make cell2 a clue Cell
			cell2.makeClueCell(symbol1);
			// set the value of cell1
			cell1.changeSymbol(symbol2);
		}
		// 3. cell2 is a clue Cell but cell1 isn't
		else if (clue2 && !clue1) {
			// make cell1 a clue Cell
			cell1.makeClueCell(symbol2);
			// set the value of cell2
			cell2.changeSymbol(symbol1);
		}
		// 4. Both Cells are normal Cells.
		else {
			// set the Cell's values
			cell1.changeSymbol(symbol2);
			cell2.changeSymbol(symbol1);
		}
	}
}
