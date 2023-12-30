/**
 * 
 */
package com.kori_47.sudoku;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * This class is used to define and run tests on the reference implementation
 * of {@link Symbol}, i.e, the {@code Symbol} instances returned by
 * {@link Symbols#of(Integer, Object)} method and it's derivetives.
 * 
 * @author <a href="https://github.com/kennedykori">Kennedy Kori</a>
 *
 * @since Sat, 22 Feb 2020 20:13:09
 */
public class ReferenceSymbolImplementationTest implements SymbolTest {

	@Override
	public Symbol<Object> createEqualValue() {
		return Symbols.of(Integer.valueOf(2), Integer.valueOf(2));
	}

	@Override
	public Symbol<Object> createAnotherEqualValue() {
		return Symbols.of(Integer.valueOf(2), Integer.valueOf(2));
	}

	@Override
	public Symbol<Object> createNonEqualValue() {
		return Symbols.of(Integer.valueOf(0), Integer.valueOf(0));
	}

	@Override
	public Symbol<Object> createValue() {
		return Symbols.of(Integer.valueOf(2), Integer.valueOf(2));
	}

	@Override
	public Symbol<Object> createSmallerValue() {
		return Symbols.of(Integer.valueOf(1), Integer.valueOf(1));
	}

	@Override
	public Symbol<Object> createLargerValue() {
		return Symbols.of(Integer.valueOf(3), Integer.valueOf(3));
	}
	
	@Test
	public void testToString() {
		Symbol<?> symbol = createValue();
		Symbol<?> symbol1 = createSmallerValue();
		Symbol<String> symbol2 = Symbols.of(Integer.valueOf(1), "One");
		
		assertEquals("Symbol{id=2, value=2}", symbol.toString());
		assertEquals("Symbol{id=1, value=1}", symbol1.toString());
		assertEquals("Symbol{id=1, value=One}", symbol2.toString());
	}
}
