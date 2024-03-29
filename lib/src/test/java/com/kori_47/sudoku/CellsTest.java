/**
 * 
 */
package com.kori_47.sudoku;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Comparator;

import org.junit.jupiter.api.Test;

/**
 * This class defines the tests for the methods and components found on the
 * {@link Cells} utility class.
 * 
 * @author <a href="https://github.com/kennedykori">Kennedy Kori</a>
 *
 * @since Mon, 24 Feb 2020 19:51:38
 */
public class CellsTest {
	
	/**
	 * Test {@link Cells#of(String, int, int, Symbol)} and it's derivertives.
	 */
	@Test
	public void testOf() {
		Cell<Integer> cell1 = Cells.of("0/0", 0, 0, Symbols.of(Integer.valueOf(0), Integer.valueOf(0)));
		Cell<Character> cell2 = Cells.of("1/3", 1, 3);
		
		// assert that the Cells returned aren't null
		assertNotNull(cell1);
		assertNotNull(cell2);
		
		// assert that the Cells returned have the correct id
		assertEquals("0/0", cell1.id());
		assertEquals("1/3", cell2.id());
		
		// assert that the Cells returned have the correct x coordinates
		assertEquals(0, cell1.x());
		assertEquals(1, cell2.x());
		
		// assert that the Cells returned have the correct x coordinates
		assertEquals(0, cell1.y());
		assertEquals(3, cell2.y());
		
		// assert that the Cells returned have the correct values
		assertEquals(Symbols.of(Integer.valueOf(0), Integer.valueOf(0)), cell1.symbol().get());
		assertEquals(null, cell2.symbol().orElse(null));
		assertEquals(Symbols.of(Integer.valueOf(0), Character.valueOf(' ')),
				Cells.of("", 0, 0, Symbols.of(Integer.valueOf(0), Character.valueOf(' '))).symbol().get());
		assertEquals(null, Cells.of("", 0, 0, null).symbol().orElse(null));
		
		// assert that a NullPointerException is thrown if id is null
		assertThrows(NullPointerException.class, () -> Cells.of(null, 0, 0));
		assertThrows(NullPointerException.class, () -> Cells.of(null, 0, 0, null));
		
		// assert that an IllegalArgumentException is thrown if x or y is negative
		assertThrows(IllegalArgumentException.class, () -> Cells.of("", -1, 0));
		assertThrows(IllegalArgumentException.class, () -> Cells.of("", 0, -30));
		assertThrows(IllegalArgumentException.class, () -> Cells.of("", -8, 0, null));
		assertThrows(IllegalArgumentException.class, () -> Cells.of("", 0, -45, null));
	}

	/**
	 * Test {@link Cells#defaultCellFactory()} static factory method.
	 */
	@Test
	public void testDefaultCellFactory() {
		CellFactory<Integer> cellFactory = Cells.defaultCellFactory();
		
		// assert that the CellFactory returned isn't null
		assertNotNull(cellFactory);
		
		Cell<Integer> cell1 = cellFactory.createCell("0/0", 0, 0, Symbols.of(Integer.valueOf(0), Integer.valueOf(0)));
		Cell<Integer> cell2 = cellFactory.createCell("0/0", 0, 0, null);
		
		// assert that the Cells returned aren't null
		assertNotNull(cell1);
		assertNotNull(cell2);
	}
	
	/**
	 * Test {@link Cells#defaultComparator()} static utility method.
	 */
	@Test
	public void testDefaultComparator() {
		Comparator<Cell<?>> comparator = Cells.defaultComparator();
		
		// assert that the Comparator returned isn't null
		assertNotNull(comparator);
		
		// create Cells for testing the Comparator
		Cell<Integer> cell1 = Cells.of("1", 2, 3);
		Cell<Integer> cell2 = Cells.of("2", 3, 2);
		Cell<Integer> cell3 = Cells.of("3", 3, 3);
		Cell<Integer> cell4 = Cells.of("4", 2, 0);
		Cell<Integer> cell5 = Cells.of("5", 2, 0);
		Cell<Integer> cell6 = Cells.of("5", 2, 0);
		
		// assert the Comparator returns the correct values
		assertTrue(comparator.compare(cell1, cell2) > 0);
		assertTrue(comparator.compare(cell1, cell3) < 0);
		assertTrue(comparator.compare(cell2, cell3) < 0);
		assertTrue(comparator.compare(cell3, cell4) > 0);
		assertTrue(comparator.compare(cell5, cell4) > 0);
		assertTrue(comparator.compare(cell6, cell6) == 0);
		assertTrue(comparator.compare(cell5, cell6) == 0);
	}
	
	/**
	 * Test {@link Cells#hashCode(Cell)} static utility method.
	 */
	@Test
	public void testHashCode() {
		// create Cells for testing the hash code
		Cell<Integer> cell1 = Cells.of("1", 2, 3);
		Cell<Character> cell2 = Cells.of("2", 3, 2);
		Cell<Character> cell3 = Cells.of("2", 3, 2, Symbols.of(Integer.valueOf(0), Character.valueOf(' ')));
		
		// assert that multiple calls of the method with the same Cell returns
		// the same value.
		int hash1 = cell1.hashCode(), hash2 = cell2.hashCode();
		assertEquals(hash1, cell1.hashCode());
		assertEquals(hash2, cell2.hashCode());
		assertEquals(hash2, cell2.hashCode());
		assertEquals(hash1, cell1.hashCode());
		assertEquals(hash2, cell2.hashCode());
		
		// assert that equal Cells have equal hash codes
		assertEquals(cell2, cell3);  // first assert that the two Cells are equal
		assertEquals(cell2.hashCode(), cell3.hashCode());  // assert that the two Cells have the same hash code
		
		// assert that even after modification of a Cell, it still has the same hash code
		// as per the specification of the Cell#hashCode() method.
		cell1.changeSymbol(Symbols.of(Integer.valueOf(0), Integer.valueOf(0)));  // assign a value to cell1
		cell2.changeSymbol(Symbols.of(Integer.valueOf(1), Character.valueOf('A'))); // assign a value to cell2
		assertEquals(hash1, cell1.hashCode());
		assertEquals(hash2, cell2.hashCode());
		
		// assert that the method doesn't accept null values
		assertThrows(NullPointerException.class, () -> Cells.hashCode(null));
	}
	
	/**
	 * Test {@link Cells#equals(Cell, Object)} static utility method.
	 */
	@Test
	public void testEquals() {
		// create Cells for testing for equality
		Cell<Integer> cell1 = Cells.of("1", 2, 3);
		Cell<Character> cell2 = Cells.of("2", 3, 2);
		Cell<Character> cell3 = Cells.of("2", 3, 2, Symbols.of(Integer.valueOf(0), Character.valueOf(' ')));
		
		// assert that the Cell method works as intended
		assertFalse(Cells.equals(cell1, cell2)); // cell1 and cell2 aren't equal
		assertTrue(Cells.equals(cell1, cell1));  // a Cell is equal to itself
		assertTrue(Cells.equals(cell2, cell2));  // a Cell is equal to itself
		assertTrue(Cells.equals(cell2, cell3));  // cell2 and cell3 should be equal
		assertFalse(Cells.equals(cell2, Symbols.of(Integer.valueOf(0), Character.valueOf(' '))));  // comparison to a non Cell should be false
		assertFalse(Cells.equals(cell1, null));  // comparison to null should always return false
		
		// assert that even after modification of a Cell, it's equality results isn't affected
		cell2.changeSymbol(Symbols.of(Integer.valueOf(1), Character.valueOf('A'))); // assign a value to cell2
		assertTrue(Cells.equals(cell2, cell3));  // cell2 and cell3 should still be equal
		
		// assert that the method throws a NullPointerException when the 1st parameter is null
		assertThrows(NullPointerException.class, () -> Cells.equals(null, cell2));
	}
	
	/**
	 * Test {@link Cells#deepEquals(Cell, Object)} static utility method.
	 */
	@Test
	public void testDeepEquals() {
		// create Cells for testing for equality
		Cell<Integer> cell1 = Cells.of("1", 2, 3);
		Cell<Character> cell2 = Cells.of("2", 3, 2);
		Cell<Character> cell3 = Cells.of("2", 3, 2, Symbols.of(Integer.valueOf(0), Character.valueOf(' ')));
		Cell<Character> cell4 = Cells.of("2", 3, 2, Symbols.of(Integer.valueOf(1), Character.valueOf('A')));
		Cell<Character> cell5 = Cells.of("2", 3, 2, Symbols.of(Integer.valueOf(0), Character.valueOf(' ')));
		
		// assert that the Cell method works as intended
		assertFalse(Cells.deepEquals(cell1, cell2)); // cell1 and cell2 aren't equal
		assertTrue(Cells.deepEquals(cell1, cell1));  // a Cell is equal to itself
		assertTrue(Cells.deepEquals(cell2, cell2));  // a Cell is equal to itself
		assertTrue(Cells.deepEquals(cell3, cell5));  // cell3 and cell5 should be equal
		assertFalse(Cells.deepEquals(cell2, cell3));  // cell2 and cell3 should not be equal
		assertFalse(Cells.deepEquals(cell2, cell4));  // cell2 and cell4 should not be equal
		assertFalse(Cells.deepEquals(cell3, cell4));  // cell3 and cell4 should not be equal, they have different Symbols
		assertFalse(Cells.deepEquals(cell2, Symbols.of(Integer.valueOf(0), Character.valueOf(' '))));  // comparison to a non Cell should be false
		assertFalse(Cells.deepEquals(cell1, null));  // comparison to null should always return false
		
		// assert that after modification of a Cell, it's equality results is affected
		cell2.changeSymbol(Symbols.of(Integer.valueOf(1), Character.valueOf('A'))); // assign a value to cell2
		assertTrue(Cells.deepEquals(cell2, cell4));  // cell2 and cell4 should now be equal
		
		// assert that the method throws a NullPointerException when the 1st parameter is null
		assertThrows(NullPointerException.class, () -> Cells.equals(null, cell2));
	}
	
	/**
	 * Test {@link Cells#toString(Cell, String)} and it's derivertives.
	 */
	@Test
	public void testToString() {
		// create Cells for testing the toString method
		Cell<Integer> cell1 = Cells.of("1", 2, 3);
		Cell<Character> cell2 = Cells.of("2", 3, 2);
		Cell<Integer> cell3 = Cells.of("2", 3, 2, Symbols.of(Integer.valueOf(0), Integer.valueOf(0)));
		
		// assert the methods return the expected values
		assertEquals("Cell{id=1, coord=(x:2, y:3), value=[-]}", Cells.toString(cell1));
		assertEquals("Cell{id=1, coord=(x:2, y:3), value=[-]}", Cells.toString(cell1, null));
		assertEquals("Cell{id=1, coord=(x:2, y:3), value=[*]}", Cells.toString(cell1, "*"));
		assertEquals("Cell{id=2, coord=(x:3, y:2), value=[-]}", Cells.toString(cell2));
		assertEquals("Cell{id=2, coord=(x:3, y:2), value=[Symbol{id=0, value=0}]}", Cells.toString(cell3));
	}
	
	/**
	 * Test {@link Cells#swapCellSymbols(Cell, Cell)} static utility method.
	 */
	@Test
	public void testSwapCellSymbols() {
		// create Cells for testing the swapCellProperties method
		Cell<Integer> cell1 = Cells.of("1", 2, 3);
		Cell<Character> cell2 = Cells.of("2", 3, 2);
		Cell<Integer> cell3 = Cells.of("2", 3, 2, Symbols.of(Integer.valueOf(0), Integer.valueOf(0)));
		Cell<Character> cell4 = Cells.of("2", 3, 2, Symbols.of(Integer.valueOf(1), Character.valueOf('A')));
		Cell<Character> cell5 = Cells.of("1/0", 1, 0, Symbols.of(Integer.valueOf(9), Character.valueOf('I')));
		Cell<Character> cell6 = Cells.of("2/1", 2, 1, Symbols.of(Integer.valueOf(5), Character.valueOf('E')));
		// Cell copies
		Cell<Integer> cell1Copy = Cells.of(cell1.id(), cell1.x(), cell1.y(), cell1.symbol().orElse(null));
		Cell<Character> cell2Copy = Cells.of(cell2.id(), cell2.x(), cell2.y(), cell2.symbol().orElse(null));
		Cell<Integer> cell3Copy = Cells.of(cell3.id(), cell3.x(), cell3.y(), cell3.symbol().orElse(null));
		Cell<Character> cell4Copy = Cells.of(cell4.id(), cell4.x(), cell4.y(), cell4.symbol().orElse(null));
		Cell<Character> cell5Copy = Cells.of(cell5.id(), cell5.x(), cell5.y(), cell5.symbol().orElse(null));
		Cell<Character> cell6Copy = Cells.of(cell6.id(), cell6.x(), cell6.y(), cell6.symbol().orElse(null));
		
		// swap the cell pairs
		Cells.swapCellSymbols(cell1, cell3);
		Cells.swapCellSymbols(cell2, cell4);
		Cells.swapCellSymbols(cell5, cell6);
		
		// check that the swapping worked as expected
		assertEquals(cell1.symbol().orElse(null), cell3Copy.symbol().orElse(null)); // cell1's symbol should now be equal to what cell3's symbol was, i.e cell3Copy symbol
		assertEquals(cell3.symbol().orElse(null), cell1Copy.symbol().orElse(null)); // cell3's symbol should now be equal to what cell1's symbol was, i.e cell1Copy symbol
		assertEquals(cell2.symbol().orElse(null), cell4Copy.symbol().orElse(null)); // cell2's symbol should now be equal to what cell4's symbol was, i.e cell4Copy symbol
		assertEquals(cell4.symbol().orElse(null), cell2Copy.symbol().orElse(null)); // cell4's symbol should now be equal to what cell2's symbol was, i.e cell2Copy symbol
		assertEquals(cell5.symbol().orElse(null), cell6Copy.symbol().orElse(null)); // cell5's symbol should now be equal to what cell6's symbol was, i.e cell6Copy symbol
		assertEquals(cell6.symbol().orElse(null), cell5Copy.symbol().orElse(null)); // cell6's symbol should now be equal to what cell5's symbol was, i.e cell5Copy symbol
	}
}
