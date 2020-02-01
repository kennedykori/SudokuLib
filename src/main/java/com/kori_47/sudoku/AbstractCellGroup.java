/**
 * 
 */
package com.kori_47.sudoku;

import static java.util.Collections.unmodifiableMap;
import static java.util.stream.Collectors.toMap;
import static java.util.Objects.requireNonNull;

import static com.kori_47.utils.ObjectUtils.requireGreaterThanOrEqualTo;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <p>This is a skeletal implementation of the {@link CellGroup} interface.
 * 
 * @param <V> the type of value held by the {@link Symbol}s supported by this {@link CellGroup}.
 * 
 * @author <a href="https://github.com/kennedykori">Kennedy Kori</a>
 *
 * @since Oct 19, 2019, 1:20:22 AM
 * 
 * @see CellGroup
 * @see LatinSquare
 */
public abstract class AbstractCellGroup<V> implements CellGroup<V> {
	
	private final int size;
	
	// protected values that concrete classes need to access 
	protected final Map<String, Cell<V>> cells;
	
	// views
	private final Map<String, Cell<V>> cellsView;

	/**
	 * This constructor initializes the {@code id}, {@code size} and {@code cells} properties of a
	 * {@link CellGroup} with the provided values.
	 * 
	 * @param size the size of this {@code CellGroup}.
	 * @param cells the {@code Cell}s that this {@code CellGroup} will have.
	 * 
	 * @throws NullPointerException if {@code id} and/or {@code cells} are/is {@code null}.
	 * @throws IllegalArgumentException if {@code size} is less than {@code 1} or the number of the
	 * given {@code cells} is less than {@code size}.
	 * 
	 * @implSpec
	 * <p> This constructor first validates that {@code size >= 1} before assigning it to a final instance
	 * field. It then validates that {@code cells} is not {@code null} and that {@code cells.size() >= size}
	 * before sorting and creating a defensive copy of {@code cells} on a {@link LinkedHashMap} and assiging
	 * the newly copied {@code Map} to a protected final instance field named {@code cells}.
	 * 
	 * <p> This constructor also creates a final instance field {@code cellsView} that returns an unmodifiable
	 * view of the {@code cells} {@code Map}. This field is then used as the return value of the {@link #cells()}
	 * method which prevents modification of the cells in this {@code CellGroup}.  
	 */
	protected AbstractCellGroup(int size, Map<String, Cell<V>> cells) {
		this.size = requireGreaterThanOrEqualTo(1, size, "size must be greater than or equal to 1.");
		this.cells = validateCells(cells, this.size);
		this.cellsView = unmodifiableMap(this.cells);
	}

	/**
	* {@inheritDoc}
	*/
	@Override
	public int size() {
		return size;
	}

	/**
	* {@inheritDoc}
	*/
	@Override
	public Map<String, Cell<V>> cells() {
		return cellsView;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @implSpec
	 * This implementation is equivalent to, for this {@code cellGroup}:
	 * <pre> {@code
	 * return Integer.hashCode(cellGroup.size()) + cellGroup.cells().hashCode();
	 * }
	 * </pre>
	 */
	@Override
	public int hashCode() {
		return Integer.hashCode(size) + cells.hashCode();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @implSpec
	 * This implementation first checks if the specified object is this {@code CellGroup}; if so it
	 * returns {@code true}. Then it checks if the specified object is a {@code CellGroup} whose size,
	 * id and cells {@code Map} are equal to the equivalent properties of this {@code CellGroup}; if not,
	 * it returns {@code false}. If so, it returns {@code true}.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (!(obj instanceof CellGroup)) return false;
		CellGroup<?> _obj = (CellGroup<?>) obj;
		return size == _obj.size() && cells.equals(_obj.cells());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return toXYV();
	}
	
	/**
	 * Validate that given {@code Map} of cells is of the right size(has atleast {@code size}
	 * cells) then sort and perform a defensive copy of {@code cells}.
	 * 
	 * @param <V> the type of value held by the {@code Symbol}s supported by the {@link Cell}s
	 * in this {@link CellGroup}.
	 * 
	 * @param cells the cells to be validated.
	 * @param size the size of this {@code CellGroup}.
	 * 
	 * @return a new {@code LinkedHashMap} containing {@code cells}.
	 */
	private static final <V> Map<String, Cell<V>> validateCells(Map<String, Cell<V>> cells, int size) {
		requireNonNull(cells, "cells cannot be null.");
		requireGreaterThanOrEqualTo(size, cells.size(), "You must provide atleast " + size + " cells");
		// return a new LinkedHashMap containing cells.
		return cells.values().stream()
				.sorted()
				.collect(toMap(cell -> cell.id(), cell -> cell,
						(oldValue, newValue) -> newValue,
						() -> new LinkedHashMap<String, Cell<V>>(cells.size())));
	}
}
