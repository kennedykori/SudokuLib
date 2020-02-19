/**
 * 
 */
package com.kori_47.sudoku;

import static java.util.Collections.unmodifiableMap;
import static java.util.Objects.requireNonNull;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * <p>This is a simple implementation of the {@link Sudoku} interface.
 * 
 * @author <a href="https://github.com/kennedykori">Kennedy Kori</a>
 *
 * @since Wed, 1 Jan 2020 12:55:30
 */
class SimpleSudoku<V> extends SimpleLatinSquare<V> implements Sudoku<V> {
	
	// ================================================
	// PRIMARY FIELDS
	// ================================================
	private final SudokuVariant variant;
	private final BlockFactory<V> blockFactory;
	// protected values that concrete classes need to access
	protected final Map<String, Block<V>> blocks;

	// ================================================
	// COLLECTION VIEWS
	// ================================================
	private final Map<String, Block<V>> blockViews;
	
	/**
	 * Creates a new {@link Sudoku} with the given properties.
	 * 
	 * @param variant the {@link SudokuVariant} that describes the new {@code Sudoku}.
	 * @param symbols the {@link Set} of {@link Symbol}s to use when filling this {@code Sudoku} {@link Cell}s.
	 * @param emptySymbol the empty {@code Symbol} that the new {@code Sudoku} should use.
	 * @param cellFactory the {@link CellFactory} that the new {@code Sudoku} will use when creating new {@code Cell}s.
	 * @param rowFactory the {@link RowFactory} that the new {@code Sudoku} will use when creating new {@link Row}s.
	 * @param columnFactory the {@link ColumnFactory} that the new {@code Sudoku} will use when creating new {@link Column}s.
	 * @param blockFactory the {@link BlockFactory} that the new {@code Sudoku} will use when creating new {@link Block}s.
	 * 
	 * @throws NullPointerException if any of the following arguments to this constructor are/is {@code null}.
	 * @throws IllegalArgumentException if the size of the {@code symbols} {@code Set} is less than {@code variant.size()};
	 */
	public SimpleSudoku(SudokuVariant variant, Set<Symbol<V>> symbols, Symbol<V> emptySymbol, CellFactory<V> cellFactory,
			RowFactory<V> rowFactory, ColumnFactory<V> columnFactory, BlockFactory<V> blockFactory) {
		super(requireNonNull(variant, "variant can't be null.").size(), symbols, emptySymbol, cellFactory, rowFactory, columnFactory);
		this.variant = variant;
		this.blockFactory = requireNonNull(blockFactory, "blockFactory cannot be null.");
		this.blocks = new LinkedHashMap<String, Block<V>>(this.variant.size());
		// Initialize Views
		this.blockViews = unmodifiableMap(blocks);
		
		// create blocks
		initBlocks();
	}

	/**
	 * Creates a new {@link Sudoku} from the properties of the given {@code Sudoku}. The {@link Cell} values of
	 * the given {@code Sudoku} are <strong><i>NOT</i></strong> copied into the new {@code Sudoku}.
	 * 
	 * @param sudoku the {@code Sudoku} from who's properties to create a new {@code Sudoku} from.
	 * 
	 * @throws NullPointerException if {@code sudoku} is {@code null}.
	 */
	public SimpleSudoku(Sudoku<V> sudoku) {
		super(requireNonNull(sudoku, "sudoku cannot be null."));
		this.variant =  requireNonNull(sudoku.variant(), "variant can't be null.");
		this.blockFactory = requireNonNull(sudoku.blockFactory(), "blockFactory cannot be null.");
		this.blocks = new LinkedHashMap<String, Block<V>>(this.variant.size());
		
		// Initialize Views
		this.blockViews = unmodifiableMap(blocks);
		
		// create blocks
		initBlocks();
	}

	public Sudoku<V> flipHorizontally() {
		SimpleSudoku<V> nSdk= new SimpleSudoku<>(this); // new Sudoku 
		for (int index = 0, index2 = (size() - 1); index < size(); index++, index2--) {
			Row<V> cr = rows.get(Integer.toString(index));
			Row<V> nr = nSdk.rows.get(Integer.toString(index2));
			// copy the row values from the current row to the new row  
			for (int x = 0; x < size(); x++) {
				nr.getCell(x, nr.y()).get().changeSymbol(cr.getCell(x, cr.y()).get().value().orElse(null));
			}
		}		
		return nSdk;
	}

	@Override
	public Sudoku<V> flipVertically() {
		SimpleSudoku<V> nSdk= new SimpleSudoku<>(this); // new Sudoku 
		for (int index = 0, index2 = (size() - 1); index < size(); index++, index2--) {
			Column<V> cc = columns.get(Integer.toString(index));
			Column<V> nc = nSdk.columns.get(Integer.toString(index2));
			// copy the column values from the current row to the new column
			for (int y = 0; y < size(); y++) {
				nc.getCell(nc.x(), y).get().changeSymbol(cc.getCell(cc.x(), y).get().value().orElse(null));
			}
		}		
		return nSdk;
	}
	
	@Override
	public Sudoku<V> copy() {
		SimpleSudoku<V> newSudoku = new SimpleSudoku<>(this);
		// copy the current Sudoku's cell values to the new Sudoku
		cells.values().forEach(cell -> {
			newSudoku.cells.get(cell.id()).changeSymbol(cell.value().orElse(null));
		});
		return newSudoku;
	}

	@Override
	public SudokuVariant variant() {
		return variant;
	}

	@Override
	public BlockFactory<V> blockFactory() {
		return blockFactory;
	}
	
	@Override
	public Map<String, Block<V>> blocks() {
		return blockViews;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @implSpec
	 * This implementation is equivalent to, for this {@code sdk}:
	 * <pre> {@code
	 * return sdk.variant().hashCode() + sdk.emptySymbol().hashCode() + sdk.cells().hashCode()
			+ sdk.columns().hashCode() + sdk.rows().hashCode() + sdk.symbols().hashCode() + sdk.blocks().hashCode();
	 * }
	 * </pre>
	 */
	@Override
	public int hashCode() {
		return variant.hashCode() + emptySymbol().hashCode() + cells.hashCode() + columns.hashCode() + rows.hashCode()
			+ symbols.hashCode() + blocks.hashCode();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @implSpec
	 * This implementation first checks if the specified object is this {@code Sudoku}; if so it returns {@code true}.
	 * Then it checks if the specified object is a {@code Sudoku} whose id, empty {@code Symbol}, variant, symbols {@code Map},
	 * columns {@code Map}, rows {@code Map}, blocks {@code Map} and cells {@code Map} are equal to the equivalent
	 * properties of this {@code Sudoku}; if not, it returns {@code false}. If so, it returns {@code true}.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (!(obj instanceof Sudoku)) return false;
		Sudoku<?> _obj = (Sudoku<?>) obj;
		return emptySymbol().equals(_obj.emptySymbol()) && variant.equals(_obj.variant()) && symbols.equals(_obj.symbols())
				&& columns.equals(_obj.columns()) && rows.equals(_obj.rows()) && blocks.equals(_obj.blocks())
				&& cells.equals(_obj.cells());
	}
	
	/**
	 * Initializes this {@code Sudoku}s blocks
	 */
	private final void initBlocks() {
		// create the blocks using this class SudokuVariant
		Set<Block<V>> createdBlocks = requireNonNull(variant.createBlocks(this), "'variant.createBlocks()' shouldn't return null.");
		
		// make sure the createdBlocks are the correct number
		if (createdBlocks.size() != variant.size())
			throw new SudokuException(
					String.format("The number of blocks created (%d) is less than the expected number (%d).", 
							createdBlocks.size(),
							variant.size()
						));
		
		// copy the createdBlocks into this.blocks
		createdBlocks.forEach(block -> blocks.put(block.id(), block));
	}
}
