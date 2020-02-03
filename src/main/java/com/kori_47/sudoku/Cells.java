/**
 * 
 */
package com.kori_47.sudoku;

import static java.util.Collections.unmodifiableSet;
import static java.util.Objects.hash;
import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * This class consists exclusively of static methods that create and operate on {@link Cell}s.
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
	private static final Comparator<Cell<?>> DEFAULT_CELL_COMPARATOR = (cell1, cell2) -> Integer.compare(
				// TODO Needs further testing to ensure the comparison gives excepted results
				Integer.compare(cell1.y(), cell2.y()),
				Integer.compare(cell1.x(), cell2.x())
			);
	
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
	 */
	public static final <V> Cell<V> of(String id, int x, int y, Symbol<V> value) {
		return new SimpleCell<V>(id, x, y, value);
	}
	
	public static final Comparator<Cell<?>> defaultComparator() {
		return DEFAULT_CELL_COMPARATOR;
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
		 */
		SimpleCell(String id, int x, int y, Symbol<V> value) {
			this.id= requireNonNull(id, "id cannot be null.");
			this.x = x;
			this.y = y;
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
			changeSymbol(requireNonNull(initialValue, "initialValue cannot be null."));
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
			return hash(
					id.hashCode(), Integer.valueOf(x), Integer.valueOf(y),
					Objects.hashCode(value), notes(), Boolean.valueOf(isClueCell())
				);
		}
		
		@Override
		public boolean equals(Object obj) {
			if (obj == this) return true;
			if (!(obj instanceof Cell)) return false;
			Cell<?> _obj =  (Cell<?>)obj;
			boolean valuesEqual = (isNull(value))? _obj.value().orElse(null) == null : value.equals(_obj.value().orElse(null));
			return x == _obj.x() && y == _obj.y() && id.equals(_obj.id()) && valuesEqual && notes().equals(_obj.notes())
					&& clueCell == _obj.isClueCell();
		}
		
		@Override
		public String toString() {
			return String.format("Cell{id=%s, coord=(x:%d, y:%d), value=%s}", id, x,  y, value);
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
