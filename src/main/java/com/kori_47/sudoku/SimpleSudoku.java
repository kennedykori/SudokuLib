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
	 * @param cellFactory the {@link CellFactory} that the new {@code Sudoku} will use when creating new {@code Cell}s.
	 * @param rowFactory the {@link RowFactory} that the new {@code Sudoku} will use when creating new {@link Row}s.
	 * @param columnFactory the {@link ColumnFactory} that the new {@code Sudoku} will use when creating new {@link Column}s.
	 * @param blockFactory the {@link BlockFactory} that the new {@code Sudoku} will use when creating new {@link Block}s.
	 * 
	 * @throws NullPointerException if any of the following arguments to this constructor are/is {@code null}.
	 * @throws IllegalArgumentException if the size of the {@code symbols} {@code Set} is less than {@code variant.size()};
	 */
	public SimpleSudoku(SudokuVariant variant, Set<Symbol<V>> symbols, CellFactory<V> cellFactory, RowFactory<V> rowFactory,
			ColumnFactory<V> columnFactory, BlockFactory<V> blockFactory) {
		super(requireNonNull(variant, "variant can't be null.").size(), symbols, cellFactory, rowFactory, columnFactory);
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
	
	@Override
	public Sudoku<V> copy() {
		SimpleSudoku<V> newSudoku = new SimpleSudoku<>(this);
		// copy the current Sudoku's cell values to the new Sudoku
		cells.values().forEach(cell -> {
			newSudoku.cells.get(cell.id()).changeSymbol(cell.symbol().orElse(null));
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
	 * The default implementation is equivalent to, for this {@code sdk}:
	 * <pre> {@code
	 * return LatinSquares.hashCode(sdk);
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
	 * The default implementation is equivalent to, for this {@code sdk}:
	 * <pre> {@code
	 * return LatinSquares.equals(sdk, obj);
	 * }
	 */
	@Override
	public boolean equals(Object obj) {
		return LatinSquares.equals(this, obj);
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
