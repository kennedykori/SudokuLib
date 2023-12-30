/**
 * 
 */
package com.kori_47.sudoku;

/**
 * This represents a region in a {@link Sudoku}. For example, the famous <i>9x9</i> {@code Sudoku}
 * has nine <i>3x3</i> regions. This is a marker interface and has no addition methods other than
 * the ones it inherits from other interfaces.
 * 
 * @param <V> the type of value held by the {@link Symbol}s supported by this {@code Block}.
 * 
 * @author <a href="https://github.com/kennedykori">Kennedy Kori</a>
 *
 * @since Sat, 28 Dec 2019 11:40:23
 * 
 * @see Sudoku
 * @see CellGroup
 * @see InterpolatableCellGroup
 * @see UniqueCellGroup
 */
public interface Block<V> extends UniqueCellGroup<V>, InterpolatableCellGroup<V> {

}
