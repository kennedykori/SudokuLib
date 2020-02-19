/**
 * 
 */
package com.kori_47.sudoku;
import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;

/**
 * This exception should be thrown by {@link Cell} implementations once they detected
 * modification of a {@link Cell#isClueCell() clue} cell.
 * 
 * @author <a href="https://github.com/kennedykori">Kennedy Kori</a>
 *
 * @since Fri, 20 Dec 2019 20:49:42
 */
public class ClueCellModificationException extends SudokuException {

	/**
	 * SUID
	 */
	private static final long serialVersionUID = -4550690207731960182L;
	
	/**
	 * the clue cell on which modification was attempted.
	 */
	private final Cell<?> clueCell; 
	
	/**
	 * Creates a new {@code ClueCellModificationException} with the given clue cell. The 
	 * {@code clueCell} is the cell on which modification was attempted and must not be 
	 * {@code null}. The detail message for this exception is generated from the given
	 * {@code clueCell}.
	 * 
	 * @param clueCell the clue cell on which modification was attempted.
	 */
	public ClueCellModificationException(Cell<?> clueCell) {
		this(clueCell, null);
	}
	
	/**
	 * Creates a new {@code ClueCellModificationException} with the given clue cell 
	 * and detail message. The {@code clueCell} is the cell on which modification was
	 * attempted and must not be {@code null}. An optional message can also be passed
	 * to be used as the detail message. If {@code null} is given as the message, then
	 * a default message is generated from the given {@code clueCell}.
	 * 
	 * @param clueCell the initial cell on which modification was attempted.
	 * @param message an optional detail message.
	 * 
	 * @throws NullPointerException if {@code intialCell} is {@code null}.
	 */
	public ClueCellModificationException(Cell<?> clueCell, String message) {
		super(nonNull(message)? message :
			String.format("You tried to modify %s which is a clue cell.", requireNonNull(clueCell, "clueCell cannot be null.")));
		this.clueCell = clueCell;
	}

	public Cell<?> getInitialCell() {
		return clueCell;
	}
}
