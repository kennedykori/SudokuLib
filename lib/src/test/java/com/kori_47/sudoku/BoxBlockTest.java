/**
 * 
 */
package com.kori_47.sudoku;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * This is a test interface that implementors of the {@link BoxBlock} interface can use to test
 * and validate their implementations.
 * 
 * @author <a href="https://github.com/kennedykori">Kennedy Kori</a>
 *
 * @since Tue, 19 May 2020 13:47:06
 */
public interface BoxBlockTest extends BlockTest<BoxBlock<Object>> {

	@Test
	@Override
	default void testCellGroupAccessors() {
		BlockTest.super.testCellGroupAccessors();

		BoxBlock<Object> value = createValue();

		// assert that accessors return values in a valid range
		assertTrue(value.blockColumns() > 0);
		assertTrue(value.blockRows() > 0);
		assertEquals((value.blockColumns() * value.blockRows()), value.size());
	}

	@Test
	void testBlockRows();

	@Test
	void testBlockColumns();
}
