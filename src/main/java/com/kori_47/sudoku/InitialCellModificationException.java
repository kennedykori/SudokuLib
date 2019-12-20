/**
 * 
 */
package com.kori_47.sudoku;
import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;

/**
 * This exception should be thrown by {@link Cell} implementations once they detected
 * modification of an initial cell.
 * 
 * @author <a href="https://github.com/kennedykori">Kennedy Kori</a>
 *
 * @since Fri, 20 Dec 2019 20:49:42
 */
public class InitialCellModificationException extends SudokuException {

	/**
	 * SUID
	 */
	private static final long serialVersionUID = -4550690207731960182L;
	
	/**
	 * the initial cell on which modification was attempted.
	 */
	private final Cell<?> initialCell; 
	
	/**
	 * Creates a new {@code InitialCellModificationException} with the given initial cell. The 
	 * {@code initialCell} is the cell on which modification was attempted and must not be 
	 * {@code null}. The detail message for this exception is generated from the given {@code initialCell}.
	 * 
	 * @param initialCell the initial cell on which modification was attempted.
	 */
	public InitialCellModificationException(Cell<?> initialCell) {
		this(initialCell, null);
	}
	
	/**
	 * Creates a new {@code InitialCellModificationException} with the given initial cell 
	 * and detail message. The {@code initialCell} is the cell on which modification was attempted
	 * and must not be {@code null}. An optional message can also be passed to be used as the detail
	 * message. If {@code null} is given as the message, then a default message is generated from the
	 * given {@code initialCell}.
	 * 
	 * @param initialCell the initial cell on which modification was attempted.
	 * @param message an optional detail message.
	 * 
	 * @throws NullPointerException if {@code intialCell} is {@code null}.
	 */
	public InitialCellModificationException(Cell<?> initialCell, String message) {
		super(nonNull(message)? message :
			String.format("You tried to modify %s which is an initial cell.", requireNonNull(initialCell, "initialCell cannot be null.")));
		this.initialCell = initialCell;
	}

	public Cell<?> getInitialCell() {
		return initialCell;
	}
}
