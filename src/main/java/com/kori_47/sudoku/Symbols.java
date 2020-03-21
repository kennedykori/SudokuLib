/**
 * 
 */
package com.kori_47.sudoku;

import static java.util.stream.Collectors.toSet;
import static java.util.stream.IntStream.range;

import java.util.Comparator;

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
	 * default {@code Symbol} comparator
	 */
	private static final Comparator<Symbol<?>> DEFAULT_SYMBOL_COMPARATOR = Comparator.comparingInt(symbol -> symbol.id().intValue());

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
	 * @throws NullPointerException if {@code id} or/and {@code value} is/are {@code null}.
	 */
	public static final <V> Symbol<V> of(Integer id, V value) {
		return new SimpleSymbol<V>(id, value);
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
				.mapToObj(Integer::valueOf)
				.map(value -> of(value, value))
				.collect(toSet());
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
	 * Returns a default {@link Comparator} that can be used to compare two {@link Symbol}s for equality and
	 * ordering. The comparator returned performs comparisons based on the {@code id}'s of the {@code Symbol}'s
	 * and thus might be <i>inconsistent with equals</i> if the {@code equals} implementations of the 
	 * {@code Symbol}s used uses more than just the {@code id} property to make equality comparisons.
	 * 
	 * @return a {@code Comparator} that can be used for {@code Symbol} comparisons.
	 */
	public static final Comparator<Symbol<?>> defaultComparator() {
		return DEFAULT_SYMBOL_COMPARATOR;
	}
	
	/**
	 * Returns the hash code of the given {@link Symbol}. This method uses both the {@code id} and {@code value}
	 * properties of a {@code Symbol} to calculate it's hash code.
	 * 
	 * @param symbol the {@code Symbol} whose hash code we want.
	 * 
	 * @return the hash code of the given {@code Symbol}.
	 * 
	 * @throws NullPointerException if {@code symbol} is {@code null}.
	 */
	public static final int hashCode(Symbol<?> symbol) {
		requireNonNull(symbol, "symbol cannot be null.");
		int hashCode = symbol.id().hashCode();
		hashCode = 92821 * hashCode + symbol.value().hashCode();
		return hashCode;
	}
	
	/**
	 * Compares the given {@link Symbol} and {@code Object} for equality. Returns {@code true} if
	 * the given object is also a {@code Symbol} and the two {@code Symbol}s have equal identifiers
	 * and values. 
	 * 
	 * @param symbol the {@code Symbol}  to compare to the given object for equality. Must <b>NOT</b> be {@code null}.
	 * @param obj the object to compare for equality with the given {@code Symbol}. Maybe {@code null}.
	 * 
	 * @return {@code true} if the given {@code Symbol} is equal to the given object, {@code false} otherwise.
	 * 
	 * @throws NullPointerException if {@code symbol} is {@code null}.
	 */
	public static final boolean equals(Symbol<?> symbol, Object obj) {
		requireNonNull(symbol, "symbol cannot be null.");
		if (obj == symbol) return true;
		if (!(obj instanceof Symbol)) return false;
		Symbol<?> _obj = (Symbol<?>)obj;
		return symbol.id().equals(_obj.id()) && symbol.value().equals(_obj.value());
	}
	
	/**
	 * Returns a {@code String} representation of a {@link Symbol}.
	 * 
	 * @param symbol the {@code Symbol} whose {@code String} representation we are interested in.
	 * 
	 * @return the {@code String} representation of the given {@code Symbol}.
	 * 
	 * @throws NullPointerException if {@code symbol} is {@code null}.
	 */
	public static final String toString(Symbol<?> symbol) {
		requireNonNull(symbol, "symbol cannot be null.");
		return String.format("Symbol{id=%d, value=%s}", symbol.id(), symbol.value()); 
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
		return of(Integer.valueOf(offset), (char)('A' + (--offset)));
	}
	
	/**
	 * This is a simple implementation of the {@link Symbol} interface.
	 * 
	 * @param <V> the type of value that this {@code Symbol} supports.
	 * 
	 * @author <a href="https://github.com/kennedykori">Kennedy Kori</a>
	 *
	 * @since Sat, 28 Dec 2019 21:43:37
	 * 
	 */
	private static class SimpleSymbol<V> implements Symbol<V> {

		private final Integer id;
		private final V value;
		private final int hashCode;
		
		/**
		 * Creates a new {@code Symbol} with the given {@code id} and {@code value}. 
		 * 
		 * @param id the {@code id} of the new {@code Symbol}.
		 * @param value the {@code value} of the new {@code Symbol}.
		 * 
		 * @throws NullPointerException if {@code id} or/and {@code value} is/are {@code null}.
		 */
		SimpleSymbol(Integer id, V value) {
			this.id = requireNonNull(id, "id cannot be null.");
			this.value = requireNonNull(value, "value cannot be null.");
			// cache the hash code
			this.hashCode = Symbols.hashCode(this);
		}
		
		@Override
		public Integer id() {
			return id;
		}

		@Override
		public V value() {
			return value;
		}
		
		@Override
		public int hashCode() {
			return hashCode;
		}
		
		@Override
		public boolean equals(Object obj) {
			return Symbols.equals(this, obj);
		}
		
		@Override
		public String toString() {
			return Symbols.toString(this);
		}
	}

	// make constructor private to prevent instantiation of this class
	private Symbols() { }
}
