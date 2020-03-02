/**
 * 
 */
package com.kori_47.sudoku;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

/**
 * This class is used to define and run tests on the reference implementation
 * of {@link Cell}, i.e, the {@code Cell} instances returned by
 * {@link Cells#of(String, int, int, Symbol)} method and it's derivetives.
 * 
 * @author <a href="https://github.com/kennedykori">Kennedy Kori</a>
 *
 * @since Mon, 24 Feb 2020 16:24:46
 */
public class ReferenceCellImplementationTest implements CellTest {

	@Override
	public Cell<Object> createEqualValue() {
		return Cells.of("1/1", 1, 1);
	}

	@Override
	public Cell<Object> createAnotherEqualValue() {
		return Cells.of("1/1", 1, 1);
	}

	@Override
	public Cell<Object> createNonEqualValue() {
		return Cells.of("1/0", 1, 0);
	}

	@Override
	public Cell<Object> createValue() {
		return Cells.of("1/1", 1, 1);
	}

	@Override
	public Cell<Object> createSmallerValue() {
		return Cells.of("0/0", 0, 0);
	}

	@Override
	public Cell<Object> createLargerValue() {
		return Cells.of("2/2", 2, 2);
	}
	
	@Test
	@Override
	public void testDeapEquals() {
		CellTest.super.testDeapEquals();
		
		// add more test
		Cell<Integer> cell = Cells.of("0/0", 0, 0, Symbols.emptyNumberSymbol());
		Cell<Integer> cell1 = Cells.of("0/0", 0, 0, Symbols.of(Integer.valueOf(10), Integer.valueOf(10)));
		Cell<Integer> cell2 = Cells.of("1/1", 1, 1);
		
		assertFalse(cell.deepEquals(cell1));
		assertFalse(cell.deepEquals(cell2));
		assertFalse(cell2.deepEquals(cell));
		// a Cell is not a Symbol
		assertFalse(cell.deepEquals(Symbols.emptyLetterSymbol()));
	}
	
	
	@Test
	public void testToString() {
		Cell<Integer> cell = Cells.of("0/0", 0, 0, Symbols.emptyNumberSymbol());
		Cell<Character> cell1 = Cells.of("1/1", 1, 1, Symbols.emptyLetterSymbol());
		
		assertEquals(Cells.toString(cell), cell.toString());
		assertEquals(Cells.toString(cell1), cell1.toString());
	}
}
