/**
 * 
 */
package com.kori_47.sudoku;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;

/**
 * This class defines the tests for the methods and components found on the
 * {@link Symbols} utility class.
 * 
 * @author <a href="https://github.com/kennedykori">Kennedy Kori</a>
 *
 * @since Sat, 22 Feb 2020 23:17:48
 */
public class SymbolsTest {

	/**
	 * Tests the {@link Symbols#of(Integer, Object)} static factory method.
	 */
	@Test
	public void testOf() {
		Symbol<Integer> numberSymbol = Symbols.of(Integer.valueOf(1), Integer.valueOf(1));
		Symbol<Character> charSymbol = Symbols.of(Integer.valueOf(3), Character.valueOf('C'));
		
		// assert that the Symbols returned by the method aren't null.
		assertNotNull(numberSymbol);
		assertNotNull(charSymbol);
		
		// assert that the Symbols returned have being assigned the correct id
		assertEquals(Integer.valueOf(1), numberSymbol.id());
		assertEquals(Integer.valueOf(3), charSymbol.id());
		
		// assert that the Symbols returned have being assigned the correct value
		assertEquals(Integer.valueOf(1), numberSymbol.value());
		assertEquals(Character.valueOf('C'), charSymbol.value());
		
		// assert that a NullPointerException is thrown incase null values are passed to the method
		assertThrows(NullPointerException.class, () -> Symbols.of(null, null));
		assertThrows(NullPointerException.class, () -> Symbols.of(null, Integer.valueOf(0)));
		assertThrows(NullPointerException.class, () -> Symbols.of(Integer.valueOf(0), null));
	}
	
	/**
	 * Tests the {@link Symbols#emptyNumberSymbol()} static utility method.
	 */
	@Test
	public void testEmptyNumberSymbol() {
		Symbol<Integer> emptyNumberSymbol = Symbols.emptyNumberSymbol();
		
		// assert that the method doesn't return a null value
		assertNotNull(emptyNumberSymbol);
		
		// assert that the Symbol returned has being assigned the correct id and value
		assertEquals(Integer.valueOf(0), emptyNumberSymbol.id());
		assertEquals(Integer.valueOf(0), emptyNumberSymbol.value());
	}
	
	/**
	 * Tests the {@link Symbols#emptyLetterSymbol()} static utility method.
	 */
	@Test
	public void testEmptyLetterSymbol() {
		Symbol<Character> emptyLetterSymbol = Symbols.emptyLetterSymbol();
		
		// assert that the method doesn't return a null value
		assertNotNull(emptyLetterSymbol);
		
		// assert that the Symbol returned has being assigned the correct id and value
		assertEquals(Integer.valueOf(0), emptyLetterSymbol.id());
		assertEquals(Character.valueOf(' '), emptyLetterSymbol.value());
	}
	
	/**
	 * Tests the {@link Symbols#numberSymbolsUpTo(int)} static utility method.
	 */
	@Test
	public void testNumberSymbolsUpTo() {
	 	Set<Symbol<Integer>> sixNumberSymbols = Symbols.numberSymbolsUpTo(7);
	 	Set<Symbol<Integer>> tenNumberSymbols = Symbols.numberSymbolsUpTo(11);
	 	
	 	// assert that the method doesn't return a null value
	 	assertNotNull(sixNumberSymbols);
	 	assertNotNull(tenNumberSymbols);
	 	
	 	// assert that the method returns the correct number of Symbols
	 	assertEquals(6, sixNumberSymbols.size());
	 	assertEquals(10, tenNumberSymbols.size());
	 	
	 	// assert that the returned Set has the correct values
	 	assertTrue(sixNumberSymbols.contains(Symbols.of(Integer.valueOf(1), Integer.valueOf(1))));
	 	assertTrue(sixNumberSymbols.contains(Symbols.of(Integer.valueOf(5), Integer.valueOf(5))));
	 	assertTrue(tenNumberSymbols.contains(Symbols.of(Integer.valueOf(3), Integer.valueOf(3))));
	 	assertTrue(tenNumberSymbols.contains(Symbols.of(Integer.valueOf(8), Integer.valueOf(8))));
	 	assertTrue(tenNumberSymbols.contains(Symbols.of(Integer.valueOf(10), Integer.valueOf(10))));
	 	assertFalse(sixNumberSymbols.contains(Symbols.of(Integer.valueOf(7), Integer.valueOf(7))));
	 	assertFalse(tenNumberSymbols.contains(Symbols.of(Integer.valueOf(26), Integer.valueOf(26))));
	 	
	 	// assert that an IllegalArgumentException is thrown if upTo < 1
	 	assertThrows(IllegalArgumentException.class, () -> Symbols.numberSymbolsUpTo(0));
	 	assertThrows(IllegalArgumentException.class, () -> Symbols.numberSymbolsUpTo(-2));
	}
	
	/**
	 * Tests the {@link Symbols#letterSymbolsUpTo(int)} static utility method.
	 */
	@Test
	public void testLetterSymbolsUpTo() {
	 	Set<Symbol<Character>> sixLetterSymbols = Symbols.letterSymbolsUpTo(7);
	 	Set<Symbol<Character>> tenLetterSymbols = Symbols.letterSymbolsUpTo(11);
	 	
	 	// assert that the method doesn't return a null value
	 	assertNotNull(sixLetterSymbols);
	 	assertNotNull(tenLetterSymbols);
	 	
	 	// assert that the method returns the correct number of Symbols
	 	assertEquals(6, sixLetterSymbols.size());
	 	assertEquals(10, tenLetterSymbols.size());
	 	
	 	// assert that the returned Set has the correct values
	 	assertTrue(sixLetterSymbols.contains(Symbols.of(Integer.valueOf(1), Character.valueOf('A'))));
	 	assertTrue(sixLetterSymbols.contains(Symbols.of(Integer.valueOf(5), Character.valueOf('E'))));
	 	assertTrue(tenLetterSymbols.contains(Symbols.of(Integer.valueOf(3), Character.valueOf('C'))));
	 	assertTrue(tenLetterSymbols.contains(Symbols.of(Integer.valueOf(8), Character.valueOf('H'))));
	 	assertTrue(tenLetterSymbols.contains(Symbols.of(Integer.valueOf(10), Character.valueOf('J'))));
	 	assertFalse(sixLetterSymbols.contains(Symbols.of(Integer.valueOf(7), Character.valueOf('G'))));
	 	assertFalse(tenLetterSymbols.contains(Symbols.of(Integer.valueOf(26), Character.valueOf('Z'))));
	 	
	 	// assert that an IllegalArgumentException is thrown if upTo < 1
	 	assertThrows(IllegalArgumentException.class, () -> Symbols.letterSymbolsUpTo(0));
	 	assertThrows(IllegalArgumentException.class, () -> Symbols.letterSymbolsUpTo(-2));
	 	
	 	// assert that an IllegalArgumentException is thrown if upTo > 27
		assertThrows(IllegalArgumentException.class, () -> Symbols.letterSymbolsUpTo(28));
		assertThrows(IllegalArgumentException.class, () -> Symbols.letterSymbolsUpTo(200));
	}
}
