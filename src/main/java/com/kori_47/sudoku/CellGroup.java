/**
 * 
 */
package com.kori_47.sudoku;

import static java.util.Objects.requireNonNull;

import java.util.Map;

/**
 * A {@code CellGroup} is a logical ordering of a group of cells in a LatinSquare. Examples of {@code CellGroup}s 
 * are Rows and Columns.
 * 
 * @author Kennedy Kori
 *
 * @since Oct 17, 2019, 2:50:14 AM
 */
public interface CellGroup<V> extends Formattable {
	
	void addCell(Cell<V> cell);
	
	Cell<V> removeCell(String cellId);
	
	default Cell<V> removeCell(Cell<V> cell) {
		return removeCell(requireNonNull(cell, "cell cannot be null").id());
	}

	Cell<V> getCell(String cellId);
	
	int id();
	
	int size();
	
	Map<String, Cell<V>> cells();
}
