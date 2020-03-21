/**
 * 
 */
package com.kori_47.sudoku;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * This is a test interface that implementors of the {@link Column} interface can use to test
 * and validate their implementations.
 * 
 * @author <a href="https://github.com/kennedykori">Kennedy Kori</a>
 *
 * @since Mon, 23 Mar 2020 12:33:15
 */
public interface ColumnTest extends UniqueCellGroupTest<Column<Object>>, InterpolatableCellGroupTest<Column<Object>> {
	
	@Test
	@Override
	default void testCellGroupAccessors() {
		UniqueCellGroupTest.super.testCellGroupAccessors();
		InterpolatableCellGroupTest.super.testCellGroupAccessors();

		Column<Object> column = createValue();

		// assert that the index of this Column is a positive number but less than size
		assertTrue(column.x() >= 0);
		assertTrue(column.x() < column.size());
	}
}
