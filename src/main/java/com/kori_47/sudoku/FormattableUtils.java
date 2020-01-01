/**
 * 
 */
package com.kori_47.sudoku;

import static java.util.stream.Collector.of;

import java.util.Comparator;
import java.util.StringJoiner;

import static java.util.Objects.requireNonNull;

/**
 * This is a utility class composed of only static methods that can be used to represent
 * {@link Formattable}s on different forms/formats as defined by the {@code Formattable}
 * interface.
 * 
 * @author <a href="https://github.com/kennedykori">Kennedy Kori</a>
 *
 * @since Sun, 22 Dec 2019 21:56:14
 */
public final class FormattableUtils {
	
	/**
	 * A comparator used to sort rows on reverse based on their index
	 */
	private static final Comparator<Row<?>> REVERSE_ROW_COMPARATOR = (row1, row2) -> Integer.compare(row2.y(), row1.y());
	
	/**
	 * The default delimiter used by methods in this class when none is provided.
	 */
	public static final String DEFAULT_DELIMITER = " | ";

	/**
	 * The default placeholder used to denote the absence of a value. This is the
	 * placeholder used by the methods of this class when none is provided.
	 */
	public static final String DEFAULT_PLACEHOLDER = "-";

	/**
	 * The default separator used by methods in this class when none is provided.
	 */
	public static final String DEFAULT_SEPARATOR = "/";

	/* ============================================================================
	 * toXY()
	 * ============================================================================
	 */
	/**
	 * Takes a {@link Cell} and returns the <strong><i>{@link Formattable#toXY() XY}</i></strong>
	 * representation of this {@code Cell} as defined in the {@link Formattable} interface. The
	 * <strong><i>XY</i></strong> representation of a {@code Cell} is constructed by concatenating
	 * a {@code Cell}'s x and y coordinates with the two values separated by a given separator.
	 * 
	 * @implNote
	 * A call to this method is similar to calling {@link #toXY(Cell, String)} with {@link #DEFAULT_SEPARATOR}
	 * as the separator. 
	 * 
	 * @param cell the {@code Cell} who's representation we are interested in.
	 * 
	 * @return the <strong><i>XY</i></strong> representation of the given {@code Cell} as a
	 * {@code String}.
	 * 
	 * @throws NullPointerException if {@code cell} is {@code null}.
	 */
	public static final String toXY(final Cell<?> cell) {
		return toXY(cell, DEFAULT_SEPARATOR);
	}

	/**
	 * Takes a {@link Cell} and returns the <strong><i>{@link Formattable#toXY() XY}</i></strong>
	 * representation of this {@code Cell} as defined in the {@link Formattable} interface. The
	 * <strong><i>XY</i></strong> representation of a {@code Cell} is constructed by concatenating
	 * a {@code Cell}'s x and y coordinates with the two values separated by a given separator.
	 * 
	 * @param cell the {@code Cell} who's representation we are interested in.
	 * @param separator a {@code String} to separate the two values <i>(x and y coordinates)</i>.
	 * 
	 * @return the <strong><i>XY</i></strong> representation of the given {@code Cell} as a
	 * {@code String}.
	 * 
	 * @throws NullPointerException if any of the arguments to this method is/are {@code null}.
	 */
	public static final String toXY(final Cell<?> cell, final String separator) {
		requireNonNull(cell, "cell cannot be null.");
		requireNonNull(separator, "separator cannot be null.");
		return cell.x() + separator + cell.y();
	}

	/**
	 * Takes a {@link CellGroup} and returns the <strong><i>{@link Formattable#toXY() XY}</i></strong>
	 * representation of this {@code CellGroup} as defined in the {@link Formattable} interface. The 
	 * <strong><i>XV</i></strong> representation of a {@code CellGroup} is constructed by joining the
	 * <strong><i>XV</i></strong> representation of each {@link Cell} in the {@code CellGroup} delimited
	 * by a given delimiter.
	 * 
	 * @implNote
	 * A call to this method is similar to calling {@link #toXY(CellGroup, String)} with 
	 * {@link #DEFAULT_DELIMITER} as the delimiter.  
	 * 
	 * @param cellGroup the {@code CellGroup} who's representation we are interested in.
	 * 
	 * @return the <strong><i>XV</i></strong> representation of the given {@code CellGroup} as a
	 * {@code String}.
	 * 
	 * @throws NullPointerException if {@code cellGroup} is {@code null}.
	 */
	public static final String toXY(final CellGroup<?> cellGroup) {
		return toXY(cellGroup, DEFAULT_DELIMITER);
	}

	/**
	 * Takes a {@link CellGroup} and returns the <strong><i>{@link Formattable#toXY() XY}</i></strong>
	 * representation of this {@code CellGroup} as defined in the {@link Formattable} interface. The 
	 * <strong><i>XV</i></strong> representation of a {@code CellGroup} is constructed by joining the
	 * <strong><i>XV</i></strong> representation of each {@link Cell} in the {@code CellGroup} delimited
	 * by a given delimiter.
	 * 
	 * @param cellGroup the {@code CellGroup} who's representation we are interested in.
	 * @param delimiter the {@code String} to be used between each {@code Cell}'s <strong><i>XV</i></strong>
	 * representation in the given {@code CellGroup}.
	 * 
	 * @return the <strong><i>XV</i></strong> representation of the given {@code CellGroup} as a
	 * {@code String}.
	 * 
	 * @throws NullPointerException if any of the arguments to this method is/are {@code null}.
	 */
	public static final String toXY(final CellGroup<?> cellGroup, final String delimiter) {
		requireNonNull(cellGroup, "cellGroup cannot be null.");
		requireNonNull(delimiter, "delimiter cannot be null.");
		return cellGroup.cells().values().stream().sorted().collect(of(
				() -> new StringJoiner(delimiter),
				(joiner, cell) -> joiner.add(cell.toXY()),
				(joiner1, joiner2) -> joiner1.merge(joiner2), 
				StringJoiner::toString ));
	}

	/**
	 * Takes a {@link LatinSquare} and returns the <strong><i>{@link Formattable#toXY() XY}</i></strong>
	 * representation of this {@code LatinSquare} as defined in the {@link Formattable} interface. The 
	 * <strong><i>XV</i></strong> representation of a {@code LatinSquare} is constructed by joining the
	 * <strong><i>XV</i></strong> representation of each {@link Row} in the {@code LatinSquare} delimited
	 * by a given delimiter.
	 * 
	 * @implNote
	 * A call to this method is similar to calling {@link #toXY(LatinSquare, String)} with 
	 * {@link #DEFAULT_DELIMITER} as the delimiter.  
	 * 
	 * @param latinSquare the {@code LatinSquare} who's representation we are interested in.
	 * 
	 * @return the <strong><i>XV</i></strong> representation of the given {@code LatinSquare} as a
	 * {@code String}.
	 * 
	 * @throws NullPointerException if {@code latinSquare} is {@code null}.
	 */
	public static final String toXY(final LatinSquare<?> latinSquare) {
		return toXY(latinSquare, DEFAULT_DELIMITER);
	}
	
	/**
	 * Takes a {@link LatinSquare} and returns the <strong><i>{@link Formattable#toXY() XY}</i></strong>
	 * representation of this {@code LatinSquare} as defined in the {@link Formattable} interface. The 
	 * <strong><i>XV</i></strong> representation of a {@code LatinSquare} is constructed by joining the
	 * <strong><i>XV</i></strong> representation of each {@link Row} in the {@code LatinSquare} delimited
	 * by a given delimiter.
	 * 
	 * @param latinSquare the {@code LatinSquare} who's representation we are interested in.
	 * @param delimiter the {@code String} to be used between each {@code Row}'s <strong><i>XV</i></strong>
	 * representation in the given {@code LatinSquare}.
	 * 
	 * @return the <strong><i>XV</i></strong> representation of the given {@code LatinSquare} as a
	 * {@code String}.
	 * 
	 * @throws NullPointerException if any of the arguments to this method is/are {@code null}.
	 */
	public static final String toXY(final LatinSquare<?> latinSquare, final String delimiter) {
		requireNonNull(latinSquare, "latinSquare cannot be null.");
		requireNonNull(delimiter, "delimiter cannot be null.");
		return latinSquare.rows().values().stream().sorted(REVERSE_ROW_COMPARATOR).collect(of(
				() -> new StringJoiner(delimiter),
				(joiner, row) -> joiner.add(row.toXY()),
				(joiner1, joiner2) -> joiner1.merge(joiner2), 
				StringJoiner::toString ));
	}

	/* ============================================================================
	 * toXYV()
	 * ============================================================================
	 */
	/**
	 * Takes a {@link Cell} and returns the x and y coordinates plus the value of the {@link Symbol} 
	 * of the given {@code Cell} as a {@code String}.
	 * 
	 * @implNote
	 * A call to this method is similar to calling {@link #toXYV(Cell, String)} with 
	 * {@link #DEFAULT_SEPARATOR} as the separator.  
	 * 
	 * @param cell the {@code Cell} who's representation is of interest.
	 * 
	 * @return returns the x and y coordinates plus the value of the {@code Symbol} of the given
	 * {@code Cell} as a {@code String}.
	 * 
	 * @throws NullPointerException if {@code Cell} is {@code null}.
	 */
	public static final String toXYV(final Cell<?> cell) {
		return toXYV(cell, DEFAULT_SEPARATOR);
	}

	/**
	 * Takes a {@link Cell} and returns the x and y coordinates plus the value of the {@link Symbol} 
	 * of the given {@code Cell} as a {@code String}.
	 * 
	 * @implNote
	 * A call to this method is similar to calling {@link #toXYV(Cell, String, String)} with 
	 * {@link #DEFAULT_PLACEHOLDER} as the placeholder.  
	 * 
	 * @param cell the {@code Cell} who's representation is of interest.
	 * @param separator a {@code String} to separate the three values <i>(x and y coordinates plus
	 * the {@code Symbol} value)</i>.
	 * 
	 * @return returns the x and y coordinates plus the value of the {@code Symbol} of the given
	 * {@code Cell} as a {@code String}.
	 * 
	 * @throws NullPointerException if any of the arguments to this method is/are {@code null}.
	 */
	public static final String toXYV(final Cell<?> cell, final String separator) {
		return toXYV(cell, separator, DEFAULT_PLACEHOLDER);
	}
	
	/**
	 * Takes a {@link Cell} and returns the x and y coordinates plus the value of the {@link Symbol} 
	 * of the given {@code Cell} as a {@code String}.
	 * 
	 * @param cell the {@code Cell} who's representation is of interest.
	 * @param separator a {@code String} to separate the three values <i>(x and y coordinates plus
	 * the {@code Symbol} value)</i>.
	 * @param placeholder a {@code String} to be used as a "stand-in" value in case the given 
	 * {@code Cell} has no set {@code Symbol}.
	 * 
	 * @return returns the x and y coordinates plus the value of the {@code Symbol} of the given
	 * {@code Cell} as a {@code String}.
	 * 
	 * @throws NullPointerException if any of the arguments to this method is/are {@code null}.
	 */
	public static final String toXYV(final Cell<?> cell, final String separator, final String placeholder) {
		requireNonNull(cell, "cell cannot be null.");
		requireNonNull(separator, "separator cannot be null.");
		requireNonNull(placeholder, "placeholder cannot be null.");
		return String.format("%d%s%d%s%s", cell.x(), separator, cell.y(), separator,
				((cell.value().isPresent())? cell.value().get().value() : placeholder));
	}
	
	/**
	 * Takes a {@link CellGroup} and returns the <strong><i>{@link Formattable#toXYV() XYV}</i></strong>
	 * representation of this {@code CellGroup} as defined in the {@link Formattable} interface. The
	 * <strong><i>XVY</i></strong> representation of a {@code CellGroup} is constructed by joining the
	 * <strong><i>XVY</i></strong> representation of each {@link Cell} in the {@code CellGroup} delimited
	 * by a given delimiter.
	 * 
	 * @implNote
	 * A call to this method is similar to calling {@link #toXYV(CellGroup, String, String)} with
	 * {@link #DEFAULT_DELIMITER} as the {@code delimiter} and {@link #DEFAULT_PLACEHOLDER} as the
	 * {@code placeholder}.
	 * 
	 * @param cellGroup the {@code CellGroup} who's representation we are interested in.
	 * 
	 * @return the <strong><i>XVY</i></strong> representation of the given {@code CellGroup} as a
	 * {@code String}.
	 * 
	 * @throws NullPointerException if {@code cellGroup} is {@code null}.
	 */
	public static final String toXYV(final CellGroup<?> cellGroup) {
		return toXYV(cellGroup, DEFAULT_DELIMITER);
	}

	/**
	 * Takes a {@link CellGroup} and returns the <strong><i>{@link Formattable#toXYV() XYV}</i></strong>
	 * representation of this {@code CellGroup} as defined in the {@link Formattable} interface. The
	 * <strong><i>XVY</i></strong> representation of a {@code CellGroup} is constructed by joining the
	 * <strong><i>XVY</i></strong> representation of each {@link Cell} in the {@code CellGroup} delimited
	 * by a given delimiter.
	 * 
	 * @implNote
	 * A call to this method is similar to calling {@link #toXYV(CellGroup, String, String)} with
	 * {@link #DEFAULT_PLACEHOLDER} as the {@code placeholder}.
	 * 
	 * @param cellGroup the {@code CellGroup} who's representation we are interested in.
	 * @param delimiter the {@code String} to be used between each {@code Cell}'s <strong><i>XVY</i></strong>
	 * representation in the given {@code CellGroup}.
	 * 
	 * @return the <strong><i>XVY</i></strong> representation of the given {@code CellGroup} as a
	 * {@code String}.
	 * 
	 * @throws NullPointerException if any of the arguments to this method is/are {@code null}.
	 */
	public static final String toXYV(final CellGroup<?> cellGroup, final String delimiter) {
		return toXYV(cellGroup, delimiter, DEFAULT_PLACEHOLDER);
	}
	
	/**
	 * Takes a {@link CellGroup} and returns the <strong><i>{@link Formattable#toXYV() XYV}</i></strong>
	 * representation of this {@code CellGroup} as defined in the {@link Formattable} interface. The 
	 * <strong><i>XVY</i></strong> representation of a {@code CellGroup} is constructed by joining the
	 * <strong><i>XVY</i></strong> representation of each {@link Cell} in the {@code CellGroup} delimited
	 * by a given delimiter.
	 * 
	 * @param cellGroup the {@code CellGroup} who's representation we are interested in.
	 * @param delimiter the {@code String} to be used between each {@code Cell}'s <strong><i>XVY</i></strong>
	 * representation in the given {@code CellGroup}.
	 * @param placeholder a {@code String} to be used as a "stand-in" value in case some {@code Cell}s
	 * in the given {@code CellGroup} have no set {@code Symbol}.
	 * 
	 * @return the <strong><i>XVY</i></strong> representation of the given {@code CellGroup} as a
	 * {@code String}.
	 * 
	 * @throws NullPointerException if any of the arguments to this method is/are {@code null}.
	 */
	public static final String toXYV(final CellGroup<?> cellGroup, final String delimiter, final String placeholder) {
		requireNonNull(cellGroup, "cellGroup cannot be null.");
		requireNonNull(delimiter, "delimiter cannot be null.");
		requireNonNull(placeholder, "placeholder cannot be null.");
		return cellGroup.cells().values().stream().sorted().collect(of(
				() -> new StringJoiner(delimiter),
				(joiner, cell) -> joiner.add(cell.toXYV()),
				(joiner1, joiner2) -> joiner1.merge(joiner2), 
				StringJoiner::toString ));
	}
	
	/**
	 * Takes a {@link LatinSquare} and returns the <strong><i>{@link Formattable#toXYV() XYV}</i></strong>
	 * representation of this {@code LatinSquare} as defined in the {@link Formattable} interface. The
	 * <strong><i>XVY</i></strong> representation of a {@code LatinSquare} is constructed by joining the
	 * <strong><i>XVY</i></strong> representation of each {@link Row} in the {@code LatinSquare} delimited
	 * by a given delimiter.
	 * 
	 * @implNote
	 * A call to this method is similar to calling {@link #toXYV(LatinSquare, String, String)} with
	 * {@link #DEFAULT_DELIMITER} as the {@code delimiter} and {@link #DEFAULT_PLACEHOLDER} as the
	 * {@code placeholder}.
	 * 
	 * @param latinSquare the {@code LatinSquare} who's representation we are interested in.
	 * 
	 * @return the <strong><i>XVY</i></strong> representation of the given {@code LatinSquare} as a
	 * {@code String}.
	 * 
	 * @throws NullPointerException if {@code latinSquare} is {@code null}.
	 */
	public static final String toXYV(final LatinSquare<?> latinSquare) {
		return toXYV(latinSquare, DEFAULT_DELIMITER);
	}

	/**
	 * Takes a {@link LatinSquare} and returns the <strong><i>{@link Formattable#toXYV() XYV}</i></strong>
	 * representation of this {@code LatinSquare} as defined in the {@link Formattable} interface. The
	 * <strong><i>XVY</i></strong> representation of a {@code LatinSquare} is constructed by joining the
	 * <strong><i>XVY</i></strong> representation of each {@link Row} in the {@code LatinSquare} delimited
	 * by a given delimiter.
	 * 
	 * @implNote
	 * A call to this method is similar to calling {@link #toXYV(LatinSquare, String, String)} with
	 * {@link #DEFAULT_PLACEHOLDER} as the {@code placeholder}.
	 * 
	 * @param latinSquare the {@code LatinSquare} who's representation we are interested in.
	 * @param delimiter the {@code String} to be used between each {@code Row}'s <strong><i>XVY</i></strong>
	 * representation in the given {@code LatinSquare}.
	 * 
	 * @return the <strong><i>XVY</i></strong> representation of the given {@code LatinSquare} as a
	 * {@code String}.
	 * 
	 * @throws NullPointerException if any of the arguments to this method is/are {@code null}.
	 */
	public static final String toXYV(final LatinSquare<?> latinSquare, final String delimiter) {
		return toXYV(latinSquare, delimiter, DEFAULT_PLACEHOLDER);
	}
	
	/**
	 * Takes a {@link LatinSquare} and returns the <strong><i>{@link Formattable#toXYV() XYV}</i></strong>
	 * representation of this {@code LatinSquare} as defined in the {@link Formattable} interface. The 
	 * <strong><i>XVY</i></strong> representation of a {@code LatinSquare} is constructed by joining the
	 * <strong><i>XVY</i></strong> representation of each {@link Row} in the {@code LatinSquare} delimited
	 * by a given delimiter.
	 * 
	 * @param latinSquare the {@code LatinSquare} who's representation we are interested in.
	 * @param delimiter the {@code String} to be used between each {@code Row}'s <strong><i>XVY</i></strong>
	 * representation in the given {@code LatinSquare}.
	 * @param placeholder a {@code String} to be used as a "stand-in" value in case some {@code Cell}s
	 * in the given {@code LatinSquare} have no set {@code Symbol}.
	 * 
	 * @return the <strong><i>XVY</i></strong> representation of the given {@code LatinSquare} as a
	 * {@code String}.
	 * 
	 * @throws NullPointerException if any of the arguments to this method is/are {@code null}.
	 */
	public static final String toXYV(final LatinSquare<?> latinSquare, final String delimiter, final String placeholder) {
		requireNonNull(latinSquare, "latinSquare cannot be null.");
		requireNonNull(delimiter, "delimiter cannot be null.");
		requireNonNull(placeholder, "placeholder cannot be null.");
		return latinSquare.rows().values().stream().sorted(REVERSE_ROW_COMPARATOR).collect(of(
				() -> new StringJoiner(delimiter),
				(joiner, row) -> joiner.add(row.toXYV()),
				(joiner1, joiner2) -> joiner1.merge(joiner2), 
				StringJoiner::toString ));
	}
	/* ============================================================================
	 * toXYI()
	 * ============================================================================
	 */
	/**
	 * Takes a {@link Cell} and returns the <strong><i>{@link Formattable#toXYI() XYI}</i></strong>
	 * representation of this {@code Cell} as defined in the {@link Formattable} interface. The
	 * <strong><i>XYI</i></strong> representation of a {@code Cell} is constructed by concatenating 
	 * the x and y coordinates plus the id of the {@link Symbol} set on {@code Cell}, with each of
	 * the three values separated by a given separator.
	 * 
	 * @implNote
	 * A call to this method is similar to calling {@link #toXYI(Cell, String, String)} with
	 * {@link #DEFAULT_SEPARATOR} as the {@code separator} and {@link #DEFAULT_PLACEHOLDER}
	 * as the {@code placeholder}.
	 * 
	 * @param cell the {@code Cell} who's representation we are interested in.
	 * 
	 * @return the <strong><i>XYI</i></strong> representation of the given {@code Cell} as a
	 * {@code String}.
	 * 
	 * @throws NullPointerException if {@code cell} is {@code null}.
	 */
	public static final String toXYI(final Cell<?> cell) {
		return toXYI(cell, DEFAULT_SEPARATOR);
	}

	/**
	 * Takes a {@link Cell} and returns the <strong><i>{@link Formattable#toXYI() XYI}</i></strong>
	 * representation of this {@code Cell} as defined in the {@link Formattable} interface. The
	 * <strong><i>XYI</i></strong> representation of a {@code Cell} is constructed by concatenating
	 * the x and y coordinates plus the id of the {@link Symbol} set on {@code Cell}, with each of the
	 * three values separated by a given separator.
	 * 
	 * @implNote
	 * A call to this method is similar to calling {@link #toXYI(Cell, String, String)} with
	 * {@link #DEFAULT_PLACEHOLDER} as the {@code placeholder}.
	 * 
	 * @param cell the {@code Cell} who's representation we are interested in.
	 * @param separator a {@code String} to separate the three values <i>(x and y coordinates plus
	 * the id of the {@code Symbol})</i>.
	 * 
	 * @return the <strong><i>XYI</i></strong> representation of the given {@code Cell} as a
	 * {@code String}.
	 * 
	 * @throws NullPointerException if any of the arguments to this method is/are {@code null}.
	 */
	public static final String toXYI(final Cell<?> cell, final String separator) {
		return toXYI(cell, separator, DEFAULT_PLACEHOLDER);
	}

	/**
	 * Takes a {@link Cell} and returns the <strong><i>{@link Formattable#toXYI() XYI}</i></strong>
	 * representation of this {@code Cell} as defined in the {@link Formattable} interface. The
	 * <strong><i>XYI</i></strong> representation of a {@code Cell} is constructed by concatenating
	 * the x and y coordinates plus the id of the {@link Symbol} set on {@code Cell}, with each of the
	 * three values separated by a given separator.
	 * 
	 * @param cell the {@code Cell} who's representation we are interested in.
	 * @param separator a {@code String} to separate the three values <i>(x and y coordinates plus
	 * the id of the {@code Symbol})</i>.
	 * @param placeholder a {@code String} to be used as a "stand-in" value in case the given 
	 * {@code Cell} has no set {@code Symbol}.
	 * 
	 * @return the <strong><i>XYI</i></strong> representation of the given {@code Cell} as a {@code String}.
	 * 
	 * @throws NullPointerException if any of the arguments to this method is/are {@code null}.
	 */
	public static final String toXYI(final Cell<?> cell, final String separator, final String placeholder) {
		requireNonNull(cell, "cell cannot be null.");
		requireNonNull(separator, "separator cannot be null.");
		requireNonNull(placeholder, "placeholder cannot be null.");
		return String.format("%d%s%d%s%d", cell.x(), separator, cell.y(), separator,
				(cell.value().isPresent()? cell.value().get().id() : placeholder));
	}

	/**
	 * Takes a {@link CellGroup} and returns the <strong><i>{@link Formattable#toXYI() XYI}</i></strong>
	 * representation of this {@code CellGroup} as defined in the {@link Formattable} interface. The
	 * <strong><i>XYI</i></strong> representation of a {@code CellGroup} is constructed by joining the
	 * <strong><i>XYI</i></strong> representation of each {@link Cell} in the {@code CellGroup} delimited
	 * by a given delimiter.
	 * 
	 * @implNote
	 * A call to this method is similar to calling {@link #toXYI(CellGroup, String, String)} with
	 * {@link #DEFAULT_DELIMITER} as the {@code delimiter} and {@link #DEFAULT_PLACEHOLDER} as the
	 * {@code placeholder}.
	 * 
	 * @param cellGroup the {@code CellGroup} who's representation we are interested in.
	 * 
	 * @return the <strong><i>XYI</i></strong> representation of the given {@code CellGroup} as a {@code String}.
	 * 
	 * @throws NullPointerException if {@code cellGroup} is {@code null}.
	 */
	public static final String toXYI(final CellGroup<?> cellGroup) {
		return toXYI(cellGroup, DEFAULT_DELIMITER);
	}

	/**
	 * Takes a {@link CellGroup} and returns the <strong><i>{@link Formattable#toXYI() XYI}</i></strong>
	 * representation of this {@code CellGroup} as defined in the {@link Formattable} interface. The
	 * <strong><i>XYI</i></strong> representation of a {@code CellGroup} is constructed by joining the
	 * <strong><i>XYI</i></strong> representation of each {@link Cell} in the {@code CellGroup} delimited
	 * by a given delimiter.
	 * 
	 * @implNote
	 * A call to this method is similar to calling {@link #toXYI(CellGroup, String, String)} with
	 * {@link #DEFAULT_PLACEHOLDER} as the {@code placeholder}.
	 * 
	 * @param cellGroup the {@code CellGroup} who's representation we are interested in.
	 * @param delimiter the {@code String} to be used between each {@code Cell}'s <strong><i>XYI</i></strong>
	 * representation in the given {@code CellGroup}.
	 * 
	 * @return the <strong><i>XYI</i></strong> representation of the given {@code CellGroup} as a {@code String}.
	 * 
	 * @throws NullPointerException if any of the arguments to this method is/are {@code null}.
	 */
	public static final String toXYI(final CellGroup<?> cellGroup, final String delimiter) {
		return toXYI(cellGroup, delimiter, DEFAULT_PLACEHOLDER);
	}
	
	/**
	 * Takes a {@link CellGroup} and returns the <strong><i>{@link Formattable#toXYI() XYI}</i></strong>
	 * representation of this {@code CellGroup} as defined in the {@link Formattable} interface. The
	 * <strong><i>XYI</i></strong> representation of a {@code CellGroup} is constructed by joining the
	 * <strong><i>XYI</i></strong> representation of each {@link Cell} in the {@code CellGroup} delimited
	 * by a given delimiter.
	 * 
	 * @param cellGroup the {@code CellGroup} who's representation we are interested in.
	 * @param delimiter the {@code String} to be used between each {@code Cell}'s <strong><i>XYI</i></strong>
	 * representation in the given {@code CellGroup}.
	 * @param placeholder a {@code String} to be used as a "stand-in" value in case some {@code Cell}s
	 * in the given {@code CellGroup} have no set {@code Symbol}.
	 * 
	 * @return the <strong><i>XYI</i></strong> representation of the given {@code CellGroup} as a {@code String}.
	 * 
	 * @throws NullPointerException if any of the arguments to this method is/are {@code null}.
	 */
	public static final String toXYI(final CellGroup<?> cellGroup, final String delimiter, final String placeholder) {
		requireNonNull(cellGroup, "cellGroup cannot be null.");
		requireNonNull(delimiter, "delimiter cannot be null.");
		requireNonNull(placeholder, "placeholder cannot be null.");
		return cellGroup.cells().values().stream().sorted().collect(of(
				() -> new StringJoiner(delimiter),
				(joiner, cell) -> joiner.add(cell.toXYI()),
				(joiner1, joiner2) -> joiner1.merge(joiner2), 
				StringJoiner::toString ));
	}
	
	/**
	 * Takes a {@link LatinSquare} and returns the <strong><i>{@link Formattable#toXYI() XYI}</i></strong>
	 * representation of this {@code LatinSquare} as defined in the {@link Formattable} interface. The
	 * <strong><i>XYI</i></strong> representation of a {@code LatinSquare} is constructed by joining the
	 * <strong><i>XYI</i></strong> representation of each {@link Row} in the {@code LatinSquare} delimited
	 * by a given delimiter.
	 * 
	 * @implNote
	 * A call to this method is similar to calling {@link #toXYI(LatinSquare, String, String)} with
	 * {@link #DEFAULT_DELIMITER} as the {@code delimiter} and {@link #DEFAULT_PLACEHOLDER} as the
	 * {@code placeholder}.
	 * 
	 * @param latinSquare the {@code LatinSquare} who's representation we are interested in.
	 * 
	 * @return the <strong><i>XYI</i></strong> representation of the given {@code LatinSquare} as a {@code String}.
	 * 
	 * @throws NullPointerException if {@code latinSquare} is {@code null}.
	 */
	public static final String toXYI(final LatinSquare<?> latinSquare) {
		return toXYI(latinSquare, DEFAULT_DELIMITER);
	}

	/**
	 * Takes a {@link LatinSquare} and returns the <strong><i>{@link Formattable#toXYI() XYI}</i></strong>
	 * representation of this {@code LatinSquare} as defined in the {@link Formattable} interface. The
	 * <strong><i>XYI</i></strong> representation of a {@code LatinSquare} is constructed by joining the
	 * <strong><i>XYI</i></strong> representation of each {@link Row} in the {@code LatinSquare} delimited
	 * by a given delimiter.
	 * 
	 * @implNote
	 * A call to this method is similar to calling {@link #toXYI(LatinSquare, String, String)} with
	 * {@link #DEFAULT_PLACEHOLDER} as the {@code placeholder}.
	 * 
	 * @param latinSquare the {@code LatinSquare} who's representation we are interested in.
	 * @param delimiter the {@code String} to be used between each {@code Row}'s <strong><i>XYI</i></strong>
	 * representation in the given {@code LatinSquare}.
	 * 
	 * @return the <strong><i>XYI</i></strong> representation of the given {@code LatinSquare} as a {@code String}.
	 * 
	 * @throws NullPointerException if any of the arguments to this method is/are {@code null}.
	 */
	public static final String toXYI(final LatinSquare<?> latinSquare, final String delimiter) {
		return toXYI(latinSquare, delimiter, DEFAULT_PLACEHOLDER);
	}
	
	/**
	 * Takes a {@link LatinSquare} and returns the <strong><i>{@link Formattable#toXYI() XYI}</i></strong>
	 * representation of this {@code LatinSquare} as defined in the {@link Formattable} interface. The
	 * <strong><i>XYI</i></strong> representation of a {@code LatinSquare} is constructed by joining the
	 * <strong><i>XYI</i></strong> representation of each {@link Row} in the {@code LatinSquare} delimited
	 * by a given delimiter.
	 * 
	 * @param latinSquare the {@code LatinSquare} who's representation we are interested in.
	 * @param delimiter the {@code String} to be used between each {@code Row}'s <strong><i>XYI</i></strong>
	 * representation in the given {@code LatinSquare}.
	 * @param placeholder a {@code String} to be used as a "stand-in" value in case some {@code Cell}s
	 * in the given {@code LatinSquare} have no set {@code Symbol}.
	 * 
	 * @return the <strong><i>XYI</i></strong> representation of the given {@code LatinSquare} as a {@code String}.
	 * 
	 * @throws NullPointerException if any of the arguments to this method is/are {@code null}.
	 */
	public static final String toXYI(final LatinSquare<?> latinSquare, final String delimiter, final String placeholder) {
		requireNonNull(latinSquare, "latinSquare cannot be null.");
		requireNonNull(delimiter, "delimiter cannot be null.");
		requireNonNull(placeholder, "placeholder cannot be null.");
		return latinSquare.rows().values().stream().sorted(REVERSE_ROW_COMPARATOR).collect(of(
				() -> new StringJoiner(delimiter),
				(joiner, row) -> joiner.add(row.toXYI()),
				(joiner1, joiner2) -> joiner1.merge(joiner2), 
				StringJoiner::toString ));
	}
	/* ============================================================================
	 * toV()
	 * ============================================================================
	 */
	/**
	 * Takes a {@link Cell} and returns the <strong><i>{@link Formattable#toV() V}</i></strong>
	 * representation of this {@code Cell} as defined in the {@link Formattable} interface. The
	 * <strong><i>V</i></strong> representation of a {@code Cell} is equivalent to the value of
	 * the {@link Symbol} set on {@code Cell} as a {@code String}.
	 * 
	 * @implNote
	 * A call to this method is similar to calling {@link #toV(Cell, String)} with
	 * {@link #DEFAULT_PLACEHOLDER} as the {@code placeholder}.
	 * 
	 * @param cell the {@code Cell} who's representation we are interested in.
	 * 
	 * @return the <strong><i>V</i></strong> representation of the given {@code Cell} as a
	 * {@code String}.
	 * 
	 * @throws NullPointerException if {@code cell} is {@code null}.
	 */
	public static final String toV(final Cell<?> cell) {
		return toV(cell, DEFAULT_PLACEHOLDER);
	}

	/**
	 * Takes a {@link Cell} and returns the <strong><i>{@link Formattable#toV() V}</i></strong>
	 * representation of this {@code Cell} as defined in the {@link Formattable} interface. The
	 * <strong><i>V</i></strong> representation of a {@code Cell} is equivalent to the value of
	 * the {@link Symbol} set on {@code Cell} as a {@code String}.
	 * 
	 * @param cell the {@code Cell} who's representation we are interested in.
	 * @param placeholder a {@code String} to be used as a "stand-in" value in case the given 
	 * {@code Cell} has no set {@code Symbol}.
	 * 
	 * @return the <strong><i>V</i></strong> representation of the given {@code Cell} as a
	 * {@code String}.
	 * 
	 * @throws NullPointerException if any of the arguments to this method is/are {@code null}.
	 */
	public static final String toV(final Cell<?> cell, final String placeholder) {
		requireNonNull(cell, "cell cannot be null.");
		requireNonNull(placeholder, "placeholder cannot be null.");
		return cell.value().isPresent()? cell.value().get().value().toString() : placeholder;
	}

	/**
	 * Takes a {@link CellGroup} and returns the <strong><i>{@link Formattable#toV() V}</i></strong>
	 * representation of this {@code CellGroup} as defined in the {@link Formattable} interface. The
	 * <strong><i>V</i></strong> representation of a {@code CellGroup} is constructed by joining the
	 * <strong><i>V</i></strong> representation of each {@link Cell} in the {@code CellGroup} delimited
	 * by a given delimiter.
	 * 
	 * @implNote
	 * A call to this method is similar to calling {@link #toV(CellGroup, String, String)} with
	 * {@link #DEFAULT_DELIMITER} as the {@code delimiter} and {@link #DEFAULT_PLACEHOLDER} as the
	 * {@code placeholder}.
	 * 
	 * @param cellGroup the {@code CellGroup} who's representation we are interested in.
	 * 
	 * @return the <strong><i>V</i></strong> representation of the given {@code CellGroup} as a {@code String}.
	 * 
	 * @throws NullPointerException if {@code cellGroup} is {@code null}.
	 */
	public static final String toV(final CellGroup<?> cellGroup) {
		return toV(cellGroup, DEFAULT_DELIMITER);
	}

	/**
	 * Takes a {@link CellGroup} and returns the <strong><i>{@link Formattable#toV() V}</i></strong>
	 * representation of this {@code CellGroup} as defined in the {@link Formattable} interface. The
	 * <strong><i>V</i></strong> representation of a {@code CellGroup} is constructed by joining the
	 * <strong><i>V</i></strong> representation of each {@link Cell} in the {@code CellGroup} delimited
	 * by a given delimiter.
	 * 
	 * @implNote
	 * A call to this method is similar to calling {@link #toV(CellGroup, String, String)} with
	 * {@link #DEFAULT_PLACEHOLDER} as the {@code placeholder}.
	 * 
	 * @param cellGroup the {@code CellGroup} who's representation we are interested in.
	 * @param delimiter the {@code String} to be used between each {@code Cell}'s <strong><i>V</i></strong>
	 * representation in the given {@code CellGroup}.
	 * 
	 * @return the <strong><i>V</i></strong> representation of the given {@code CellGroup} as a {@code String}.
	 * 
	 * @throws NullPointerException if any of the arguments to this method is/are {@code null}.
	 */
	public static final String toV(final CellGroup<?> cellGroup, final String delimiter) {
		return toV(cellGroup, delimiter, DEFAULT_PLACEHOLDER);
	}

	/**
	 * Takes a {@link CellGroup} and returns the <strong><i>{@link Formattable#toV() V}</i></strong>
	 * representation of this {@code CellGroup} as defined in the {@link Formattable} interface. The
	 * <strong><i>V</i></strong> representation of a {@code CellGroup} is constructed by joining the
	 * <strong><i>V</i></strong> representation of each {@link Cell} in the {@code CellGroup} delimited
	 * by a given delimiter.
	 * 
	 * @param cellGroup the {@code CellGroup} who's representation we are interested in.
	 * @param delimiter the {@code String} to be used between each {@code Cell}'s <strong><i>V</i></strong>
	 * representation in the given {@code CellGroup}.
	 * @param placeholder a {@code String} to be used as a "stand-in" value in case some {@code Cell}s
	 * in the given {@code CellGroup} have no set {@code Symbol}.
	 * 
	 * @return the <strong><i>V</i></strong> representation of the given {@code CellGroup} as a {@code String}.
	 * 
	 * @throws NullPointerException if any of the arguments to this method is/are {@code null}.
	 */
	public static final String toV(final CellGroup<?> cellGroup, final String delimiter, final String placeholder) {
		requireNonNull(cellGroup, "cellGroup cannot be null.");
		requireNonNull(delimiter, "delimiter cannot be null.");
		requireNonNull(placeholder, "placeholder cannot be null.");
		return cellGroup.cells().values().stream().sorted().collect(of(
				() -> new StringJoiner(delimiter),
				(joiner, cell) -> joiner.add(cell.toV()),
				(joiner1, joiner2) -> joiner1.merge(joiner2), 
				StringJoiner::toString ));
	}

	/**
	 * Takes a {@link LatinSquare} and returns the <strong><i>{@link Formattable#toV() V}</i></strong>
	 * representation of this {@code LatinSquare} as defined in the {@link Formattable} interface. The
	 * <strong><i>V</i></strong> representation of a {@code LatinSquare} is constructed by joining the
	 * <strong><i>V</i></strong> representation of each {@link Row} in the {@code LatinSquare} delimited
	 * by a given delimiter.
	 * 
	 * @implNote
	 * A call to this method is similar to calling {@link #toV(LatinSquare, String, String)} with
	 * {@link #DEFAULT_DELIMITER} as the {@code delimiter} and {@link #DEFAULT_PLACEHOLDER} as the
	 * {@code placeholder}.
	 * 
	 * @param latinSquare the {@code LatinSquare} who's representation we are interested in.
	 * 
	 * @return the <strong><i>V</i></strong> representation of the given {@code LatinSquare} as a {@code String}.
	 * 
	 * @throws NullPointerException if {@code latinSquare} is {@code null}.
	 */
	public static final String toV(final LatinSquare<?> latinSquare) {
		return toV(latinSquare, DEFAULT_DELIMITER);
	}

	/**
	 * Takes a {@link LatinSquare} and returns the <strong><i>{@link Formattable#toV() V}</i></strong>
	 * representation of this {@code LatinSquare} as defined in the {@link Formattable} interface. The
	 * <strong><i>V</i></strong> representation of a {@code LatinSquare} is constructed by joining the
	 * <strong><i>V</i></strong> representation of each {@link Row} in the {@code LatinSquare} delimited
	 * by a given delimiter.
	 * 
	 * @implNote
	 * A call to this method is similar to calling {@link #toV(LatinSquare, String, String)} with
	 * {@link #DEFAULT_PLACEHOLDER} as the {@code placeholder}.
	 * 
	 * @param latinSquare the {@code LatinSquare} who's representation we are interested in.
	 * @param delimiter the {@code String} to be used between each {@code Row}'s <strong><i>V</i></strong>
	 * representation in the given {@code LatinSquare}.
	 * 
	 * @return the <strong><i>V</i></strong> representation of the given {@code LatinSquare} as a {@code String}.
	 * 
	 * @throws NullPointerException if any of the arguments to this method is/are {@code null}.
	 */
	public static final String toV(final LatinSquare<?> latinSquare, final String delimiter) {
		return toV(latinSquare, delimiter, DEFAULT_PLACEHOLDER);
	}

	/**
	 * Takes a {@link LatinSquare} and returns the <strong><i>{@link Formattable#toV() V}</i></strong>
	 * representation of this {@code LatinSquare} as defined in the {@link Formattable} interface. The
	 * <strong><i>V</i></strong> representation of a {@code LatinSquare} is constructed by joining the
	 * <strong><i>V</i></strong> representation of each {@link Row} in the {@code LatinSquare} delimited
	 * by a given delimiter.
	 * 
	 * @param latinSquare the {@code LatinSquare} who's representation we are interested in.
	 * @param delimiter the {@code String} to be used between each {@code Row}'s <strong><i>V</i></strong>
	 * representation in the given {@code LatinSquare}.
	 * @param placeholder a {@code String} to be used as a "stand-in" value in case some {@code Cell}s
	 * in the given {@code LatinSquare} have no set {@code Symbol}.
	 * 
	 * @return the <strong><i>V</i></strong> representation of the given {@code LatinSquare} as a {@code String}.
	 * 
	 * @throws NullPointerException if any of the arguments to this method is/are {@code null}.
	 */
	public static final String toV(final LatinSquare<?> latinSquare, final String delimiter, final String placeholder) {
		requireNonNull(latinSquare, "latinSquare cannot be null.");
		requireNonNull(delimiter, "delimiter cannot be null.");
		requireNonNull(placeholder, "placeholder cannot be null.");
		return latinSquare.rows().values().stream().sorted(REVERSE_ROW_COMPARATOR).collect(of(
				() -> new StringJoiner(delimiter),
				(joiner, row) -> joiner.add(row.toV()),
				(joiner1, joiner2) -> joiner1.merge(joiner2), 
				StringJoiner::toString ));
	}
	/* ============================================================================
	 * toI()
	 * ============================================================================
	 */
	/**
	 * Takes a {@link Cell} and returns the <strong><i>{@link Formattable#toI() I}</i></strong>
	 * representation of this {@code Cell} as defined in the {@link Formattable} interface. The
	 * <strong><i>I</i></strong> representation of a {@code Cell} is equivalent to the id of
	 * the {@link Symbol} set on {@code Cell} as a {@code String}.
	 * 
	 * @implNote
	 * A call to this method is similar to calling {@link #toI(Cell, String)} with
	 * {@link #DEFAULT_PLACEHOLDER} as the {@code placeholder}.
	 * 
	 * @param cell the {@code Cell} who's representation we are interested in.
	 * 
	 * @return the <strong><i>I</i></strong> representation of the given {@code Cell} as a
	 * {@code String}.
	 * 
	 * @throws NullPointerException if {@code cell} is {@code null}.
	 */
	public static final String toI(final Cell<?> cell) {
		return toV(cell, DEFAULT_PLACEHOLDER);
	}

	/**
	 * Takes a {@link Cell} and returns the <strong><i>{@link Formattable#toI() I}</i></strong>
	 * representation of this {@code Cell} as defined in the {@link Formattable} interface. The
	 * <strong><i>I</i></strong> representation of a {@code Cell} is equivalent to the id of
	 * the {@link Symbol} set on {@code Cell} as a {@code String}.
	 * 
	 * @param cell the {@code Cell} who's representation we are interested in.
	 * @param placeholder a {@code String} to be used as a "stand-in" value in case the given 
	 * {@code Cell} has no set {@code Symbol}.
	 * 
	 * @return the <strong><i>I</i></strong> representation of the given {@code Cell} as a
	 * {@code String}.
	 * 
	 * @throws NullPointerException if any of the arguments to this method is/are {@code null}.
	 */
	public static final String toI(final Cell<?> cell, final String placeholder) {
		requireNonNull(cell, "cell cannot be null.");
		requireNonNull(placeholder, "placeholder cannot be null.");
		return cell.value().isPresent()? Integer.toString(cell.value().get().id()) : placeholder;
	}

	/**
	 * Takes a {@link CellGroup} and returns the <strong><i>{@link Formattable#toI() I}</i></strong>
	 * representation of this {@code CellGroup} as defined in the {@link Formattable} interface. The
	 * <strong><i>I</i></strong> representation of a {@code CellGroup} is constructed by joining the
	 * <strong><i>I</i></strong> representation of each {@link Cell} in the {@code CellGroup} delimited
	 * by a given delimiter.
	 * 
	 * @implNote
	 * A call to this method is similar to calling {@link #toI(CellGroup, String, String)} with
	 * {@link #DEFAULT_DELIMITER} as the {@code delimiter} and {@link #DEFAULT_PLACEHOLDER} as the
	 * {@code placeholder}.
	 * 
	 * @param cellGroup the {@code CellGroup} who's representation we are interested in.
	 * 
	 * @return the <strong><i>I</i></strong> representation of the given {@code CellGroup} as a {@code String}.
	 * 
	 * @throws NullPointerException if {@code cellGroup} is {@code null}.
	 */
	public static final String toI(final CellGroup<?> cellGroup) {
		return toI(cellGroup, DEFAULT_DELIMITER);
	}

	/**
	 * Takes a {@link CellGroup} and returns the <strong><i>{@link Formattable#toI() I}</i></strong>
	 * representation of this {@code CellGroup} as defined in the {@link Formattable} interface. The
	 * <strong><i>I</i></strong> representation of a {@code CellGroup} is constructed by joining the
	 * <strong><i>I</i></strong> representation of each {@link Cell} in the {@code CellGroup} delimited
	 * by a given delimiter.
	 * 
	 * @implNote
	 * A call to this method is similar to calling {@link #toI(CellGroup, String, String)} with
	 * {@link #DEFAULT_PLACEHOLDER} as the {@code placeholder}.
	 * 
	 * @param cellGroup the {@code CellGroup} who's representation we are interested in.
	 * @param delimiter the {@code String} to be used between each {@code Cell}'s <strong><i>I</i></strong>
	 * representation in the given {@code CellGroup}.
	 * 
	 * @return the <strong><i>I</i></strong> representation of the given {@code CellGroup} as a {@code String}.
	 * 
	 * @throws NullPointerException if any of the arguments to this method is/are {@code null}.
	 */
	public static final String toI(final CellGroup<?> cellGroup, final String delimiter) {
		return toI(cellGroup, delimiter, DEFAULT_PLACEHOLDER);
	}

	/**
	 * Takes a {@link CellGroup} and returns the <strong><i>{@link Formattable#toI() I}</i></strong>
	 * representation of this {@code CellGroup} as defined in the {@link Formattable} interface. The
	 * <strong><i>I</i></strong> representation of a {@code CellGroup} is constructed by joining the
	 * <strong><i>I</i></strong> representation of each {@link Cell} in the {@code CellGroup} delimited
	 * by a given delimiter.
	 * 
	 * @param cellGroup the {@code CellGroup} who's representation we are interested in.
	 * @param delimiter the {@code String} to be used between each {@code Cell}'s <strong><i>I</i></strong>
	 * representation in the given {@code CellGroup}.
	 * @param placeholder a {@code String} to be used as a "stand-in" value in case some {@code Cell}s
	 * in the given {@code CellGroup} have no set {@code Symbol}.
	 * 
	 * @return the <strong><i>I</i></strong> representation of the given {@code CellGroup} as a {@code String}.
	 * 
	 * @throws NullPointerException if any of the arguments to this method is/are {@code null}.
	 */
	public static final String toI(final CellGroup<?> cellGroup, final String delimiter, final String placeholder) {
		requireNonNull(cellGroup, "cellGroup cannot be null.");
		requireNonNull(delimiter, "delimiter cannot be null.");
		requireNonNull(placeholder, "placeholder cannot be null.");
		return cellGroup.cells().values().stream().sorted().collect(of(
				() -> new StringJoiner(delimiter),
				(joiner, cell) -> joiner.add(cell.toI()),
				(joiner1, joiner2) -> joiner1.merge(joiner2), 
				StringJoiner::toString ));
	}

	/**
	 * Takes a {@link LatinSquare} and returns the <strong><i>{@link Formattable#toI() I}</i></strong>
	 * representation of this {@code LatinSquare} as defined in the {@link Formattable} interface. The
	 * <strong><i>I</i></strong> representation of a {@code LatinSquare} is constructed by joining the
	 * <strong><i>I</i></strong> representation of each {@link Row} in the {@code LatinSquare} delimited
	 * by a given delimiter.
	 * 
	 * @implNote
	 * A call to this method is similar to calling {@link #toI(LatinSquare, String, String)} with
	 * {@link #DEFAULT_DELIMITER} as the {@code delimiter} and {@link #DEFAULT_PLACEHOLDER} as the
	 * {@code placeholder}.
	 * 
	 * @param latinSquare the {@code LatinSquare} who's representation we are interested in.
	 * 
	 * @return the <strong><i>I</i></strong> representation of the given {@code LatinSquare} as a {@code String}.
	 * 
	 * @throws NullPointerException if {@code latinSquare} is {@code null}.
	 */
	public static final String toI(final LatinSquare<?> latinSquare) {
		return toV(latinSquare, DEFAULT_DELIMITER);
	}

	/**
	 * Takes a {@link LatinSquare} and returns the <strong><i>{@link Formattable#toI() I}</i></strong>
	 * representation of this {@code LatinSquare} as defined in the {@link Formattable} interface. The
	 * <strong><i>I</i></strong> representation of a {@code LatinSquare} is constructed by joining the
	 * <strong><i>I</i></strong> representation of each {@link Row} in the {@code LatinSquare} delimited
	 * by a given delimiter.
	 * 
	 * @implNote
	 * A call to this method is similar to calling {@link #toI(LatinSquare, String, String)} with
	 * {@link #DEFAULT_PLACEHOLDER} as the {@code placeholder}.
	 * 
	 * @param latinSquare the {@code LatinSquare} who's representation we are interested in.
	 * @param delimiter the {@code String} to be used between each {@code Row}'s <strong><i>I</i></strong>
	 * representation in the given {@code LatinSquare}.
	 * 
	 * @return the <strong><i>I</i></strong> representation of the given {@code LatinSquare} as a {@code String}.
	 * 
	 * @throws NullPointerException if any of the arguments to this method is/are {@code null}.
	 */
	public static final String toI(final LatinSquare<?> latinSquare, final String delimiter) {
		return toV(latinSquare, delimiter, DEFAULT_PLACEHOLDER);
	}

	/**
	 * Takes a {@link LatinSquare} and returns the <strong><i>{@link Formattable#toI() I}</i></strong>
	 * representation of this {@code LatinSquare} as defined in the {@link Formattable} interface. The
	 * <strong><i>I</i></strong> representation of a {@code LatinSquare} is constructed by joining the
	 * <strong><i>I</i></strong> representation of each {@link Row} in the {@code LatinSquare} delimited
	 * by a given delimiter.
	 * 
	 * @param latinSquare the {@code LatinSquare} who's representation we are interested in.
	 * @param delimiter the {@code String} to be used between each {@code Row}'s <strong><i>I</i></strong>
	 * representation in the given {@code LatinSquare}.
	 * @param placeholder a {@code String} to be used as a "stand-in" value in case some {@code Cell}s
	 * in the given {@code LatinSquare} have no set {@code Symbol}.
	 * 
	 * @return the <strong><i>I</i></strong> representation of the given {@code LatinSquare} as a {@code String}.
	 * 
	 * @throws NullPointerException if any of the arguments to this method is/are {@code null}.
	 */
	public static final String toI(final LatinSquare<?> latinSquare, final String delimiter, final String placeholder) {
		requireNonNull(latinSquare, "latinSquare cannot be null.");
		requireNonNull(delimiter, "delimiter cannot be null.");
		requireNonNull(placeholder, "placeholder cannot be null.");
		return latinSquare.rows().values().stream().sorted(REVERSE_ROW_COMPARATOR).collect(of(
				() -> new StringJoiner(delimiter),
				(joiner, row) -> joiner.add(row.toI()),
				(joiner1, joiner2) -> joiner1.merge(joiner2), 
				StringJoiner::toString ));
	}
	
	// private constructor to prevent instantiation of this class.
	private FormattableUtils() { }
}
