/**
 * 
 */
package com.kori_47.sudoku;

import org.junit.jupiter.api.Test;

/**
 * This is a test interface that implementors of the {@link Formattable} interface can use to test
 * and validate their implementations.
 * 
 * @author <a href="https://github.com/kennedykori">Kennedy Kori</a>
 *
 * @since Sun, 23 Feb 2020 17:50:01
 */
public interface FormattableTest<T extends Formattable> extends Testable<T> {
	
	/**
	 * Tests the {@link Formattable#toXY()} method.
	 */
	@Test
	void testToXY();
	
	/**
	 * Tests the {@link Formattable#toXYV()} method.
	 */
	@Test
	void testToXYV();
	
	/**
	 * Tests the {@link Formattable#toXYI()} method.
	 */
	@Test
	void testToXYI();
	
	/**
	 * Tests the {@link Formattable#toV()} method.
	 */
	@Test
	void testToV();
	
	/**
	 * Tests the {@link Formattable#toI()} method.
	 */
	@Test
	void testToI();
}
