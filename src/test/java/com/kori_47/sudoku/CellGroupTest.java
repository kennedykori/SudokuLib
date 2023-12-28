/**
 * 
 */
package com.kori_47.sudoku;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.junit.jupiter.api.Test;

/**
 * This is a test interface that implementors of the {@link CellGroup} interface can use to test
 * and validate their implementations.
 * 
 * @author <a href="https://github.com/kennedykori">Kennedy Kori</a>
 *
 * @since Sun, 22 Mar 2020 20:53:43
 */
public interface CellGroupTest<T extends CellGroup<Object>> extends TestHashCode<T>, FormattableTest<T> {
	
	/**
	 * {@inheritDoc}
	 * @implSpec
	 * The default implementation for this {@code cellGroup} only checks that {@code cellGroup.toI()}
	 * equals to {@link Formattables#toI(CellGroup)}.
	 */
	@Test
	@Override
	default void testToI() {
		T cellGroup = createValue();
		
		assertEquals(Formattables.toI(cellGroup), cellGroup.toI());
	}
	
	/**
	 * {@inheritDoc}
	 * @implSpec
	 * The default implementation for this {@code cellGroup} only checks that {@code cellGroup.toV()}
	 * equals to {@link Formattables#toV(CellGroup)}.
	 */
	@Test
	@Override
	default void testToV() {
		T cellGroup = createValue();
		
		assertEquals(Formattables.toV(cellGroup), cellGroup.toV());
	}
	
	/**
	 * {@inheritDoc}
	 * @implSpec
	 * The default implementation for this {@code cellGroup} only checks that {@code cellGroup.toXY()}
	 * equals to {@link Formattables#toXY(CellGroup)}.
	 */
	@Test
	@Override
	default void testToXY() {
		T cellGroup = createValue();
		
		assertEquals(Formattables.toXY(cellGroup), cellGroup.toXY());
	}
	
	/**
	 * {@inheritDoc}
	 * @implSpec
	 * The default implementation for this {@code cellGroup} only checks that {@code cellGroup.toXYI()}
	 * equals to {@link Formattables#toXYI(CellGroup)}.
	 */
	@Test
	@Override
	default void testToXYI() {
		T cellGroup = createValue();
		
		assertEquals(Formattables.toXYI(cellGroup), cellGroup.toXYI());
	}
	
	/**
	 * {@inheritDoc}
	 * @implSpec
	 * The default implementation for this {@code cellGroup} only checks that {@code cellGroup.toXYV()}
	 * equals to {@link Formattables#toXYV(CellGroup)}.
	 */
	@Test
	@Override
	default void testToXYV() {
		T cellGroup = createValue();
		
		assertEquals(Formattables.toXYV(cellGroup), cellGroup.toXYV());
	}
	
	/**
	 * Tests a {@code CellGroup}'s {@link CellGroup#iterator() iterator()} method.
	 */
	@Test
	default void testIterator() {
		T cellGroup = createValue();
		
		// assert that the returned iterator isn't null.
		assertNotNull(cellGroup.iterator());
	}
	
	/**
	 * Tests a {@code CellGroup}'s {@link CellGroup#spliterator() spliterator()} method.
	 */
	@Test
	default void testSpliterator() {
		T cellGroup = createValue();
		
		// assert that the returned spliterator isn't null.
		assertNotNull(cellGroup.spliterator());
	}
	
	/**
	 * Tests a {@code CellGroup}'s accessor methods.
	 */
	@Test
	default void testCellGroupAccessors() {
		T cellGroup = createValue();
		
		// get accessor values
		int size = cellGroup.size();
		Map<String, Cell<Object>> cells = cellGroup.cells();
		
		// assert that the returned values are valid
		assertTrue(size >= 0); // Size should never be negative
		assertNotNull(cells); // cells should never be null
		assertEquals(cells.size(), size); // for most CellGroups, this should be true
		
		// assert that multiple calls of the CellGroups accessors return the same value
		// as long as no information has changed.
		assertEquals(cellGroup.size(), size);
		assertEquals(cellGroup.cells(), cells);
		
		assertEquals(cellGroup.size(), size);
		assertEquals(cellGroup.cells(), cells);
	}
	
	/**
	 * Tests a {@code CellGroup}'s {@link CellGroup#getCell(String) getCell(String)} method.
	 */
	@Test
	void testGetCellUsingCellId();
	
	/**
	 * Tests a {@code CellGroup}'s {@link CellGroup#getCell(int, int) getCell(int, int)} method.
	 */
	@Test
	void testGetCellUsingCellCoordinates();
}
