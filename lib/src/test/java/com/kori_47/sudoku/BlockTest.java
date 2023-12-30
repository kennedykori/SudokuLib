/**
 * 
 */
package com.kori_47.sudoku;

/**
 * This is a test interface that implementors of the {@link Block} interface can use to test
 * and validate their implementations.
 * 
 * @author <a href="https://github.com/kennedykori">Kennedy Kori</a>
 *
 * @since Tue, 19 May 2020 13:22:44
 */
public interface BlockTest<T extends Block<Object>> extends UniqueCellGroupTest<T>, InterpolatableCellGroupTest<T> {

	@Override
	default void testCellGroupAccessors() {
		InterpolatableCellGroupTest.super.testCellGroupAccessors();
	}
}
