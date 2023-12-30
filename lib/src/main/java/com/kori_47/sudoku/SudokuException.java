/**
 * 
 */
package com.kori_47.sudoku;

/**
 * This is the root of all sudoku exceptions.
 * 
 * @author <a href="https://github.com/kennedykori">Kennedy Kori</a>
 *
 * @since Oct 17, 2019, 3:07:59 AM
 */
public class SudokuException extends RuntimeException {

	/**
	 * SUID
	 */
	private static final long serialVersionUID = 6554249159913601862L;

	/**
	 * Constructs a new {@code SudokuException} exception with null as its detail message.
	 */
	public SudokuException() {
		super();
	}

	/**
	 * Constructs a new {@code SudokuException} exception with the specified detail message.
	 * 
	 * @param message the detail message.
	 */
	public SudokuException(String message) {
		super(message);
	}

	/**
	 * Constructs a new {@code SudokuException} with the specified cause and a detail message of 
	 * (cause==null ? null : cause.toString()) (which typically contains the class and detail message of cause).
	 * This constructor is useful for runtime exceptions that are little more than wrappers for other throwables.
	 * 
	 * @param cause the cause (which is saved for later retrieval by the {@link #getCause()} method).
	 * (A null value is permitted, and indicates that the cause is nonexistent or unknown.)
	 */
	public SudokuException(Throwable cause) {
		super(cause);
	}

	/**
	 * Constructs a new {@code SudokuException} with the specified detail message and cause.
	 * <p>
	 * Note that the detail message associated with cause is not automatically incorporated in this runtime exception's
	 * detail message.
	 * </p>
	 * 
	 * @param message the detail message.
	 * @param cause the cause (which is saved for later retrieval by the {@link #getCause()} method).
	 * (A null value is permitted, and indicates that the cause is nonexistent or unknown.)
	 */
	public SudokuException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructs a new {@code SudokuException} exception with the specified detail message, cause, suppression 
	 * enabled or disabled, and writable stack trace enabled or disabled.
	 * 
	 * @param message the detail message.
	 * @param cause the cause. (A null value is permitted, and indicates that the cause is nonexistent or unknown.)
	 * @param enableSuppression whether or not suppression is enabled or disabled
	 * @param writableStackTrace whether or not the stack trace should be writable
	 */
	protected SudokuException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
