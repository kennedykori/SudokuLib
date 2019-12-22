/**
 * 
 */
package com.kori_47.sudoku;

import static java.util.Objects.requireNonNull;

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
public interface Symbol<V> extends Comparable<Symbol<V>> {
	
	/**
	 * Performs order and equality comparisons on two {@link Symbol}s.
	 * 
	 * @param other another symbol to compare to this one.
	 * 
	 * @return the value {@code 0} if this {@code Symbol} is
     *         equal to the argument {@code Symbol}; a value less than
     *         {@code 0} if this {@code Symbol} is less than the argument
     *         {@code Symbol}; and a value greater than {@code 0} if this
     *         {@code Symbol} is greater than the argument {@code Symbol}.
     * 
     * @throws NullPointerException if {@code other} is {@code null}.
     * 
     * @implSpec
     * The default implementation performs comparison based on the {@link #id() ids}
     * of the two symbols. That is, the implementation is equivalent to:
     * <pre> {@code
	 * return Integer.compare(this.id(), other.id());
	 * }
	 * </pre>
	 */
	@Override
	default int compareTo(Symbol<V> other) {
		return Integer.compare(id(), requireNonNull(other, "other cannot be null.").id());
	}
	
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
