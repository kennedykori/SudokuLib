/**
 * 
 */
package com.kori_47.sudoku;

import static com.kori_47.utils.ObjectUtils.requireGreaterThanOrEqualTo;

import java.util.Map;

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
 * @see SudokuVariants
 */
public interface Sudoku<V> extends LatinSquare<V> {

	/**
	 * Returns the hash code value for this {@code Sudoku}. The hash code of a {@code Sudoku} should
	 * be derived from the hash codes of the following properties of a {@code Sudoku}: 
	 * <ul>
	 * <li>The hash code value of this {@code Sudoku}'s <i>{@link #id() id}</i>.</li>
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
	 * <li>The <i>{@link #id() ids}</i> of the {@code Sudoku}s.</li>
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
	 * Creates and returns a new {@code Sudoku} whose {@link Row}s and {@link Cell}s are permuted so
	 * that the new {@code Sudoku} is upside down in relation to this one.
	 * 
	 * @return a new identical but upside down {@code Sudoku} in relation to this one.
	 */
	Sudoku<V> flipHorizontally();

	/**
	 * Creates and returns a new {@code Sudoku} whose {@link Column}s and {@link Cell}s are permuted so
	 * that the new {@code Sudoku} is flipped sideways in relation to this one.
	 * 
	 * @return a new identical but flipped sideways {@code Sudoku} in relation to this one.
	 */
	Sudoku<V> flipVertically();

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
	 * this interface can also choose to return an unmodifiable {@code Map} instead to prevent modifications.
	 * 
	 * @return a {@code Map} of the {@code Block}s contained in this {@code Sudoku}.
	 */
	Map<String, Block<V>> blocks();
	
	/**
	 * This represent the properties needed to describe a valid {@link Sudoku}, i.e. size, the number of
	 * {@link Row}s and {@link Column}s in a {@link Block} and the number of {@code Block}s in a 
	 * {@code Sudoku}. This interface is used to describe a {@code Sudoku} with equal sized {@link Block blocks}
	 * or regions. 
	 * 
	 * @author <a href="https://github.com/kennedykori">Kennedy Kori</a>
	 *
	 * @since Sat, 28 Dec 2019 11:30:27
	 * 
	 * @see Sudoku
	 * @see SudokuVariants
	 */
	static interface SudokuVariant {

		/**
		 * Returns the size of the {@link Sudoku} described by this variant.
		 * 
		 * @return the size of the {@code Sudoku} described by this variant.
		 */
		int size();

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
	 * This represents a set of commonly used {@link SudokuVariant}s including the famous 9x9 variant.
	 * 
	 * @author <a href="https://github.com/kennedykori">Kennedy Kori</a>
	 *
	 * @since Sat, 28 Dec 2019 12:22:12
	 */
	static enum SudokuVariants implements SudokuVariant {

		/**
		 * Describes a <strong><i>4x4</i></strong> {@code Sudoku}.  
		 */
		VARIANT_4x4(4, 2, 2, 2, 2),
		
		/**
		 * Describes a tall <strong><i>6x6</i></strong> {@code Sudoku}.  
		 */
		VARIANT_6x6_TALL(6, 2, 3, 2, 3),
		
		/**
		 * Describes a fat <strong><i>6x6</i></strong> {@code Sudoku}.  
		 */
		VARIANT_6x6_FAT(6, 3, 2, 3, 2),
		
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
		
		private SudokuVariants(int size, int blockRows, int blockColumns, int xBlocks, int yBlocks) {
			this.size = requireGreaterThanOrEqualTo(1, size, "size must be greater tha or equal to 1.");
			this.blockRows = requireGreaterThanOrEqualTo(1, blockRows, "blockRows must be greater tha or equal to 1.");
			this.blockColumns = requireGreaterThanOrEqualTo(1, blockColumns, "blockColumns must be greater tha or equal to 1.");
			this.xBlocks = requireGreaterThanOrEqualTo(1, xBlocks, "xBlocks must be greater tha or equal to 1.");
			this.yBlocks = requireGreaterThanOrEqualTo(1, yBlocks, "yBlocks must be greater tha or equal to 1.");
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
	}
}
