/**
 * 
 */
package com.kori_47.sudoku;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

/**
 * This is a test interface that implementors of the {@link InterpolatableCellGroup} interface can use to test
 * and validate their implementations.
 * 
 * @author <a href="https://github.com/kennedykori">Kennedy Kori</a>
 *
 * @since Mon, 23 Mar 2020 13:25:41
 */
public interface InterpolatableCellGroupTest<T extends InterpolatableCellGroup<Object>> extends CellGroupTest<T> {
	
	@Test
	@Override
	default void testCellGroupAccessors() {
		CellGroupTest.super.testCellGroupAccessors();
		T cellGroup = createValue();
		T cellGroup1 = createEqualValue();
		T cellGroup2 = createNonEqualValue();
		
		// assert that the return values aren't null
		assertNotNull(cellGroup.startCell());
		assertNotNull(cellGroup.endCell());
		
		// assert that equal CellGroups have equal values
		assertEquals(cellGroup.startCell(), cellGroup1.startCell());
		assertEquals(cellGroup.endCell(), cellGroup1.endCell());
		
		// assert that non equal CellGroups don't have equal values
		assertNotEquals(cellGroup.startCell(), cellGroup2.startCell());
		assertNotEquals(cellGroup.endCell(), cellGroup2.endCell());
	}
}
