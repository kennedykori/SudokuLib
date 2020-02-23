/**
 * 
 */
package com.kori_47.sudoku;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * This interface add tests of an object's {@link Object#hashCode() hashCode}
 * method implementation.
 * 
 * @author <a href="https://github.com/kennedykori">Kennedy Kori</a>
 *
 * @since Sun, 23 Feb 2020 18:07:44
 */
public interface TestHashCode<T> extends TestEquals<T> {
	
	/**
	 * Test that equal {@code Testable} instances have the same hash code value. 
	 */
	@Test
	default void testEqualValuesHaveSameHashCode() {
		T value = createValue();
		T equalValue = createEqualValue();
		T anotherEqualValue = createAnotherEqualValue();
		int hashCode = value.hashCode();
		
		assertEquals(hashCode, equalValue.hashCode());
		assertEquals(hashCode, anotherEqualValue.hashCode());
	}
	
	/**
	 * Test that multiple calls of the {@code hashCode} method returns the same
	 * value as long as no information used on the equals comparisons has been
	 * modified.
	 */
	@Test
	default void testForHashCodeConsistency() {
		T value = createValue();
		T value1 = createEqualValue();
		T value2 = createAnotherEqualValue();
		
		// orginal hash code values
		int valueHashCode = value.hashCode();
		int value1HashCode = value1.hashCode();
		int value2HashCode = value2.hashCode();
		
		assertEquals(valueHashCode, value.hashCode());
		assertEquals(value1HashCode, value1.hashCode());
		assertEquals(value2HashCode, value2.hashCode());
		
		assertEquals(valueHashCode, value.hashCode());
		assertEquals(value1HashCode, value1.hashCode());
		assertEquals(value2HashCode, value2.hashCode());
	}
}
