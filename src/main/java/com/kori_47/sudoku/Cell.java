/**
 * 
 */
package com.kori_47.sudoku;


/**
 * A cell is an intersection between a column and a row in a LatinSquare. Each cell holds a single value and has an 
 * x and y coordinate. The x coordinate of the cell is the id of the column of the cell and the y coordinate of the 
 * cell is the id of the row of the cell. A cell can be marked as an initial cell making it non editable until it is 
 * changed back to a normal cell. Apart from having a value, a cell can also have a note which is like a possible value 
 * of the cell
 * 
 * @author Kennedy Kori
 *
 * @since Oct 17, 2019, 12:38:39 AM
 */
public interface Cell<V> extends Formattable {
	
	void changeSymbol(Symbol<V> value);
	
	void markInitial(Symbol<V> initialValue);
	
	void markNormal();
	
	int x();
	
	int y();

	String id();
	
	Symbol<V> value();
	
	Symbol<V> note();
	
	boolean isInitial();
}
