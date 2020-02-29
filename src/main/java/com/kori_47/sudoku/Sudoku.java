/**
 * 
 */
package com.kori_47.sudoku;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toMap;

import java.util.HashSet;

import static com.kori_47.utils.ObjectUtils.requireGreaterThanOrEqualTo;

import java.util.Map;
import java.util.Set;

/**
 * <p> A {@code Sudoku} is a logic puzzle whose goal is to fill a grid with {@link Symbol}s such that,
 * each of the {@link Row}s, {@link Column}s and {@link Block}s that compose the grid contain all
 * the {@code Symbol}s in the set of {@code Symbol}s applicable to a given {@code Sudoku}.
 * 
 * <p> A {@code Sudoku} is an special case of a {@link LatinSquare}.
 * 
 * @param <V> the type of value held by the {@code Symbol}s supported by this {@code Sudoku}.
 * 
 * @author <a href="https://github.com/kennedykori">Kennedy Kori</a>
 *
 * @since Sat, 28 Dec 2019 11:29:31
 * 
 * @see LatinSquare
 * @see Block
 * @see SudokuVariant
 * @see BoxBlocksSudokuVariants
 */
public interface Sudoku<V> extends LatinSquare<V> {

	/**
	 * Returns the hash code value for this {@code Sudoku}. The hash code of a {@code Sudoku} should
	 * be derived from the hash codes of the following properties of a {@code Sudoku}: 
	 * <ul>
	 * <li>The hash code value of this {@code Sudoku}'s <i>{@link #variant() variant}</i>.</li>
	 * <li>The hash code value of this {@code Sudoku}'s <i>{@link #emptySymbol() emptySymbol}</i>.</li>
	 * <li>The hash code value of this {@code Sudoku}'s <i>{@link #cells() cells}</i> {@code Map}.</li>
	 * <li>The hash code value of this {@code Sudoku}'s <i>{@link #rows() rows}</i> {@code Map}.</li>
	 * <li>The hash code value of this {@code Sudoku}'s <i>{@link #blocks() blocks}</i> {@code Map}.</li>
	 * <li>The hash code value of this {@code Sudoku}'s <i>{@link #columns() columns}</i> {@code Map}.</li>
	 * <li>The hash code value of this {@code Sudoku}'s <i>{@link #symbols() symbols}</i> {@code Map}.</li>
	 * </ul>
	 * 
	 * @return the hash code value of this {@code Sudoku}.
	 */
	@Override
	int hashCode();

	/**
	 * Compares the specified object with this {@code Sudoku} for equality. Returns {@code true} if the
	 * given object is also a {@code Sudoku} and the two {@code Sudoku}s are identical. Two {@code Sudoku}s are
	 * said to be equal if each of the following of their properties are also equal:
	 * <ul>
	 * <li>The <i>{@link #variant() variants}</i> of the {@code Sudoku}s.</li>
	 * <li>The <i>{@link #emptySymbol() emptySymbols}</i> of the {@code Sudoku}s.</li>
	 * <li>The <i>{@link #cells() cells}</i> {@code Map}s of the {@code Sudoku}s.</li>
	 * <li>The <i>{@link #rows() rows}</i> {@code Map}s of the {@code Sudoku}s.</li>
	 * <li>The <i>{@link #blocks() blocks}</i> {@code Map}s of the {@code Sudoku}s.</li>
	 * <li>The <i>{@link #columns() columns}</i> {@code Map}s of the {@code Sudoku}s.</li>
	 * <li>The <i>{@link #symbols() symbols}</i> {@code Map}s of the {@code Sudoku}s.</li>
	 * </ul>
	 * 
	 * @param obj the object to compare for equality with this {@code Sudoku}.
	 * 
	 * @return {@code true} if this {@code Sudoku} is equal to {@code obj} argument, {@code false} otherwise.
	 */
	@Override
	boolean equals(Object obj);

	/**
	 * Returns a new {@code Sudoku} that has the same properties, {@link Cell cells} and 
	 * cell values as this one.
	 * 
	 * @return a new {@code Sudoku} that has the same properties, {@code Cell}s and cell
	 * values as this one.
	 */
	Sudoku<V> copy();
	
	/**
	 * Returns the {@link SudokuVariant} that describes this {@code Sudoku}.
	 * 
	 * @return the {@code SudokuVariant} that describes this {@code Sudoku}.
	 */
	SudokuVariant variant();

	/**
	 * Returns the {@link BlockFactory} used by this {@code Sudoku} to create new {@link Block}s.
	 * 
	 * @return the {@code BlockFactory} used by this {@code Sudoku} to create new {@code Block}s.
	 */
	BlockFactory<V> blockFactory();
	
	/**
	 * Returns a {@code Map} of the {@link Block}s contained in this {@code Sudoku}. Modification of the 
	 * returned {@code Map} should not alter the {@code Block}s of this {@code Sudoku}. Implementations of
	 * this interface can also choose to return an immutable {@code Map} instead to prevent modifications.
	 * 
	 * @return a {@code Map} of the {@code Block}s contained in this {@code Sudoku}.
	 */
	Map<String, Block<V>> blocks();
	
	/**
	 * A {@code SudokuVariant} describes the size of a {@link Sudoku} and determines the sizes and shapes
	 * of the {@link Block}s used in a {@code Sudoku}. Implementations of this interface should provide the
	 * algorithm used to create the {@code Block}s by overriding the {@link #createBlocks(Sudoku)} method.
	 * {@code Sudoku} implementations should then use that method to create new {@code Block}s. Instances
	 * of this class should be reusable across multiple {@code Sudoku} instances.
	 * 
	 * @author <a href="https://github.com/kennedykori">Kennedy Kori</a>
	 *
	 * @since Sat, 28 Dec 2019 11:30:27
	 * 
	 * @see Sudoku
	 * @see BoxSudokuVariant
	 */
	static interface SudokuVariant {

		/**
		 * This method creates the {@link Block}s used in a {@link Sudoku} and returns a {@link Set} of
		 * all the {@code Blocks} created. Unless otherwise specified, the size of the {@code Set} returned
		 * should be equal to the value returned by the {@link #size()} method. Some {@code Sudoku} implementations
		 * may throw an exception if the size of the {@code Set} returned isn't equal to {@link #size()}.
		 * 
		 * @param <V> the type of values held by {@code Symbol}s of the given {@code Sudoku}.
		 * 
		 * @param sudoku the {@code Sudoku} instance for which to create {@code Block}s for. 
		 * 
		 * @return a {@code Set} of all the {@code Block}s created by this method which unless otherwise
		 * specified, should be equal to {@link #size()}.
		 * 
		 * @throws NullPointerException if {@code sudoku} is {@code null}.
		 */
		<V> Set<Block<V>> createBlocks(Sudoku<V> sudoku);
		
		/**
		 * Returns the size of the {@link Sudoku} described by this variant.
		 * 
		 * @return the size of the {@code Sudoku} described by this variant.
		 */
		int size();
	}
	
	/**
	 * This represent the properties needed to describe a valid {@link Sudoku} with equally sized box shaped
	 * {@link Block}s, i.e. all the {@code Block}s have the same number of {@link Row}s and {@link Column}s.
	 * Examples of {@code Sudoku}s that can be described by this variant are the famous <strong><i>9x9</i></strong>
	 * classical {@code Sudoku}, the <strong><i>6x6</i></strong> {@code Sudoku}, the <strong><i>25x25</i></strong>
	 * Super {@code Sudoku}, etc. 
	 * 
	 * @author <a href="https://github.com/kennedykori">Kennedy Kori</a>
	 *
	 * @since Mon, 3 Feb 2020 02:02:04
	 * 
	 * @see Sudoku
	 * @see SudokuVariant
	 */
	static interface BoxBlocksSudokuVariant extends SudokuVariant {
		
		/**
		 * Returns the number of {@link Row}s in each {@link Block} inside the {@link Sudoku} described by
		 * this variant.
		 * 
		 * @return the number of {@code Row}s in each {@code Block} inside the {@code Sudoku} described
		 * by this variant.
		 */
		int blockRows();

		/**
		 * Returns the number of {@link Column}s in each {@link Block} inside the {@link Sudoku} described by
		 * this variant.
		 * 
		 * @return the number of {@code Column}s in each {@code Block} inside the {@code Sudoku} described
		 * by this variant.
		 */
		int blockColumns();

		/**
		 * Returns the number of horizontal {@link Block}s that make up the {@link Sudoku} described by this
		 * variant. 
		 * 
		 * @return the number of horizontal {@code Block}s that make up the {@code Sudoku} described by this
		 * variant. 
		 */
		int xBlocks();

		/**
		 * Returns the number of vertical {@link Block}s that make up the {@link Sudoku} described by this
		 * variant. 
		 * 
		 * @return the number of vertical {@code Block}s that make up the {@code Sudoku} described by this
		 * variant. 
		 */
		int yBlocks();
	}
	
	/**
	 * This represents a set of commonly used {@link BoxBlocksSudokuVariant}s including the famous 9x9 variant.
	 * 
	 * @author <a href="https://github.com/kennedykori">Kennedy Kori</a>
	 *
	 * @since Sat, 28 Dec 2019 12:22:12
	 * 
	 * @see BoxBlocksSudokuVariant
	 * @see SudokuVariant
	 */
	static enum BoxBlocksSudokuVariants implements BoxBlocksSudokuVariant {

		/**
		 * Describes a <strong><i>4x4</i></strong> {@code Sudoku}.  
		 */
		VARIANT_4x4(4, 2, 2, 2, 2),
		
		/**
		 * Describes a tall <strong><i>6x6</i></strong> {@code Sudoku}.  
		 */
		VARIANT_6x6_TALL(6, 2, 3, 2, 3),
		
		/**
		 * Describes a wide <strong><i>6x6</i></strong> {@code Sudoku}.  
		 */
		VARIANT_6x6_WIDE(6, 3, 2, 3, 2),
		
		/**
		 * Describes a <strong><i>9x9</i></strong> {@code Sudoku}.  
		 */
		VARIANT_9x9(9, 3, 3, 3, 3),
		
		/**
		 * Describes a <strong><i>16x16</i></strong> {@code Sudoku}.  
		 */
		VARIANT_16x16(16, 4, 4, 4, 4);
		
		/**
		 * the size of the sudoku
		 */
		private final int size;
		
		/**
		 * the number of rows in a block. 
		 */
		private final int blockRows;
		
		/**
		 * the number of columns in a block.
		 */
		private final int blockColumns;
		
		/**
		 * the number of horizontal blocks that make a complete sudoku.
		 */
		private final int xBlocks;
		
		/**
		 * the number of vertical blocks that make a complete sudoku.
		 */
		private final int yBlocks;
		
		private BoxBlocksSudokuVariants(int size, int blockRows, int blockColumns, int xBlocks, int yBlocks) {
			this.size = requireGreaterThanOrEqualTo(1, size, "size must be greater tha or equal to 1.");
			this.blockRows = requireGreaterThanOrEqualTo(1, blockRows, "blockRows must be greater tha or equal to 1.");
			this.blockColumns = requireGreaterThanOrEqualTo(1, blockColumns, "blockColumns must be greater tha or equal to 1.");
			this.xBlocks = requireGreaterThanOrEqualTo(1, xBlocks, "xBlocks must be greater tha or equal to 1.");
			this.yBlocks = requireGreaterThanOrEqualTo(1, yBlocks, "yBlocks must be greater tha or equal to 1.");
		}

		/**
		 * {@inheritDoc}
		 * @apiNote
		 * This method creates {@code Block}s starting from the first {@link Row} of the {@code Sudoku} moving to the last,
		 * and starting from the first {@code Column} moving to the last. 
		 */
		@Override
		public <V> Set<Block<V>> createBlocks(Sudoku<V> sudoku) {
			requireNonNull(sudoku, "sudoku cannot be null.");
			Set<Block<V>> blocks = new HashSet<Block<V>>(size);
			// get the first sudoku cell
			Cell<V> firstSudokuCell = sudoku.startCell();
			
			// initialize blocks
			for (int index = 0, x = firstSudokuCell.x(), y = firstSudokuCell.y(); index < size; index++) { 
				// extract the cells of the next block and store them on a Map
				Map<String, Cell<V>> blockCells = extractBlockCells(x, y, sudoku);
				// get the start cell of this block
				Cell<V> startCell = sudoku.getCell(x, y).get();
				// get the end cell of this block
				Cell<V> endCell = sudoku.getCell(x + blockRows, y + blockColumns).get();
				// create the next block
				Block<V> block = sudoku.blockFactory().createBlock(Integer.toString(index), size, blockCells, startCell, endCell);
				// add the created block to the blocks Set
				blocks.add(block);
				
				// calculate and set the next blocks starting coordinates
				if ((index + 1) % xBlocks() == 0) { // if we have reached the last column?
					x = firstSudokuCell.x(); // then reset to the index of the first column
					y += blockColumns; // and move up to the next row
				} else {
					x += blockColumns; // move to the start of the next block
				}
			}
			
			return blocks;
		}
		
		@Override
		public int size() {
			return size;
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
		public int xBlocks() {
			return xBlocks;
		}

		@Override
		public int yBlocks() {
			return yBlocks;
		}
		
		/**
		 * Extract and return a {@code Map} of {@code Block} {@code Cell}s given the starting coordinates
		 * of the {@code Block}.
		 * 
		 * @param x the starting x coordinate of the {@code Block}; 
		 * @param y the starting y coordinate of the {@code Block};
		 * @param sudoku the {@code Sudoku} to extract the {@code Cell}s from.
		 * 
		 * @return a {@code Map} of the {@code Cells} that belong to the stated {@code Block}.
		 */
		private final <V> Map<String, Cell<V>> extractBlockCells(int x, int y, Sudoku<V> sudoku) {
			// find the ends of the next block
			final int x1 = x + blockColumns, y1 = y + blockRows;
			return sudoku.cells().values().stream()
					.filter(cell -> cell.x() >= x && x < x1)
					.filter(cell -> cell.y() >= y && y < y1)
					.collect(toMap(cell -> cell.id(), cell -> cell));
		}
	}
}
