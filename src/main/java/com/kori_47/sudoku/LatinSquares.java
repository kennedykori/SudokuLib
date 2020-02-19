/**
 * 
 */
package com.kori_47.sudoku;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toMap;

import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

import com.kori_47.sudoku.Sudoku.SudokuVariant;

/**
 * This class consists exclusively of static methods that create and operate on {@link LatinSquare}s, including
 * {@link Sudoku}s.
 * 
 * @author <a href="https://github.com/kennedykori">Kennedy Kori</a>
 *
 * @since Wed, 5 Feb 2020 01:51:45
 * 
 * @see LatinSquare
 * @see Sudoku
 */
public final class LatinSquares {

	/**
	 * Creates a new {@link LatinSquare} from the properties of the given {@code LatinSquare}.
	 * 
	 * @param <V> the type of values held by the {@link Symbol}s supported by the new {@code LatinSquare}.
	 * 
	 * @param latinSquare the {@code LatinSquare} from whose properties to create a new {@code LatinSquare} from.
	 * 
	 * @return a new {@code LatinSquare} instance created from the properties of the provided {@code LatinSquare}.
	 * 
	 * @throws NullPointerException if {@code latinSquare} is {@code null}.
	 * 
	 * @see #latinSquareOf(int, Set, Symbol, CellFactory, RowFactory, ColumnFactory)
	 * 
	 * @implNote
	 * The returned {@code LatinSquare} instance will have the same characteristics as that created by the 
	 * {@link #latinSquareOf(int, Set, Symbol, CellFactory, RowFactory, ColumnFactory)} method. 
	 */
	public static final <V> LatinSquare<V> latinSquareOf(LatinSquare<V> latinSquare) {
		return new SimpleLatinSquare<V>(latinSquare);
	}
	
	/**
	 * Creates a new {@link LatinSquare} with the given properties.
	 * 
	 * @param <V> the type of values held by the {@link Symbol}s supported by the new {@code LatinSquare}.
	 * 
	 * @param size the size that the new {@code LatinSquare} should be.
	 * @param symbols the {@link Set} of {@link Symbol}s to use when filling this {@code LatinSquare} {@link Cell}s.
	 * @param emptySymbol the empty {@code Symbol} that the new {@code LatinSquare} should use.
	 * 
	 * @return a {@code LatinSquare} instance with the given properties.
	 * 
	 * @throws NullPointerException if any of the following arguments to this constructor are/is {@code null}.
	 * @throws IllegalArgumentException if {@code size} is less than {@code 1} and/or the size of {@code symbols} {@code Set}
	 * 			is less than {@code size}.
	 * 
	 * @see #latinSquareOf(int, Set, Symbol, CellFactory)
	 * 
	 * @implNote
	 * A call to this method is similar to calling {@link #latinSquareOf(int, Set, Symbol, CellFactory)} with the {@code cellFactory}
	 * instance gotten from calling {@link Cells#defaultCellFactory()}.
	 */
	public static final <V> LatinSquare<V> latinSquareOf(int size, Set<Symbol<V>> symbols, Symbol<V> emptySymbol) {
		return latinSquareOf(size, symbols, emptySymbol, Cells.defaultCellFactory());
	}
	
	/**
	 * Creates a new {@link LatinSquare} with the given properties.
	 * 
	 * @param <V> the type of values held by the {@link Symbol}s supported by the new {@code LatinSquare}.
	 * 
	 * @param size the size that the new {@code LatinSquare} should be.
	 * @param symbols the {@link Set} of {@link Symbol}s to use when filling this {@code LatinSquare} {@link Cell}s.
	 * @param emptySymbol the empty {@code Symbol} that the new {@code LatinSquare} should use.
	 * @param cellFactory the {@link CellFactory} that the new {@code LatinSquare} will use when creating new {@code Cell}s.
	 * 
	 * @return a {@code LatinSquare} instance with the given properties.
	 * 
	 * @throws NullPointerException if any of the following arguments to this constructor are/is {@code null}.
	 * @throws IllegalArgumentException if {@code size} is less than {@code 1} and/or the size of {@code symbols} {@code Set}
	 * 			is less than {@code size}.
	 * 
	 * @see #latinSquareOf(int, Set, Symbol, CellFactory, RowFactory, ColumnFactory)
	 * 
	 * @implNote
	 * A call to this method is similar to calling {@link #latinSquareOf(int, Set, Symbol, CellFactory, RowFactory, ColumnFactory)} with
	 * {@code rowFactory} and {@code columnFactory} instances gotten from calling {@link CellGroups#defaultRowFactory()} and
	 * {@link CellGroups#defaultColumnFactory()} respectfully.
	 */
	public static final <V> LatinSquare<V> latinSquareOf(int size, Set<Symbol<V>> symbols, Symbol<V> emptySymbol, CellFactory<V> cellFactory) {
		return latinSquareOf(size, symbols, emptySymbol, cellFactory, CellGroups.defaultRowFactory(), CellGroups.defaultColumnFactory());
	}
	
	/**
	 * Creates a new {@link LatinSquare} with the given properties.
	 * 
	 * @param <V> the type of values held by the {@link Symbol}s supported by the new {@code LatinSquare}.
	 * 
	 * @param size the size that the new {@code LatinSquare} should be.
	 * @param symbols the {@link Set} of {@link Symbol}s to use when filling this {@code LatinSquare} {@link Cell}s.
	 * @param emptySymbol the empty {@code Symbol} that the new {@code LatinSquare} should use.
	 * @param cellFactory the {@link CellFactory} that the new {@code LatinSquare} will use when creating new {@code Cell}s.
	 * @param rowFactory the {@link RowFactory} that the new {@code LatinSquare} will use when creating new {@link Row}s.
	 * @param columnFactory the {@link ColumnFactory} that the new {@code LatinSquare} will use when creating new {@link Column}s.
	 * 
	 * @return a {@code LatinSquare} instance with the given properties.
	 * 
	 * @throws NullPointerException if any of the following arguments to this constructor are/is {@code null}.
	 * @throws IllegalArgumentException if {@code size} is less than {@code 1} and/or the size of {@code symbols} {@code Set}
	 * 			is less than {@code size}.
	 * 
	 * @implNote
	 * The returned {@code LatinSquare} instance has the following characteristics:
	 * <ul>
	 * <li>The {@link LatinSquare#startCell() first} {@code Cell} of the {@code LatinSquare} has an x and y coordinate of {@code 0}.</li>
	 * <li>The {@link LatinSquare#endCell() last} {@code Cell} of the {@code LatinSquare} has an x and y coordinate of {@code size - 1}.</li>
	 * <li>
	 * The identifiers of the {@code Cell}s of the {@code LatinSquare} are generated from the x and y coordinates of a {@code Cell}
	 * separated by a forward slash. i.e the 1st {@code Cell} has an id of {@code "0/0"}, the 2nd has an id of {@code "1/0"} and so on. 
	 * </li>
	 * <li>
	 * 		The identifiers of the {@link Row}s and {@link Column}s of the {@code LatinSquare} are the {@code String} representations of their indices.
	 * 		i.e, the 1st {@code Row} and {@code Column} have an id of {@code "0"}, the 2nd have an id of {@code "1"}, etc.
	 * </li>
	 * <li>
	 * 		All the <i>view</i> methods of the {@code LatinSquare} return unmodifiable collections. e.g {@link LatinSquare#cells() cells()},
	 * 		{@link LatinSquare#rows() rows()}, {@link LatinSquare#columns() columns()}, {@link LatinSquare#symbols() symbols()}, etc, all
	 * 		return immutable {@code Map}s.
	 * </li>
	 * </ul>
	 * 
	 * <p>
	 * In addition, the {@link LatinSquare#hashCode() hashCode()} and {@link LatinSquare#equals(Object) equals(Object)} methods are implemented in
	 * accordance to their specification in the {@code LatinSquare} interface. It should also be noted that the {@code LatinSquare} instance returned
	 * is <i>not thread safe</i>.
	 */
	public static final <V> LatinSquare<V> latinSquareOf(int size, Set<Symbol<V>> symbols, Symbol<V> emptySymbol, CellFactory<V> cellFactory,
			 RowFactory<V> rowFactory, ColumnFactory<V> columnFactory) {
		return new SimpleLatinSquare<V>(size, symbols, emptySymbol, cellFactory, rowFactory, columnFactory);
	}

	/**
	 * Creates a new {@link Sudoku} from the properties of the given {@code Sudoku}.
	 * 
	 * @param <V> the type of values held by the {@link Symbol}s supported by the new {@code Sudoku}.
	 * 
	 * @param sudoku the {@code Sudoku} from whose propeties to create a new {@code Sudoku} instance from.
	 * 
	 * @return a new {@code Sudoku} instance created from the properties of the provided {@code Sudoku}.
	 * 
	 * @throws NullPointerException if the given {@code Sudoku} is {@code null}.
	 * 
	 * @see #sudokuOf(SudokuVariant, Set, Symbol, CellFactory, RowFactory, ColumnFactory, BlockFactory)
	 * 
	 * @implNote
	 * The returned {@code Sudoku} instance will have the same characteristics as that created by the 
	 * {@link #sudokuOf(SudokuVariant, Set, Symbol, CellFactory, RowFactory, ColumnFactory, BlockFactory)} method. 
	 */
	public static final <V> Sudoku<V> sudokuOf(Sudoku<V> sudoku) {
		return new SimpleSudoku<V>(sudoku);
	}

	/**
	 * Creates a new {@link Sudoku} with the given properties.
	 * 
	 * @param <V> the type of values held by the {@link Symbol}s supported by the new {@code Sudoku}.
	 * 
	 * @param variant the {@link SudokuVariant} that describes the new {@code Sudoku} to be created.
	 * @param symbols the {@link Set} of {@link Symbol}s to use when filling this {@code Sudoku}'s {@link Cell}s.
	 * @param emptySymbol the empty {@code Symbol} that the new {@code Sudoku} should use.
	 * 
	 * @return a {@code Sudoku} instance with the given properties.
	 * 
	 * @throws NullPointerException if any of the following arguments to this constructor are/is {@code null}.
	 * @throws IllegalArgumentException if the size of {@code symbols} {@code Set} is less than {@code variant.size()}.
	 * 
	 * @see #sudokuOf(SudokuVariant, Set, Symbol, CellFactory)
	 * 
	 * @implNote
	 * A call to this method is similar to calling {@link #sudokuOf(SudokuVariant, Set, Symbol, CellFactory)} with the {@code cellFactory}
	 * instance gotten from calling {@link Cells#defaultCellFactory()}.
	 */
	public static final <V> Sudoku<V> sudokuOf(SudokuVariant variant, Set<Symbol<V>> symbols, Symbol<V> emptySymbol) {
		return sudokuOf(variant, symbols, emptySymbol, Cells.defaultCellFactory());
	}

	/**
	 * Creates a new {@link Sudoku} with the given properties.
	 * 
	 * @param <V> the type of values held by the {@link Symbol}s supported by the new {@code Sudoku}.
	 * 
	 * @param variant the {@link SudokuVariant} that describes the new {@code Sudoku} to be created.
	 * @param symbols the {@link Set} of {@link Symbol}s to use when filling this {@code Sudoku}'s {@link Cell}s.
	 * @param emptySymbol the empty {@code Symbol} that the new {@code Sudoku} should use.
	 * @param cellFactory the {@link CellFactory} that the new {@code Sudoku} will use when creating new {@code Cell}s.
	 * 
	 * @return a {@code Sudoku} instance with the given properties.
	 * 
	 * @throws NullPointerException if any of the following arguments to this constructor are/is {@code null}.
	 * @throws IllegalArgumentException if the size of {@code symbols} {@code Set} is less than {@code variant.size()}.
	 * 
	 * @see #sudokuOf(SudokuVariant, Set, Symbol, CellFactory, RowFactory, ColumnFactory)
	 * 
	 * @implNote
	 * A call to this method is similar to calling {@link #sudokuOf(SudokuVariant, Set, Symbol, CellFactory, RowFactory, ColumnFactory)} with
	 * {@code rowFactory} and {@code columnFactory} instances gotten from calling {@link CellGroups#defaultRowFactory()} and
	 * {@link CellGroups#defaultColumnFactory()} respectfully.
	 */
	public static final <V> Sudoku<V> sudokuOf(SudokuVariant variant, Set<Symbol<V>> symbols, Symbol<V> emptySymbol, CellFactory<V> cellFactory) {
		return sudokuOf(variant, symbols, emptySymbol, cellFactory, CellGroups.defaultRowFactory(), CellGroups.defaultColumnFactory());
	}

	/**
	 * Creates a new {@link Sudoku} with the given properties.
	 * 
	 * @param <V> the type of values held by the {@link Symbol}s supported by the new {@code Sudoku}.
	 * 
	 * @param variant the {@link SudokuVariant} that describes the new {@code Sudoku} to be created.
	 * @param symbols the {@link Set} of {@link Symbol}s to use when filling this {@code Sudoku}'s {@link Cell}s.
	 * @param emptySymbol the empty {@code Symbol} that the new {@code Sudoku} should use.
	 * @param cellFactory the {@link CellFactory} that the new {@code Sudoku} will use when creating new {@code Cell}s.
	 * @param rowFactory the {@link RowFactory} that the new {@code Sudoku} will use when creating new {@link Row}s.
	 * @param columnFactory the {@link ColumnFactory} that the new {@code Sudoku} will use when creating new {@link Column}s.
	 * 
	 * @return a {@code Sudoku} instance with the given properties.
	 * 
	 * @throws NullPointerException if any of the following arguments to this constructor are/is {@code null}.
	 * @throws IllegalArgumentException if the size of {@code symbols} {@code Set} is less than {@code variant.size()}.
	 *
	 * @see #sudokuOf(SudokuVariant, Set, Symbol, CellFactory, RowFactory, ColumnFactory, BlockFactory)
	 * 
	 * @implNote
	 * A call to this method is similar to calling {@link #sudokuOf(SudokuVariant, Set, Symbol, CellFactory, RowFactory, ColumnFactory, BlockFactory)}
	 * with the {@code blockFactory}instance gotten from calling {@link CellGroups#defaultBoxBlockFactory()}.
	 */
	public static final <V> Sudoku<V> sudokuOf(SudokuVariant variant, Set<Symbol<V>> symbols, Symbol<V> emptySymbol, CellFactory<V> cellFactory,
			 RowFactory<V> rowFactory, ColumnFactory<V> columnFactory) {
		return sudokuOf(variant, symbols, emptySymbol, cellFactory, rowFactory, columnFactory, CellGroups.defaultBoxBlockFactory());
	}
	
	/**
	 * Creates a new {@link Sudoku} with the given properties.
	 * 
	 * @param <V> the type of values held by the {@link Symbol}s supported by the new {@code Sudoku}.
	 * 
	 * @param variant the {@link SudokuVariant} that describes the new {@code Sudoku} to be created.
	 * @param symbols the {@link Set} of {@link Symbol}s to use when filling this {@code Sudoku}'s {@link Cell}s.
	 * @param emptySymbol the empty {@code Symbol} that the new {@code Sudoku} should use.
	 * @param cellFactory the {@link CellFactory} that the new {@code Sudoku} will use when creating new {@code Cell}s.
	 * @param rowFactory the {@link RowFactory} that the new {@code Sudoku} will use when creating new {@link Row}s.
	 * @param columnFactory the {@link ColumnFactory} that the new {@code Sudoku} will use when creating new {@link Column}s.
	 * @param blockFactory the {@link BlockFactory} that the new {@code Sudoku} will use when creating new {@link BlockFactory}s.
	 * 
	 * @return a {@code Sudoku} instance with the given properties.
	 * 
	 * @throws NullPointerException if any of the following arguments to this constructor are/is {@code null}.
	 * @throws IllegalArgumentException if the size of {@code symbols} {@code Set} is less than {@code variant.size()}.
	 * 
	 * @see #sudokuOf(LatinSquare, SudokuVariant, BlockFactory)
	 * 
	 * @implNote
	 * The returned {@code Sudoku} instance has the following characteristics:
	 * <ul>
	 * <li>The {@link Sudoku#startCell() first} {@code Cell} of the {@code Sudoku} has an x and y coordinate of {@code 0}.</li>
	 * <li>The {@link Sudoku#endCell() last} {@code Cell} of the {@code Sudoku} has an x and y coordinate of {@code size - 1}.</li>
	 * <li>
	 * The identifiers of the {@code Cell}s of the {@code Sudoku} are generated from the x and y coordinates of a {@code Cell}
	 * separated by a forward slash. i.e the 1st {@code Cell} has an id of {@code "0/0"}, the 2nd has an id of {@code "1/0"} and so on. 
	 * </li>
	 * <li>
	 * 		The identifiers of the {@link Row}s and {@link Column}s of the {@code Sudoku} are the {@code String} representations of their indices.
	 * 		i.e, the 1st {@code Row} and {@code Column} have an id of {@code "0"}, the 2nd have an id of {@code "1"}, etc.
	 * </li>
	 * <li>
	 * 		All the <i>view</i> methods of the {@code Sudoku} return unmodifiable collections. e.g {@link Sudoku#cells() cells()},
	 * 		{@link Sudoku#rows() rows()}, {@link Sudoku#columns() columns()}, {@link Sudoku#symbols() symbols()}, etc, all
	 * 		return immutable {@code Map}s.
	 * </li>
	 * <li>The identifiers of the {@link Block}s of the {@code Sudoku} are determined by the given {@code SudokuVariant}.</li>
	 * </ul>
	 * 
	 * <p>
	 * In addition, the {@link Sudoku#hashCode() hashCode()} and {@link Sudoku#equals(Object) equals(Object)} methods are implemented in
	 * accordance to their specification in the {@code Sudoku} interface. It should also be noted that the {@code Sudoku} instance returned
	 * is <i>not thread safe</i>.
	 */
	public static final <V> Sudoku<V> sudokuOf(SudokuVariant variant, Set<Symbol<V>> symbols, Symbol<V> emptySymbol, CellFactory<V> cellFactory,
			 RowFactory<V> rowFactory, ColumnFactory<V> columnFactory, BlockFactory<V> blockFactory) {
		return new SimpleSudoku<V>(variant, symbols, emptySymbol, cellFactory, rowFactory, columnFactory, blockFactory);
	}

	/**
	 * <p>
	 * Creates a new {@link Sudoku} from the given {@link LatinSquare}, {@link SudokuVariant} and {@link BlockFactory}. The given {@code LatinSquare}
	 * must be of the same size as the value returned by the {@code size()} method of the given {@code SudokuVariant}.
	 * 
	 * <p>
	 * The provided {@code LatinSquare} is used as the {@code Sudoku}'s backing {@code LatinSquare} and clients can provide different backing
	 * {@code LatinSquare}s to customize the behaviour of the {@code Sudoku}, i.e how {@link Cell}s, {@link Row}s and {@link Column}s are initialized
	 * and stored, how {@link LatinSquare#flipHorizontally() flipHorizontally()} and {@link LatinSquare#flipVertically() flipVertically()} are
	 * implemented, etc.
	 * 
	 * @param <V> the type of values held by the {@link Symbol}s supported by the new {@code Sudoku}.
	 * 
	 * @param latinSquare the {@code LatinSquare} from which to create a new {@code Sudoku} from.
	 * @param variant the {@code SudokuVariant} that describes the new {@code Sudoku}.
	 * 
	 * @return a {@code Sudoku} instance with the given properties.
	 * 
	 * @see #sudokuOf(LatinSquare, SudokuVariant, BlockFactory)
	 * 
	 * @throws NullPointerException if any of the given arguments are {@code null}.
	 * @throws IllegalArgumentException if the size of the given {@code LatinSquare} is not equal to size of the given {@code SudokuVariant}.
	 * 
	 * @implNote
	 * A call to this method is similar to calling {@link #sudokuOf(LatinSquare, SudokuVariant, BlockFactory)} with the {@code blockFactory}
	 * instance gotten from calling {@link CellGroups#defaultBoxBlockFactory()}.
	 */
	public static final <V> Sudoku<V> sudokuOf(LatinSquare<V> latinSquare, SudokuVariant variant) {
		return sudokuOf(latinSquare, variant, CellGroups.defaultBoxBlockFactory());
	}
	
	/**
	 * <p>
	 * Creates a new {@link Sudoku} from the given {@link LatinSquare}, {@link SudokuVariant} and {@link BlockFactory}. The given {@code LatinSquare}
	 * must be of the same size as the value returned by the {@code size()} method of the given {@code SudokuVariant}.
	 * 
	 * <p>
	 * The provided {@code LatinSquare} is used as the {@code Sudoku}'s backing {@code LatinSquare} and clients can provide different backing
	 * {@code LatinSquare}s to customize the behaviour of the {@code Sudoku}, i.e how {@link Cell}s, {@link Row}s and {@link Column}s are initialized
	 * and stored, how {@link LatinSquare#flipHorizontally() flipHorizontally()} and {@link LatinSquare#flipVertically() flipVertically()} are
	 * implemented, etc.
	 * 
	 * @param <V> the type of values held by the {@link Symbol}s supported by the new {@code Sudoku}.
	 * 
	 * @param latinSquare the {@code LatinSquare} from which to create a new {@code Sudoku} from.
	 * @param variant the {@code SudokuVariant} that describes the new {@code Sudoku}.
	 * @param blockFactory the {@code BlockFactory} that will be used to create the new {@code Sudoku}'s {@code Block}s.
	 * 
	 * @return a {@code Sudoku} instance with the given properties.
	 * 
	 * @see #latinSquareOf(int, Set, Symbol, CellFactory, RowFactory, ColumnFactory)
	 * 
	 * @throws NullPointerException if any of the given arguments are {@code null}.
	 * @throws IllegalArgumentException if the size of the given {@code LatinSquare} is not equal to size of the given {@code SudokuVariant}.
	 * 
	 * @implNote
	 * The characteristics of the returned {@code Sudoku} instance are largely depend on the provided {@code LatinSquare} and {@code SudokuVariant}.
	 */
	public static final <V> Sudoku<V> sudokuOf(LatinSquare<V> latinSquare, SudokuVariant variant, BlockFactory<V> blockFactory) {
		return new CompositeSudoku<V>(latinSquare, variant, blockFactory);
	}
	
	/**
	 * Returns the hash code of the given {@link LatinSquare}. The hash code of the {@code LatinSquare} should be derived from the
	 * hash codes of the following properties of a {@code LatinSquare}:
	 * <ul>
	 * <li>The hash code value of this {@code LatinSquare}'s <i>{@link LatinSquare#size() size}</i> as returned by {@link Integer#hashCode(int)}</li>
	 * <li>The hash code value of this {@code LatinSquare}'s <i>{@link LatinSquare#emptySymbol() emptySymbol}</i>.</li>
	 * <li>The hash code value of this {@code LatinSquare}'s <i>{@link LatinSquare#cells() cells}</i> {@code Map}.</li>
	 * <li>The hash code value of this {@code LatinSquare}'s <i>{@link LatinSquare#rows() rows}</i> {@code Map}.</li>
	 * <li>The hash code value of this {@code LatinSquare}'s <i>{@link LatinSquare#columns() columns}</i> {@code Map}.</li>
	 * <li>The hash code value of this {@code LatinSquare}'s <i>{@link LatinSquare#symbols() symbols}</i> {@code Map}.</li>
	 * </ul>
	 * 
	 * <p>
	 * The hash code value returned by this method is guaranteed to be obey the contract of the {@link LatinSquare#hashCode hashCode}
	 * method as defined in the {@code LatinSquare} interface.
	 * 
	 * @param latinSquare the {@code LatinSquare} whose hash code we are interested in.
	 * 
	 * @return the hash code of the given {@code LatinSquare}.
	 * 
	 * @throws NullPointerException if {@code latinSquare} is {@code null}.
	 * 
	 * @see LatinSquare#hashCode()
	 */
	public static final int latinSquareHashCode(LatinSquare<?> latinSquare) {
		requireNonNull(latinSquare, "latinSquare cannot be null.");
		int hashCode = Integer.hashCode(latinSquare.size());
		hashCode = 31 * hashCode + latinSquare.emptySymbol().hashCode();
		hashCode = 31 * hashCode + latinSquare.cells().hashCode();
		hashCode = 31 * hashCode + latinSquare.rows().hashCode();
		hashCode = 31 * hashCode + latinSquare.columns().hashCode();
		hashCode = 31 * hashCode + latinSquare.symbols().hashCode();
		return hashCode;
	}
	
	/**
	 * Compares the given {@code LatinSquare} with the given {@code Object} for equality. Returns {@code true} if the
	 * given object is also a {@code LatinSquare} and the two {@code LatinSquare}s are identical. Two
	 * {@code LatinSquare}s are said to be equal if each of the following of their properties are also equal:
	 * <ul>
	 * <li>The <i>{@link LatinSquare#size() sizes}</i> of the {@code LatinSquare}s</li>
	 * <li>The <i>{@link LatinSquare#emptySymbol() emptySymbols}</i> of the {@code LatinSquare}s.</li>
	 * <li>The <i>{@link LatinSquare#cells() cells}</i> {@code Map}s of the {@code LatinSquare}s.</li>
	 * <li>The <i>{@link LatinSquare#rows() rows}</i> {@code Map}s of the {@code LatinSquare}s.</li>
	 * <li>The <i>{@link LatinSquare#columns() columns}</i> {@code Map}s of the {@code LatinSquare}s.</li>
	 * <li>The <i>{@link LatinSquare#symbols() symbols}</i> {@code Map}s of the {@code LatinSquare}s.</li>
	 * </ul>
	 * 
	 * <p>
	 * This {@code equals} implementation is guaranteed to obey the contract of the {@link LatinSquare#equals(Object) equals}
	 * method as defined in the {@code LatinSquare} interface.
	 * 
	 * @param latinSquare the {@code LatinSquare} to compare to the given object for equality. Must <b>NOT</b> be {@code null}.
	 * @param obj the object to compare for equality with the given {@code LatinSquare}. Maybe {@code null}.
	 * 
	 * @return {@code true} if the given {@code LatinSquare} is equal to the given object, {@code false} otherwise.
	 * 
	 * @throws NullPointerException if {@code latinSquare} is {@code null}.
	 * 
	 * @see LatinSquare#equals(Object)
	 */
	public static final boolean latinSquareEquals(LatinSquare<?> latinSquare, Object obj) {
		requireNonNull(latinSquare, "latinSquare cannot be null.");
		if (latinSquare == obj) return true;
		if (!(obj instanceof LatinSquare)) return false;
		LatinSquare<?> _obj = (LatinSquare<?>) obj;
		return latinSquare.size() == _obj.size() && latinSquare.emptySymbol().equals(_obj.emptySymbol()) && latinSquare.symbols().equals(_obj.symbols())
				&& latinSquare.columns().equals(_obj.columns()) && latinSquare.rows().equals(_obj.rows()) && latinSquare.cells().equals(_obj.cells());
	}
	
	/**
	 * Returns the hash code of the given {@link Sudoku}. The hash code of the {@code Sudoku} should be derived from the hash codes
	 * of the following properties of a {@code Sudoku}:
	 * <ul>
	 * <li>The hash code value of this {@code Sudoku}'s <i>{@link Sudoku#variant() variant}</i>.</li>
	 * <li>The hash code value of this {@code Sudoku}'s <i>{@link Sudoku#emptySymbol() emptySymbol}</i>.</li>
	 * <li>The hash code value of this {@code Sudoku}'s <i>{@link Sudoku#cells() cells}</i> {@code Map}.</li>
	 * <li>The hash code value of this {@code Sudoku}'s <i>{@link Sudoku#rows() rows}</i> {@code Map}.</li>
	 * <li>The hash code value of this {@code Sudoku}'s <i>{@link Sudoku#blocks() blocks}</i> {@code Map}.</li>
	 * <li>The hash code value of this {@code Sudoku}'s <i>{@link Sudoku#columns() columns}</i> {@code Map}.</li>
	 * <li>The hash code value of this {@code Sudoku}'s <i>{@link Sudoku#symbols() symbols}</i> {@code Map}.</li>
	 * </ul>
	 * 
	 * <p>
	 * The hash code value returned by this method is guaranteed to be obey the contract of the {@link Sudoku#hashCode hashCode}
	 * method as defined in the {@code Sudoku} interface.
	 * 
	 * @param sudoku the {@code Sudoku} whose hash code we are interested in.
	 * 
	 * @return the hash code of the given {@code Sudoku}.
	 * 
	 * @throws NullPointerException if {@code sudoku} is {@code null}.
	 * 
	 * @see Sudoku#hashCode()
	 */
	public static final int sudokuHashCode(Sudoku<?> sudoku) {
		requireNonNull(sudoku, "sudoku cannot be null.");
		int hashCode = latinSquareHashCode(sudoku);
		return 31 * hashCode + sudoku.variant().hashCode(); 
	}
	
	/**
	 * Compares the given {@code Sudoku} with the given {@code Object} for equality. Returns {@code true} if the given object is
	 * also a {@code Sudoku} and the two {@code Sudoku}s are identical. Two {@code Sudoku}s are said to be equal if each of the
	 * following of their properties are also equal:
	 * <ul>
	 * <li>The <i>{@link Sudoku#variant() variants}</i> of the {@code Sudoku}s.</li>
	 * <li>The <i>{@link Sudoku#emptySymbol() emptySymbols}</i> of the {@code Sudoku}s.</li>
	 * <li>The <i>{@link Sudoku#cells() cells}</i> {@code Map}s of the {@code Sudoku}s.</li>
	 * <li>The <i>{@link Sudoku#rows() rows}</i> {@code Map}s of the {@code Sudoku}s.</li>
	 * <li>The <i>{@link Sudoku#blocks() blocks}</i> {@code Map}s of the {@code Sudoku}s.</li>
	 * <li>The <i>{@link Sudoku#columns() columns}</i> {@code Map}s of the {@code Sudoku}s.</li>
	 * <li>The <i>{@link Sudoku#symbols() symbols}</i> {@code Map}s of the {@code Sudoku}s.</li>
	 * </ul>
	 * 
	 * <p>
	 * This {@code equals} implementation is guaranteed to obey the contract of the {@link Sudoku#equals(Object) equals}
	 * method as defined in the {@code Sudoku} interface.
	 * 
	 * @param sudoku the {@code Sudoku} to compare to the given object for equality. Must <b>NOT</b> be {@code null}.
	 * @param obj the object to compare for equality with the given {@code Sudoku}. Maybe {@code null}.
	 * 
	 * @return {@code true} if the given {@code Sudoku} is equal to the given object, {@code false} otherwise.
	 * 
	 * @throws NullPointerException if {@code sudoku} is {@code null}.
	 * 
	 * @see Sudoku#equals(Object)
	 */
	public static final boolean sudokuEquals(Sudoku<?> sudoku, Object obj) {
		requireNonNull(sudoku, "sudoku cannot be null.");
		if (!(obj instanceof Sudoku)) return false;
		return latinSquareEquals(sudoku, obj) && sudoku.variant().equals(((Sudoku<?>) obj).variant());
	}
	
	/**
	 * Creates and initializes the given {@link Sudoku}'s {@link Block}s and returns a {@code Map} of the created
	 * {@code Block}s. This method uses the {@link SudokuVariant#createBlocks(Sudoku) createBlocks(Sudoku)} method
	 * of the given {@code Sudoku}'s {@link SudokuVariant variant} to create the new {@code Block}s. This method
	 * also validates that the {@code Set} returned by the {@code createBlocks(Sudoku)} method is not {@code null}
	 * and the size of the {@code Set} is equal to the value returned by the {@code size()} method of the given
	 * {@code Sudoku}'s variant.
	 * 
	 * @param <V> the {@link Symbol} values supported by the given {@code Sudoku}.
	 * 
	 * @param sudoku the {@code Sudoku} whose {@code Block}s we want to create.
	 * @param mapSupplier a {@link Supplier} that returns a new and empty {@code Map} to insert the new {@code Block}s.
	 * @return a {@code Map} of the newly created {@code Block}s.
	 * 
	 * @throws NullPointerException if any of the given arguments are {@code null} or if the {@code Set} of {@code Blocks}
	 * returned by {@code sudoku.variant().createBlocks(Sudoku)} is {@code null}.
	 * @throws SudokuException if the size of {@code Set} returned by {@code sudoku.variant().createBlocks(Sudoku)} is not
	 * equal to {@code sudoku.variant().size()}.
	 */
	public static final <V> Map<String, Block<V>> initializeSudokuBlocks(Sudoku<V> sudoku, Supplier<Map<String, Block<V>>> mapSupplier) {
		requireNonNull(sudoku, "sudoku cannot be null.");
		requireNonNull(mapSupplier, "mapSupplier cannot be null.");
		// create the blocks using this class SudokuVariant
		Set<Block<V>> createdBlocks = requireNonNull(sudoku.variant().createBlocks(sudoku), "'variant.createBlocks()' shouldn't return null.");
		
		// make sure the createdBlocks are the correct number
		if (createdBlocks.size() != sudoku.variant().size())
			throw new SudokuException(
					String.format("The number of blocks created (%d) is less than the expected number (%d).",
							createdBlocks.size(),
							sudoku.variant().size()
						));
		
		return createdBlocks.stream().collect(toMap(block -> block.id(), block -> block, (block1, block2) -> block2, mapSupplier));
	}
	
	// make constructor private to prevent instantiation of this class
	private LatinSquares() { }
}
