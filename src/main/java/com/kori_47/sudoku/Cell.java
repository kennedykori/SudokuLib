/**
 * 
 */
package com.kori_47.sudoku;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;
import java.util.Optional;
import java.util.Set;

/**
 * A {@code Cell} is an intersection between a {@link Column} and a {@link Row} in a {@link LatinSquare}. Each cell holds
 * a {@link Symbol} and has an x and y coordinate which are the {@code Column} and {@code Row} respectfully, that this
 * {@code Cell} belongs to. The x coordinate of a {@code Cell} is the <strong><i>i<sup>th</sup></i></strong> {@code Column}
 * of the {@link LatinSquare} <strong><i>L</i></strong> and the y coordinate of a {@code Cell} is the
 * <strong><i>j<sup>th</sup></i></strong>{@code Row} of the {@code LatinSquare} <strong><i>L</i></strong> such that:
 * <pre>
 * 				x = i and 0 <= i < s
 * 		and		y = j and 0 <= j < s
 * 		where	s is the size of L
 * </pre>
 * 
 * <p>
 * <i>
 * <strong>NOTE:</strong> {@code Cell}s are mutable since they permit modification of their {@code Symbol}. As such,
 * the {@code equals} and {@code hashCode} implementations of the {@code Cell} are based on the non-mutable properties of the
 * {@code Cell}, that is, the {@code id}, {@code x} and {@code y} coordinates of the {@code Cell}. For more comprehensive
 * equality comparisons, implementors can override the {@link #deepEquals(Object)} method and provide a more thural implementation.
 * </i>
 * </p>
 * 
 * @param <V> the type of value held by the {@code Symbol}s supported by this {@code Cell}.
 * 
 * @author <a href="https://github.com/kennedykori">Kennedy Kori</a>
 *
 * @since Oct 17, 2019, 12:38:39 AM
 * 
 * @see LatinSquare
 * @see Column
 * @see Row
 * @see Symbol
 */
public interface Cell<V> extends Formattable, Comparable<Cell<V>>, Unique<String> {

	/**
	 * {@inheritDoc}
	 * 
	 * @implSpec
	 * The default implementation uses {@link Formattables#toXY(Cell)} to construct the desired
	 * representation.
	 */
	@Override
	default String toXY() {
		return Formattables.toXY(this);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @implSpec
	 * The default implementation uses {@link Formattables#toXYV(Cell)} to construct the desired
	 * representation.
	 */
	@Override
	default String toXYV() {
		return Formattables.toXYV(this);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @implSpec
	 * The default implementation uses {@link Formattables#toXYI(Cell)} to construct the desired
	 * representation.
	 */
	@Override
	default String toXYI() {
		return Formattables.toXYI(this);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @implSpec
	 * The default implementation uses {@link Formattables#toV(Cell)} to construct the desired
	 * representation.
	 */
	@Override
	default String toV() {
		return Formattables.toV(this);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @implSpec
	 * The default implementation uses {@link Formattables#toI(Cell)} to construct the desired
	 * representation.
	 */
	@Override
	default String toI() {
		return Formattables.toI(this);
	}

	/**
	 * Performs order and equality comparisons on two {@code Cell}s.
	 * 
	 * @param other another {@code Cell} to compare to this one.
	 * 
	 * @return the value {@code 0} if this {@code Cell} is
     *         equal to the argument {@code Cell}; a value less than
     *         {@code 0} if this {@code Cell} is less than the argument
     *         {@code Cell}; and a value greater than {@code 0} if this
     *         {@code Cell} is greater than the argument {@code Cell}.
     * 
     * @throws NullPointerException if {@code other} is {@code null}.
     * 
     * @see Cells#defaultComparator()
     * 
     * @implSpec
     * The default implementation uses the {@link Comparator} returned by {@link Cells#defaultComparator()}
     * to perform the needed comparisons. 
	 */
	@Override
	default int compareTo(Cell<V> other) { 
		requireNonNull(other, "other cannot be null.");
		return Cells.defaultComparator().compare(this, other);
	}
	
	/**
	 * Clears this {@code Cell} by changing the {@code Cell}s {@link Symbol} to a {@code null} value.
	 * 
	 * @implSpec
	 * The default implementation is equivalent to, for this {@code cell}:
	 * <pre> {@code
	 * cell.changeSymbol(null);
	 * }
	 * </pre>
	 */
	default void clear() {
		changeSymbol(null);
	}
	
	/**
	 * This method is meant to be used as alternative to {@link #equals(Object)} and concrete
	 * implementations of this interface can provide a more detailed and comprehensive comparison
	 * of two {@code Cell}s. This method exists because this interface imposes a strict specification
	 * on the {@code equals} method that might not be comprehensive enough for some implementations.
	 * 
	 * <p>
	 * <i>
	 * <strong>NOTE:</strong> Since this is meant to be used as an alternative to {@code equals},
	 * implementations of this method should maintain the general contracts of the {@code equals} method
	 * to ensure correct and predictable results.
	 * </i>
	 * 
	 * @param otherObj the other object to compare for equality with this one.
	 * 
	 * @return {@code true} if this {@code Cell} is equal to {@code otherObj}, {@code false} otherwise.
	 * 
	 * @see #equals(Object)
	 * 
	 * @implSpec
	 * The default implementation of this method simply calls the {@code equals} method of this {@code Cell}.
	 * That is, the default implementation is equivalent to:
	 * <pre> {@code
	 * return this.equals(otherObj);
	 * }
	 * </pre>
	 */
	default boolean deepEquals(Object otherObj) {
		return this.equals(otherObj);
	}
	
	/**
	 * Returns the hash code value for this {@link Cell}. Since a {@code Cell} is mutable, the
	 * hash code value of a {@code Cell} is derived from the following three properties of a
	 * {@code Cell} that are not expected to mutate within the lifetime of a {@code Cell}:
	 * <ul>
	 * <li>The hash code of the {@link #id()} of this {@code Cell}.</li>
	 * <li>The hash code of the {@link #x() x coordinate} of this {@code Cell}.</li>
	 * <li>The hash code of the {@link #y() y coordinate} of this {@code Cell}.</li>
	 * </ul>
	 * 
	 * @return the hash code value of this {@code Cell}.
	 */
	@Override
	int hashCode();
	
	/**
	 * Compares the given object with this {@code Cell} for equality. Returns {@code true} if the
	 * given object is also a {@code Cell} and the two {@code Cell}s are identical. Since {@code Cell}s
	 * are mutable, two {@code Cell}s are said to be equal only if each of the following of their properties
	 * are also equal:
	 * <ul>
	 * <li>The <i>{@link #id() ids}</i> of the {@code Cell}s</li>
	 * <li>The <i>{@link #x() x coordinates}</i> of the {@code Cell}s.</li>
	 * <li>The <i>{@link #y() y coordinates}</i> of the {@code Cell}s.</li>
	 * </ul>
	 * 
	 * <p>
	 * Each of the properties above is not expected to mutate within the lifetime of a {@code Cell} and
	 * each {@code Cell} within a given {@link CellGroup} should have a unique combination of these properties.
	 * Using non mutable properties for equals comparisons helps to ensure that a {@link Set} of {@code Cell}s
	 * works correctly even if it's cells are mutated and helps to ensure that the general contract of the
	 * {@code hashCode} method is maintained. For a more comprehensive comparisons of {@code Cell}s
	 * {@link #deepEquals(Object)} should be used instead.
	 * 
	 * @param obj the object to compare for equality with this {@code Cell}.
	 * 
	 * @return {@code true} if this {@code Cell} is equal to {@code obj} argument, {@code false} otherwise.
	 * 
	 * @see #deepEquals(Object)
	 */
	@Override
	boolean equals(Object obj);

	/**
	 * <p> Changes the {@link Symbol} of this cell to a new value. {@code null} values are allowed.
	 * 
	 * @param value the new {@code Symbol} to set on this cell.
 	 */
	void changeSymbol(Symbol<V> value);
	
	
	/**
	 * Returns the x coordinate of this cell. This is also the index of the {@link Column} in which
	 * this cell belongs and thus should always be a positive {@code Integer}.
	 * 
	 * @return the x coordinate of this cell.
	 */
	int x();
	
	/**
	 * Returns the y coordinate of this cell. This is also the index of the {@link Row} in which
	 * this cell belongs and thus should always be a positive {@code Integer}.
	 * 
	 * @return the y coordinate of this cell.
	 */
	int y();

	/**
	 * Returns a unique {@code String} that can be used to identify this cell in a {@code CellGroup}.
	 * 
	 * @return a unique identifier of this cell in a {@code CellGroup}.
	 */
	String id();
	
	/**
	 * Returns an {@link Optional} describing the {@link Symbol} set on this cell or an empty if no
	 * {@code Symbol} is currently set on the cell.
	 * 
	 * @return an {@code Optional} describing the {@code Symbol} set on this cell or an empty if no
	 * {@code Symbol} is currently set on the cell.
	 */
	Optional<Symbol<V>> symbol();
}
