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

		// assert that the returned columns were initialized correctly
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
	public void testBoxBlockOf() {
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
	 * @see #testBoxBlockOf1Exceptions()
	 */
	@Test
	public void testBoxBlockOfExceptions() {
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

		// assert that a SudokuException is thrown where appropriate
		assertThrows(SudokuException.class, () ->
			CellGroups.boxBlockOf(
					"4", 3,
					Arrays.asList(
						Cells.of("2/2", 2, 2),
						Cells.of("3/2", 3, 2),
						Cells.of("3/3", 3, 3)
					).stream().
					collect(toMap(cell -> cell.id(), cell -> cell)),
					Cells.of("2/2", 4, 4),
					Cells.of("3/3", 3, 3)
			)
		); // assert that the product of the calculated blockRows and blockColumns is equal to the given size

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
	 * @see #testBoxBlockOfExceptions()
	 */
	@Test
	public void testBoxBlockOf1Exceptions() {
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

	/**
	 * Test {@link CellGroups#defaultRowFactory()} creates and returns the expected value.
	 */
	@Test
	public void testDefaultRowFactory() {
		// assert the the returned RowFactory isn't null
		assertNotNull(CellGroups.defaultRowFactory());

		// create test values
		Row<Object> row = CellGroups.defaultRowFactory().createRow(
				"1", 5,
				Arrays.asList(
					Cells.of("0/0", 0, 0),
					Cells.of("1/0", 1, 0),
					Cells.of("2/0", 2, 0),
					Cells.of("3/0", 3, 0),
					Cells.of("4/0", 4, 0)
				).stream().collect(toMap(cell -> cell.id(), cell -> cell)), 
			0);

		Row<Object> row1 = CellGroups.defaultRowFactory().createRow(
				"2", 3,
				Arrays.asList(
					Cells.of("0/1", 0, 1),
					Cells.of("1/1", 1, 1),
					Cells.of("2/1", 2, 1)
				).stream().collect(toMap(cell -> cell.id(), cell -> cell)), 
			1);

		// assert the values created aren't null
		assertNotNull(row);
		assertNotNull(row1);

		// assert that the returned rows were initialized correctly
		assertEquals(5, row.size());
		assertEquals(5, row.cells().size());
		assertEquals(0, row.y());
		assertEquals("1", row.id());
		assertEquals(Cells.of("0/0", 0, 0), row.startCell());
		assertEquals(Cells.of("4/0", 4, 0), row.endCell());

		assertEquals(3, row1.size());
		assertEquals(3, row1.cells().size());
		assertEquals(1, row1.y());
		assertEquals("2", row1.id());
		assertEquals(Cells.of("0/1", 0, 1), row1.startCell());
		assertEquals(Cells.of("2/1", 2, 1), row1.endCell()); 
	}

	/**
	 * Test {@link CellGroups#defaultColumnFactory()} creates and returns the expected value.
	 */
	@Test
	public void testDefaultColumnFactory() {
		// assert the the returned ColumnFactory isn't null
		assertNotNull(CellGroups.defaultColumnFactory());

		// create test values
		Column<Object> column = CellGroups.defaultColumnFactory().createColumn(
				"9", 10,
				Arrays.asList(
					Cells.of("9/0", 9, 0),
					Cells.of("9/1", 9, 1),
					Cells.of("9/2", 9, 2),
					Cells.of("9/3", 9, 3),
					Cells.of("9/4", 9, 4),
					Cells.of("9/5", 9, 5),
					Cells.of("9/6", 9, 6),
					Cells.of("9/7", 9, 7),
					Cells.of("9/8", 9, 8),
					Cells.of("9/9", 9, 9)
				).stream().collect(toMap(cell -> cell.id(), cell -> cell)), 
			9);

		Column<Object> column1 = CellGroups.columnOf(
				"1", 1,
				Arrays.asList(
					Cells.of("0/0", 0, 0)
				).stream().collect(toMap(cell -> cell.id(), cell -> cell)), 
			0);

		// assert the values created aren't null
		assertNotNull(column);
		assertNotNull(column1);

		// assert that the returned columns were initialized correctly
		assertEquals(10, column.size());
		assertEquals(10, column.cells().size());
		assertEquals(9, column.x());
		assertEquals("9", column.id());
		assertEquals(Cells.of("9/0", 9, 0), column.startCell());
		assertEquals(Cells.of("9/9", 9, 9), column.endCell());

		assertEquals(1, column1.size());
		assertEquals(1, column1.cells().size());
		assertEquals(0, column1.x());
		assertEquals("1", column1.id());
		assertEquals(Cells.of("0/0", 0, 0), column1.startCell());
		assertEquals(Cells.of("0/0", 0, 0), column1.endCell());
	}

	/**
	 * Test {@link CellGroups#defaultBoxBlockFactory()} creates and returns the expected value.
	 */
	@Test
	public void testDefaultBoxBlockFactory() {
		// assert the the returned BoxBlockFactory isn't null
		assertNotNull(CellGroups.defaultBoxBlockFactory());

		BoxBlock<Object> boxBlock = (BoxBlock<Object>) CellGroups.defaultBoxBlockFactory().createBlock(
			"4", 6,
			Arrays.asList(
				Cells.of("3/2", 3, 2),
				Cells.of("4/2", 4, 2),
				Cells.of("5/2", 5, 2),
				Cells.of("3/3", 3, 3),
				Cells.of("4/3", 4, 3),
				Cells.of("5/3", 5, 3)
			).stream().collect(toMap(cell -> cell.id(), cell -> cell)),
			Cells.of("3/2", 3, 2),
			Cells.of("5/3", 5, 3)
		);

		BoxBlock<Object> boxBlock1 = (BoxBlock<Object>) CellGroups.defaultBoxBlockFactory().createBlock(
			"3", 10,
			Arrays.asList(
				Cells.of("0/2", 0, 2),
				Cells.of("1/2", 1, 2),
				Cells.of("2/2", 2, 2),
				Cells.of("3/2", 3, 2),
				Cells.of("4/2", 4, 2),
				Cells.of("0/3", 0, 3),
				Cells.of("1/3", 1, 3),
				Cells.of("2/3", 2, 3),
				Cells.of("3/3", 3, 3),
				Cells.of("4/3", 4, 3)
			).stream().collect(toMap(cell -> cell.id(), cell -> cell)),
			Cells.of("0/2", 0, 2),
			Cells.of("4/3", 4, 3)
		);

		// assert the value returned isn't null
		assertNotNull(boxBlock);
		assertNotNull(boxBlock1);

		// assert that the returned blocks were initialized correctly
		assertEquals(6, boxBlock.size());
		assertEquals(6, boxBlock.cells().size());
		assertEquals(3, boxBlock.blockColumns());
		assertEquals(2, boxBlock.blockRows());
		assertEquals("4", boxBlock.id());
		assertEquals(Cells.of("3/2", 3, 2), boxBlock.startCell());
		assertEquals(Cells.of("5/3", 5, 3), boxBlock.endCell());

		assertEquals(10, boxBlock1.size());
		assertEquals(10, boxBlock1.cells().size());
		assertEquals(5, boxBlock1.blockColumns());
		assertEquals(2, boxBlock1.blockRows());
		assertEquals("3", boxBlock1.id());
		assertEquals(Cells.of("0/2", 0, 2), boxBlock1.startCell());
		assertEquals(Cells.of("4/3", 4, 3), boxBlock1.endCell());
	}

	/**
	 * Test {@link CellGroups#defaultUniqueCellGroupComparator()} performs comparisons as expected.
	 */
	@Test
	public void testDefaultUniqueCellGroupComparator() {
		// create test values
		CellGroups.defaultUniqueCellGroupComparator();
	}
}
