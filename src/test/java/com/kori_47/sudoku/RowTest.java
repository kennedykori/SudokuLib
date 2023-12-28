/**
 * 
 */
package com.kori_47.sudoku;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * This is a test interface that implementors of the {@link Row} interface can use to test
 * and validate their implementations.
 * 
 * @author <a href="https://github.com/kennedykori">Kennedy Kori</a>
 *
 * @since Sun, 22 Mar 2020 22:14:52
 */
public interface RowTest extends UniqueCellGroupTest<Row<Object>>, InterpolatableCellGroupTest<Row<Object>> {
	
	@Test
	@Override
	default void testCellGroupAccessors() {
		UniqueCellGroupTest.super.testCellGroupAccessors();
		InterpolatableCellGroupTest.super.testCellGroupAccessors();

		Row<Object> row = createValue();

		// assert that the index of this Row is a positive number but less than size
		assertTrue(row.y() >= 0);
		assertTrue(row.y() < row.size());
	}
}
