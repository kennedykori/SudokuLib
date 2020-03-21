/**
 * 
 */
package com.kori_47.sudoku;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * This is a test interface that implementors of the {@link UniqueCellGroup} interface can use to test
 * and validate their implementations.
 * 
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
	}

	/**
	 * Returns a smaller value of the {@code Testable} entity. That is, returns
	 * a value smaller than the value returned by the {@link #createValue()}
	 * method.
	 * 
	 * @return a smaller value of the {@code Testable} entity.
	 */
	T createSmallerValue();
	
	/**
	 * Returns an equal instance of the {@code Testable} entity. That is, returns
	 * a value equal to the value returned by the {@link #createValue()} method.
	 * 
	 * @return an equal instance of the {@code Testable} entity.
	 */
	T createEqualValue();
	
	/**
	 * Returns a larger value of the {@code Testable} entity. That is, returns
	 * a value larger than the value returned by the {@link #createValue()}
	 * method.
	 * 
	 * @return a larger value of the {@code Testable} entity.
	 */
	T createLargerValue();
	
	/**
	 * Tests that a {@code 0} is returned when comparing two equal values.
	 */
	@Test
	default void testReturnsZeroWhenComparingEqualValues() {
		T value = createValue();
		T equalValue = createEqualValue();
		
		assertEquals(0, equalValue.compareTo(value));
		assertEquals(0, value.compareTo(equalValue));
		assertEquals(0, value.compareTo(value));
	}

	/**
	 * Tests that a positive {@code Integer} is returned when comparing larger values to
	 * smaller values.
	 */
	@Test
	default void testReturnsPositiveIntegerWhenComparingToSmallerValues() {
		T value = createValue();
		T smallerValue = createSmallerValue();
		T largerValue = createLargerValue();
		
		assertTrue(value.compareTo(smallerValue) > 0);
		assertTrue(largerValue.compareTo(value) > 0);
		assertTrue(largerValue.compareTo(smallerValue) > 0);
	}
	
	/**
	 * Tests that a negative {@code Integer} is returned when comparing smaller values to
	 * larger values.
	 */
	@Test
	default void testReturnsNegativeIntegerWhenComparingToLargerValues() {
		T value = createValue();
		T smallerValue = createSmallerValue();
		T largerValue = createLargerValue();
		
		assertTrue(smallerValue.compareTo(value) < 0);
		assertTrue(value.compareTo(largerValue) < 0);
		assertTrue(smallerValue.compareTo(largerValue) < 0);
	}
	
	/**
	 * Test that for any non-null {@code Testable} values {@code x}, {@code y} and {@code z},
	 * if {@code x.compareTo(y)>0 && y.compareTo(z)>0}, then {@code x.compareTo(z)>0}.
	 */
	@Test
	default void testForTransitivityDuringComparisions() {
		T value = createValue();
		T smallerValue = createSmallerValue();
		T largerValue = createLargerValue();
		
		assertTrue(largerValue.compareTo(value) > 0
				&& value.compareTo(smallerValue) > 0
				&& largerValue.compareTo(smallerValue) > 0);
		assertTrue(smallerValue.compareTo(value) < 0
				&& value.compareTo(largerValue) < 0
				&& smallerValue.compareTo(largerValue) < 0);
	}
	
	/**
	 * Test that for any non-null {@code Testable} values {@code x}, {@code y} and {@code z},
	 * {@code x.compareTo(y)==0} implies that {@code sgn(x.compareTo(z)) == sgn(y.compareTo(z))}
	 * for all {@code z}.
	 */
	@Test
	default void testForConsistencyDuringComparisions() {
		T value = createValue();
		T equalValue = createEqualValue();
		T smallerValue = createSmallerValue();
		T largerValue = createLargerValue();
		
		assertTrue(value.compareTo(equalValue) == 0
				&& value.compareTo(smallerValue) > 0
				&& equalValue.compareTo(smallerValue) > 0);
		
		assertTrue(value.compareTo(equalValue) == 0
				&& value.compareTo(largerValue) < 0
				&& equalValue.compareTo(largerValue) < 0);
	}
}
