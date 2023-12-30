/**
 * 
 */
package com.kori_47.sudoku;

/**
 * Represents an entity or element of a {@link LatinSquare} that is unique within a given context.
 * All {@code Unique} elements have an {@link #id() identifier} that can be used to distinguish them
 * from other related elements within a given context.
 * 
 * <p>
 * Examples of {@code Unique} elements are {@link Cell}s which should have a unique id within a
 * {@link CellGroup} <i>(the context)</i>, {@link Row}s and {@link Column}s which should have a
 * unique id within the {@code LatinSquare} in which they belong, {@link Symbol}s which should
 * have a unique id within a {@code LatinSquare}, etc.
 * 
 * @param <T> the type of identifier used by this element.
 * 
 * @author <a href="https://github.com/kennedykori">Kennedy Kori</a>
 *
 * @since Sat, 1 Feb 2020 20:20:46
 * 
 * @see LatinSquare
 * @see CellGroup
 * @see Cell
 * @see Symbol
 */
public interface Unique<T> {
	
	/**
	 * Returns the identifier used to uniquely identify of this element.
	 * 
	 * @return  the identifier of this element.
	 */
	T id();
}
