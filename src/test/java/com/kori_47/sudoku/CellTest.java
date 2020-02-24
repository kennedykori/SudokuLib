/**
 * 
 */
package com.kori_47.sudoku;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;

/**
 * This is a test interface that implementors of the {@link Cell} interface can use to test
 * and validate their implementations.
 * 
 * @author <a href="https://github.com/kennedykori">Kennedy Kori</a>
 *
 * @since Sun, 23 Feb 2020 17:45:34
 */
public interface CellTest extends TestHashCode<Cell<Object>>, ComparableTest<Cell<Object>>, FormattableTest<Cell<Object>> {
	
	/**
	 * {@inheritDoc}
	 * @implSpec
	 * The default implementation for this {@code cell} only checks that {@code cell.toI()}
	 * equals to {@link FormattableUtils#toI(Cell)}.
	 */
	@Test
	@Override
	default void testToI() {
		Cell<Object> cell = createValue();
		
		assertEquals(FormattableUtils.toI(cell), cell.toI());
	}
	
	/**
	 * {@inheritDoc}
	 * @implSpec
	 * The default implementation for this {@code cell} only checks that {@code cell.toV()}
	 * equals to {@link FormattableUtils#toV(Cell)}.
	 */
	@Test
	@Override
	default void testToV() {
		Cell<Object> cell = createValue();
		
		assertEquals(FormattableUtils.toV(cell), cell.toV());
	}
	
	/**
	 * {@inheritDoc}
	 * @implSpec
	 * The default implementation for this {@code cell} only checks that {@code cell.toXY()}
	 * equals to {@link FormattableUtils#toXY(Cell)}.
	 */
	@Test
	@Override
	default void testToXY() {
		Cell<Object> cell = createValue();
		
		assertEquals(FormattableUtils.toXY(cell), cell.toXY());
	}
	
	/**
	 * {@inheritDoc}
	 * @implSpec
	 * The default implementation for this {@code cell} only checks that {@code cell.toXYI()}
	 * equals to {@link FormattableUtils#toXYI(Cell)}.
	 */
	@Test
	@Override
	default void testToXYI() {
		Cell<Object> cell = createValue();
		
		assertEquals(FormattableUtils.toXYI(cell), cell.toXYI());
	}
	
	/**
	 * {@inheritDoc}
	 * @implSpec
	 * The default implementation for this {@code cell} only checks that {@code cell.toXYV()}
	 * equals to {@link FormattableUtils#toXYV(Cell)}.
	 */
	@Test
	@Override
	default void testToXYV() {
		Cell<Object> cell = createValue();
		
		assertEquals(FormattableUtils.toXYV(cell), cell.toXYV());
	}
	
	/**
	 * Test a normal {@code Cell}'s {@link Cell#clear() clear()} method.
	 */
	@Test
	default void testClearOnNormalCell() {
		Cell<Object> cell = createValue();
		cell.makeNormalCell();  // make sure this is a normal cell
		
		// set a Symbol on the Cell
		cell.changeSymbol(Symbols.of(Integer.valueOf(0), new Object()));
		// set some notes
		cell.makeNote(Symbols.of(Integer.valueOf(1), new Object()));
		cell.makeNote(Symbols.of(Integer.valueOf(2), new Object()));
		
		// assert that the values we set worked
		assertTrue(cell.value().isPresent());
		assertFalse(cell.notes().isEmpty());
		
		// clear the cell
		cell.clear();
		
		// assert that cell.value() returns an empty Optional
		assertFalse(cell.value().isPresent());
		// assert that the notes were cleared
		assertTrue(cell.notes().isEmpty());
		
		// assert that clearing an already cleared Cell doesn'throw an exception
		assertDoesNotThrow(() -> cell.clear());
	}
	
	/**
	 * Test a clue {@code Cell}'s {@link Cell#clear() clear()} method.
	 */
	@Test
	default void testClearOnClueCell() {
		Cell<Object> cell = createValue();
		cell.makeClueCell(Symbols.of(Integer.valueOf(0), new Object()));  // make sure this is a clue cell
		
		// assert that this is a clue cell
		assertTrue(cell.isClueCell());
		
		// clear the cell
		cell.clear();
		
		// assert that the Cell has been reverted to a normal Cell
		assertFalse(cell.isClueCell());
		// assert that cell.value() returns an empty Optional
		assertFalse(cell.value().isPresent());
		// assert that the notes were cleared
		assertTrue(cell.notes().isEmpty());
		
		// assert that clearing an already cleared Cell doesn'throw an exception
		assertDoesNotThrow(() -> cell.clear());
	}
	
	/**
	 * Test a {@code Cell}'s {@link Cell#deepEquals(Object) deepEquals(Object)} method. 
	 */
	@Test
	default void testDeapEquals() {
		Cell<Object> cell = createValue();
		Cell<Object> equalCell = createEqualValue();
		Cell<Object> nonEqualCell = createNonEqualValue();
		
		assertTrue(cell.deepEquals(cell));
		assertTrue(cell.deepEquals(equalCell));
		assertFalse(cell.deepEquals(nonEqualCell));
	}
	
	/**
	 * Test a normal {@code Cell}'s {@link Cell#changeSymbol(Symbol) changeSymbol(Symbol)} method. 
	 */
	@Test
	default void testChangeSymbolOnNormalCell() {
		Cell<Object> cell = createValue();
		cell.clear();  // clear the Cell making it a normal Cell
		
		// assert that the Cell has no set Symbol
		assertFalse(cell.value().isPresent());
		
		// create a Symbol to set to the Cell
		Symbol<Object> symbol = Symbols.of(Integer.valueOf(0), new Object());
		Symbol<Object> symbol1 = Symbols.of(Integer.valueOf(5), new Object());
		
		// change the Cell's Symbol
		cell.changeSymbol(symbol);
		
		// assert that the Cell's Symbol was changed
		assertTrue(cell.value().isPresent());
		assertEquals(symbol, cell.value().get());
		
		// change the Cell's Symbol
		cell.changeSymbol(symbol1);	
		
		// assert that the Cell's Symbol was changed
		assertTrue(cell.value().isPresent());
		assertEquals(symbol1, cell.value().get());
		
		// assert that we can pass null when changing the Cell's Symbol
		assertDoesNotThrow(() -> cell.changeSymbol(null));
		assertFalse(cell.value().isPresent());
	}
	
	/**
	 * Test a clue {@code Cell}'s {@link Cell#changeSymbol(Symbol) changeSymbol(Symbol)} method. 
	 */
	@Test
	default void testChangeSymbolOnClueCell() {
		Cell<Object> cell = createValue();
		cell.makeClueCell(Symbols.of(Integer.valueOf(0), new Object()));  // make sure this is a clue cell
		
		// assert that this is a clue Cell
		assertTrue(cell.isClueCell());
		
		// assert that ClueCellModificationException is thrown when changing the Symbol on a clue Cell
		assertThrows(ClueCellModificationException.class, () -> cell.changeSymbol(Symbols.of(Integer.valueOf(4), new Object())));
	}
	
	/**
	 * Test a {@code Cell}'s {@link Cell#makeClueCell(Symbol) makeClueCell(Symbol)} method. 
	 */
	@Test
	default void testMakeClueCell() {
		Cell<Object> cell = createValue();
		cell.clear();  // clear the Cell making it a normal Cell
		
		// assert that this is not a clue cell
		assertFalse(cell.isClueCell());
		
		// create a Symbol to set to the Cell
		Symbol<Object> symbol = Symbols.of(Integer.valueOf(0), new Object());
		Symbol<Object> symbol1 = Symbols.of(Integer.valueOf(5), new Object());
		
		// make the Cell a clue Cell
		cell.makeClueCell(symbol);
		
		// assert the Cell is now a clue Cell with the correct Symbol
		assertTrue(cell.isClueCell());
		assertEquals(symbol, cell.value().get());
		
		// assert that modification of the Cell throws a ClueCellModificationException
		assertThrows(ClueCellModificationException.class, () -> cell.changeSymbol(symbol1));
		assertThrows(ClueCellModificationException.class, () -> cell.makeNote(symbol1));
		assertThrows(ClueCellModificationException.class, () -> cell.removeNote(symbol1));
		
		// assert that calling this method an a clue Cell doesn't throw a ClueCellModificationException
		assertDoesNotThrow(() -> cell.makeClueCell(symbol1));
		assertTrue(cell.isClueCell());
		
		// assert that a NullPointerException is thrown when passed to this method
		assertThrows(NullPointerException.class, () -> cell.makeClueCell(null));
	}
	
	/**
	 * Test a {@code Cell}'s {@link Cell#makeNormalCell() makeNormalCell()} method. 
	 */
	@Test
	default void testMakeNormalCell() {
		Cell<Object> cell = createValue();
		cell.makeClueCell(Symbols.of(Integer.valueOf(0), new Object()));  // make sure this is a clue cell
		
		// assert that this is a clue Cell
		assertTrue(cell.isClueCell());
		
		// make the Cell a normal Cell
		cell.makeNormalCell();
		
		// assert the Cell is now a normal Cell
		assertFalse(cell.isClueCell());
		
		// create a Symbol to set to the Cell
		Symbol<Object> symbol = Symbols.of(Integer.valueOf(3), new Object());
		Symbol<Object> symbol1 = Symbols.of(Integer.valueOf(4), new Object());
		
		// assert that further modifications of the Cell do not throw ClueCellModificationException
		assertDoesNotThrow(() -> cell.changeSymbol(symbol));
		assertDoesNotThrow(() -> cell.makeNote(symbol1));
		assertDoesNotThrow(() -> cell.removeNote(symbol1));
		
		// assert that calling this method on a normal Cell does not throw any exception
		assertDoesNotThrow(() -> cell.makeNormalCell());
		assertDoesNotThrow(() -> cell.makeNormalCell());
	}
	
	/**
	 * Test a normal {@code Cell}'s {@link Cell#makeNote(Symbol) makeNote(Symbol)} method.
	 */
	@Test
	default void testMakeNoteOnNormalCell() {
		Cell<Object> cell = createValue();
		cell.clear();  // clear the Cell making it a normal Cell
		
		// assert that this is not a clue Cell and it has no notes
		assertFalse(cell.isClueCell());
		assertTrue(cell.notes().isEmpty());
		
		// create a Symbol to set to the Cell
		Symbol<Object> symbol = Symbols.of(Integer.valueOf(3), new Object());
		Symbol<Object> symbol1 = Symbols.of(Integer.valueOf(4), new Object());
		
		// add notes to the Cell
		cell.makeNote(symbol);
		cell.makeNote(symbol1);
		
		// assert that the Cell has two notes
		assertEquals(2, cell.notes().size());
		
		// assert that adding an existing note returns cleanly and that the number of
		// notes doesn't change
		assertDoesNotThrow(() -> cell.makeNote(symbol1));
		assertEquals(2, cell.notes().size());
		
		// assert that adding a null note results in a NullPointerException
		assertThrows(NullPointerException.class, ()  -> cell.makeNote(null));
	}
	
	/**
	 * Test a clue {@code Cell}'s {@link Cell#makeNote(Symbol) makeNote(Symbol)} method.
	 */
	@Test
	default void testMakeClueOnNormalCell() {
		Cell<Object> cell = createValue();
		cell.makeClueCell(Symbols.of(Integer.valueOf(0), new Object()));  // make sure this is a clue cell
		
		// assert that this is a clue Cell
		assertTrue(cell.isClueCell());
		
		// create a Symbol to set to the Cell
		Symbol<Object> symbol = Symbols.of(Integer.valueOf(3), new Object());
		Symbol<Object> symbol1 = Symbols.of(Integer.valueOf(4), new Object());
		
		// assert throws a ClueCellModificationException when adding notes on a clue Cell
		assertThrows(ClueCellModificationException.class, ()  -> cell.makeNote(symbol));
		assertThrows(ClueCellModificationException.class, ()  -> cell.makeNote(symbol1));
	}
	
	/**
	 * Test a normal {@code Cell}'s {@link Cell#removeNote(Symbol) removeNote(Symbol)} method.
	 */
	@Test
	default void testRemoveNoteOnNormalCell() {
		Cell<Object> cell = createValue();
		cell.clear();  // clear the Cell making it a normal Cell
		
		// assert that this is not a clue Cell and it has no notes
		assertFalse(cell.isClueCell());
		assertTrue(cell.notes().isEmpty());
		
		// create a Symbol to set to the Cell
		Symbol<Object> symbol = Symbols.of(Integer.valueOf(3), new Object());
		Symbol<Object> symbol1 = Symbols.of(Integer.valueOf(4), new Object());
		
		// add notes to the Cell
		cell.makeNote(symbol);
		cell.makeNote(symbol1);
		
		// assert that the Cell has two notes
		assertEquals(2, cell.notes().size());
		assertTrue(cell.notes().contains(symbol));
		assertTrue(cell.notes().contains(symbol1));
		
		// remove a Symbol
		cell.removeNote(symbol);
		
		// assert that the note was removed
		assertEquals(1, cell.notes().size());
		assertFalse(cell.notes().contains(symbol));
		assertTrue(cell.notes().contains(symbol1));
		
		// remove a Symbol
		cell.removeNote(symbol1);
		
		// assert that the note was removed
		assertEquals(0, cell.notes().size());
		assertFalse(cell.notes().contains(symbol));
		assertFalse(cell.notes().contains(symbol1));
		
		// assert that removing an non existent note returns cleanly
		assertDoesNotThrow(() -> cell.removeNote(symbol1));
		assertEquals(0, cell.notes().size());
		
		// assert that removing a null note results in a NullPointerException
		assertThrows(NullPointerException.class, ()  -> cell.removeNote(null));
	}
	
	/**
	 * Test a clue {@code Cell}'s {@link Cell#removeNote(Symbol) removeNote(Symbol)} method.
	 */
	@Test
	default void testRemoveNoteOnClueCell() {
		Cell<Object> cell = createValue();
		cell.makeClueCell(Symbols.of(Integer.valueOf(0), new Object()));  // make sure this is a clue cell
		
		// assert that this is a clue Cell
		assertTrue(cell.isClueCell());
		
		// create a Symbol to set to the Cell
		Symbol<Object> symbol = Symbols.of(Integer.valueOf(3), new Object());
		Symbol<Object> symbol1 = Symbols.of(Integer.valueOf(4), new Object());
		
		// assert throws a ClueCellModificationException when removing notes on a clue Cell
		assertThrows(ClueCellModificationException.class, ()  -> cell.removeNote(symbol));
		assertThrows(ClueCellModificationException.class, ()  -> cell.removeNote(symbol1));
	}
	
	/**
	 * Test a normal {@code Cell}'s {@link Cell#reset(Symbol) reset(Symbol)} method.
	 */
	@Test
	default void testResetOnNormalCell() {
		Cell<Object> cell = createValue();
		cell.makeNormalCell();  // make sure this is a normal cell
		
		// set a Symbol on the Cell
		cell.changeSymbol(Symbols.of(Integer.valueOf(0), new Object()));
		// set some notes
		cell.makeNote(Symbols.of(Integer.valueOf(1), new Object()));
		cell.makeNote(Symbols.of(Integer.valueOf(2), new Object()));
		
		// assert that the values we set worked
		assertTrue(cell.value().isPresent());
		assertFalse(cell.notes().isEmpty());
		
		// create a Symbol to set to the Cell
		Symbol<Object> symbol = Symbols.of(Integer.valueOf(3), new Object());
		
		// reset the cell
		cell.reset(symbol);
		
		// assert that cell.value() is present and equals to symbol
		assertTrue(cell.value().isPresent());
		assertEquals(symbol, cell.value().get());
		// assert that the notes were cleared
		assertTrue(cell.notes().isEmpty());
		
		// assert that passing null doesn'throw an exception
		assertDoesNotThrow(() -> cell.reset(null));
	}
	
	/**
	 * Test a clue {@code Cell}'s {@link Cell#reset(Symbol) reset(Symbol)} method.
	 */
	@Test
	default void testResetOnClueCell() {
		Cell<Object> cell = createValue();
		cell.makeClueCell(Symbols.of(Integer.valueOf(0), new Object()));  // make sure this is a clue cell
		
		// assert that this is a clue cell
		assertTrue(cell.isClueCell());
		
		// create a Symbol to set to the Cell
		Symbol<Object> symbol = Symbols.of(Integer.valueOf(3), new Object());
		
		// reset the cell
		cell.reset(symbol);
		
		// assert that the Cell has been reverted to a normal Cell
		assertFalse(cell.isClueCell());
		// assert that cell.value() is present and equals to symbol
		assertTrue(cell.value().isPresent());
		assertEquals(symbol, cell.value().get());
		// assert that the notes were cleared
		assertTrue(cell.notes().isEmpty());
	}
	
	/**
	 * Tests a {@code Cell} accessor method work as expected.
	 */
	@Test
	default void testCellAccessors() {
		Cell<Object> cell = createValue();
		cell.clear();  // clear the Cell making it a normal Cell
		
		// assert that this is a normal Cell
		assertFalse(cell.isClueCell());
		
		// create a Symbol to set to the Cell
		Symbol<Object> symbol = Symbols.of(Integer.valueOf(0), new Object());
		Symbol<Object> symbol1 = Symbols.of(Integer.valueOf(1), new Object());
		Symbol<Object> symbol2 = Symbols.of(Integer.valueOf(2), new Object());
		Symbol<Object> symbol3 = Symbols.of(Integer.valueOf(3), new Object());
		
		//  set some values on the cell
		cell.changeSymbol(symbol);
		cell.makeNote(symbol1);
		cell.makeNote(symbol2);
		cell.makeNote(symbol3);
		
		// assert that accessors return correct values
		assertEquals(symbol, cell.value().get());
		assertEquals(3, cell.notes().size());
		assertFalse(cell.isClueCell());
		
		// get accessor values
		int x = cell.x();
		int y = cell.y();
		String id = cell.id();
		Symbol<Object> value = cell.value().get();
		Set<Symbol<Object>> notes = cell.notes();
		
		// assert that multiple calls of the Cell accessors return the same value
		// as long as no information has changed.
		assertEquals(x, cell.x());
		assertEquals(y, cell.y());
		assertEquals(id, cell.id());
		assertEquals(value, cell.value().get());
		assertEquals(notes, cell.notes());
		assertFalse(cell.isClueCell());
		
		assertEquals(x, cell.x());
		assertEquals(y, cell.y());
		assertEquals(id, cell.id());
		assertEquals(value, cell.value().get());
		assertEquals(notes, cell.notes());
		assertFalse(cell.isClueCell());
	}
}
