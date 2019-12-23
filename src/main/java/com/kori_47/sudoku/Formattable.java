/**
 * 
 */
package com.kori_47.sudoku;

/**
 * This represents a {@link LatinSquare} <i>element</i> that can be formatted to show its representation
 * based on a combination of the following properties:
 * <ul>
 *   <li> The x coordinate of the element or of it's elements if this is a container element that has
 *        elements with an x coordinate. </li>
 *   <li> The y coordinate of the element or of it's elements if this is a container element that has
 *        elements with a y coordinate. </li>
 *   <li> The id of the {@link Symbol} contained by this element or of it's elements if this is a container
 *        element that has elements which hold or contain {@code Symbol}s. </li>
 *   <li> The value of the {@link Symbol} contained by this element or of it's elements if this is a container
 *        element that has elements which hold or contain {@code Symbol}s. </li>
 * </ul>
 * 
 * Examples of {@code Formattable}s are {@link Cell} and any {@link CellGroup} implementation including 
 * {@code LatinSquare}.
 * 
 * @author <a href="https://github.com/kennedykori">Kennedy Kori</a>
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
