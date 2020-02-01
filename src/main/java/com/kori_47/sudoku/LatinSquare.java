/**
 * 
 */
package com.kori_47.sudoku;

import java.util.Map;

/**
 * A {@code LatinSquare} is an <i>n × n</i> matrix filled with <i>n</i> different {@link Symbol}s,
 * such that entries in each {@link Row} and {@link Column} are distinct. This interface describes a
 * {@code LatinSquare} where <i>n</i> is the {@link #size()} of the {@code LatinSquare} and {@link #symbols()}
 * is the set of {@code Symbol}s that can be used as entries on this {@code LatinSquare}.
 * 
 * @param <V> the type of value held by the {@link Symbol}s supported by this {@code LatinSquare}.
 * 
 * @author <a href="https://github.com/kennedykori">Kennedy Kori</a>
 *
 * @since Oct 19, 2019, 12:37:48 AM
 * 
 * @see Symbol
 * @see Row
 * @see Column
 * @see Cell
 * @see CellGroup
 * @see InterpolatableCellGroup
 */
public interface LatinSquare<V> extends InterpolatableCellGroup<V> {

	/**
	 * {@inheritDoc}
	 * 
	 * @implSpec
	 * The default implementation uses {@link FormattableUtils#toXY(LatinSquare)} to construct the desired
	 * representation.
	 */
	@Override
	default String toXY() {
		return FormattableUtils.toXY(this);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @implSpec
	 * The default implementation uses {@link FormattableUtils#toXYV(LatinSquare)} to construct the desired
	 * representation.
	 */
	@Override
	default String toXYV() {
		return FormattableUtils.toXYV(this);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @implSpec
	 * The default implementation uses {@link FormattableUtils#toXYI(LatinSquare)} to construct the desired
	 * representation.
	 */
	@Override
	default String toXYI() {
		return FormattableUtils.toXYI(this);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @implSpec
	 * The default implementation uses {@link FormattableUtils#toV(LatinSquare)} to construct the desired
	 * representation.
	 */
	@Override
	default String toV() {
		return FormattableUtils.toV(this);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @implSpec
	 * The default implementation uses {@link FormattableUtils#toI(LatinSquare)} to construct the desired
	 * representation.
	 */
	@Override
	default String toI() {
		return FormattableUtils.toI(this);
	}

	/**
	 * Mutates all the {@link Cell}s in this {@code LatinSquare} by setting them to the
	 * {@link #emptySymbol() empty symbol} associated with this {@code LatinSquare}. This
	 * mutation also clears the notes in each {@code Cell} and reverts any initial {@code Cell}
	 * to a start {@code Cell}.
	 * 
	 * @implSpec
	 * The default implementation is equivalent to, for this {@code latinSquare}:
	 * <pre> {@code
	 * latinSquare.cells().values().forEach(cell -> cell.reset(emptySymbol()));
	 * }
	 * </pre>
	 */
	default void reset() {
		cells().values().forEach(cell -> cell.reset(emptySymbol()));
	}

	/**
	 * Mutates all the {@link Cell}s in this {@code LatinSquare} by setting each {@code Cell}s
	 * {@link Symbol} to a {@code null} value. This mutation also clears the notes in each {@code Cell}
	 * and reverts any initial {@code Cell} to a start {@code Cell}.
	 * 
	 * @implSpec
	 * The default implementation is equivalent to, for this {@code latinSquare}:
	 * <pre> {@code
	 * latinSquare.cells().values().forEach(cell -> cell.clear());
	 * }
	 * </pre>
	 */
	default void clear() {
		cells().values().forEach(cell -> cell.clear());
	}

	/**
	 * Returns the hash code value for this {@code LatinSquare}. The hash code of a {@code LatinSquare} should
	 * be derived from the hash codes of the following properties of a {@code LatinSquare}: 
	 * <ul>
	 * <li>The hash code value of this {@code LatinSquare}'s <i>{@link #size() size}</i> as returned by {@link Integer#hashCode(int)}</li>
	 * <li>The hash code value of this {@code LatinSquare}'s <i>{@link #emptySymbol() emptySymbol}</i>.</li>
	 * <li>The hash code value of this {@code LatinSquare}'s <i>{@link #cells() cells}</i> {@code Map}.</li>
	 * <li>The hash code value of this {@code LatinSquare}'s <i>{@link #rows() rows}</i> {@code Map}.</li>
	 * <li>The hash code value of this {@code LatinSquare}'s <i>{@link #columns() columns}</i> {@code Map}.</li>
	 * <li>The hash code value of this {@code LatinSquare}'s <i>{@link #symbols() symbols}</i> {@code Map}.</li>
	 * </ul>
	 * 
	 * @return the hash code value of this {@code LatinSquare}.
	 */
	@Override
	int hashCode();

	/**
	 * Compares the specified object with this {@code LatinSquare} for equality. Returns {@code true} if the
	 * given object is also a {@code LatinSquare} and the two {@code LatinSquare}s are identical. Two
	 * {@code LatinSquare}s are said to be equal if each of the following of their properties are also equal:
	 * <ul>
	 * <li>The <i>{@link #size() sizes}</i> of the {@code LatinSquare}s</li>
	 * <li>The <i>{@link #emptySymbol() emptySymbols}</i> of the {@code LatinSquare}s.</li>
	 * <li>The <i>{@link #cells() cells}</i> {@code Map}s of the {@code LatinSquare}s.</li>
	 * <li>The <i>{@link #rows() rows}</i> {@code Map}s of the {@code LatinSquare}s.</li>
	 * <li>The <i>{@link #columns() columns}</i> {@code Map}s of the {@code LatinSquare}s.</li>
	 * <li>The <i>{@link #symbols() symbols}</i> {@code Map}s of the {@code LatinSquare}s.</li>
	 * </ul>
	 * 
	 * @param obj the object to compare for equality with this {@code LatinSquare}.
	 * 
	 * @return {@code true} if this {@code LatinSquare} is equal to {@code obj} argument, {@code false} otherwise.
	 */
	@Override
	boolean equals(Object obj);
	
	/**
	 * Creates and returns a new {@code LatinSquare} whose {@link Row}s and {@link Cell}s are permuted so
	 * that the new {@code LatinSquare} is upside down in relation to this one.
	 * 
	 * @return a new identical but upside down {@code LatinSquare} in relation to this one.
	 */
	LatinSquare<V> flipHorizontally();

	/**
	 * Creates and returns a new {@code LatinSquare} whose {@link Column}s and {@link Cell}s are permuted so
	 * that the new {@code LatinSquare} is flipped sideways in relation to this one.
	 * 
	 * @return a new identical but flipped sideways {@code LatinSquare} in relation to this one.
	 */
	LatinSquare<V> flipVertically();

	/**
	 * Returns a new {@code LatinSquare} that has the same properties, {@link Cell cells} and 
	 * cell values as this one.
	 * 
	 * @return a new {@code LatinSquare} that has the same properties, {@code Cell}s and cell
	 * values as this one.
	 */
	LatinSquare<V> copy();

	/**
	 * Returns the {@link CellFactory} used by this {@code LatinSquare} to create new {@link Cell}s.
	 * 
	 * @return the {@code CellFactory} used by this {@code LatinSquare} to create new {@code Cell}s.
	 */
	CellFactory<V> cellFactory();

	/**
	 * Returns the {@link RowFactory} used by this {@code LatinSquare} to create new {@link Row}s.
	 * 
	 * @return the {@code RowFactory} used by this {@code LatinSquare} to create new {@code Row}s.
	 */
	RowFactory<V> rowFactory();

	/**
	 * Returns the {@link ColumnFactory} used by this {@code LatinSquare} to create new {@link Column}s.
	 * 
	 * @return the {@code ColumnFactory} used by this {@code LatinSquare} to create new {@code Column}s.
	 */
	ColumnFactory<V> columnFactory();
	
	/**
	 * Returns a {@code Map} of the {@link Row}s contained in this {@code LatinSquare}. Modification of the 
	 * returned {@code Map} should not alter the {@code Row}s of this {@code LatinSquare}. Implementations of this 
	 * interface can also choose to return an unmodifiable {@code Map} instead to prevent modifications.
	 * 
	 * @return a {@code Map} of the {@code Row}s contained in this {@code LatinSquare}.
	 */
	Map<String, Row<V>> rows();

	/**
	 * Returns a {@code Map} of the {@link Column}s contained in this {@code LatinSquare}. Modification of the 
	 * returned {@code Map} should not alter the {@code Column}s of this {@code LatinSquare}. Implementations of
	 * this interface can also choose to return an unmodifiable {@code Map} instead to prevent modifications.
	 * 
	 * @return a {@code Map} of the {@code Column}s contained in this {@code LatinSquare}.
	 */
	Map<String, Column<V>> columns();

	/**
	 * Returns the empty {@link Symbol} used by this {@code LatinSquare}.
	 * 
	 * @return the empty {@code Symbol} used by this {@code LatinSquare}.
	 */
	Symbol<V> emptySymbol();

	/**
	 * <p>Returns a {@code Map} of the {@link Symbol}s used to fill this {@code LatinSquare}'s {@link Cell}s. The
	 * returned {@code Map}'s size must be equal to the size of this {@code LatinSquare}.
	 * 
	 * <p> Modification of the returned {@code Map} should not alter the {@code Symbol}s of this {@code LatinSquare}.
	 * Implementations of this interface can also choose to return an unmodifiable {@code Map} instead to prevent
	 * modifications.
	 * 
	 * @return a {@code Map} of the {@code Symbol}s contained in this {@code LatinSquare}.
	 */
	Map<Integer, Symbol<V>> symbols();
}
