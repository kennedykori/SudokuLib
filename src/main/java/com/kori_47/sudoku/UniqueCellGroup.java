/**
 * 
 */
package com.kori_47.sudoku;

/**
 * This represents an {@link CellGroup} that is unique within a given context.
 * 
 * @param <V> the type of value held by the {@link Symbol}s supported by this {@link CellGroup}.
 * 
 * @author <a href="https://github.com/kennedykori">Kennedy Kori</a>
 *
 * @since Sun, 2 Feb 2020 01:11:00
 */
public interface UniqueCellGroup<V> extends CellGroup<V>, Unique<String>, Comparable<UniqueCellGroup<V>> {
	
	/**
	 * Returns the hash code value for this {@code CellGroup}. The hash code of a {@code CellGroup} should
	 * be derived from the hash codes of the following properties of a {@code CellGroup}: 
	 * <ul>
	 * <li>The hash code value of this {@code CellGroup}'s <i>id</i>.</li>
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
	 * <li>The <i>ids</i> of the {@code CellGroup}s.</li>
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
	 * Returns a {@code String} that identifies this {@code CellGroup} in a given context.
	 * 
	 * @return the unique identifier of this {@code CellGroup} in a given context.
	 */
	String id();
}
