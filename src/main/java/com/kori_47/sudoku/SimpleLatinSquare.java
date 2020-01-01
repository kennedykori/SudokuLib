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
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * <p>This is a simple implementation of the {@link LatinSquare} interface.
 * 
 * <p><i><strong>Note:</strong> This class has a natural ordering that is inconsistent with equals.</i>
 * 
 * @author <a href="https://github.com/kennedykori">Kennedy Kori</a>
 *
 * @since Mon, 30 Dec 2019 23:42:00
 */
public class SimpleLatinSquare<V> implements LatinSquare<V> {
	
	private static final String DEFAULT_LATIN_SQUARE_ID = "LS";
		

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
	 * @param latinSquare the {@code LatinSquare} from who's properties to create a new {@code LatinSquare} from.
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
	public Cell<V> startCell() {
		return getCell(0, 0).get();
	}

	@Override
	public Cell<V> endCell() {
		return getCell(size() - 1, size() - 1).get();
	}

	@Override
	public int compareTo(CellGroup<V> other) {
		requireNonNull(other, "other cannot be null.");
		return Integer.compare(size, other.size());
	}

	@Override
	public LatinSquare<V> flipHorizontally() {
		SimpleLatinSquare<V> nLS = new SimpleLatinSquare<>(this); // new LatinSquare 
		for (int index = 0, index2 = (size - 1); index < size; index++, index2--) {
			Row<V> cr = rows.get(Integer.toString(index));
			Row<V> nr = nLS.rows.get(Integer.toString(index2));
			// copy the row values from the current row to the new row  
			for (int x = 0; x < size; x++) {
				nr.getCell(x, nr.y()).get().changeSymbol(cr.getCell(x, cr.y()).get().value().orElse(null));
			}
		}		
		return nLS;
	}

	@Override
	public LatinSquare<V> flipVertically() {
		SimpleLatinSquare<V> nLS = new SimpleLatinSquare<>(this); // new LatinSquare 
		for (int index = 0, index2 = (size - 1); index < size; index++, index2--) {
			Column<V> cc = columns.get(Integer.toString(index));
			Column<V> nc = nLS.columns.get(Integer.toString(index2));
			// copy the column values from the current row to the new column
			for (int y = 0; y < size; y++) {
				nc.getCell(nc.x(), y).get().changeSymbol(cc.getCell(cc.x(), y).get().value().orElse(null));
			}
		}		
		return nLS;
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
	public String id() {
		return DEFAULT_LATIN_SQUARE_ID;
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
	 * return ls.id().hashCode() + Integer.hashCode(ls.size()) + ls.emptySymbol().hashCode() + ls.cells().hashCode()
			+ ls.columns().hashCode() + ls.rows().hashCode() + ls.symbols().hashCode();
	 * }
	 * </pre>
	 */
	@Override
	public int hashCode() {
		return id().hashCode() + Integer.hashCode(size) + emptySymbol.hashCode() + cells.hashCode()
			+ columns.hashCode() + rows.hashCode() + symbols.hashCode();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @implSpec
	 * This implementation first checks if the specified object is this {@code LatinSquare}; if so it
	 * returns {@code true}. Then it checks if the specified object is a {@code LatinSquare} whose size,
	 * id, empty {@code Symbol}, symbols {@code Map}, columns {@code Map}, rows {@code Map} and cells {@code Map}
	 * are equal to the equivalent properties of this {@code LatinSquare}; if not, it returns {@code false}. If so,
	 * it returns {@code true}.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (!(obj instanceof LatinSquare)) return false;
		LatinSquare<?> _obj = (LatinSquare<?>) obj;
		return size == _obj.size() && id().equals(_obj.id()) && emptySymbol.equals(_obj.emptySymbol())
				&& symbols.equals(_obj.symbols()) && columns.equals(_obj.columns()) && rows.equals(_obj.rows())
				&& cells.equals(_obj.cells());
	}
	
	@Override
	public String toString() {
		return toXYV();
	}

	private void init() {
		// create cells
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				Cell<V> cell = cellFactory.createCell(x, y, emptySymbol);
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
				.collect(toMap(symbol -> Integer.valueOf(symbol.id()),
						symbol -> symbol,
						(oldValue, newValue) -> newValue,
						() -> new LinkedHashMap<Integer, Symbol<V>>(size)));
	}
}
