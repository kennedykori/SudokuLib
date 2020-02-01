/**
 * 
 */
package com.kori_47.sudoku;

import static java.util.Objects.requireNonNull;

import java.util.Map;

/**
 * <p>This is a skeletal implementation of the {@link UniqueCellGroup} interface. This class extends from
 * {@link AbstractCellGroup} abstract class and some of it's default methods are implemented in terms of
 * it's super class implementations.
 * 
 * <p><i><strong>Note:</strong> This class has a natural ordering that is inconsistent with equals.</i>
 * 
 * @param <V> the type of value held by the {@link Symbol}s supported by this {@link CellGroup}.
 *
 * @author <a href="https://github.com/kennedykori">Kennedy Kori</a>
 *
 * @since Sun, 2 Feb 2020 00:48:02
 */
public abstract class AbstractUniqueCellGroup<V> extends AbstractCellGroup<V> implements UniqueCellGroup<V> {

	/**
	 * the unqiue identifier of this {@code UniqueCellGroup}.
	 */
	private final String id;
	
	/**
	 * This constructor initializes the {@code id}, {@code size} and {@code cells} properties of a
	 * {@link UniqueCellGroup} with the provided values.
	 * 
	 * @param id the identifier of this {@code UniqueCellGroup}.
	 * @param size the size of this {@code UniqueCellGroup}.
	 * @param cells the {@code Cell}s that this {@code UniqueCellGroup} will have.
	 * 
	 * @throws NullPointerException if {@code id} and/or {@code cells} are/is {@code null}.
	 * @throws IllegalArgumentException if {@code size} is less than {@code 1} or the number of the
	 * given {@code cells} is less than {@code size}.
	 * 
	 * @implSpec
	 * <p> This constructor first calls {@code super} with the given {@code size} and {@code cells} properties
	 * and then validates that id is not {@code null} before assigning the value to a final instance field with
	 * the same name.
	 */
	protected AbstractUniqueCellGroup(String id, int size, Map<String, Cell<V>> cells) {
		super(size, cells);
		this.id = requireNonNull(id, "id cannot be null.");
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws NullPointerException {@inheritDoc}
	 * @throws ClassCastException {@inheritDoc}
	 * 
	 * @implSpec
	 * This implementation compares this {@link UniqueCellGroup} with another by their {@code id}s. That is, this
	 * implementation is equivalent to, for this {@code uniqueCellGroup}:
	 * <pre> {@code
	 * return uniqueCellGroup.id().compareTo(other.id());
	 * }
	 * </pre>
	 */
	@Override
	public int compareTo(UniqueCellGroup<V> other) {
		requireNonNull(other, "other cannot be null.");
		return id.compareTo(other.id());
	}

	@Override
	public String id() {
		return id;
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * @implSpec
	 * This implementation is equivalent to, for this {@code uniqueCellGroup}:
	 * <pre> {@code
	 * return cellGroup.id().hashCode() + super.hashCode();
	 * }
	 * </pre>
	 */
	@Override
	public int hashCode() {
		return id.hashCode() + super.hashCode();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @implSpec
	 * This implementation first checks if the specified object is this {@code UniqueCellGroup}; if so it
	 * returns {@code true}. Then it checks if the specified object is a {@code UniqueCellGroup} whose size,
	 * id and cells {@code Map} are equal to the equivalent properties of this {@code UniqueCellGroup}; if not,
	 * it returns {@code false}. If so, it returns {@code true}.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (!(obj instanceof UniqueCellGroup)) return false;
		UniqueCellGroup<?> _obj = (UniqueCellGroup<?>) obj;
		return size() == _obj.size() && id.equals(_obj.id()) && cells.equals(_obj.cells());
	}
}
