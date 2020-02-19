/**
 * 
 */
package com.kori_47.sudoku;

/**
 * This represents an {@link CellGroup} that is unique within a given context <i>(e.g within a {@link LatinSquare})</i>.
 * All {@code UniqueCellGroup}s have an {@link #id() identifier} that can be used to distinguish them from other
 * {@code UniqueCellGroup}s.
 * 
 * @param <V> the type of value held by the {@link Symbol}s supported by this {@code UniqueCellGroup}.
 * 
 * @author <a href="https://github.com/kennedykori">Kennedy Kori</a>
 *
 * @since Sun, 2 Feb 2020 01:11:00
 * 
 * @see CellGroup
 * @see LatinSquare
 * @see Unique
 */
public interface UniqueCellGroup<V> extends CellGroup<V>, Unique<String>, Comparable<UniqueCellGroup<V>> {
	
	/**
	 * Returns the hash code value for this {@code UniqueCellGroup}. The hash code of a {@code UniqueCellGroup} should
	 * be derived from the hash codes of the following properties of a {@code UniqueCellGroup}: 
	 * <ul>
	 * <li>The hash code value of this {@code UniqueCellGroup}'s <i>id</i>.</li>
	 * <li>The hash code value of this {@code UniqueCellGroup}'s <i>size</i> as returned by {@link Integer#hashCode(int)}</li>
	 * <li>The hash code value of this {@code UniqueCellGroup}'s <i>cells</i> {@code Map}.</li>
	 * </ul>
	 * 
	 * @return the hash code value of this {@code UniqueCellGroup}.
	 */
	@Override
	int hashCode();

	/**
	 * Compares the specified object with this {@code UniqueCellGroup} for equality. Returns {@code true} if the
	 * given object is also a {@code UniqueCellGroup} and the two {@code UniqueCellGroup}s are identical. Two
	 * {@code UniqueCellGroup}s are said to be equal if each of the following of their properties are also equal:
	 * <ul>
	 * <li>The <i>ids</i> of the {@code UniqueCellGroup}s.</li>
	 * <li>The <i>sizes</i> of the {@code UniqueCellGroup}s</li>
	 * <li>The <i>cells</i> {@code Map}s of the {@code UniqueCellGroup}s .</li>
	 * </ul>
	 * 
	 * @param obj the object to compare for equality with this {@code UniqueCellGroup}.
	 * 
	 * @return {@code true} if this {@code UniqueCellGroup} is equal to {@code obj} argument, {@code false} otherwise.
	 */
	@Override
	boolean equals(Object obj);
	
	/**
	 * Returns a {@code String} that uniquely identifies this {@code UniqueCellGroup} within a given context.
	 * 
	 * @return the unique identifier of this {@code UniqueCellGroup} within a given context.
	 */
	String id();
}
