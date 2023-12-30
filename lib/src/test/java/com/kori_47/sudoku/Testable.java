/**
 * 
 */
package com.kori_47.sudoku;

/**
 * Represents an entity that can be tested.
 * 
 * @param <T> the type of entity to be tested.
 * 
 * @author <a href="https://github.com/kennedykori">Kennedy Kori</a>
 *
 * @since Wed, 19 Feb 2020 20:24:40
 */
public interface Testable<T> {

	/**
	 * Returns a new instance of the testable entity to run tests on.
	 * 
	 * @return a new instance of the testable entity to run tests on.
	 */
	T createValue();
}
