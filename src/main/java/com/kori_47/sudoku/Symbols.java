/**
 * 
 */
package com.kori_47.sudoku;

import static java.util.stream.Collectors.toSet;
import static java.util.stream.IntStream.range;
import static java.util.Objects.hash;
import static java.util.Objects.requireNonNull;

import static com.kori_47.utils.ObjectUtils.requireGreaterThanOrEqualTo;
import static com.kori_47.utils.ObjectUtils.requireInRange;

import java.util.Set;

/**
 * This class consists exclusively of static methods that return different implementations of 
 * {@link Symbol} such as {@link #numberSymbolsUpTo(int) number symbols} and
 * {@link #letterSymbolsUpTo(int) letter symbols}.
 * 
 * @author <a href="https://github.com/kennedykori">Kennedy Kori</a>
 *
 * @since Mon, 23 Dec 2019 23:15:24
 * 
 * @see Symbol
 */
public final class Symbols {

	/**
	 * Returns a new {@link Symbol} with the given {@code id} and {@code value}.
	 * 
	 * @param <V> the type of {@code value}.
	 * 
	 * @param id the identifier of the new {@code Symbol} to be returned.
	 * @param value the {@code value} of the {@code Symbol} to be returned.
	 * 
	 * @return a new {@code Symbol} with the given {@code id} and {@code value}.
	 * 
	 * @throws NullPointerException if {@code value} is {@code null}.
	 */
	public static final <V> Symbol<V> of(int id, V value) {
		return new SimpleSymbol<V>(id, value);
	}

	/**
	 * Returns a number {@link Symbol} that can be used as an empty {@code Symbol} in a {@link LatinSquare}.
	 * The returned {@code Symbol} has an {@code id} and {@code value} of zero.
	 * 
	 * @return an number {@code Symbol} that can be used as an empty {@code Symbol} in a {@code LatinSquare}.
	 * 
	 * @see #numberSymbolsUpTo(int)
	 */
	public static final Symbol<Integer> emptyNumberSymbol() {
		return of(0, Integer.valueOf(0));
	}

	/**
	 * Returns a {@link Set} of number {@link Symbol}s starting from {@code 1} up to the specified limit
	 * <strong><i>(exclusive)</i></strong>. That is, returns a {@code Set} of {@code Symbol}s with the first
	 * {@code Symbol} having an id and value of {@code 1}, the second having an id and value of {@code 2} and so
	 * on until the last {@code Symbol} has a value and id of the specified limit minus {@code 1}.
	 * 
	 * @param upTo the limit <strong><i>(exclusive)</i></strong> of the {@link Symbol}s to be returned.
	 * 
	 * @return a {@code Set} of number {@code Symbol}s starting from {@code 1} up to the specified limit
	 * <strong><i>(exclusive)</i></strong>.
	 * 
	 * @throws IllegalArgumentException if {@code upTo} is less than {@code 1}.
	 * 
	 * @see #emptyNumberSymbol()
	 */
	public static final Set<Symbol<Integer>> numberSymbolsUpTo(int upTo) {
		requireGreaterThanOrEqualTo(1, upTo, "upTo must be greater than or equal to 1.");
		return range(1, upTo)
				.mapToObj(value -> of(value, Integer.valueOf(value)))
				.collect(toSet());
	}

	/**
	 * Returns a letter {@link Symbol} that can be used as an empty {@code Symbol} in a {@link LatinSquare}.
	 * The returned {@code Symbol} has an {@code id} of {@code 0} and the {@code value} is the space
	 * {@code Character}.
	 * 
	 * @return an letter {@code Symbol} that can be used as an empty {@code Symbol} in a {@code LatinSquare}.
	 * 
	 * @see #letterSymbolsUpTo(int)
	 */
	public static final Symbol<Character> emptyLetterSymbol() {
		return of(0, ' ');
	}

	/**
	 * Returns a {@link Set} of letter {@link Symbol}s starting from {@code 1} up to the specified limit
	 * <strong><i>(exclusive)</i></strong>. That is, returns a {@code Set} of {@code Symbol}s with the first
	 * {@code Symbol} having an id of {@code 1} and the value {@code A}, the second having an id of {@code 2}
	 * and the value {@code B} and so on until the last {@code Symbol} has an id of {@code 26} and the value
	 * {@code Z}. Unlike {@link #numberSymbolsUpTo(int)}, the stated limit must not exit {@code 27} or else an
	 * {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param upTo the limit <strong><i>(exclusive)</i></strong> of the {@link Symbol}s to be returned.
	 * 
	 * @return a {@code Set} of letter {@code Symbol}s starting from {@code 1} up to the specified limit
	 * <strong><i>(exclusive)</i></strong>.
	 * 
	 * @throws IllegalArgumentException if {@code upTo} is less than {@code 1} or greater than {@code 27}.
	 * 
	 * @see #emptyLetterSymbol()
	 */
	public static final Set<Symbol<Character>> letterSymbolsUpTo(int upTo) {
		requireInRange(1, 28, upTo, "upTo must be greater than or equal to 1 and less than or equal to 27.");
		return range(1, upTo)
				.mapToObj(Symbols::nextLetterSymbol)
				.collect(toSet());
	}

	/**
	 * This helper method used internally by the {@link #letterSymbolsUpTo(int)} to convert an {@code Integer}
	 * into a {@code Character}. 
	 * 
	 * @param offset the number to construct a {@code Character} from.
	 * 
	 * @return a new letter {@code Symbol} constructed from the given {@code Symbol}.
	 */
	private static final Symbol<Character> nextLetterSymbol(int offset) {
		return of(offset, (char)('A' + (--offset)));
	}
	/**
	 * This is a simple implementation of the {@link Symbol} interface.
	 * 
	 * @author <a href="https://github.com/kennedykori">Kennedy Kori</a>
	 *
	 * @since Sat, 28 Dec 2019 21:43:37
	 * 
	 * @param <V> the type of value that this {@code Symbol} supports.
	 */
	private static class SimpleSymbol<V> implements Symbol<V> {

		private final int id;
		private final V value;
		
		/**
		 * Creates a new {@code Symbol} with the given {@code id} and {@code value}. 
		 * 
		 * @param id the {@code id} of the new {@code Symbol}.
		 * @param value the {@code value} of the new {@code Symbol}.
		 * 
		 * @throws NullPointerException if {@code value} is {@code null}.
		 */
		SimpleSymbol(int id, V value) {
			this.id = id;
			this.value = requireNonNull(value, "value cannot be null.");
		}
		
		@Override
		public int id() {
			return id;
		}

		@Override
		public V value() {
			return value;
		}
		
		@Override
		public int hashCode() {
			return hash(Integer.valueOf(id), value);
		}
		
		@Override
		public boolean equals(Object obj) {
			if (obj == this) return true;
			if (obj == null || !(obj instanceof SimpleSymbol)) return false;
			SimpleSymbol<?> _obj = (SimpleSymbol<?>)obj;
			return id == _obj.id && value.equals(_obj.value);
		}
		
		@Override
		public String toString() {
			return String.format("[ id : %d, value : %s ]", id, value);
		}
	}

	// make constructor private to prevent instantiation of this class
	private Symbols() { }
}
