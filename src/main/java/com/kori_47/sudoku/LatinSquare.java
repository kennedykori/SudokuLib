/**
 * 
 */
package com.kori_47.sudoku;

import static java.util.Objects.requireNonNull;

import java.util.Map;
import java.util.Optional;

/**
 * A {@code LatinSquare} is an <i>n × n</i> matrix filled with <i>n</i> different {@link Symbol}s,
 * such that entries in each {@link Row} and {@link Column} are distinct. This interface describes a
 * {@code LatinSquare} where <i>n</i> is the {@link #size()} of the {@code LatinSquare} and {@link #symbols()}
 * is the set of {@code Symbol}s that can be used as entries on this {@code LatinSquare}.
 * 
 * @param <V> the type of value held by the {@link Symbol}s supported by this {@code LatinSquare}.
 * 
 * @author <a href="https://github.com/kennedykori">Kennedy Kori</a>
 *
 * @since Oct 19, 2019, 12:37:48 AM
 * 
 * @see Symbol
 * @see Row
 * @see Column
 * @see Cell
 * @see CellGroup
 * @see InterpolatableCellGroup
 */
public interface LatinSquare<V> extends InterpolatableCellGroup<V> {

	/**
	 * {@inheritDoc}
	 * 
	 * @implSpec
	 * The default implementation uses {@link Formattables#toXY(LatinSquare)} to construct the desired
	 * representation.
	 */
	@Override
	default String toXY() {
		return Formattables.toXY(this);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @implSpec
	 * The default implementation uses {@link Formattables#toXYV(LatinSquare)} to construct the desired
	 * representation.
	 */
	@Override
	default String toXYV() {
		return Formattables.toXYV(this);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @implSpec
	 * The default implementation uses {@link Formattables#toXYI(LatinSquare)} to construct the desired
	 * representation.
	 */
	@Override
	default String toXYI() {
		return Formattables.toXYI(this);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @implSpec
	 * The default implementation uses {@link Formattables#toV(LatinSquare)} to construct the desired
	 * representation.
	 */
	@Override
	default String toV() {
		return Formattables.toV(this);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @implSpec
	 * The default implementation uses {@link Formattables#toI(LatinSquare)} to construct the desired
	 * representation.
	 */
	@Override
	default String toI() {
		return Formattables.toI(this);
	}

	/**
	 * Mutates all the {@link Cell}s in this {@code LatinSquare} by setting them to the
	 * {@link #emptySymbol() empty symbol} associated with this {@code LatinSquare}. This
	 * mutation also clears the notes in each {@code Cell} and reverts any initial {@code Cell}
	 * to a start {@code Cell}.
	 * 
	 * @implSpec
	 * The default implementation is equivalent to, for this {@code latinSquare}:
	 * <pre> {@code
	 * latinSquare.cells().values().forEach(cell -> cell.reset(emptySymbol()));
	 * }
	 * </pre>
	 */
	default void reset() {
		cells().values().forEach(cell -> cell.reset(emptySymbol()));
	}

	/**
	 * Mutates all the {@link Cell}s in this {@code LatinSquare} by setting each {@code Cell}s
	 * {@link Symbol} to a {@code null} value. This mutation also clears the notes in each {@code Cell}
	 * and reverts any initial {@code Cell} to a start {@code Cell}.
	 * 
	 * @implSpec
	 * The default implementation is equivalent to, for this {@code latinSquare}:
	 * <pre> {@code
	 * latinSquare.cells().values().forEach(cell -> cell.clear());
	 * }
	 * </pre>
	 */
	default void clear() {
		cells().values().forEach(cell -> cell.clear());
	}
	
	/**
	 * Sets the value of the given {@link Cell} to that of the given {@link Symbol} if and only if the given
	 * {@code Cell} and {@code Symbol} are part of this {@code LatinSquare}, otherwise, a {@link SudokuException}
	 * is thrown. It should be noted that this method isn't required to make any other checks on the {@code Cell}
	 * and as such, other exceptions such as {@link ClueCellModificationException} maybe thrown if the given
	 * {@code Cell} is a clue {@code Cell}.
	 * 
	 * @param cell the {@code Cell} whose value we want to change.
	 * @param symbol the {@code Symbol} to set the given {@code Cell}.
	 * 
	 * @throws NullPointerException if any of the given arguments is/are {@code null}.
	 * @throws SudokuException if the given {@code Cell} or {@code Symbol} is not part of this {@code LatinSquare}.
	 * 
	 * @apiNote
	 * {@link LatinSquare} clients are encouraged to use this method when changing the value of a {@code Cell} as
	 * it ensures that the {@code Symbol} set on the {@code Cell} is part of this {@code LatinSquare}'s {@link #symbols() symbols}
	 * thus preserving the integrity of this {@code LatinSquare}.
	 * 
	 * @implSpec
	 * This method first checks that the given {@code Cell} isn't {@code null} and if it is, throws a {@link NullPointerException}.
	 * It then proceeds to check that the given {@code Cell} is part of this {@code LatinSquare} and throws a {@link SudokuException}
	 * if not. The method then checks that the given {@code Symbol} isn't {@code null} and is part if this {@code LatinSquare} and if
	 * not throws a {@code NullPointerException} and {@code SudokuException} respectively. If everything checks out, the method finishes
	 * by setting the given {@code Symbol} on the given {@code Cell}.
	 * 
	 * <p>
	 * The above steps can be summarized as, for this {@code latinSquare}:
	 * <pre> {@code
	 * Objects.requireNonNull(cell);
	 * if (!latinSquare.cells().containsKey(cell.id()))
	 * 	throw new SudokuException();
	 * Objects.requireNonNull(symbol);
	 * if (!latinSquare.symbols().containsKey(symbol.id()))
	 * 	throw new SudokuException();
	 * 
	 * cell.changeSymbol(symbol);
	 * }
	 * </pre>
	 */
	default void changeSymbol(Cell<V> cell, Symbol<V> symbol) {
		// start by validating that the given Cell is part of this LatinSquare
		if (!cells().containsKey(requireNonNull(cell, "cell cannot be null").id()))
			throw new SudokuException("The given cell(" + cell + ") isn't part of this LatinSquare.");
		// start by validating that the given Symbol is part of this LatinSquare
		if (!symbols().containsKey(requireNonNull(symbol, "symbol cannot be null").id()))
			throw new SudokuException("The given symbol(" + cell + ") isn't one of the Symbols of this LatinSquare.");
		// change the Cell's Symbol
		cell.changeSymbol(symbol);
	}
	
	/**
	 * Returns an {@link Optional} describing the {@link Row} in which the given {@link Cell} belongs to, or
	 * an empty {@code Optional} if the {@code Cell} doesn't belong to this {@code LatinSquare}.
	 * 
	 * @param cell the {@code Cell} whose parent {@code Row} we want to find.
	 * 
	 * @return an {@code Optional} describing the {@code Row} that the given {@code Cell} belongs to, or
	 * an empty {@code Optional} if the {@code Cell} doesn't belong to this {@code LatinSquare}.
	 * 
	 * @throws NullPointerException if the given {@code Cell} is {@code null}.
	 * 
	 * @implSpec
	 * The default implementation is equivalent to, for this {@code latinSquare}:
	 * <pre> {@code
	 * latinSquare.rows().values().stream().filter(row -> row.y() == cell.y()).findFirst();
	 * }
	 * </pre>
	 */
	default Optional<Row<V>> locateParentRow(Cell<V> cell) {
		requireNonNull(cell, "cell cannot be null.");
		return rows().values().stream().filter(row -> row.y() == cell.y()).findFirst();
	}
	
	/**
	 * Returns an {@link Optional} describing the {@link Column} in which the given {@link Cell} belongs to, or
	 * an empty {@code Optional} if the {@code Cell} doesn't belong to this {@code LatinSquare}.
	 * 
	 * @param cell the {@code Cell} whose parent {@code Column} we want to find.
	 * 
	 * @return an {@code Optional} describing the {@code Column} that the given {@code Cell} belongs to, or
	 * an empty {@code Optional} if the {@code Cell} doesn't belong to this {@code LatinSquare}.
	 * 
	 * @throws NullPointerException if the given {@code Cell} is {@code null}.
	 * 
	 * @implSpec
	 * The default implementation is equivalent to, for this {@code latinSquare}:
	 * <pre> {@code
	 * latinSquare.columns().values().stream().filter(column -> column.x() == cell.x()).findFirst();
	 * }
	 * </pre>
	 */
	default Optional<Column<V>> locateParentColumn(Cell<V> cell) {
		requireNonNull(cell, "cell cannot be null.");
		return columns().values().stream().filter(column -> column.x() == cell.x()).findFirst();
	}

	/**
	 * Returns the hash code value for this {@code LatinSquare}. The hash code of a {@code LatinSquare} should
	 * be derived from the hash codes of the following properties of a {@code LatinSquare}: 
	 * <ul>
	 * <li>The hash code value of this {@code LatinSquare}'s <i>{@link #size() size}</i> as returned by {@link Integer#hashCode(int)}</li>
	 * <li>The hash code value of this {@code LatinSquare}'s <i>{@link #emptySymbol() emptySymbol}</i>.</li>
	 * <li>The hash code value of this {@code LatinSquare}'s <i>{@link #cells() cells}</i> {@code Map}.</li>
	 * <li>The hash code value of this {@code LatinSquare}'s <i>{@link #rows() rows}</i> {@code Map}.</li>
	 * <li>The hash code value of this {@code LatinSquare}'s <i>{@link #columns() columns}</i> {@code Map}.</li>
	 * <li>The hash code value of this {@code LatinSquare}'s <i>{@link #symbols() symbols}</i> {@code Map}.</li>
	 * </ul>
	 * 
	 * @return the hash code value of this {@code LatinSquare}.
	 */
	@Override
	int hashCode();

	/**
	 * Compares the specified object with this {@code LatinSquare} for equality. Returns {@code true} if the
	 * given object is also a {@code LatinSquare} and the two {@code LatinSquare}s are identical. Two
	 * {@code LatinSquare}s are said to be equal if each of the following of their properties are also equal:
	 * <ul>
	 * <li>The <i>{@link #size() sizes}</i> of the {@code LatinSquare}s</li>
	 * <li>The <i>{@link #emptySymbol() emptySymbols}</i> of the {@code LatinSquare}s.</li>
	 * <li>The <i>{@link #cells() cells}</i> {@code Map}s of the {@code LatinSquare}s.</li>
	 * <li>The <i>{@link #rows() rows}</i> {@code Map}s of the {@code LatinSquare}s.</li>
	 * <li>The <i>{@link #columns() columns}</i> {@code Map}s of the {@code LatinSquare}s.</li>
	 * <li>The <i>{@link #symbols() symbols}</i> {@code Map}s of the {@code LatinSquare}s.</li>
	 * </ul>
	 * 
	 * @param obj the object to compare for equality with this {@code LatinSquare}.
	 * 
	 * @return {@code true} if this {@code LatinSquare} is equal to {@code obj} argument, {@code false} otherwise.
	 */
	@Override
	boolean equals(Object obj);
	
	/**
	 * Permutes this {@code LatinSquare}'s {@link Cell} values so that after this method returns, the {@code LatinSquare} appears
	 * to have been turned upside down.
	 * 
	 * <p>
	 * Cosider the following {@code LatinSquare}:
	 * <pre>
	 * 		+---+---+---+---+
	 * 		| 1 | 2 | 3 | 4 |
	 * 		+---+---+---+---+
	 * 		| 2 | 3 | 4 | 1 |
	 * 		+---+---+---+---+
	 * 		| 3 | 4 | 1 | 2 |
	 * 		+---+---+---+---+
	 * 		| 4 | 1 | 2 | 3 |
	 * 		+---+---+---+---+
	 * </pre>
	 * 
	 * After calling {@code flipHorizontally()}, the {@code LatinSquare} should have the following {@code Cell} values:
	 * <pre>
	 * 		+---+---+---+---+
	 * 		| 4 | 1 | 2 | 3 |
	 * 		+---+---+---+---+
	 * 		| 3 | 4 | 1 | 2 |
	 * 		+---+---+---+---+
	 * 		| 2 | 3 | 4 | 1 |
	 * 		+---+---+---+---+
	 * 		| 1 | 2 | 3 | 4 |
	 * 		+---+---+---+---+
	 * </pre>
	 * 
	 * @see #flipVertically()
	 */
	void flipHorizontally();

	/**
	 * Permutes this {@code LatinSquare}'s {@link Cell} values so that after this method returns, the {@code LatinSquare} appears
	 * to have been turned sideways.
	 * 
	 * <p>
	 * Cosider the following {@code LatinSquare}:
	 * <pre>
	 * 		+---+---+---+---+
	 * 		| 1 | 2 | 3 | 4 |
	 * 		+---+---+---+---+
	 * 		| 2 | 3 | 4 | 1 |
	 * 		+---+---+---+---+
	 * 		| 3 | 4 | 1 | 2 |
	 * 		+---+---+---+---+
	 * 		| 4 | 1 | 2 | 3 |
	 * 		+---+---+---+---+
	 * </pre>
	 * 
	 * After calling {@code flipVertically()}, the {@code LatinSquare} should have the following {@code Cell} values:
	 * <pre>
	 * 		+---+---+---+---+
	 * 		| 4 | 3 | 2 | 1 |
	 * 		+---+---+---+---+
	 * 		| 1 | 4 | 3 | 2 |
	 * 		+---+---+---+---+
	 * 		| 2 | 1 | 4 | 3 |
	 * 		+---+---+---+---+
	 * 		| 3 | 2 | 1 | 4 |
	 * 		+---+---+---+---+
	 * </pre>
	 * 
	 * @see #flipHorizontally()
	 */
	void flipVertically();

	/**
	 * Returns a new {@code LatinSquare} that is exactly identical to this one, i.e has the same properties, and 
	 * cell values as this one.
	 * 
	 * @return a new {@code LatinSquare} that is exactly identical to this one.
	 */
	LatinSquare<V> copy();

	/**
	 * Returns the {@link CellFactory} used by this {@code LatinSquare} to create new {@link Cell}s.
	 * 
	 * @return the {@code CellFactory} used by this {@code LatinSquare} to create new {@code Cell}s.
	 */
	CellFactory<V> cellFactory();

	/**
	 * Returns the {@link RowFactory} used by this {@code LatinSquare} to create new {@link Row}s.
	 * 
	 * @return the {@code RowFactory} used by this {@code LatinSquare} to create new {@code Row}s.
	 */
	RowFactory<V> rowFactory();

	/**
	 * Returns the {@link ColumnFactory} used by this {@code LatinSquare} to create new {@link Column}s.
	 * 
	 * @return the {@code ColumnFactory} used by this {@code LatinSquare} to create new {@code Column}s.
	 */
	ColumnFactory<V> columnFactory();
	
	/**
	 * Returns a {@code Map} of the {@link Row}s contained in this {@code LatinSquare}. Modification of the 
	 * returned {@code Map} should not alter the {@code Row}s of this {@code LatinSquare}. Implementations of this 
	 * interface can also choose to return an immutable {@code Map} instead to prevent modifications.
	 * 
	 * @return a {@code Map} of the {@code Row}s contained in this {@code LatinSquare}.
	 */
	Map<String, Row<V>> rows();

	/**
	 * Returns a {@code Map} of the {@link Column}s contained in this {@code LatinSquare}. Modification of the 
	 * returned {@code Map} should not alter the {@code Column}s of this {@code LatinSquare}. Implementations of
	 * this interface can also choose to return an immutable {@code Map} instead to prevent modifications.
	 * 
	 * @return a {@code Map} of the {@code Column}s contained in this {@code LatinSquare}.
	 */
	Map<String, Column<V>> columns();

	/**
	 * Returns the empty {@link Symbol} used by this {@code LatinSquare}.
	 * 
	 * @return the empty {@code Symbol} used by this {@code LatinSquare}.
	 */
	Symbol<V> emptySymbol();

	/**
	 * <p>Returns a {@code Map} of the {@link Symbol}s used to fill this {@code LatinSquare}'s {@link Cell}s. The
	 * returned {@code Map}'s size must be equal to the size of this {@code LatinSquare}.
	 * 
	 * <p> Modification of the returned {@code Map} should not alter the {@code Symbol}s of this {@code LatinSquare}.
	 * Implementations of this interface can also choose to return an immutable {@code Map} instead to prevent
	 * modifications.
	 * 
	 * @return a {@code Map} of the {@code Symbol}s contained in this {@code LatinSquare}.
	 */
	Map<Integer, Symbol<V>> symbols();
}
