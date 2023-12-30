/**
 * 
 */
package com.kori_47.sudoku;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Represents an entity that can be tested for equality using the {@link Object#equals(Object)
 * equals(Object)} method.
 * 
 * @param <T> the type of entity to be tested.
 * 
 * @author <a href="https://github.com/kennedykori">Kennedy Kori</a>
 *
 * @since Wed, 19 Feb 2020 20:43:55
 * 
 * @see Testable
 * @see Object#equals(Object)
 */
public interface TestEquals<T> extends Testable<T> {

	/**
	 * Returns an equal instance of the {@code Testable} entity. That is, returns
	 * a value equal to the value returned by the {@link #createValue()} method.
	 * 
	 * @return an equal instance of the {@code Testable} entity.
	 */
	T createEqualValue();
	
	/**
	 * Returns another equal instance of the {@code Testable} entity. That is, returns
	 * a value equal to the values returned by the {@link #createValue()} and
	 * {@link #createEqualValue()} methods.
	 * 
	 * @return an equal instance of the {@code Testable} entity.
	 */
	T createAnotherEqualValue();
	
	/**
	 * Returns a non equal instance of the {@code Testable} entity. That is, returns
	 * a value not equal to the value returned by the {@link #createValue()} method.
	 * 
	 * @return a non equal instance of the testable entity.
	 */
	T createNonEqualValue();

	/**
	 * Test that a non-null {@code Testable} value is equal to itself.
	 */
	@Test
	default void testForReflexivity() {
		T value1 = createValue();
		T value2 = createNonEqualValue();
		
		assertEquals(value1, value1);
		assertEquals(value2, value2);
	}

	/**
	 * Test that for any non-null {@code Testable} values {@code x} and {@code y},
	 * {@code x.equals(y)} should return {@code true} if and only if {@code y.equals(x)}
	 * returns {@code true}.
	 */
	@Test
	default void testForSymmetry() {
		T x = createValue();
		T y = createEqualValue();
		
		assertTrue(x.equals(y));
		assertTrue(y.equals(x));
	}
	
	/**
	 * Test that for any non-null {@code Testable} values {@code x}, {@code y} and {@code z},
	 * if {@code x.equals(y)} returns {@code true} and {@code y.equals(z)} returns true, then
	 * {@code x.equals(z)} should also return {@code true}.
	 */
	@Test
	default void testForTransitivity() {
		T x = createValue();
		T y = createEqualValue();
		T z = createAnotherEqualValue();
		
		assertTrue(x.equals(y));
		assertTrue(y.equals(z));
		assertTrue(x.equals(z));
	}

	/**
	 * Test that for any non-null {@code Testable} values {@code x} and {@code y},
	 * multiple invocations of {@code x.equals(y)} should consistently return
	 * {@code true} or consistently return {@code false}, provided no information
	 * used in equals comparisons on the objects is modified.
	 */
	@Test
	default void testForConsistency() {
		T x = createValue();
		T y = createEqualValue();
		T nonEqualValue = createNonEqualValue();
		
		// test that multiple calls of equals on equal values returns true
		assertEquals(x, y);
		assertEquals(x, y);
		assertEquals(y, x);
		assertEquals(y, x);
		
		// test that multiple calls of equals on non-equal values returns false
		assertNotEquals(x, nonEqualValue);
		assertNotEquals(x, nonEqualValue);
		assertNotEquals(y, nonEqualValue);
		assertNotEquals(y, nonEqualValue);
	}
	
	/**
	 * Test that a {@code Testable} value is not equal to {@code null}.
	 */
	@Test
	default void testValueDoesNotEqualNull() {
		T value = createValue();
		
		assertFalse(value.equals(null));
	}

	/**
	 * Test that a {@code Testable} value is not equal to a different value.
	 */
	@Test
	default void testValueDoesNotEqualDifferentValue() {
		T value = createValue();
		T differentValue = createNonEqualValue();
		
		assertNotEquals(value, differentValue);
		assertNotEquals(differentValue, value);
	}
}
