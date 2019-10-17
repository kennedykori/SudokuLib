/**
 * 
 */
package com.kori_47.sudoku;

/**
 * This represents an ordered {@link CellGroup}. That is a CellGroup whose ending positions 
 * can easily be calculated using a simple formulae when given the starting positions.
 * 
 * @author Kennedy Kori
 *
 * @since Oct 17, 2019, 3:00:43 AM
 */
public interface OrderedCellGroup<V> extends CellGroup<V> {
	
	int startX();
	
	int startY();
	
	int endX();

	int endY();
}
