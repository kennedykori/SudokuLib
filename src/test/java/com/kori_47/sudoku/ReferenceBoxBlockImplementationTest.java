/**
 * 
 */
package com.kori_47.sudoku;

import static java.util.stream.Collectors.toMap;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;

/**
 * This class is used to define and run tests on the reference implementation of {@link BoxBlock},
 * i.e, the {@code BoxBlock} instances returned by {@link CellGroups#boxBlockOf(String, int, Map, Cell, int, int)}
 * method and it's derivetives.
 * 
 * @author <a href="https://github.com/kennedykori">Kennedy Kori</a>
 *
 * @since Thu, 21 May 2020 16:35:11
 */
public class ReferenceBoxBlockImplementationTest implements BoxBlockTest {

	@Override
	public BoxBlock<Object> createSmallerValue() {
		Map<String, Cell<Object>> cells = Arrays.asList(
			Cells.of("0/0", 0, 0),
			Cells.of("1/0", 1, 0),
			Cells.of("2/0", 2, 0),
			Cells.of("0/1", 0, 1),
			Cells.of("1/1", 1, 1),
			Cells.of("2/1", 2, 1),
			Cells.of("0/2", 0, 2),
			Cells.of("1/2", 1, 2),
			Cells.of("2/2", 2, 2)
		).stream().collect(toMap(cell -> cell.id(), cell -> cell));

		return CellGroups.boxBlockOf("1", 9, cells, Cells.of("0/0", 0, 0), 3, 3);
	}

	@Override
	public BoxBlock<Object> createEqualValue() {
		Map<String, Cell<Object>> cells = Arrays.asList(
			Cells.of("3/3", 3, 3),
			Cells.of("4/3", 4, 3),
			Cells.of("5/3", 5, 3),
			Cells.of("3/4", 3, 4),
			Cells.of("4/4", 4, 4),
			Cells.of("5/4", 5, 4),
			Cells.of("3/5", 3, 5),
			Cells.of("4/5", 4, 5),
			Cells.of("5/5", 5, 5)
		).stream().collect(toMap(cell -> cell.id(), cell -> cell));
		
		return CellGroups.boxBlockOf("5", 9, cells, Cells.of("3/3", 3, 3), 3, 3);
	}

	@Override
	public BoxBlock<Object> createLargerValue() {
		Map<String, Cell<Object>> cells = Arrays.asList(
			Cells.of("6/6", 6, 6),
			Cells.of("7/6", 7, 6),
			Cells.of("8/6", 8, 6),
			Cells.of("6/7", 6, 7),
			Cells.of("7/7", 7, 7),
			Cells.of("8/7", 8, 7),
			Cells.of("6/8", 6, 8),
			Cells.of("7/8", 7, 8),
			Cells.of("8/8", 8, 8)
		).stream().collect(toMap(cell -> cell.id(), cell -> cell));
	
		return CellGroups.boxBlockOf("9", 9, cells, Cells.of("6/6", 6, 6), 3, 3);
	}

	@Test
	@Override
	public void testGetCellUsingCellId() {
		BoxBlock<Object> blockBox = createValue();

		// get some Cell using their id's
		Optional<Cell<Object>> cell1 = blockBox.getCell("3/3");
		Optional<Cell<Object>> cell2 = blockBox.getCell("5/4");
		Optional<Cell<Object>> cell3 = blockBox.getCell("6/5");

		// assert that getting an available or non available Cell using id
		// returns correct availability results
		assertTrue(cell1.isPresent());
		assertTrue(cell2.isPresent());
		assertFalse(cell3.isPresent());

		// assert that getting a Cell using id returns a valid result
		assertEquals(cell1.get(), Cells.of("3/3", 3, 3));
		assertEquals(cell2.get(), Cells.of("5/4", 5, 4));
	}

	@Test
	@Override
	public void testGetCellUsingCellCoordinates() {
		BoxBlock<Object> blockBox = createValue();

		// get some Cell using their coordinates
		Optional<Cell<Object>> cell1 = blockBox.getCell(3, 3);
		Optional<Cell<Object>> cell2 = blockBox.getCell(5, 4);
		Optional<Cell<Object>> cell3 = blockBox.getCell(6, 5);

		// assert that getting an available or non available Cell using coordinates
		// returns correct availability results
		assertTrue(cell1.isPresent());
		assertTrue(cell2.isPresent());
		assertFalse(cell3.isPresent());

		// assert that getting a Cell using coordinates returns a valid result
		assertEquals(cell1.get(), Cells.of("3/3", 3, 3));
		assertEquals(cell2.get(), Cells.of("5/4", 5, 4));
	}


	@Test
	@Override
	public void testStartCell() {
		BoxBlock<Object> boxBlock1 = createSmallerValue();
		BoxBlock<Object> boxBlock2 = createValue();
		BoxBlock<Object> boxBlock3 = createLargerValue();
		BoxBlock<Object> boxBlock4 = createNonEqualValue();

		// assert that we have the correct start Cells
		assertEquals(boxBlock1.startCell(), Cells.of("0/0", 0, 0));
		assertEquals(boxBlock2.startCell(), Cells.of("3/3", 3, 3));
		assertEquals(boxBlock3.startCell(), Cells.of("6/6", 6, 6));
		assertEquals(boxBlock4.startCell(), Cells.of("3/0", 3, 0));
	}

	@Test
	@Override
	public void testEndCell() {
		BoxBlock<Object> boxBlock1 = createSmallerValue();
		BoxBlock<Object> boxBlock2 = createValue();
		BoxBlock<Object> boxBlock3 = createLargerValue();
		BoxBlock<Object> boxBlock4 = createNonEqualValue();

		// assert that we have the correct end Cells
		assertEquals(boxBlock1.endCell(), Cells.of("2/2", 2, 2));
		assertEquals(boxBlock2.endCell(), Cells.of("5/5", 5, 5));
		assertEquals(boxBlock3.endCell(), Cells.of("8/8", 8, 8));
		assertEquals(boxBlock4.endCell(), Cells.of("5/2", 5, 2));
	}

	@Test
	@Override
	public void testBlockRows() {
		BoxBlock<Object> boxBlock1 = createValue();
		BoxBlock<Object> boxBlock2 = createNonEqualValue();

		// assert that we have the correct blockRows values
		assertEquals(boxBlock1.blockRows(), 3);
		assertEquals(boxBlock2.blockRows(), 3);
	}

	@Test
	@Override
	public void testBlockColumns() {
		BoxBlock<Object> boxBlock1 = createValue();
		BoxBlock<Object> boxBlock2 = createNonEqualValue();

		// assert that we have the correct blockColumns values
		assertEquals(boxBlock1.blockColumns(), 3);
		assertEquals(boxBlock2.blockColumns(), 3);
	}

	@Override
	public BoxBlock<Object> createAnotherEqualValue() {
		Map<String, Cell<Object>> cells = Arrays.asList(
			Cells.of("3/3", 3, 3),
			Cells.of("4/3", 4, 3),
			Cells.of("5/3", 5, 3),
			Cells.of("3/4", 3, 4),
			Cells.of("4/4", 4, 4),
			Cells.of("5/4", 5, 4),
			Cells.of("3/5", 3, 5),
			Cells.of("4/5", 4, 5),
			Cells.of("5/5", 5, 5)
		).stream().collect(toMap(cell -> cell.id(), cell -> cell));
		
		return CellGroups.boxBlockOf("5", 9, cells, Cells.of("3/3", 3, 3), 3, 3);
	}

	@Override
	public BoxBlock<Object> createNonEqualValue() {
		Map<String, Cell<Object>> cells = Arrays.asList(
			Cells.of("3/0", 3, 0),
			Cells.of("4/0", 4, 0),
			Cells.of("5/0", 5, 0),
			Cells.of("3/1", 3, 1),
			Cells.of("4/1", 4, 1),
			Cells.of("5/1", 5, 1),
			Cells.of("3/2", 3, 2),
			Cells.of("4/2", 4, 2),
			Cells.of("5/2", 5, 2)
		).stream().collect(toMap(cell -> cell.id(), cell -> cell));
		
		return CellGroups.boxBlockOf("2", 9, cells, Cells.of("3/0", 3, 0), Cells.of("5/2", 5, 2));
	}

	@Override
	public BoxBlock<Object> createValue() {
		Map<String, Cell<Object>> cells = Arrays.asList(
			Cells.of("3/3", 3, 3),
			Cells.of("4/3", 4, 3),
			Cells.of("5/3", 5, 3),
			Cells.of("3/4", 3, 4),
			Cells.of("4/4", 4, 4),
			Cells.of("5/4", 5, 4),
			Cells.of("3/5", 3, 5),
			Cells.of("4/5", 4, 5),
			Cells.of("5/5", 5, 5)
		).stream().collect(toMap(cell -> cell.id(), cell -> cell));
		
		return CellGroups.boxBlockOf("5", 9, cells, Cells.of("3/3", 3, 3), 3, 3);
	}
}
