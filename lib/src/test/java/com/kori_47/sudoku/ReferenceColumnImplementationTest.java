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
 * This class is used to define and run tests on the reference implementation of {@link Column},
 * i.e, the {@code Row} instances returned by {@link CellGroups#columnOf(String, int, java.util.Map, int)}
 * method and it's derivetives.
 * 
 * @author <a href="https://github.com/kennedykori">Kennedy Kori</a>
 *
 * @since Wed, 20 May 2020 14:32:36
 */
public class ReferenceColumnImplementationTest implements ColumnTest {

	@Override
	public Column<Object> createSmallerValue() {
		return CellGroups.columnOf(
				"1", 9,
				Arrays.asList(
					Cells.of("1/0", 1, 0),
					Cells.of("1/1", 1, 1),
					Cells.of("1/2", 1, 2),
					Cells.of("1/3", 1, 3),
					Cells.of("1/4", 1, 4),
					Cells.of("1/5", 1, 5),
					Cells.of("1/6", 1, 6),
					Cells.of("1/7", 1, 7),
					Cells.of("1/8", 1, 8)
				).stream().collect(toMap(cell -> cell.id(), cell -> cell)), 
			1);
	}

	@Override
	public Column<Object> createEqualValue() {
		return CellGroups.columnOf(
				"2", 9,
				Arrays.asList(
					Cells.of("2/0", 2, 0),
					Cells.of("2/1", 2, 1),
					Cells.of("2/2", 2, 2),
					Cells.of("2/3", 2, 3),
					Cells.of("2/4", 2, 4),
					Cells.of("2/5", 2, 5),
					Cells.of("2/6", 2, 6),
					Cells.of("2/7", 2, 7),
					Cells.of("2/8", 2, 8)
				).stream().collect(toMap(cell -> cell.id(), cell -> cell)), 
			2);
	}

	@Override
	public Column<Object> createLargerValue() {
		return CellGroups.columnOf(
				"3", 9,
				Arrays.asList(
					Cells.of("3/0", 3, 0),
					Cells.of("3/1", 3, 1),
					Cells.of("3/2", 3, 2),
					Cells.of("3/3", 3, 3),
					Cells.of("3/4", 3, 4),
					Cells.of("3/5", 3, 5),
					Cells.of("3/6", 3, 6),
					Cells.of("3/7", 3, 7),
					Cells.of("3/8", 3, 8)
				).stream().collect(toMap(cell -> cell.id(), cell -> cell)), 
			3);
	}

	@Test
	@Override
	public void testGetCellUsingCellId() {
		Column<Object> column = createValue();

		// get some Cell using their id's
		Optional<Cell<Object>> cell1 = column.getCell("2/1");
		Optional<Cell<Object>> cell2 = column.getCell("2/2");
		Optional<Cell<Object>> cell3 = column.getCell("3/1");

		// assert that getting an available or non available Cell using id
		// returns correct availability results
		assertTrue(cell1.isPresent());
		assertTrue(cell2.isPresent());
		assertFalse(cell3.isPresent());

		// assert that getting a Cell using id returns a valid result
		assertEquals(cell1.get(), Cells.of("2/1", 2, 1));
		assertEquals(cell2.get(), Cells.of("2/2", 2, 2));
	}

	@Test
	@Override
	public void testGetCellUsingCellCoordinates() {
		Column<Object> column = createValue();

		// get some Cell using their coordinates
		Optional<Cell<Object>> cell1 = column.getCell(2, 1);
		Optional<Cell<Object>> cell2 = column.getCell(2, 2);
		Optional<Cell<Object>> cell3 = column.getCell(3, 1);

		// assert that getting an available or non available Cell using coordinates
		// returns correct availability results
		assertTrue(cell1.isPresent());
		assertTrue(cell2.isPresent());
		assertFalse(cell3.isPresent());

		// assert that getting a Cell using coordinates returns a valid result
		assertEquals(cell1.get(), Cells.of("2/1", 2, 1));
		assertEquals(cell2.get(), Cells.of("2/2", 2, 2));
	}

	@Test
	@Override
	public void testStartCell() {
		Column<Object> column1 = createSmallerValue();
		Column<Object> column2 = createValue();
		Column<Object> column3 = createLargerValue();
		Column<Object> column4 = createNonEqualValue();

		// assert that we have the correct start Cells
		assertEquals(column1.startCell(), Cells.of("1/0", 1, 0));
		assertEquals(column2.startCell(), Cells.of("2/0", 2, 0));
		assertEquals(column3.startCell(), Cells.of("3/0", 3, 0));
		assertEquals(column4.startCell(), Cells.of("2/0", 2, 0));
	}

	@Test
	@Override
	public void testEndCell() {
		Column<Object> column1 = createSmallerValue();
		Column<Object> column2 = createValue();
		Column<Object> column3 = createLargerValue();
		Column<Object> column4 = createNonEqualValue();

		// assert that we have the correct end Cells
		assertEquals(column1.endCell(), Cells.of("1/8", 1, 8));
		assertEquals(column2.endCell(), Cells.of("2/8", 2, 8));
		assertEquals(column3.endCell(), Cells.of("3/8", 3, 8));
		assertEquals(column4.endCell(), Cells.of("2/4", 2, 4));
	}

	@Override
	public Column<Object> createAnotherEqualValue() {
		return CellGroups.columnOf(
				"2", 9,
				Arrays.asList(
					Cells.of("2/0", 2, 0),
					Cells.of("2/1", 2, 1),
					Cells.of("2/2", 2, 2),
					Cells.of("2/3", 2, 3),
					Cells.of("2/4", 2, 4),
					Cells.of("2/5", 2, 5),
					Cells.of("2/6", 2, 6),
					Cells.of("2/7", 2, 7),
					Cells.of("2/8", 2, 8)
				).stream().collect(toMap(cell -> cell.id(), cell -> cell)), 
			2);
	}

	@Override
	public Column<Object> createNonEqualValue() {
		return CellGroups.columnOf(
				"2", 5,
				Arrays.asList(
					Cells.of("2/0", 2, 0),
					Cells.of("2/1", 2, 1),
					Cells.of("2/2", 2, 2),
					Cells.of("2/3", 2, 3),
					Cells.of("2/4", 2, 4)
				).stream().collect(toMap(cell -> cell.id(), cell -> cell)), 
			2);
	}

	@Override
	public Column<Object> createValue() {
		return CellGroups.columnOf(
				"2", 9,
				Arrays.asList(
					Cells.of("2/0", 2, 0),
					Cells.of("2/1", 2, 1),
					Cells.of("2/2", 2, 2),
					Cells.of("2/3", 2, 3),
					Cells.of("2/4", 2, 4),
					Cells.of("2/5", 2, 5),
					Cells.of("2/6", 2, 6),
					Cells.of("2/7", 2, 7),
					Cells.of("2/8", 2, 8)
				).stream().collect(toMap(cell -> cell.id(), cell -> cell)), 
			2);
	}
}
