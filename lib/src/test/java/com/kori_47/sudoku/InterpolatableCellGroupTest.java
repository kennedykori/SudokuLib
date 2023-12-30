/**
 * 
 */
package com.kori_47.sudoku;

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
		assertNotNull(cellGroup1.startCell());
		assertNotNull(cellGroup1.endCell());
		assertNotNull(cellGroup2.startCell());
		assertNotNull(cellGroup2.endCell());
	}

	@Test
	void testStartCell();

	@Test
	void testEndCell();
}
