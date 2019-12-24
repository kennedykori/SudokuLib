/**
 * 
 */
package com.kori_47.sudoku;

import static java.util.Objects.requireNonNull;

import java.util.Iterator;

import static com.kori_47.utils.ObjectUtils.requireInRange;

import java.util.Map;
import java.util.Optional;
import java.util.Spliterator;

/**
 * This represents a logical ordering of a group of cells in a {@link LatinSquare}. Each {@code CellGroup}
 * in a complete {@code LatinSquare} should contain each of the {@link Symbol symbols} in the {@code LatinSquare}
 * exactly once. Examples of {@code CellGroup}s are {@link Row} and {@link Column}.
 * 
 * @param <V> the type of value held by the {@link Symbol}s supported by the {@link Cell}s in this {@link CellGroup}.
 * 
 * @author <a href="https://github.com/kennedykori">Kennedy Kori</a>
 *
 * @since Oct 17, 2019, 2:50:14 AM
 */
public interface CellGroup<V> extends Formattable, Iterable<Cell<V>>, Comparable<CellGroup<V>> {

	/**
	 * {@inheritDoc}
	 * 
	 * @implSpec
	 * The default implementation uses {@link FormattableUtils#toXY(CellGroup)} to construct the desired
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
	 * The default implementation uses {@link FormattableUtils#toXYV(CellGroup)} to construct the desired
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
	 * The default implementation uses {@link FormattableUtils#toXYI(CellGroup)} to construct the desired
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
	 * The default implementation uses {@link FormattableUtils#toV(CellGroup)} to construct the desired
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
	 * The default implementation uses {@link FormattableUtils#toI(CellGroup)} to construct the desired
	 * representation.
	 */
	@Override
	default String toI() {
		return FormattableUtils.toI(this);
	}
	
	/**
	 * Returns an {@link Optional} describing a {@code Cell} with the given cell id, or an empty if no such 
	 * {@code Cell} exists in this {@code CellGroup}.
	 * 
	 * @param cellId the id of the {@code Cell} to return.
	 * 
	 * @return an {@code Optional} describing a {@code Cell} with the given cell id, or an empty if no such 
	 * {@code Cell} exists in this {@code CellGroup}.
	 * 
	 * @throws NullPointerException if {@code cellId} is {@code null}.
	 * 
	 * @implSpec
	 * The default implementation is equivalent to, for this {@code cellGroup}:
	 * <pre> {@code
	 * Cell<V> cell = cellGroup.cells().get(cellId);
	 * return Optional.ofNullable(cell);
	 * }
	 * </pre>
	 */
	default Optional<Cell<V>> getCell(String cellId) {
		requireNonNull(cellId, "cellId cannot be null.");
		return Optional.ofNullable(cells().get(cellId));
	}

	/**
	 * Returns an {@link Optional} describing a {@code Cell} with the given x and y coordinates, or an 
	 * empty if no such {@code Cell} exists in this {@code CellGroup}.
	 * 
	 * @param x the x coordinate of the {@code Cell} to return.
	 * @param y the y coordinate of the {@code Cell} to return.
	 * 
	 * @return an {@code Optional} describing a {@code Cell} with the given x and y coordinates, or an
	 * empty if no such {@code Cell} exists in this {@code CellGroup}.
	 * 
	 * @throws IllegalArgumentException if {@code x} and/or {@code y} is/are negative or greater than
	 * {@link #size() size} of this {@code CellGroup}.
	 * 
	 * @implSpec
	 * The default implementation is equivalent to, for this {@code cellGroup}:
	 * <pre> {@code
	 * return cellGroup.cells().values().stream()
	 * 		.filter(c -> c.x() == x)
	 * 		.filter(c -> c.y() == y)
	 * 		.findFirst();
	 * }
	 * </pre>
	 */
	default Optional<Cell<V>> getCell(int x, int y) {
		requireInRange(0, size(), x, "x must be non negative and less than " + size());
		requireInRange(0, size(), y, "y must be non negative and less than " + size());
		return cells().values().stream()
				.filter(cell -> cell.x() == x)
				.filter(cell -> cell.y() == y)
				.findFirst();
	}

	/**
	 * Returns an iterator over the {@link Cell}s in this {@code CellGroup}.
	 * 
	 * @return an iterator over the {@code Cell}s in this {@code CellGroup}.
	 * 
	 * @implSpec
	 * The default implementation is equivalent to, for this {@code cellGroup}:
	 * <pre> {@code
	 * return cellGroup.cells().values().iterator();
	 * }
	 * </pre>
	 */
	@Override
	default Iterator<Cell<V>> iterator() {
		return cells().values().iterator();
	}

	/**
	 * Creates and returns a {@link Spliterator} over the {@link Cell}s in this {@code CellGroup}.
	 * 
	 * @return a {@code Spliterator} over the {@code Cell}s in this {@code CellGroup}.
	 * 
	 * @implSpec
	 * The default implementation is equivalent to, for this {@code cellGroup}:
	 * <pre> {@code
	 * return cellGroup.cells().values().spliterator();
	 * }
	 * </pre>
	 */
	@Override
	default Spliterator<Cell<V>> spliterator() {
		return cells().values().spliterator();
	}
	
	/**
	 * Returns a {@code String} that identifies this {@code CellGroup} in a given {@code LatinSquare}.
	 * 
	 * @return the unique identifier of this {@code CellGroup} in a {@code LatinSquare}.
	 */
	String id();
	
	/**
	 * Returns the number of {@code Cell}s in this {@code CellGroup}. This should normally be equal
	 * to the size of the {@code LatinSquare} in which this {@code CellGroup} belongs.
	 * 
	 * @return the number of {@code Cell}s in this {@code LatinSquare}.
	 */
	int size();
	
	/**
	 * Returns a {@code Map} of the {@code Cell}s contained in this {@code CellGroup}. Modification of the 
	 * returned {@code Map} should not alter the contents of this {@code CellGroup}. Implementations of this 
	 * interface can also choose to return an unmodifiable {@code Map} instead to prevent modifications.
	 * 
	 * @return a {@code Map} of the {@code Cell}s contained in this {@code CellGroup}
	 */
	Map<String, Cell<V>> cells();
}
