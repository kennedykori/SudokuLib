/**
 * 
 */
package com.kori_47.sudoku;

import static java.util.stream.Collectors.toMap;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

/**
 * @author <a href="https://github.com/kennedykori">Kennedy Kori</a>
 *
 * @since Thu, 21 May 2020 20:05:15
 */
public class CellGroupsTest {

	/**
	 * Test {@link CellGroups#rowOf(String, int, java.util.Map, int)} creates and returns
	 * the expected value.
	 */
	@Test
	public void testRowOf() {
		// create test values
		Row<Object> row = CellGroups.rowOf(
				"2", 5,
				Arrays.asList(
					Cells.of("0/2", 0, 2),
					Cells.of("1/2", 1, 2),
					Cells.of("2/2", 2, 2),
					Cells.of("3/2", 3, 2),
					Cells.of("4/2", 4, 2)
				).stream().collect(toMap(cell -> cell.id(), cell -> cell)), 
			2);

		Row<Object> row1 = CellGroups.rowOf(
				"7", 7,
				Arrays.asList(
					Cells.of("0/6", 0, 6),
					Cells.of("1/6", 1, 6),
					Cells.of("2/6", 2, 6),
					Cells.of("3/6", 3, 6),
					Cells.of("4/6", 4, 6),
					Cells.of("5/6", 5, 6),
					Cells.of("6/6", 6, 6)
				).stream().collect(toMap(cell -> cell.id(), cell -> cell)), 
			6);

		// assert the value returned isn't null
		assertNotNull(row);
		assertNotNull(row1);

		// assert that the returned rows were initialized correctly
		assertEquals(5, row.size());
		assertEquals(5, row.cells().size());
		assertEquals(2, row.y());
		assertEquals("2", row.id());
		assertEquals(Cells.of("0/2", 0, 2), row.startCell());
		assertEquals(Cells.of("4/2", 4, 2), row.endCell());

		assertEquals(7, row1.size());
		assertEquals(7, row1.cells().size());
		assertEquals(6, row1.y());
		assertEquals("7", row1.id());
		assertEquals(Cells.of("0/6", 0, 6), row1.startCell());
		assertEquals(Cells.of("6/6", 6, 6), row1.endCell());

		// assert that comparisons of the two blocks works as expected
		assertNotEquals(row, row1);
		assertTrue(row.compareTo(row1) < 0);
	
		// assert that the returned blocks have the expected cells
		assertTrue(row.cells().containsKey("1/2"));
		assertTrue(row.cells().containsKey("2/2"));
		assertTrue(row.cells().containsKey("3/2"));
		assertFalse(row.cells().containsKey("1/3"));
	
		assertTrue(row1.cells().containsKey("1/6"));
		assertTrue(row1.cells().containsKey("2/6"));
		assertTrue(row1.cells().containsKey("4/6"));
		assertFalse(row1.cells().containsKey("5/5"));
	}

	/**
	 * Test {@link CellGroups#rowOf(String, int, java.util.Map, int)} throws the appropriate
	 * exceptions incase of invalid parameters.
	 */
	@Test
	public void testRowOfExceptions() {
		// assert that an IllegalException is thrown where appropriate
		assertThrows(IllegalArgumentException.class, () -> 
			CellGroups.rowOf("2", 0, Arrays.asList(Cells.of("0/2", 0, 2)).stream().
					collect(toMap(cell -> cell.id(), cell -> cell)), 2)); // assert that we can't pass a size of 0
		assertThrows(IllegalArgumentException.class, () -> 
			CellGroups.rowOf("2", 2, Arrays.asList(Cells.of("0/2", 0, 2)).stream().
					collect(toMap(cell -> cell.id(), cell -> cell)), 2)); // assert that # of cells given is equal to size given
		assertThrows(IllegalArgumentException.class, () -> 
			CellGroups.rowOf("2", 1, Arrays.asList(Cells.of("0/2", 0, 2)).stream().
					collect(toMap(cell -> cell.id(), cell -> cell)), -2)); // assert that y >= 0
		assertThrows(IllegalArgumentException.class, () -> 
			CellGroups.rowOf("2", 1, Arrays.asList(Cells.of("0/2", 0, 2)).stream().
					collect(toMap(cell -> cell.id(), cell -> cell)), 2)); // assert that y < size
		assertThrows(IllegalArgumentException.class, () -> 
			CellGroups.rowOf("2", 3, Arrays.asList(
						Cells.of("1/2", 1, 2),
						Cells.of("2/2", 2, 2),
						Cells.of("3/2", 3, 2)
					).stream().
					collect(toMap(cell -> cell.id(), cell -> cell)), 2)); // assert that the cells given have a valid x coordinate (0 to size - 1).

		// assert that a NullPointerException is thrown where appropriate
		assertThrows(NullPointerException.class, () -> 
			CellGroups.rowOf(null, 1, Arrays.asList(Cells.of("0/0", 0, 0)).stream().
					collect(toMap(cell -> cell.id(), cell -> cell)), 0)); // assert that we can't pass a null id
		assertThrows(NullPointerException.class, () ->  CellGroups.rowOf("", 1, null, 0)); // assert that we can't pass a null cells map
	}

	/**
	 * Test {@link CellGroups#columnOf(String, int, java.util.Map, int)} creates and returns
	 * the expected value.
	 */
	@Test
	public void testColumnOf() {
		// create test values
		Column<Object> column = CellGroups.columnOf(
				"2", 5,
				Arrays.asList(
					Cells.of("2/0", 2, 0),
					Cells.of("2/1", 2, 1),
					Cells.of("2/2", 2, 2),
					Cells.of("2/3", 2, 3),
					Cells.of("2/4", 2, 4)
				).stream().collect(toMap(cell -> cell.id(), cell -> cell)), 
			2);

		Column<Object> column1 = CellGroups.columnOf(
				"6", 7,
				Arrays.asList(
					Cells.of("5/0", 5, 0),
					Cells.of("5/1", 5, 1),
					Cells.of("5/2", 5, 2),
					Cells.of("5/3", 5, 3),
					Cells.of("5/4", 5, 4),
					Cells.of("5/5", 5, 5),
					Cells.of("5/6", 5, 6)
				).stream().collect(toMap(cell -> cell.id(), cell -> cell)), 
			5);

		// assert the value returned isn't null
		assertNotNull(column);
		assertNotNull(column1);

		// assert that the returned rows were initialized correctly
		assertEquals(5, column.size());
		assertEquals(5, column.cells().size());
		assertEquals(2, column.x());
		assertEquals("2", column.id());
		assertEquals(Cells.of("2/0", 2, 0), column.startCell());
		assertEquals(Cells.of("2/4", 2, 4), column.endCell());

		assertEquals(7, column1.size());
		assertEquals(7, column1.cells().size());
		assertEquals(5, column1.x());
		assertEquals("6", column1.id());
		assertEquals(Cells.of("5/0", 5, 0), column1.startCell());
		assertEquals(Cells.of("5/6", 5, 6), column1.endCell());

		// assert that comparisons of the two blocks works as expected
		assertNotEquals(column, column1);
		assertTrue(column.compareTo(column1) < 0);
	
		// assert that the returned blocks have the expected cells
		assertTrue(column.cells().containsKey("2/1"));
		assertTrue(column.cells().containsKey("2/2"));
		assertTrue(column.cells().containsKey("2/3"));
		assertFalse(column.cells().containsKey("4/3"));
	
		assertTrue(column1.cells().containsKey("5/1"));
		assertTrue(column1.cells().containsKey("5/2"));
		assertTrue(column1.cells().containsKey("5/4"));
		assertFalse(column1.cells().containsKey("5/9"));	
	}

	/**
	 * Test {@link CellGroups#columnOf(String, int, java.util.Map, int)} throws the appropriate
	 * exceptions incase of invalid parameters.
	 */
	@Test
	public void testColumnOfExceptions() {
		// assert that an IllegalException is thrown where appropriate
		assertThrows(IllegalArgumentException.class, () -> 
			CellGroups.columnOf("2", 0, Arrays.asList(Cells.of("2/0", 2, 0)).stream().
					collect(toMap(cell -> cell.id(), cell -> cell)), 2)); // assert that we can't pass a size of 0
		assertThrows(IllegalArgumentException.class, () -> 
			CellGroups.columnOf("2", 2, Arrays.asList(Cells.of("2/0", 2, 0)).stream().
					collect(toMap(cell -> cell.id(), cell -> cell)), 2)); // assert that # of cells given is equal to size given
		assertThrows(IllegalArgumentException.class, () -> 
			CellGroups.columnOf("2", 1, Arrays.asList(Cells.of("2/0", 2, 0)).stream().
					collect(toMap(cell -> cell.id(), cell -> cell)), -2)); // assert that x >= 0
		assertThrows(IllegalArgumentException.class, () -> 
			CellGroups.columnOf("2", 1, Arrays.asList(Cells.of("2/0", 2, 0)).stream().
					collect(toMap(cell -> cell.id(), cell -> cell)), 2)); // assert that x < size
		assertThrows(IllegalArgumentException.class, () -> 
			CellGroups.columnOf("2", 3, Arrays.asList(
						Cells.of("2/1", 2, 1),
						Cells.of("2/2", 2, 2),
						Cells.of("2/3", 2, 3)
					).stream().
					collect(toMap(cell -> cell.id(), cell -> cell)), 2)); // assert that the cells given have a valid x coordinate (0 to size - 1).

		// assert that a NullPointerException is thrown where appropriate
		assertThrows(NullPointerException.class, () -> 
			CellGroups.columnOf(null, 1, Arrays.asList(Cells.of("0/0", 0, 0)).stream().
					collect(toMap(cell -> cell.id(), cell -> cell)), 0)); // assert that we can't pass a null id
		assertThrows(NullPointerException.class, () ->  CellGroups.columnOf("2", 1, null, 0)); // assert that we can't pass a null cells map
	}

	/**
	 * Test {@link CellGroups#boxBlockOf(String, int, java.util.Map, Cell, int, int)} and it's derivertive
	 * {@link CellGroups#boxBlockOf(String, int, java.util.Map, Cell, int, int)}.
	 */
	@Test
	public void testboxBlockOf() {
		// create test values
		BoxBlock<Object> boxBlock = CellGroups.boxBlockOf(
				"4", 4,
				Arrays.asList(
					Cells.of("2/2", 2, 2),
					Cells.of("3/2", 3, 2),
					Cells.of("2/3", 2, 3),
					Cells.of("3/3", 3, 3)
				).stream().collect(toMap(cell -> cell.id(), cell -> cell)),
				Cells.of("2/2", 2, 2),
				2, 2);

		BoxBlock<Object> boxBlock1 = CellGroups.boxBlockOf(
				"1", 4,
				Arrays.asList(
					Cells.of("0/0", 0, 0),
					Cells.of("1/0", 1, 0),
					Cells.of("0/1", 0, 1),
					Cells.of("1/1", 1, 1)
				).stream().collect(toMap(cell -> cell.id(), cell -> cell)),
				Cells.of("0/0", 0, 0),
				Cells.of("1/1", 1, 1));

		// assert the value returned isn't null
		assertNotNull(boxBlock);
		assertNotNull(boxBlock1);

		// assert that the returned blocks were initialized correctly
		assertEquals(4, boxBlock.size());
		assertEquals(4, boxBlock.cells().size());
		assertEquals(2, boxBlock.blockColumns());
		assertEquals(2, boxBlock.blockRows());
		assertEquals("4", boxBlock.id());
		assertEquals(Cells.of("2/2", 2, 2), boxBlock.startCell());
		assertEquals(Cells.of("3/3", 3, 3), boxBlock.endCell());

		assertEquals(4, boxBlock1.size());
		assertEquals(4, boxBlock1.cells().size());
		assertEquals(2, boxBlock1.blockColumns());
		assertEquals(2, boxBlock1.blockRows());
		assertEquals("1", boxBlock1.id());
		assertEquals(Cells.of("0/0", 0, 0), boxBlock1.startCell());
		assertEquals(Cells.of("1/1", 1, 1), boxBlock1.endCell());

		// assert that comparisons of the two blocks works as expected
		assertNotEquals(boxBlock, boxBlock1);
		assertTrue(boxBlock.compareTo(boxBlock1) > 0);

		// assert that the returned blocks have the expected cells
		assertTrue(boxBlock.cells().containsKey("3/2"));
		assertTrue(boxBlock.cells().containsKey("2/3"));
		assertFalse(boxBlock.cells().containsKey("0/3"));

		assertTrue(boxBlock1.cells().containsKey("1/0"));
		assertTrue(boxBlock1.cells().containsKey("0/1"));
		assertFalse(boxBlock1.cells().containsKey("2/2"));
	}

	/**
	 * Test {@link CellGroups#boxBlockOf(String, int, java.util.Map, Cell, int, int)} throws the appropriate
	 * exceptions incase of invalid parameters.
	 * 
	 * @see #testboxBlockOf1Exceptions()
	 */
	@Test
	public void testboxBlockOfExceptions() {
		// assert that an IllegalException is thrown where appropriate
		assertThrows(IllegalArgumentException.class, () -> 
			CellGroups.boxBlockOf(
					"4", 0,
					Arrays.asList(
						Cells.of("2/2", 2, 2),
						Cells.of("3/2", 3, 2),
						Cells.of("2/3", 2, 3),
						Cells.of("3/3", 3, 3)
					).stream().
					collect(toMap(cell -> cell.id(), cell -> cell)),
					Cells.of("2/2", 2, 2),
					2, 2
			)
		); // assert that we can't pass a size of 0
		assertThrows(IllegalArgumentException.class, () -> 
			CellGroups.boxBlockOf(
					"4", 4,
					Arrays.asList(
						Cells.of("2/2", 2, 2),
						Cells.of("3/2", 3, 2),
						Cells.of("3/3", 3, 3)
					).stream().
					collect(toMap(cell -> cell.id(), cell -> cell)),
					Cells.of("2/2", 2, 2),
					2, 2
			)
		); // assert that # of cells given is equal to size given
		assertThrows(IllegalArgumentException.class, () -> 
			CellGroups.boxBlockOf(
					"4", 4,
					Arrays.asList(
						Cells.of("2/2", 2, 2),
						Cells.of("3/2", 3, 2),
						Cells.of("2/3", 2, 3),
						Cells.of("3/3", 3, 3)
					).stream().
					collect(toMap(cell -> cell.id(), cell -> cell)),
					Cells.of("2/2", 2, 2),
					0, 2
			)
		); // assert that blockRow >= 1
		assertThrows(IllegalArgumentException.class, () ->
			CellGroups.boxBlockOf(
					"4", 4,
					Arrays.asList(
						Cells.of("2/2", 2, 2),
						Cells.of("3/2", 3, 2),
						Cells.of("2/3", 2, 3),
						Cells.of("3/3", 3, 3)
					).stream().
					collect(toMap(cell -> cell.id(), cell -> cell)),
					Cells.of("2/2", 2, 2),
					2, 0
			)
		); // assert that blockColumn >= 1
		assertThrows(IllegalArgumentException.class, () ->
			CellGroups.boxBlockOf(
					"4", 4,
					Arrays.asList(
						Cells.of("2/2", 2, 2),
						Cells.of("3/2", 3, 2),
						Cells.of("2/3", 2, 3),
						Cells.of("3/3", 3, 3)
					).stream().
					collect(toMap(cell -> cell.id(), cell -> cell)),
					Cells.of("4/4", 4, 4),
					2, 2
			)
		); // assert that startCell is in the provided cells Map
		assertThrows(IllegalArgumentException.class, () ->
			CellGroups.boxBlockOf(
					"4", 4,
					Arrays.asList(
						Cells.of("2/2", 2, 2),
						Cells.of("3/2", 3, 2),
						Cells.of("2/3", 2, 3),
						Cells.of("3/3", 3, 3)
					).stream().
					collect(toMap(cell -> cell.id(), cell -> cell)),
					Cells.of("2/2", 4, 4),
					2, 1
			)
		); // assert that startCell is in the provided cells Map

		// assert that a SudokuException is thrown where appropriate
		assertThrows(SudokuException.class, () ->
			CellGroups.boxBlockOf(
					"4", 4,
					Arrays.asList(
						Cells.of("2/2", 2, 2),
						Cells.of("3/2", 3, 2),
						Cells.of("2/3", 2, 3),
						Cells.of("3/3", 3, 3)
					).stream().
					collect(toMap(cell -> cell.id(), cell -> cell)),
					Cells.of("2/2", 4, 4),
					2, 2
			)
		); // assert that the endCell can be derived from the given properties

		// assert that a NullPointerException is thrown where appropriate
		assertThrows(NullPointerException.class, () -> 
			CellGroups.boxBlockOf(
					null, 4,
					Arrays.asList(
						Cells.of("2/2", 2, 2),
						Cells.of("3/2", 3, 2),
						Cells.of("2/3", 2, 3),
						Cells.of("3/3", 3, 3)
					).stream().
					collect(toMap(cell -> cell.id(), cell -> cell)),
					Cells.of("2/2", 2, 2),
					2, 2
			)
		); // assert that we can't pass a null id
		assertThrows(NullPointerException.class, () ->  CellGroups.boxBlockOf(
				"2", 4,
				Arrays.asList(
					Cells.of("2/2", 2, 2),
					Cells.of("3/2", 3, 2),
					Cells.of("2/3", 2, 3),
					Cells.of("3/3", 3, 3)
				).stream().
				collect(toMap(cell -> cell.id(), cell -> cell)), 
				null, 
				2, 2
			)
		); // assert that we can't pass a null startCell
		assertThrows(NullPointerException.class, () ->  CellGroups.boxBlockOf("2", 4, null, Cells.of("2/2", 2, 2), 2, 2)); // assert that we can't pass a null cells map
	}

	/**
	 * Test {@link CellGroups#boxBlockOf(String, int, java.util.Map, Cell, Cell)} throws the appropriate
	 * exceptions incase of invalid parameters.
	 * 
	 * @see #testboxBlockOfExceptions()
	 */
	@Test
	public void testboxBlockOf1Exceptions() {
		// assert that an IllegalException is thrown where appropriate
		assertThrows(IllegalArgumentException.class, () -> 
			CellGroups.boxBlockOf(
					"4", 0,
					Arrays.asList(
						Cells.of("2/2", 2, 2),
						Cells.of("3/2", 3, 2),
						Cells.of("2/3", 2, 3),
						Cells.of("3/3", 3, 3)
					).stream().
					collect(toMap(cell -> cell.id(), cell -> cell)),
					Cells.of("2/2", 2, 2),
					Cells.of("3/3", 3, 3)
			)
		); // assert that we can't pass a size of 0
		assertThrows(IllegalArgumentException.class, () -> 
			CellGroups.boxBlockOf(
					"4", 4,
					Arrays.asList(
						Cells.of("2/2", 2, 2),
						Cells.of("3/2", 3, 2),
						Cells.of("3/3", 3, 3)
					).stream().
					collect(toMap(cell -> cell.id(), cell -> cell)),
					Cells.of("2/2", 2, 2),
					Cells.of("3/3", 3, 3)
			)
		); // assert that # of cells given is equal to size given
		assertThrows(IllegalArgumentException.class, () ->
			CellGroups.boxBlockOf(
					"4", 4,
					Arrays.asList(
						Cells.of("2/2", 2, 2),
						Cells.of("3/2", 3, 2),
						Cells.of("2/3", 2, 3),
						Cells.of("3/3", 3, 3)
					).stream().
					collect(toMap(cell -> cell.id(), cell -> cell)),
					Cells.of("4/4", 2, 2),
					Cells.of("3/3", 3, 3)
			)
		); // assert that startCell is in the provided cells Map
		assertThrows(IllegalArgumentException.class, () ->
			CellGroups.boxBlockOf(
					"4", 4,
					Arrays.asList(
						Cells.of("2/2", 2, 2),
						Cells.of("3/2", 3, 2),
						Cells.of("2/3", 2, 3),
						Cells.of("3/3", 3, 3)
					).stream().
					collect(toMap(cell -> cell.id(), cell -> cell)),
					Cells.of("2/2", 2, 2),
					Cells.of("4/4", 3, 3)
			)
		); // assert that endCell is in the provided cells Map

		// assert that a NullPointerException is thrown where appropriate
		assertThrows(NullPointerException.class, () -> 
			CellGroups.boxBlockOf(
					null, 4,
					Arrays.asList(
						Cells.of("2/2", 2, 2),
						Cells.of("3/2", 3, 2),
						Cells.of("2/3", 2, 3),
						Cells.of("3/3", 3, 3)
					).stream().
					collect(toMap(cell -> cell.id(), cell -> cell)),
					Cells.of("2/2", 2, 2),
					Cells.of("3/3", 3, 3)
			)
		); // assert that we can't pass a null id
		assertThrows(NullPointerException.class, () ->  CellGroups.boxBlockOf(
				"2", 4,
				Arrays.asList(
					Cells.of("2/2", 2, 2),
					Cells.of("3/2", 3, 2),
					Cells.of("2/3", 2, 3),
					Cells.of("3/3", 3, 3)
				).stream().
				collect(toMap(cell -> cell.id(), cell -> cell)), 
				null, 
				Cells.of("3/3", 3, 3)
			)
		); // assert that we can't pass a null startCell
		assertThrows(NullPointerException.class, () ->  CellGroups.boxBlockOf(
				"2", 4,
				Arrays.asList(
					Cells.of("2/2", 2, 2),
					Cells.of("3/2", 3, 2),
					Cells.of("2/3", 2, 3),
					Cells.of("3/3", 3, 3)
				).stream().
				collect(toMap(cell -> cell.id(), cell -> cell)), 
				Cells.of("2/2", 2, 2), 
				null
			)
		); // assert that we can't pass a null endCell
		assertThrows(NullPointerException.class, () ->  CellGroups.boxBlockOf("2", 4, null, Cells.of("2/2", 2, 2), Cells.of("3/3", 3, 3))); // assert that we can't pass a null cells map
	}
}
