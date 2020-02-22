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
 * @param <V> the type of value supported by the {@code Symbol} implementation being tested.
 * 
 * @author <a href="https://github.com/kennedykori">Kennedy Kori</a>
 *
 * @since Sat, 22 Feb 2020 19:49:58
 */
public interface SymbolTests<V> extends TestEquals<Symbol<V>>, TestComparable<Symbol<V>> {
	
	/**
	 * Test that {@link Symbol} accessor methods do not return {@code null}.
	 */
	@Test
	default void testThatSymbolAccessorsDoNotReturnNull() {
		Symbol<V> symbol = createValue();
		
		assertNotNull(symbol.id());
		assertNotNull(symbol.value());
	}

	/**
	 * Test that equal {@link Symbol}s have equal hash code values.
	 */
	@Test
	default void testThatEqualSymbolsHaveEqualHashCodes() {
		Symbol<V> symbol = createValue();
		Symbol<V> equalSymbol = createEqualValue();
		
		assertEquals(symbol.hashCode(), equalSymbol.hashCode());
		assertEquals(equalSymbol.hashCode(), symbol.hashCode());
	}
}
