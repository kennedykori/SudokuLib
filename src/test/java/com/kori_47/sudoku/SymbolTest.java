/**
 * 
 */
package com.kori_47.sudoku;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

/**
 * This is a test interface that implementors of the {@link Symbol} interface can use
 * to test and validate their implementations.
 * 
 * @author <a href="https://github.com/kennedykori">Kennedy Kori</a>
 *
 * @since Sat, 22 Feb 2020 19:49:58
 */
public interface SymbolTest extends TestEquals<Symbol<Object>>, TestComparable<Symbol<Object>> {
	
	/**
	 * Test that {@link Symbol} accessor methods do not return {@code null}.
	 */
	@Test
	default void testThatSymbolAccessorsDoNotReturnNull() {
		Symbol<?> symbol = createValue();
		
		assertNotNull(symbol.id());
		assertNotNull(symbol.value());
	}

	/**
	 * Test that equal {@link Symbol}s have equal hash code values.
	 */
	@Test
	default void testThatEqualSymbolsHaveEqualHashCodes() {
		Symbol<?> symbol = createValue();
		Symbol<?> equalSymbol = createEqualValue();
		
		assertEquals(symbol.hashCode(), equalSymbol.hashCode());
		assertEquals(equalSymbol.hashCode(), symbol.hashCode());
	}
}
