/**
 * 
 */
package com.kori_47.sudoku;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

/**
 * @author <a href="https://github.com/kennedykori">Kennedy Kori</a>
 *
 * @since Sun, 22 Mar 2020 21:38:36
 */
public interface UniqueCellGroupTest<T extends UniqueCellGroup<Object>> extends CellGroupTest<T> {
	
	@Test
	@Override
	default void testCellGroupAccessors() {
		CellGroupTest.super.testCellGroupAccessors();
		T cellGroup = createValue();
		T cellGroup1 = createEqualValue();
		T cellGroup2 = createNonEqualValue();
		
		// assert that the returned id isn't null
		assertNotNull(cellGroup.id());
		assertNotNull(cellGroup1.id());
		assertNotNull(cellGroup2.id());
		
		// assert that equal CellGroups have the same id
		assertEquals(cellGroup.id(), cellGroup1.id());
		
		// assert that non equal CellGroups don't have the same id
		assertNotEquals(cellGroup.id(), cellGroup2.id());
	}
}
