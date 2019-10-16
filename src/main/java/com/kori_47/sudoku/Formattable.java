/**
 * 
 */
package com.kori_47.sudoku;

/**
 * This represents a LatinSquare node that can be formatted to different formats.
 * 
 * @author Kennedy Kori
 *
 * @since Oct 17, 2019, 1:21:10 AM
 */
public interface Formattable {
	
	/**
	 * Returns the x and y coordinate(s) representation of this item as a {@code String}.
	 * 
	 * @return the x and y coordinate(s) representation of this item as a {@code String}. 
	 */
	String toXY();
	
	/**
	 * Returns the x and y coordinate(s) plus the value(s) of the symbol(s) in this item as a {@code String}.
	 * 
	 * @return the x and y coordinate(s) plus the value(s) of the symbol(s) in this item as a {@code String}. 
	 */
	String toXYV();
	
	/**
	 * Returns the x and y coordinate(s) plus the id(s) of the symbol(s) in this item as a {@code String}.
	 * 
	 * @return the x and y coordinate(s) plus the id(s) of the symbol(s) in this item as a {@code String}. 
	 */
	String toXYI();
	
	/**
	 * Returns the value(s) of the the symbol(s) in this item as a {@code String}. 
	 * 
	 * @return the value(s) of the the symbol(s) in this item as a {@code String}.
	 */
	String toV();
	
	/**
	 * Returns the id(s) of the the symbol(s) in this item as a {@code String}. 
	 * 
	 * @return the id(s) of the the symbol(s) in this item as a {@code String}.
	 */
	String toI();
}
