/**
 * 
 */
package com.kori_47.sudoku;

import static java.util.Collections.unmodifiableSet;
import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;

import static com.kori_47.utils.ObjectUtils.requireNonNegative;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * This class consists exclusively of static methods thiamawesomeGROOT47!at create and operate on {@link Cell}s.
 * 
 * @author <a href="https://github.com/kennedykori">Kennedy Kori</a>
 *
 * @since Mon, 3 Feb 2020 19:02:25
 * 
 * @see Cell
 * @see Symbol
 */
public final class Cells {
	
	/**
	 * default Cell Comparator
	 */
	private static final Comparator<Cell<?>> DEFAULT_CELL_COMPARATOR = Comparator.<Cell<?>>comparingInt(Cell::y)
			.thenComparingInt(Cell::x)
			.thenComparing(Cell::id);
	
	/**
	 * Returns a new {@link Cell} with the given properties.
	 * 
	 * @param <V> the type of value held by the {@link Symbol}s supported by the returned {@code Cell}.
	 * 
	 * @param id the unique identifier of the returned {@code Cell}.
	 * @param x the x coordinate of the returned {@code Cell}.
	 * @param y the y coordinate of the returned {@code Cell}.
	 * 
	 * @return a new {@code Cell} with the given properties.
	 * 
	 * @throws NullPointerException if {@code id} is {@code null}.
	 * @throws IllegalArgumentException if either {@code x} or {@code y} is negative, i.e less than zero.
	 * 
	 * @see #of(String, int, int, Symbol)
	 * 
	 * @implNote
	 * The {@link Cell#notes() notes()} method of the new {@code Cell} instances returns an immutable {@code Set}.
	 */
	public static final <V> Cell<V> of(String id, int x, int y) {
		return of(id, x, y, null);
	}
	
	/**
	 * Returns a new {@link Cell} with the given properties.
	 * 
	 * @param <V> the type of value held by the {@link Symbol}s supported by the returned {@code Cell}.
	 * 
	 * @param id the unique identifier of the returned {@code Cell}.
	 * @param x the x coordinate of the returned {@code Cell}.
	 * @param y the y coordinate of the returned {@code Cell}.
	 * @param value the {@code Symbol} to assign to the returned {@code Cell}. Maybe {@code null}.
	 * 
	 * @return a new {@code Cell} with the given properties.
	 * 
	 * @throws NullPointerException if {@code id} is {@code null}.
	 * @throws IllegalArgumentException if either {@code x} or {@code y} is negative, i.e less than zero.
	 * 
	 * @implNote
	 * The {@link Cell#notes() notes()} method of the new {@code Cell} instances returns an immutable {@code Set}. 
	 */
	public static final <V> Cell<V> of(String id, int x, int y, Symbol<V> value) {
		return new SimpleCell<V>(id, x, y, value);
	}
	
	/**
	 * Returns a {@link CellFactory} instance that can be used for instantiating {@link Cell}s.
	 * 
	 * @param <V> the type of {@code Symbol} values held by the {@code Cell} instances returned by this factory. 
	 * 
	 * @return a {@code CellFactory} instance that can be used for instantiating {@code Cell}s.
	 * 
	 * @implNote
	 * The {@code Cell} instances created by the returned {@code CellFactory} have the same properties as those
	 * returned by {@link Cells#of(String, int, int, Symbol)}.
	 */
	public static final <V> CellFactory<V> defaultCellFactory() {
		return (id, x, y, symbol) -> of(id, x, y);
	}
	
	/**
	 * Returns a default {@link Comparator} that can be used to compare two {@link Cell}s for equality
	 * and ordering. The {@code Comparator} returned performs comparisons based on the following properties
	 * of a {@code Cell}:
	 * <ul>
	 * <li>The {@link Cell#id() id} of a {@code Cell}.</li>
	 * <li>The {@link Cell#x() x coordinate} of a {@code Cell}.</li>
	 * <li>The {@link Cell#y() y coordinate} of a {@code Cell}.</li>
	 * </ul>
	 * 
	 * <p>
	 * The {@code Comparator} returned is guaranteed to be consistent with the {@link Cell#equals(Object) equals}
	 * method of a {@link Cell} as long as the {@code Cell}'s equals method implementation maintains the strict contract
	 * of that method. It should also be noted that since the {@code Comparator} returned by this method includes the {@code id}
	 * of a {@code Cell} when performing comparisons, it will work best with {@code Cell}s who's {@code id}s are part of a sequence.
	 * 
	 * @return a {@code Comparator} that can be used for {@code Cell} comparisons.
	 * 
	 * @see Cell#compareTo(Cell)
	 * @see Cell#equals(Object)
	 * 
	 * @implSpec
	 * The {@code Comparator} returned by this method orders {@code Cell}s such that {@code Cell}s with higher {@code x} and {@code y}
	 * coordinates are considered to be "larger" than their counterparts with smaller coordinate values. The {@code y} coordinate has
	 * a higher weight than the {@code x coordinate} so that for any two {@code Cells}, {@code cell1} and  {@code cell2}, where the following
	 * is {@code true}, {@code cell2.y() > cell1.y()}, {@code cell2} will always be greater than {@code cell1} even if {@code cell1} has a
	 * higher value for the {@code x} coordinate than {@code cell2}, i.e {@code cell1.x() > cell2.x()}. If both {@code Cell}s have the same
	 * values for both the {@code x} and {@code y} coordinates, then {@code id}s of the {@code Cell}s are compared to get the final result.
	 * Consider the following:
     * 
     * <p>
     * Assume we have the following concrete implementation of {@code Cell}, {@code CellImp}:
     * <pre> 
     * <code>
     * ...
     * 
     * public CellImp(String id, int x, int y) {	// constructor
     * 	this.id = id;  // where id is the identifier of this cell 
     * 	this.x = x;    // where x is the x coordinate of this cell
     * 	this.y = y;    // where y is the y coordinate of this cell
     * }
     * 
     * public int compareTo(Cell<V> other) {		// compareTo
     * 	return Cells.defaultComparator().compare(this, other);
     * }
     * 
     * ...
     * </code>
     * </pre>
     * 
     * Then consider the following code snippet:
     * 
     * <pre> {@code
	 * CellImp<V> a = new CellImp<>("1", 2, 3);
	 * CellImp<V> b = new CellImp<>("2", 3, 2);
	 * CellImp<V> c = new CellImp<>("3", 3, 3);
	 * CellImp<V> d = new CellImp<>("4", 2, 0);
	 * CellImp<V> e = new CellImp<>("5", 2, 0);
	 * 
	 * System.out.println(a.compare(b));
	 * System.out.println(a.compare(c));
	 * System.out.println(b.compare(c));
	 * System.out.println(c.compare(d));
	 * System.out.println(e.compare(d));
	 * }
	 * </pre>
	 * 
	 * Then the code snippet above should produce the following output:
	 * <pre> {@code
	 * 1
	 * -1
	 * -1
	 * 1
	 * 1
	 * }
	 * </pre>
	 * 
	 * where {@code 1} can be any positive {@code Integer} and {@code -1} can be
	 * any negative {@code Integer}.
	 * </p>
	 */
	public static final Comparator<Cell<?>> defaultComparator() {
		return DEFAULT_CELL_COMPARATOR;
	}
	
	/**
	 * Returns the hash code of the given {@link Cell}. This method uses the following properties of a {@code Cell} to
	 * calculate it's hash code:
	 * <ul>
	 * <li>The hash code of the {@link Cell#id() id} of the {@code Cell}.</li>
	 * <li>The hash code of the {@link Cell#x() x coordinate} of the {@code Cell}.</li>
	 * <li>The hash code of the {@link Cell#y() y coordinate} of the {@code Cell}.</li>
	 * </ul>
	 * 
	 * <p>
	 * The hash code value returned by this method is guaranteed to be obey the contract of the {@link Cell#hashCode hashCode}
	 * method as defined in the {@code Cell} interface.
	 * 
	 * @param cell the {@code Cell} whose hash code we want.
	 * 
	 * @return the hash code of the given {@code Cell}.
	 * 
	 * @throws NullPointerException if {@code cell} is {@code null}.
	 * 
	 * @see Cell#hashCode()
	 */
	public static final int hashCode(Cell<?> cell) {
		requireNonNull(cell, "cell cannot be null.");
		int hashCode = cell.id().hashCode();
		hashCode = 92821 * hashCode + cell.x();
		hashCode = 92821 * hashCode + cell.y();
		return hashCode;
	}

	/**
	 * Compares the given {@link Cell} and {@code Object} for equality. Returns {@code true} if
	 * the given object is also a {@code Cell} and the two {@code Cell}s are identical. According
	 * to {@link Cell#equals(Object)} method, two {@code Cell}s are equal if the following of their
	 * properties are equal:
	 * <ul>
	 * <li>The <i>{@link #id() ids}</i> of the {@code Cell}s</li>
	 * <li>The <i>{@link #x() x coordinates}</i> of the {@code Cell}s.</li>
	 * <li>The <i>{@link #y() y coordinates}</i> of the {@code Cell}s.</li>
	 * </ul>
	 *
	 * <p>
	 * This {@code equals} implementation is guaranteed to obey the contract of the {@link Cell#equals(Object) equals}
	 * method as defined in the {@code Cell} interface.
	 * 
	 * @param cell the {@code Cell} to compare to the given object for equality. Must <b>NOT</b> be {@code null}.
	 * @param obj the object to compare for equality with the given {@code Cell}. Maybe {@code null}.
	 * 
	 * @return {@code true} if the given {@code Cell} is equal to the given object, {@code false} otherwise.
	 * 
	 * @throws NullPointerException if {@code cell} is {@code null}.
	 * 
	 * @see Cell#equals(Object)
	 */
	public static final boolean equals(Cell<?> cell, Object obj) {
		requireNonNull(cell, "cell cannot be null.");
		if (obj == cell) return true;
		if (!(obj instanceof Cell)) return false;
		Cell<?> _obj =  (Cell<?>)obj;
		return cell.x() == _obj.x() && cell.y() == _obj.y() && cell.id().equals(_obj.id());
	}
	
	/**
	 * Returns a {@code String} representation of a {@link Cell}. A call to this method is similar to calling
	 * {@link #toString(Cell, String)} with the second argument as {@code null}.
	 * 
	 * @param cell the {@code Cell} whose {@code String} representation we are interested in.
	 * 
	 * @return the {@code String} representation of the given {@code Cell}.
	 * 
	 * @throws NullPointerException if {@code cell} is {@code null}.
	 * 
	 * @see #toString(Cell, String)
	 */
	public static final String toString(Cell<?> cell) {
		return toString(cell, null);
	}
	
	/**
	 * Returns a {@code String} representation of a {@link Cell}. An optional placeholder can also be given to be used in
	 * case the given {@code Cell} has a {@code null} {@link Symbol}. If {@code null} is given as a placeholder, the default
	 * placeholder, an underscore, is used instead.
	 * 
	 * @param cell the {@code Cell} whose {@code String} representation we are interested in.
	 * @param placeholder an optional {@code String} value to be used in case the given {@code Cell} has a {@code null}
	 * 	{@code Symbol}. Can be {@code null} in which case an underscore will be used as the placeholder.
	 * 
	 * @return the {@code String} representation of the given {@code Cell}.
	 * 
	 * @throws NullPointerException if {@code cell} is {@code null}.
	 * 
	 * @see #toString(Cell)
	 */
	public static final String toString(Cell<?> cell, String placeholder) {
		requireNonNull(cell, "cell cannot be null.");
		String cellValue = (cell.value().isPresent())? cell.value().get().toString() : isNull(placeholder)? "-" : placeholder;
		return String.format("Cell{id=%s, coord=(x:%d, y:%d), value=[%s], clue=%s}", cell.id(), cell.x(),  cell.y(), cellValue, cell.isClueCell());
	}
	
	/**
	 * This is a simple implementation of the {@link Cell} interface.
	 * 
	 * @param <V> the type of value held by the {@link Symbol}s supported by this {@code Cell}.
	 * 
	 * @author <a href="https://github.com/kennedykori">Kennedy Kori</a>
	 *
	 * @since Mon, 3 Feb 2020 20:44:15
	 */
	private static class SimpleCell<V> implements Cell<V> {
		
		// ================================================
		// PRIMARY FIELDS
		// ================================================
		private final String id;
		private final int x;
		private final int y;
		private final Set<Symbol<V>> notes;
		// mutable fields
		private Symbol<V> value;
		private boolean clueCell;
		
		// ================================================
		// COLLECTION VIEWS
		// ================================================
		private final Set<Symbol<V>> notesView;
		
		/**
		 * Creates a {@link Cell} instance with the given properties.
		 * 
		 * @param id the unique identifier of the new {@code Cell}.
		 * @param x the x coordinate of the new {@code Cell}.
		 * @param y the y coordinate of the new {@code Cell}.
		 * @param value the {@code Symbol} to assign to the new {@code Cell}. Maybe {@code null}.
		 * 
		 * @throws NullPointerException if {@code id} is {@code null}.
		 * @throws IllegalArgumentException if either {@code x} or {@code y} is negative, i.e less than zero.
		 */
		SimpleCell(String id, int x, int y, Symbol<V> value) {
			this.id= requireNonNull(id, "id cannot be null.");
			this.x = requireNonNegative(x, "x must be a positive integer.");
			this.y = requireNonNegative(y, "y must be a positive integer.");
			this.value = value;
			this.notes = new HashSet<>();
			this.notesView = unmodifiableSet(this.notes);
		}

		@Override
		public void changeSymbol(Symbol<V> value) {
			checkIsClueCell();
			this.value = value;
		}

		@Override
		public void makeClueCell(Symbol<V> initialValue) {
			this.value = requireNonNull(initialValue, "initialValue cannot be null.");
			clueCell = true;
		}

		@Override
		public void makeNormalCell() {
			clueCell = false;
		}

		@Override
		public void makeNote(Symbol<V> note) {
			checkIsClueCell();
			notes.add(requireNonNull(note, "note cannot be null."));
		}

		@Override
		public void removeNote(Symbol<V> note) {
			checkIsClueCell();
			notes.remove(requireNonNull(note, "note cannot be null."));
		}

		@Override
		public void reset(Symbol<V> symbol) {
			// start by converting this to a normal Cell if this is a clue Cell
			if (clueCell)
				makeNormalCell();
			// clear any notes that this Cell my have
			notes.clear();
			// change the value of this Cell to the given Symbol
			changeSymbol(symbol);
		}

		@Override
		public int x() {
			return x;
		}

		@Override
		public int y() {
			return y;
		}

		@Override
		public String id() {
			return id;
		}

		@Override
		public Optional<Symbol<V>> value() {
			return Optional.ofNullable(value);
		}

		@Override
		public Set<Symbol<V>> notes() {
			return notesView;
		}

		@Override
		public boolean isClueCell() {
			return clueCell;
		}
		
		@Override
		public int hashCode() {
			return Cells.hashCode(this);
		}
		
		@Override
		public boolean deepEquals(Object obj) {
			if (obj == this) return true;
			if (!(obj instanceof Cell)) return false;
			Cell<?> _obj =  (Cell<?>)obj;
			boolean valuesEqual = (isNull(value))? _obj.value().orElse(null) == null : value.equals(_obj.value().orElse(null));
			return x == _obj.x() && y == _obj.y() && id.equals(_obj.id()) && valuesEqual && notes().equals(_obj.notes())
					&& clueCell == _obj.isClueCell();
		}
		
		@Override
		public boolean equals(Object obj) {
			return Cells.equals(this, obj);
		}
		
		@Override
		public String toString() {
			return Cells.toString(this);
		}
		
		/**
		 * This method checks if this {@code Cell} is currently a clue {@code Cell} and throws
		 * a {@link ClueCellModificationException} if this is a clue {@code Cell}. Otherwise,
		 * it returns silently.
		 */
		private void checkIsClueCell() {
			if (clueCell)
				throw new ClueCellModificationException(this);
		}
	}

	// make constructor private to prevent instantiation of this class
	private Cells() { }
}
