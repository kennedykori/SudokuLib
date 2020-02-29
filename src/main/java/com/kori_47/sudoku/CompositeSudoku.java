/**
 * 
 */
package com.kori_47.sudoku;

import static java.util.Collections.unmodifiableMap;
import static java.util.Objects.requireNonNull;

import static com.kori_47.sudoku.LatinSquares.initializeSudokuBlocks;
import static com.kori_47.utils.ObjectUtils.requireInRange;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * This is an implementation of the {@link Sudoku} interface that allows clients to change the backing
 * {@link LatinSquare} by dependacy injection at the creation time of the new {@link Sudoku}.
 * 
 * @author <a href="https://github.com/kennedykori">Kennedy Kori</a>
 *
 * @since Sat, 8 Feb 2020 16:57:50
 * 
 * @see Sudoku
 * @see LatinSquare
 */
final class CompositeSudoku<V> implements Sudoku<V> {
	
	// ================================================
	// PRIMARY FIELDS
	// ================================================
	private final LatinSquare<V> latinSquare;
	private final SudokuVariant variant;
	private final BlockFactory<V> blockFactory;
	private final Map<String, Block<V>> blocks;
	
	// ================================================
	// COLLECTION VIEWS
	// ================================================
	private final Map<String, Block<V>> blockViews;

	/**
	 * Creates a new {@link Sudoku} from the given {@link LatinSquare}, {@link SudokuVariant} and {@link BlockFactory}.
	 * The given {@code LatinSquare} must be of the same size as the value returned by the {@code size()} method of the
	 * given {@code SudokuVariant}.
	 * 
	 * @param latinSquare the {@code LatinSquare} from which to create a new {@code Sudoku} from.
	 * @param variant the {@code SudokuVariant} that describes the new {@code Sudoku}.
	 * @param blockFactory the {@code BlockFactory} that will be used to create the new {@code Sudoku}'s {@code Block}s.
	 * 
	 * @throws NullPointerException if any of the given arguments are {@code null}.
	 * @throws IllegalArgumentException if the size of the given {@code LatinSquare} is not equal to size of the given
	 * {@code SudokuVariant}.
	 */
	public CompositeSudoku(LatinSquare<V> latinSquare, SudokuVariant variant, BlockFactory<V> blockFactory) {
		this.variant = requireNonNull(variant, "variant cannot be null.");
		this.latinSquare = validateLatinSquare(latinSquare, this.variant.size());
		this.blockFactory = requireNonNull(blockFactory, "blockFactory cannot be null.");
		this.blocks = initializeSudokuBlocks(this, () -> new LinkedHashMap<String, Block<V>>(this.variant.size()));
		// Initialize Views
		this.blockViews = unmodifiableMap(blocks);
	}

	@Override
	public CellFactory<V> cellFactory() {
		return latinSquare.cellFactory();
	}

	@Override
	public RowFactory<V> rowFactory() {
		return latinSquare.rowFactory();
	}

	@Override
	public ColumnFactory<V> columnFactory() {
		return latinSquare.columnFactory();
	}

	@Override
	public Map<String, Row<V>> rows() {
		return latinSquare.rows();
	}

	@Override
	public Map<String, Column<V>> columns() {
		return latinSquare.columns();
	}

	@Override
	public Symbol<V> emptySymbol() {
		return latinSquare.emptySymbol();
	}

	@Override
	public Map<Integer, Symbol<V>> symbols() {
		return latinSquare.symbols();
	}

	@Override
	public Cell<V> startCell() {
		return latinSquare.startCell();
	}

	@Override
	public Cell<V> endCell() {
		return latinSquare.endCell();
	}

	@Override
	public int size() {
		return variant.size();
	}

	@Override
	public Map<String, Cell<V>> cells() {
		return latinSquare.cells();
	}

	@Override
	public void flipHorizontally() {
		latinSquare.flipHorizontally();
	}

	@Override
	public void flipVertically() {
		latinSquare.flipVertically();
	}

	@Override
	public Sudoku<V> copy() {
		return new CompositeSudoku<>(latinSquare.copy(), variant, blockFactory);
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
	
	@Override
	public int hashCode() {
		return LatinSquares.hashCode(this);
	}
	
	@Override
	public boolean equals(Object obj) {
		return LatinSquares.equals(this, obj);
	}
	
	@Override
	public String toString() {
		return latinSquare.toString();
	}
	
	/**
	 * Makes sure that the given {@link LatinSquare} is <b>NOT</b> {@code null} and is of the correct size.
	 * 
	 * @param <V> the type of {@link Symbol}s supported by the given {@code LatinSquare}.
	 * @param latinSquare the {@code LatinSquare} to validate.
	 * @param size the size that the given {@code LatinSquare} should have. 
	 * 
	 * @return the given {@code LatinSquare} if it passes all the validation test.
	 * 
	 * @throws NullPointerException if {@code latinSquare} is {@code null}.
	 * @throws IllegalArgumentException if {@code latinSquare}'s size is <b>NOT</b> to {@code size}. 
	 */
	private final static <V> LatinSquare<V> validateLatinSquare(LatinSquare<V> latinSquare, int size) {
		// start by making sure the given LatinSquare isn't null
		requireNonNull(latinSquare, "latinSquare cannot be null.");
		// then make sure the given LatinSquare has the correct size
		requireInRange(size, size + 1, latinSquare.size());
		// return the LatinSquare if everything checks out
		return latinSquare;
	}
}
