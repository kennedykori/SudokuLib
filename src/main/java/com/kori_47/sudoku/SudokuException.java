/**
 * 
 */
package com.kori_47.sudoku;

/**
 * This is the root of all sudoku exceptions.
 * 
 * @author Kennedy Kori
 *
 * @since Oct 17, 2019, 3:07:59 AM
 */
public class SudokuException extends RuntimeException {

	/**
	 * SUID
	 */
	private static final long serialVersionUID = 6554249159913601862L;

	/**
	 * 
	 */
	public SudokuException() {
		super();
	}

	/**
	 * @param message
	 */
	public SudokuException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public SudokuException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public SudokuException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	protected SudokuException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
