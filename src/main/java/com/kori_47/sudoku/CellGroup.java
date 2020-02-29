/**
 * 
 */
package com.kori_47.sudoku;

import static java.util.stream.Collectors.toSet;
import static java.util.Objects.requireNonNull;

import static com.kori_47.utils.ObjectUtils.requireInRange;

import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.Spliterator;

/**
 * This represents a logical ordering of a group of cells in a {@link LatinSquare}. Each {@code CellGroup}
 * in a complete {@code LatinSquare} should contain each of the {@link Symbol symbols} in the {@code LatinSquare}
 * exactly once. Examples of {@code CellGroup}s are {@link Row} and {@link Column}.
 * 
 * @param <V> the type of value held by the {@link Symbol}s supported by this {@link CellGroup}.
 * 
 * @author <a href="https://github.com/kennedykori">Kennedy Kori</a>
 *
 * @since Oct 17, 2019, 2:50:14 AM
 * 
 * @see AbstractCellGroup
 * @see InterpolatableCellGroup
 * @see Cell
 * @see LatinSquare
 */
public interface CellGroup<V> extends Formattable, Iterable<Cell<V>> {

	/**
	 * {@inheritDoc}
	 * 
	 * @implSpec
	 * The default implementation uses {@link Formattables#toXY(CellGroup)} to construct the desired
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
	 * The default implementation uses {@link Formattables#toXYV(CellGroup)} to construct the desired
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
	 * The default implementation uses {@link Formattables#toXYI(CellGroup)} to construct the desired
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
	 * The default implementation uses {@link Formattables#toV(CellGroup)} to construct the desired
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
	 * The default implementation uses {@link Formattables#toI(CellGroup)} to construct the desired
	 * representation.
	 */
	@Override
	default String toI() {
		return Formattables.toI(this);
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
	 * Returns a {@link Set} of all the {@link Cell#isClueCell() clue} {@link Cell}s in this
	 * {@code CellGroup}. Modification of the returned {@code Set} should not alter the contents of 
	 * this {@code CellGroup}. Implementations of this interface can also choose to return an immutable
	 * {@code Set} instead to prevent modifications.
	 * 
	 * @return a {@code Set} of all the clue {@code Cell}s in this {@code CellGroup}.
	 * 
	 * @implSpec
	 * The default implementation is equivalent to, for this {@code cellGroup}:
	 * <pre> {@code
	 * return cellGroup.cells().values().stream()
	 * 		.filter(cell -> cell.isClueCell())
	 * 		.collect(Collectors.toSet());
	 * }
	 * </pre>
	 */
	default Set<Cell<V>> clueCells() {
		return cells().values().stream().filter(cell -> cell.isClueCell()).collect(toSet());
	}

	/**
	 * Returns a {@link Set} of all the normal {@link Cell}s in this {@code CellGroup}. Modification of the
	 * returned {@code Set} should not alter the contents of this {@code CellGroup}. Implementations of this
	 * interface can also choose to return an immutable {@code Set} instead to prevent modifications.
	 * 
	 * @return a {@code Set} of all the normal {@code Cell}s in this {@code CellGroup}.
	 * 
	 * @implSpec
	 * The default implementation is equivalent to, for this {@code cellGroup}:
	 * <pre> {@code
	 * return cellGroup.cells().values().stream()
	 * 		.filter(cell -> !cell.isClueCell())
	 * 		.collect(Collectors.toSet());
	 * }
	 * </pre>
	 */
	default Set<Cell<V>> normalCells() {
		return cells().values().stream().filter(cell -> !cell.isClueCell()).collect(toSet());
	}

	/**
	 * Returns the hash code value for this {@code CellGroup}. The hash code of a {@code CellGroup} should
	 * be derived from the hash codes of the following properties of a {@code CellGroup}: 
	 * <ul>
	 * <li>The hash code value of this {@code CellGroup}'s <i>size</i> as returned by {@link Integer#hashCode(int)}</li>
	 * <li>The hash code value of this {@code CellGroup}'s <i>cells</i> {@code Map}.</li>
	 * </ul>
	 * 
	 * @return the hash code value of this {@code CellGroup}.
	 */
	@Override
	int hashCode();

	/**
	 * Compares the specified object with this {@code CellGroup} for equality. Returns {@code true} if the
	 * given object is also a {@code CellGroup} and the two {@code CellGroup}s are identical. Two {@code CellGroup}s
	 * are said to be equal if each of the following of their properties are also equal:
	 * <ul>
	 * <li>The <i>sizes</i> of the {@code CellGroup}s</li>
	 * <li>The <i>cells</i> {@code Map}s of the {@code CellGroup}s .</li>
	 * </ul>
	 * 
	 * @param obj the object to compare for equality with this {@code CellGroup}.
	 * 
	 * @return {@code true} if this {@code CellGroup} is equal to {@code obj} argument, {@code false} otherwise.
	 */
	@Override
	boolean equals(Object obj);
	
	/**
	 * Returns the number of {@code Cell}s in this {@code CellGroup}. This should normally be equal
	 * to the size of the {@link LatinSquare} in which this {@code CellGroup} belongs.
	 * 
	 * @return the number of {@code Cell}s in this {@code CellGroup}.
	 */
	int size();
	
	/**
	 * Returns a {@code Map} of the {@code Cell}s contained in this {@code CellGroup}. Modification of the 
	 * returned {@code Map} should not alter the contents of this {@code CellGroup}. Implementations of this 
	 * interface can also choose to return an immutable {@code Map} instead to prevent modifications.
	 * 
	 * @return a {@code Map} of the {@code Cell}s contained in this {@code CellGroup}
	 */
	Map<String, Cell<V>> cells();
}
