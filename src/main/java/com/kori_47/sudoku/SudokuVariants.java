/**
 * 
 */
package com.kori_47.sudoku;

import static com.kori_47.utils.ObjectUtils.requireGreaterThanOrEqualTo;


/**
 * This represents a set of commonly used {@link SudokuVariant}s including the famous 9x9 variant.
 * 
 * @author <a href="https://github.com/kennedykori">Kennedy Kori</a>
 *
 * @since Sat, 28 Dec 2019 12:22:12
 */
public enum SudokuVariants implements SudokuVariant {

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
