/**
 * 
 */
package com.kori_47.sudoku;

import java.util.Map;

/**
 * @author <a href="https://github.com/kennedykori">Kennedy Kori</a>
 *
 * @since Sat, 8 Feb 2020 16:57:50
 */
class CompositeSudoku<V> implements Sudoku<V> {

	/**
	 * 
	 */
	public CompositeSudoku() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public CellFactory<V> cellFactory() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RowFactory<V> rowFactory() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ColumnFactory<V> columnFactory() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Row<V>> rows() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Column<V>> columns() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Symbol<V> emptySymbol() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<Integer, Symbol<V>> symbols() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Cell<V> startCell() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Cell<V> endCell() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Map<String, Cell<V>> cells() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Sudoku<V> flipHorizontally() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Sudoku<V> flipVertically() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Sudoku<V> copy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SudokuVariant variant() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BlockFactory<V> blockFactory() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Block<V>> blocks() {
		// TODO Auto-generated method stub
		return null;
	}

}
