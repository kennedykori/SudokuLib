/**
 * 
 */
package com.kori_47.sudoku;

/**
 * This represents a LatinSquare symbol, i.e the values used to fill a LatinSquare's cells. 
 * Each {@code Symbol} has an id which is a positive integer with zero being the id of the empty 
 * symbol, i.e the symbol used to represent a cell that has not yet being filled. The {@link #id()} 
 * method returns the id of a symbol and the {@link #value()} method returns the value of a symbol.
 * 
 * @param <T> the type of value that this symbol represents. E.g {@code Integer} for integer values 
 * and {@code String} for letter values.
 * 
 * @author Kennedy Kori
 *
 * @since Oct 17, 2019, 12:15:56 AM
 */
public interface Symbol<V> {
	
	/**
	 * Returns the id of this symbol. 
	 * 
	 * @return the id of this symbol.
	 */
	int id();
	
	/**
	 * Returns the value of this symbol.
	 * 
	 * @return the value of this symbol.
	 */
	V value();
}
