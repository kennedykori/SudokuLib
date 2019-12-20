/**
 * 
 */
package com.kori_47.sudoku;

import java.util.Map;
import java.util.Optional;
import java.util.Spliterator;

/**
 * This represents a logical ordering of a group of cells in a {@link LatinSquare}. Each {@code CellGroup}
 * in a complete {@code LatinSquare} should contain each of the {@link Symbol symbols} in the {@code LatinSquare}
 * exactly once. Examples of {@code CellGroup}s are Rows and Columns.
 * 
 * @author <a href="https://github.com/kennedykori">Kennedy Kori</a>
 *
 * @since Oct 17, 2019, 2:50:14 AM
 */
public interface CellGroup<V> extends Formattable, Iterable<V>, Spliterator<V> {

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
	 */
	Optional<Cell<V>> getCell(String cellId);
	
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
