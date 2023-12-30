/**
 * 
 */
package com.kori_47.sudoku;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
	 * equals to {@link Formattables#toI(Cell)}.
	 */
	@Test
	@Override
	default void testToI() {
		Cell<Object> cell = createValue();
		
		assertEquals(Formattables.toI(cell), cell.toI());
	}
	
	/**
	 * {@inheritDoc}
	 * @implSpec
	 * The default implementation for this {@code cell} only checks that {@code cell.toV()}
	 * equals to {@link Formattables#toV(Cell)}.
	 */
	@Test
	@Override
	default void testToV() {
		Cell<Object> cell = createValue();
		
		assertEquals(Formattables.toV(cell), cell.toV());
	}
	
	/**
	 * {@inheritDoc}
	 * @implSpec
	 * The default implementation for this {@code cell} only checks that {@code cell.toXY()}
	 * equals to {@link Formattables#toXY(Cell)}.
	 */
	@Test
	@Override
	default void testToXY() {
		Cell<Object> cell = createValue();
		
		assertEquals(Formattables.toXY(cell), cell.toXY());
	}
	
	/**
	 * {@inheritDoc}
	 * @implSpec
	 * The default implementation for this {@code cell} only checks that {@code cell.toXYI()}
	 * equals to {@link Formattables#toXYI(Cell)}.
	 */
	@Test
	@Override
	default void testToXYI() {
		Cell<Object> cell = createValue();
		
		assertEquals(Formattables.toXYI(cell), cell.toXYI());
	}
	
	/**
	 * {@inheritDoc}
	 * @implSpec
	 * The default implementation for this {@code cell} only checks that {@code cell.toXYV()}
	 * equals to {@link Formattables#toXYV(Cell)}.
	 */
	@Test
	@Override
	default void testToXYV() {
		Cell<Object> cell = createValue();
		
		assertEquals(Formattables.toXYV(cell), cell.toXYV());
	}
	
	/**
	 * Test a {@code Cell}'s {@link Cell#clear() clear()} method.
	 */
	@Test
	default void testClear() {
		Cell<Object> cell = createValue();
		
		// set a Symbol on the Cell
		cell.changeSymbol(Symbols.of(Integer.valueOf(0), new Object()));
		
		// assert that the Cell has a value
		assertTrue(cell.symbol().isPresent());
		
		// clear the cell
		cell.clear();
		
		// assert that cell.value() returns an empty Optional
		assertFalse(cell.symbol().isPresent());
		
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
	 * Test a {@code Cell}'s {@link Cell#changeSymbol(Symbol) changeSymbol(Symbol)} method. 
	 */
	@Test
	default void testChangeSymbol() {
		Cell<Object> cell = createValue();
		cell.clear();  // clear the Cell
		
		// assert that the Cell has no set Symbol
		assertFalse(cell.symbol().isPresent());
		
		// create a Symbol to set to the Cell
		Symbol<Object> symbol = Symbols.of(Integer.valueOf(0), new Object());
		Symbol<Object> symbol1 = Symbols.of(Integer.valueOf(5), new Object());
		
		// change the Cell's Symbol
		cell.changeSymbol(symbol);
		
		// assert that the Cell's Symbol was changed
		assertTrue(cell.symbol().isPresent());
		assertEquals(symbol, cell.symbol().get());
		
		// change the Cell's Symbol
		cell.changeSymbol(symbol1);	
		
		// assert that the Cell's Symbol was changed
		assertTrue(cell.symbol().isPresent());
		assertEquals(symbol1, cell.symbol().get());
		
		// assert that we can pass null when changing the Cell's Symbol
		assertDoesNotThrow(() -> cell.changeSymbol(null));
		assertFalse(cell.symbol().isPresent());
	}
	
	/**
	 * Tests a {@code Cell} accessor method work as expected.
	 */
	@Test
	default void testCellAccessors() {
		Cell<Object> cell = createValue();
		cell.clear();  // clear the Cell
		
		// create a Symbol to set to the Cell
		Symbol<Object> symbol = Symbols.of(Integer.valueOf(0), new Object());
		
		//  set Cell's Symbol
		cell.changeSymbol(symbol);
		
		// get accessor values
		int x = cell.x();
		int y = cell.y();
		String id = cell.id();
		Symbol<Object> value = cell.symbol().get();
		
		// assert that multiple calls of the Cell accessors return the same value
		// as long as no information has changed.
		assertEquals(x, cell.x());
		assertEquals(y, cell.y());
		assertEquals(id, cell.id());
		assertEquals(value, cell.symbol().get());
		
		assertEquals(x, cell.x());
		assertEquals(y, cell.y());
		assertEquals(id, cell.id());
		assertEquals(value, cell.symbol().get());
	}
}
