/**
 * 
 */
package com.kori_47.sudoku;

import static java.util.stream.Collectors.toMap;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Test;

/**
 * This class is used to define and run tests on the reference implementation
 * of {@link Row}, i.e, the {@code Row} instances returned by
 * {@link CellGroups#rowOf(String, int, java.util.Map, int)} method and it's derivetives.
 * 
 * @author <a href="https://github.com/kennedykori">Kennedy Kori</a>
 *
 * @since Tue, 19 May 2020 14:20:15
 */
public class ReferenceRowImplementationTest implements RowTest {

	@Override
	public Row<Object> createSmallerValue() {
		return CellGroups.rowOf(
				"1", 9,
				Arrays.asList(
					Cells.of("0/1", 0, 1),
					Cells.of("1/1", 1, 1),
					Cells.of("2/1", 2, 1),
					Cells.of("3/1", 3, 1),
					Cells.of("4/1", 4, 1),
					Cells.of("5/1", 5, 1),
					Cells.of("6/1", 6, 1),
					Cells.of("7/1", 7, 1),
					Cells.of("8/1", 8, 1)
				).stream().collect(toMap(cell -> cell.id(), cell -> cell)), 
			1);
	}

	@Override
	public Row<Object> createEqualValue() {
		return CellGroups.rowOf(
				"2", 9,
				Arrays.asList(
					Cells.of("0/2", 0, 2),
					Cells.of("1/2", 1, 2),
					Cells.of("2/2", 2, 2),
					Cells.of("3/2", 3, 2),
					Cells.of("4/2", 4, 2),
					Cells.of("5/2", 5, 2),
					Cells.of("6/2", 6, 2),
					Cells.of("7/2", 7, 2),
					Cells.of("8/2", 8, 2)
				).stream().collect(toMap(cell -> cell.id(), cell -> cell)), 
			2);
	}

	@Override
	public Row<Object> createLargerValue() {
		return CellGroups.rowOf(
				"3", 9,
				Arrays.asList(
					Cells.of("0/3", 0, 3),
					Cells.of("1/3", 1, 3),
					Cells.of("2/3", 2, 3),
					Cells.of("3/3", 3, 3),
					Cells.of("4/3", 4, 3),
					Cells.of("5/3", 5, 3),
					Cells.of("6/3", 6, 3),
					Cells.of("7/3", 7, 3),
					Cells.of("8/3", 8, 3)
				).stream().collect(toMap(cell -> cell.id(), cell -> cell)), 
			3);
	}

	@Test
	@Override
	public void testGetCellUsingCellId() {
		Row<Object> row = createValue();

		// get some Cell using their id's
		Optional<Cell<Object>> cell1 = row.getCell("1/2");
		Optional<Cell<Object>> cell2 = row.getCell("2/2");
		Optional<Cell<Object>> cell3 = row.getCell("1/3");

		// assert that getting an available or non available Cell using id
		// returns correct availability results
		assertTrue(cell1.isPresent());
		assertTrue(cell2.isPresent());
		assertFalse(cell3.isPresent());

		// assert that getting a Cell using id returns a valid result
		assertEquals(cell1.get(), Cells.of("1/2", 1, 2));
		assertEquals(cell2.get(), Cells.of("2/2", 2, 2));
	}

	@Test
	@Override
	public void testGetCellUsingCellCoordinates() {
		Row<Object> row = createValue();

		// get some Cell using their coordinates
		Optional<Cell<Object>> cell1 = row.getCell(1, 2);
		Optional<Cell<Object>> cell2 = row.getCell(2, 2);
		Optional<Cell<Object>> cell3 = row.getCell(1, 3);

		// assert that getting an available or non available Cell using coordinates
		// returns correct availability results
		assertTrue(cell1.isPresent());
		assertTrue(cell2.isPresent());
		assertFalse(cell3.isPresent());

		// assert that getting a Cell using coordinates returns a valid result
		assertEquals(cell1.get(), Cells.of("1/2", 1, 2));
		assertEquals(cell2.get(), Cells.of("2/2", 2, 2));
	}

	@Test
	@Override
	public void testStartCell() {
		Row<Object> row1 = createSmallerValue();
		Row<Object> row2 = createValue();
		Row<Object> row3 = createLargerValue();
		Row<Object> row4 = createNonEqualValue();

		// assert that we have the correct start Cells
		assertEquals(row1.startCell(), Cells.of("0/1", 0, 1));
		assertEquals(row2.startCell(), Cells.of("0/2", 0, 2));
		assertEquals(row3.startCell(), Cells.of("0/3", 0, 3));
		assertEquals(row4.startCell(), Cells.of("0/2", 0, 2));
	}

	@Test
	@Override
	public void testEndCell() {
		Row<Object> row1 = createSmallerValue();
		Row<Object> row2 = createValue();
		Row<Object> row3 = createLargerValue();
		Row<Object> row4 = createNonEqualValue();

		// assert that we have the correct end Cells
		assertEquals(row1.endCell(), Cells.of("8/1", 8, 1));
		assertEquals(row2.endCell(), Cells.of("8/2", 8, 2));
		assertEquals(row3.endCell(), Cells.of("8/3", 8, 3));
		assertEquals(row4.endCell(), Cells.of("4/2", 4, 2));
	}

	@Override
	public Row<Object> createAnotherEqualValue() {
		return CellGroups.rowOf(
				"2", 9,
				Arrays.asList(
					Cells.of("0/2", 0, 2),
					Cells.of("1/2", 1, 2),
					Cells.of("2/2", 2, 2),
					Cells.of("3/2", 3, 2),
					Cells.of("4/2", 4, 2),
					Cells.of("5/2", 5, 2),
					Cells.of("6/2", 6, 2),
					Cells.of("7/2", 7, 2),
					Cells.of("8/2", 8, 2)
				).stream().collect(toMap(cell -> cell.id(), cell -> cell)), 
			2);
	}

	@Override
	public Row<Object> createNonEqualValue() {
		return CellGroups.rowOf(
				"2", 5,
				Arrays.asList(
					Cells.of("0/2", 0, 2),
					Cells.of("1/2", 1, 2),
					Cells.of("2/2", 2, 2),
					Cells.of("3/2", 3, 2),
					Cells.of("4/2", 4, 2)
				).stream().collect(toMap(cell -> cell.id(), cell -> cell)), 
			2);
	}

	@Override
	public Row<Object> createValue() {
		return CellGroups.rowOf(
				"2", 9,
				Arrays.asList(
					Cells.of("0/2", 0, 2),
					Cells.of("1/2", 1, 2),
					Cells.of("2/2", 2, 2),
					Cells.of("3/2", 3, 2),
					Cells.of("4/2", 4, 2),
					Cells.of("5/2", 5, 2),
					Cells.of("6/2", 6, 2),
					Cells.of("7/2", 7, 2),
					Cells.of("8/2", 8, 2)
				).stream().collect(toMap(cell -> cell.id(), cell -> cell)), 
			2);
	}
}
